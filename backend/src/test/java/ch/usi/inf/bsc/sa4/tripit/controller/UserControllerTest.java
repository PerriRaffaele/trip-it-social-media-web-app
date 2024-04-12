package ch.usi.inf.bsc.sa4.tripit.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.UserDTO;
import ch.usi.inf.bsc.sa4.tripit.model.FakeUser;
import ch.usi.inf.bsc.sa4.tripit.model.Friendship;
import ch.usi.inf.bsc.sa4.tripit.model.User;
import ch.usi.inf.bsc.sa4.tripit.service.JourneyService;
import ch.usi.inf.bsc.sa4.tripit.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("The User Controller Test")
public class UserControllerTest {
  @MockBean private UserService userService;
  @MockBean private JourneyService journeyService;
  //  @MockBean private OAuth2AuthorizedClientService authorizedClientService;
  //  @MockBean private FriendshipService friendshipService;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private static User user1, user2, user3;
  private static UserDTO userDTO1, userDTO2;
  private static List<User> users;
  private static List<UserDTO> userDTOS;
  private static Friendship friendship;

  @BeforeAll
  public static void dataSetUp() {
    user1 = new User("1", "Gio", "BIO", "B@usi.ch", "Bonetto");
    user2 = new User("2", "Hun", "BIO1", "H@usi.ch", "TKartist");
    user3 = new User("3", "Hun", "BIO1", "H@usi.ch", "TKartist");
    user3.makePrivate();
    userDTO1 = user1.toUserDTO();
    userDTO2 = user2.toUserDTO();
    users = new ArrayList<User>();
    users.add(user1);
    users.add(user2);
    friendship = new Friendship(user1, user2);
  }

  @BeforeEach
  void setUp() throws JsonProcessingException {
    given(userService.getAllPublic()).willReturn(users);
    given(userService.createUser(any())).willReturn(user1);
    given(userService.searchByNameStartingWith("val")).willReturn(users);
    given(userService.searchByMailStartingWith("val")).willReturn(users);
    given(userService.getById("w1")).willReturn(Optional.empty());
    given(userService.getById("1")).willReturn(Optional.of(user1));
    given(userService.getById("2")).willReturn(Optional.of(user2));
    given(userService.changePrivacy("w1")).willReturn(Optional.empty());
    given(userService.changePrivacy("1")).willReturn(Optional.of(user1));
    given(userService.updateUser(any())).willReturn(user1);
    given(userService.getByGitId("gw1")).willReturn(Optional.empty());
    given(userService.getByGitId("g1")).willReturn(Optional.of(user1));
    Mockito.doNothing().when(userService).deleteUser(any());
    //    given(userService.getById("3")).willReturn(Optional.of(user3));
    //    given(userService.getUserByToken(any())).willReturn(user2);
    given(journeyService.countNumberOfJourneys(any())).willReturn(1);
    given(journeyService.countTotalNumberOfActivitiesForUser(any())).willReturn(3);
    //    given(friendshipService.getFriendshipByRecAndSend(user1,
    // user2)).willReturn(Optional.of(friendship));
    //    given(friendshipService.getFriendshipByRecAndSend(user2,
    // user1)).willReturn(Optional.of(friendship));
    //    given(friendshipService.getFriendshipByRecAndSend(user3,
    // user2)).willReturn(Optional.empty());
    //    given(friendshipService.getFriendshipByRecAndSend(user2,
    // user3)).willReturn(Optional.empty());
  }

  @Test
  public void testGetAll() throws Exception {
    FakeUser fakeUser = new FakeUser("Gio", "Gio", "Bonetto", "none");
    MvcResult result =
        this.mockMvc
            .perform(
                get("/users/all")
                    .with(SecurityMockMvcRequestPostProcessors.authentication(fakeUser.getToken())))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    List<UserDTO> userDTOS1 =
        objectMapper.readValue(response, new TypeReference<List<UserDTO>>() {});
    assertNotNull(userDTOS1);
  }

