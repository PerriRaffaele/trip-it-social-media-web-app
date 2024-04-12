package ch.usi.inf.bsc.sa4.tripit.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.FriendshipDTO;
import ch.usi.inf.bsc.sa4.tripit.model.*;
import ch.usi.inf.bsc.sa4.tripit.service.ActivityService;
import ch.usi.inf.bsc.sa4.tripit.service.FriendshipService;
import ch.usi.inf.bsc.sa4.tripit.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("The Friendship controller")
public class FriendshipControllerTest {

  @MockBean private UserService userService;

  @MockBean private FriendshipService friendshipService;

  @MockBean private ActivityService activityService;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;
  private static User rec, send, user2, nonFrUser;

  private static List<Friendship> friendsOfUser1;

  private static Friendship fr, newFr;

  @BeforeAll
  public static void dataSetUp() {
    send = new User("1", "Gio", "BIO", "B@usi.ch", "Bonetto");
    rec = new User("2", "Hun", "BIO1", "H@usi.ch", "TKartist");
    user2 = new User("3", "Mia0", "BIO00", "Miao@usi.ch", "SuperMiao");
    nonFrUser = new User("4", "NonEx", "BIO00", "Non@usi.ch", "SuperSad");
    User user3 = new User("20", "NonEasdx", "BIO00", "Nonsa@usi.ch", "SuperSad");
    User user4 = new User("21", "NonEasdx", "BIO00", "Nonsa@usi.ch", "SuperSad");
    fr = new Friendship(FriendshipControllerTest.send, FriendshipControllerTest.rec);
    newFr = fr;
    newFr.setMuteById(rec.getId());
    Friendship fr2 = new Friendship(user2, send);
    Friendship fr3 = new Friendship(send, user3);
    Friendship fr4 = new Friendship(user4, send);
    Friendship fr5 =
        new Friendship(
            new User("21", "NonEasdx", "BIO00", "Nonsa@usi.ch", "SuperSad"), true, send, false);
    Friendship fr6 =
        new Friendship(
            send, true, new User("22", "NonEasdx", "BIO00", "Nonsa@usi.ch", "SuperSad"), false);
    Friendship fr7 =
        new Friendship(
            new User("23", "NonEasdx", "BIO00", "Nonsa@usi.ch", "SuperSad"), false, send, true);
    fr7.setMuteById(send.getId());
    friendsOfUser1 = new ArrayList<Friendship>();
    friendsOfUser1.add(fr);
    friendsOfUser1.add(fr2);
    friendsOfUser1.add(fr3);
    friendsOfUser1.add(fr4);
    friendsOfUser1.add(fr5);
    friendsOfUser1.add(fr6);
    friendsOfUser1.add(fr7);
  }

  @BeforeEach
  void setUp() throws JsonProcessingException {
    given(userService.getById("1")).willReturn(Optional.of(send));
    given(userService.getById("2")).willReturn(Optional.of(rec));
    given(userService.getById("3")).willReturn(Optional.of(user2));
    given(friendshipService.getFriendshipByRecAndSend(rec, send)).willReturn(Optional.of(fr));
    given(friendshipService.getFriendshipByRecAndSend(send, rec)).willReturn(Optional.of(fr));
    given(friendshipService.getFriendshipByRecAndSend(rec, user2)).willReturn(Optional.empty());
    given(friendshipService.getAllFriendsByUser(send)).willReturn(friendsOfUser1);
    given(friendshipService.updateFriendShip(newFr)).willReturn(newFr);
  }

