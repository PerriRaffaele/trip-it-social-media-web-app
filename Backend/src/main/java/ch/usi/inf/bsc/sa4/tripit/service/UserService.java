package ch.usi.inf.bsc.sa4.tripit.service;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.CreateUserDTO;
import ch.usi.inf.bsc.sa4.tripit.model.User;
import ch.usi.inf.bsc.sa4.tripit.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final WebClient webClient = WebClient.create("http://localhost:8080");
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAll() {
    return this.userRepository.findAll();
  }

  public Optional<User> getById(String userId) {
    return userRepository.findById(userId);
  }

  public Optional<User> getByGitId(String userId) {
    return userRepository.findByGitlabId(userId);
  }

  public List<User> searchByNameContaining(String partialName) {
    var users = userRepository.findAllByNameContaining(partialName);
    users.removeIf(user -> !user.isPublic());
    return users;
  }

  public List<User> searchByNameStartingWith(String partialName) {
    var users = userRepository.findAllByNameStartingWith(partialName);
    users.removeIf(user -> !user.isPublic());
    return users;
  }

  public List<User> searchByMailStartingWith(String partialMail) {
    var users = userRepository.findAllByEmailStartingWith(partialMail);
    users.removeIf(user -> !user.isPublic());
    return users;
  }

  public List<User> getAllPublic() {
    return userRepository.findAllByIsPublic(true);
  }

  public User createUser(CreateUserDTO createUserDTO) {
    var user =
        new User(
            createUserDTO.getName(),
            createUserDTO.getGitlabId(),
            createUserDTO.getBio(),
            createUserDTO.getEmail(),
            createUserDTO.getPicturePath(),
            createUserDTO.getUsername());
    return userRepository.save(user);
  }

  public User updateUser(User user) {
    return userRepository.save(user);
  }

  public void deleteUser(User user) {
    userRepository.delete(user);
  }

  public Optional<User> changePrivacy(String userId) {
    var optionalUser = this.getById(userId);
    if (optionalUser.isEmpty()) {
      return Optional.empty();
    } else {
      var user = optionalUser.get();
      user.changePrivacy();
      return Optional.of(this.updateUser(user));
    }
  }

  public User getUserByToken(String token) throws JsonProcessingException {
    String json =
        webClient
            .get()
            .uri("https://gitlab.com/api/v4/user")
            .header("Authorization", "Bearer " + token)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    JsonNode jsonNode = objectMapper.readTree(json);
    String gitLabId = jsonNode.get("id").asText();
    Optional<User> optionalUser = userRepository.findByGitlabId(gitLabId);
    if (optionalUser.isEmpty()) {
      CreateUserDTO createUserDTO = objectMapper.readValue(json, CreateUserDTO.class);
      return this.createUser(createUserDTO);
    } else {
      return optionalUser.get();
    }
  }
}