  @Test
  public void testAddUser() throws Exception {
    FakeUser fakeUser1 = new FakeUser("name", "gitid", "username", "pic");
    JSONObject object = new JSONObject();
    object.put("name", "gitname");
    object.put("id", "id01");
    object.put("bio", "bio");
    object.put("email", "git@git.com");
    object.put("avatar_url", "url");
    object.put("username", "uname");
    String json = object.toString();
    MvcResult result =
        this.mockMvc
            .perform(
                post("/users")
                    .with(SecurityMockMvcRequestPostProcessors.authentication(fakeUser1.getToken()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    UserDTO userDTO = objectMapper.readValue(response, UserDTO.class);
    assertNotNull(userDTO);
  }

  @Test
  public void testSearch() throws Exception {
    FakeUser fakeUser1 = new FakeUser("name", "gitid", "username", "pic");
    MvcResult result =
        this.mockMvc
            .perform(
                get("/users/search")
                    .with(SecurityMockMvcRequestPostProcessors.authentication(fakeUser1.getToken()))
                    .param("partialString", "val"))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    List<UserDTO> userDTOS1 =
        objectMapper.readValue(response, new TypeReference<List<UserDTO>>() {});
    assertNotNull(userDTOS1);
  }

  //  @Test
  //  public void testGetById() throws Exception {
  //    FakeUser fakeUser1 = new FakeUser("name", "gitid", "username", "pic");
  //    OAuth2AuthorizedClient client = new OAuth2AuthorizedClient(
  //            , "name", null
  //    );
  //    given(authorizedClientService.loadAuthorizedClient(any(), any())).willReturn(client);
  //    this.mockMvc
  //            .perform(
  //                    get("/users/w1")
  //                            .with(SecurityMockMvcRequestPostProcessors
  //                                    .authentication(fakeUser1.getToken())))
  //            .andExpect(status().isNotFound());
  //    MvcResult result = this.mockMvc
  //            .perform(
  //                    get("/users/1")
  //                            .with(SecurityMockMvcRequestPostProcessors
  //                                    .authentication(fakeUser1.getToken())))
  //            .andExpect(status().isOk())
  //            .andReturn();
  //    this.mockMvc
  //            .perform(
  //                    get("/users/3")
  //                            .with(SecurityMockMvcRequestPostProcessors
  //                                    .authentication(fakeUser1.getToken())))
  //            .andExpect(status().isForbidden());
  //  }

  @Test
  public void testChangePrivacy() throws Exception {
    FakeUser fakeUser1 = new FakeUser("name", "gitid", "username", "pic");
    this.mockMvc
        .perform(
            put("/users/w1/privacy")
                .with(SecurityMockMvcRequestPostProcessors.authentication(fakeUser1.getToken())))
        .andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc
            .perform(
                put("/users/1/privacy")
                    .with(
                        SecurityMockMvcRequestPostProcessors.authentication(fakeUser1.getToken())))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    UserDTO userDTO = objectMapper.readValue(response, UserDTO.class);
    assertNotNull(userDTO);
  }

  @Test
  public void testDeleteUser() throws Exception {
    FakeUser fakeUser1 = new FakeUser("name", "gitid", "username", "pic");
    this.mockMvc
        .perform(
            delete("/users/w1")
                .with(SecurityMockMvcRequestPostProcessors.authentication(fakeUser1.getToken())))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(
            delete("/users/1")
                .with(SecurityMockMvcRequestPostProcessors.authentication(fakeUser1.getToken())))
        .andExpect(status().isOk());
  }

  @Test
  public void testIsLogged() throws Exception {
    FakeUser fakeUser1 = new FakeUser("name", "gitid", "username", "pic");
    this.mockMvc.perform(get("/users/is-logged")).andExpect(status().isForbidden());
    this.mockMvc
        .perform(
            get("/users/is-logged")
                .with(SecurityMockMvcRequestPostProcessors.authentication(fakeUser1.getToken())))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetByGitId() throws Exception {
    this.mockMvc.perform(get("/users/gw1/gitlab")).andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc.perform(get("/users/g1/gitlab")).andExpect(status().isOk()).andReturn();
    String response = result.getResponse().getContentAsString();
    UserDTO userDTO = objectMapper.readValue(response, UserDTO.class);
    assertNotNull(userDTO);
  }
}