  @Test
  @DisplayName(
      "method get friendship a non existing friendship and an existing one, with rec and send")
  public void testGetFriendshipByRecAndSend() throws Exception {
    this.mockMvc.perform(get("/friends/5/1")).andExpect(status().isNotFound());
    this.mockMvc.perform(get("/friends/1/5")).andExpect(status().isNotFound());
    this.mockMvc.perform(get("/friends/6/5")).andExpect(status().isNotFound());
    this.mockMvc.perform(get("/friends/2/3")).andExpect(status().isNotFound());
    this.mockMvc
        .perform(get("/friends/{reqId}/{recId}", null, null))
        .andExpect(status().is4xxClientError());
    this.mockMvc
        .perform(get("/friends/{reqId}/{recId}", "1", null))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(get("/friends/{reqId}/{recId}", null, "1"))
        .andExpect(status().is4xxClientError());
    MvcResult result =
        this.mockMvc
            .perform(get("/friends/1/2"))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = result.getResponse().getContentAsString();
    FriendshipDTO fr = objectMapper.readValue(content, FriendshipDTO.class);
    assertEquals("1", fr.getRequestSender().getId());
  }

  @Test
  @DisplayName("method delete existing friendship, and nonexisting")
  public void testUnfollowFriend() throws Exception {
    this.mockMvc.perform(delete("/friends/1/2")).andExpect(status().isOk());
    this.mockMvc.perform(delete("/friends/2/1")).andExpect(status().isOk());
    this.mockMvc.perform(delete("/friends/5/2")).andExpect(status().isNotFound());
    this.mockMvc.perform(delete("/friends/2/5")).andExpect(status().isNotFound());
    this.mockMvc.perform(delete("/friends/2/3")).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("method getAllFriends By User existing friendship, and nonexisting")
  public void testGetAllFriendsByUser() throws Exception {
    this.mockMvc.perform(get("/friends/4")).andExpect(status().isNotFound());
    MvcResult res =
        this.mockMvc
            .perform(get("/friends/1"))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = res.getResponse().getContentAsString();
    List<User> friends = objectMapper.readValue(content, new TypeReference<List<User>>() {});
    assertEquals(7, friends.size());
  }

  @Test
  @DisplayName("method getAllFriends By User existing friendship, and nonexisting")
  public void testGetAllUnmutedFriends() throws Exception {
    this.mockMvc.perform(get("/friends/4/unmuted")).andExpect(status().isNotFound());
    MvcResult res =
        this.mockMvc
            .perform(get("/friends/1/unmuted"))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = res.getResponse().getContentAsString();
    List<User> friends = objectMapper.readValue(content, new TypeReference<List<User>>() {});
    assertEquals(5, friends.size());
  }

  @Test
  @DisplayName("method getAllFriends By User existing friendship, and nonexisting")
  public void testMuteFriend() throws Exception {
    this.mockMvc.perform(put("/friends/4/1/muteOrUnmute")).andExpect(status().isNotFound());
    this.mockMvc.perform(put("/friends/1/4/muteOrUnmute")).andExpect(status().isNotFound());
    this.mockMvc.perform(put("/friends/2/3/muteOrUnmute")).andExpect(status().isNotFound());
    MvcResult res =
        this.mockMvc
            .perform(put("/friends/2/1/muteOrUnmute"))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = res.getResponse().getContentAsString();
    FriendshipDTO friends = objectMapper.readValue(content, FriendshipDTO.class);
    assertTrue(friends.isSenderMuted());
  }

  @Test
  @DisplayName("method getAllFriends By User existing friendship, and nonexisting")
  public void testIsMutedFriends() throws Exception {
    this.mockMvc.perform(get("/friends/3/1/isMuted")).andExpect(status().isNotFound());
    this.mockMvc.perform(get("/friends/1/3/isMuted")).andExpect(status().isNotFound());
    this.mockMvc.perform(put("/friends/4/1/muteOrUnmute")).andExpect(status().isNotFound());
    this.mockMvc.perform(put("/friends/1/4/muteOrUnmute")).andExpect(status().isNotFound());
    MvcResult res =
        this.mockMvc.perform(get("/friends/1/2/isMuted")).andExpect(status().isOk()).andReturn();
    String content = res.getResponse().getContentAsString();
    Boolean isMuted = objectMapper.readValue(content, Boolean.class);
    assertTrue(isMuted);
  }
}
