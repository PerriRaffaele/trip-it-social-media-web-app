package ch.usi.inf.bsc.sa4.tripit.controller;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.CreateUserDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.UserDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Friendship;
import ch.usi.inf.bsc.sa4.tripit.model.User;
import ch.usi.inf.bsc.sa4.tripit.service.FriendshipService;
import ch.usi.inf.bsc.sa4.tripit.service.JourneyService;
import ch.usi.inf.bsc.sa4.tripit.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;
  private final JourneyService journeyService;
  private final OAuth2AuthorizedClientService authorizedClientService;

  private final FriendshipService friendshipService;

  @Autowired
  public UserController(
      UserService userService,
      JourneyService journeyService,
      OAuth2AuthorizedClientService authorizedClientService,
      FriendshipService friendshipService) {
    this.userService = userService;
    this.journeyService = journeyService;
    this.authorizedClientService = authorizedClientService;
    this.friendshipService = friendshipService;
  }

  // sets up the statistics of the given user

  /**
   * Sets the statistics of the given user into a UserDTO
   *
   * @param unboxedUser the user providing the statistics
   * @return a UserDTO of the given user with statistics about number of journeys and activities
   */
  private UserDTO setStatisticsInUserDTO(User unboxedUser) {
    UserDTO unboxedUserDTO = unboxedUser.toUserDTO();
    int numOfJourneys = journeyService.countNumberOfJourneys(unboxedUser);
    int totalNumberOfActivities = journeyService.countTotalNumberOfActivitiesForUser(unboxedUser);
    unboxedUserDTO.setNumberOfJourneys(numOfJourneys);
    unboxedUserDTO.setTotalNumberOfActivities(totalNumberOfActivities);
    return unboxedUserDTO;
  }

  @GetMapping("/all")
  public ResponseEntity<List<UserDTO>> getAll() {
    var userDTOs = new ArrayList<UserDTO>();

    for (var user : userService.getAllPublic()) {
      userDTOs.add(setStatisticsInUserDTO(user));
    }
    return ResponseEntity.ok(userDTOs);
  }

  @PostMapping
  public ResponseEntity<UserDTO> addUser(@RequestBody CreateUserDTO createUserDTO) {
    var savedUser = userService.createUser(createUserDTO);
    return ResponseEntity.ok(savedUser.toUserDTO());
  }

  @GetMapping("/search")
  public ResponseEntity<List<UserDTO>> getByNameContaining(
      @RequestParam("partialString") String partialString) {
    var allNameUserDTOs =
        userService.searchByNameStartingWith(partialString).stream()
            .map(User::toUserDTO)
            .collect(Collectors.toList());

    var allMailUserDTOs =
        userService.searchByMailStartingWith(partialString).stream()
            .map(User::toUserDTO)
            .collect(Collectors.toList());

    allNameUserDTOs.addAll(allMailUserDTOs);

    HashSet<Object> seen = new HashSet<>();
    allNameUserDTOs.removeIf(e -> !seen.add(e.getId()));

    return ResponseEntity.ok(allNameUserDTOs);
  }

  // refactored code of getById to fix code smell (too many return statements)
  /**
   * Checks if it should be forbidden to pass the given User in a get request, that is if the user
   * is another private user and not one of my friends
   *
   * @param unboxedUser the user to be checked
   * @param authentication the authentication of the user
   * @return true if it's forbidden to pass the User in a get request, false if it is allowed
   */
  public boolean isForbiddenGetUser(User unboxedUser, OAuth2AuthenticationToken authentication)
      throws JsonProcessingException {

    if (authentication != null) {
      OAuth2AuthorizedClient client =
          authorizedClientService.loadAuthorizedClient(
              authentication.getAuthorizedClientRegistrationId(), authentication.getName());
      String accessToken = client.getAccessToken().getTokenValue();
      User u = userService.getUserByToken(accessToken);

      Optional<Friendship> fr = friendshipService.getFriendshipByRecAndSend(unboxedUser, u);
      Optional<Friendship> fr1 = friendshipService.getFriendshipByRecAndSend(u, unboxedUser);

      return !unboxedUser.isPublic()
          && !unboxedUser.getId().equals(u.getId())
          && !fr.isPresent()
          && !fr1.isPresent();
    }
    return false;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getById(
      @PathVariable("id") String id, OAuth2AuthenticationToken authentication)
      throws JsonProcessingException {
    var optionalUser = userService.getById(id);
    if (optionalUser.isPresent()) {
      User unboxedUser = optionalUser.get();
      UserDTO unboxedUserDTO = setStatisticsInUserDTO(unboxedUser);
      if (isForbiddenGetUser(unboxedUser, authentication)) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
      } else {
        return ResponseEntity.ok(unboxedUserDTO);
      }
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}/privacy")
  public ResponseEntity<UserDTO> changePrivacy(@PathVariable("id") String id) {
    var optionalUser = userService.changePrivacy(id);
    if (optionalUser.isPresent()) {
      userService.updateUser(optionalUser.get());
    }
    return optionalUser
        .map(user -> ResponseEntity.ok(user.toUserDTO()))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
    var optionalUser = userService.getById(id);
    if (optionalUser.isPresent()) {
      userService.deleteUser(optionalUser.get());
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/is-logged")
  public ResponseEntity<?> isLogged(OAuth2AuthenticationToken authentication) {
    if (authentication == null) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/getUser")
  public ResponseEntity<UserDTO> test(OAuth2AuthenticationToken authentication)
      throws JsonProcessingException {
    if (authentication == null) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    OAuth2AuthorizedClient client =
        authorizedClientService.loadAuthorizedClient(
            authentication.getAuthorizedClientRegistrationId(), authentication.getName());
    String accessToken = client.getAccessToken().getTokenValue();
    User u = userService.getUserByToken(accessToken);
    UserDTO userDTO = setStatisticsInUserDTO(u);
    return ResponseEntity.ok(userDTO);
  }

  @GetMapping("/token")
  public String getToken(OAuth2AuthenticationToken authentication) {
    OAuth2AuthorizedClient client =
        authorizedClientService.loadAuthorizedClient(
            authentication.getAuthorizedClientRegistrationId(), authentication.getName());
    if (client == null) {
      return null;
    }
    return client.getAccessToken().getTokenValue();
  }

  @GetMapping("/{id}/gitlab")
  public ResponseEntity<UserDTO> getByGitlabId(@PathVariable("id") String id) {
    var optionalUser = userService.getByGitId(id);
    return optionalUser
        .map(user -> ResponseEntity.ok(user.toUserDTO()))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
