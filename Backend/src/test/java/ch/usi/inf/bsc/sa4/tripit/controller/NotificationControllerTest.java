package ch.usi.inf.bsc.sa4.tripit.controller;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.*;
import ch.usi.inf.bsc.sa4.tripit.model.*;
import ch.usi.inf.bsc.sa4.tripit.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("The Notification Controller Test")
public class NotificationControllerTest {

  @MockBean private NotificationService notificationService;
  @MockBean private FriendshipService friendshipService;
  @MockBean private JourneyService journeyService;
  @MockBean private UserService userService;
  @MockBean private ActivityService activityService;
  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private static User from, to;
  private static Journey journey;
  private static Activity activity;
  private static Friendship newFriendShip;
  private static NotificationDTO notificationDTO;
  private static Notification friendRequest;
  private static NotificationType friend;
  private static NotificationJourneyDTO notificationJourneyDTO;
  private static NotificationActivityDTO notificationActivityDTO;
  private static NotificationType newLike, sad, smile, angry;
  private static NotificationJourney notificationJourney;
  private static NotificationActivity notificationActivity;
  private static ZonedDateTime start, end;
  private static ZoneId zone;
  private static List<Notification> notifications;
  private static List<NotificationJourney> notificationJourneys;
  private static List<NotificationActivity> notificationActivities;
  private static Image image;

  @BeforeAll
  public static void dataSetUp() {
    image = new Image("skmfke", 100, 100, "desc", "url", "cropping");
    from = new User("u1", "Anne");
    to = new User("u2", "Lay");
    notificationDTO = new NotificationDTO("n1", "u1", "u2", "request", "friendrequest");
    journey = new Journey("myJourney1", to, image);
    zone = ZoneId.of("America/Los_Angeles");
    // Define the start and end date-time
    start = ZonedDateTime.of(2023, 3, 23, 9, 0, 0, 0, zone);
    end = ZonedDateTime.of(2023, 3, 23, 17, 0, 0, 0, zone);
    activity =
        new Visit(
            null, start, end, "title", "description", JourneyControllerTest.attraction, image);
    activity.setJourney(journey);
    friend = NotificationType.FRIEND_REQUEST;
    newLike = NotificationType.LIKE;
    sad = NotificationType.SAD_FACE;
    smile = NotificationType.SMILY_FACE;
    angry = NotificationType.ANGRY_FACE;
    friendRequest = new Notification(from, to, friend);
    notificationJourneyDTO =
        new NotificationJourneyDTO("n2", "u1", "u2", "request", "journey", "j1");
    notificationActivityDTO =
        new NotificationActivityDTO("n3", "u1", "u2", "request", "activity", "j1", "a1");
    notificationJourney = new NotificationJourney(from, to, newLike, journey);
    notificationActivity = new NotificationActivity(from, to, newLike, journey, activity);
    notifications = new ArrayList<Notification>();
    notifications.add(friendRequest);
    notifications.add(notificationJourney);
    notificationJourneys = new ArrayList<NotificationJourney>();
    notificationJourneys.add(notificationJourney);
    newFriendShip = new Friendship(from, to);
    notificationActivities = new ArrayList<NotificationActivity>();
    notificationActivities.add(notificationActivity);
  }

  @BeforeEach
  void setUp() throws JsonProcessingException {
    given(userService.getById("u1")).willReturn(Optional.of(from));
    given(userService.getById("u2")).willReturn(Optional.of(to));
    given(userService.getById("w2")).willReturn(Optional.empty());
    given(userService.getById("w1")).willReturn(Optional.empty());
    given(journeyService.getById("j1")).willReturn(Optional.of(journey));
    given(journeyService.getById("jw1")).willReturn(Optional.empty());
    given(activityService.getById("aw1")).willReturn(Optional.empty());
    given(activityService.getById("a1")).willReturn(Optional.of(activity));
    given(notificationService.createNotificationFriend(any(), any(), any()))
        .willReturn(friendRequest);
    given(notificationService.createNotificationJourney(any(), any(), any(), any()))
        .willReturn(notificationJourney);
    given(notificationService.createNotificationActivity(any(), any(), any(), any(), any()))
        .willReturn(notificationActivity);
    given(notificationService.getUserNotifications(any())).willReturn(notifications);
    given(notificationService.getFromNotificationJourney(any(), any(), any()))
        .willReturn(notificationJourneys);
    given(notificationService.getJourneyLikes(any())).willReturn(notificationJourneys);
    given(notificationService.getById("nw1")).willReturn(Optional.empty());
    given(notificationService.getById("n1")).willReturn(Optional.of(notificationJourney));
    given(notificationService.getActivityLikes(any(), any())).willReturn(notificationActivities);
    Mockito.doNothing().when(notificationService).deleteNotification(any());
    Mockito.doNothing().when(notificationService).updateNotification(any());
    given(friendshipService.updateFriendShip(any())).willReturn(newFriendShip);
    given(friendshipService.getFriendshipByRecAndSend(any(), any())).willReturn(Optional.empty());
  }

  @Test
  public void testPostFriendRequest() throws Exception {
    NotificationDTO wrongNotification =
        new NotificationDTO("n2", "u1", "w2", "request", "friendrequest");
    NotificationDTO wrongNotification2 =
        new NotificationDTO("n2", "w1", "u2", "request", "friendrequest");
    String wrong1 = objectMapper.writeValueAsString(wrongNotification);
    this.mockMvc
        .perform(
            post("/notification/friend").contentType(MediaType.APPLICATION_JSON).content(wrong1))
        .andExpect(status().isNotFound());
    String wrong2 = objectMapper.writeValueAsString(wrongNotification2);
    this.mockMvc
        .perform(
            post("/notification/friend").contentType(MediaType.APPLICATION_JSON).content(wrong2))
        .andExpect(status().isNotFound());
    String correct = objectMapper.writeValueAsString(notificationDTO);
    MvcResult result =
        this.mockMvc
            .perform(
                post("/notification/friend")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(correct))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    NotificationDTO nDTO = objectMapper.readValue(response, NotificationDTO.class);
    assertEquals(nDTO.getFrom(), friendRequest.getUserFrom().getId());
  }

  @Test
  public void testPostJourney() throws Exception {
    NotificationJourneyDTO wrongNotification =
        new NotificationJourneyDTO("n2", "u1", "w2", "request", "friendrequest", "j1");
    NotificationJourneyDTO wrongNotification2 =
        new NotificationJourneyDTO("n2", "w1", "u2", "request", "friendrequest", "j1");
    NotificationJourneyDTO wrongNotification3 =
        new NotificationJourneyDTO("n2", "u1", "u2", "request", "friendrequest", "jw1");
    String wrongJ1 = objectMapper.writeValueAsString(wrongNotification);
    String wrongJ2 = objectMapper.writeValueAsString(wrongNotification2);
    String wrongJ3 = objectMapper.writeValueAsString(wrongNotification3);
    this.mockMvc
        .perform(
            post("/notification/journey").contentType(MediaType.APPLICATION_JSON).content(wrongJ1))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(
            post("/notification/journey").contentType(MediaType.APPLICATION_JSON).content(wrongJ2))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(
            post("/notification/journey").contentType(MediaType.APPLICATION_JSON).content(wrongJ3))
        .andExpect(status().isNotFound());
    String correct = objectMapper.writeValueAsString(notificationJourneyDTO);
    MvcResult result =
        this.mockMvc
            .perform(
                post("/notification/journey")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(correct))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    NotificationJourneyDTO output = objectMapper.readValue(response, NotificationJourneyDTO.class);
    assertEquals(output.getTo(), notificationJourney.getUserTo().getId());
  }

  @Test
  public void testPostActivity() throws Exception {
    NotificationActivityDTO wrongActivity1 =
        new NotificationActivityDTO("n3", "w1", "u2", "request", "activity", "j1", "a1");
    NotificationActivityDTO wrongActivity2 =
        new NotificationActivityDTO("n3", "u1", "w2", "request", "activity", "j1", "a1");
    NotificationActivityDTO wrongActivity3 =
        new NotificationActivityDTO("n3", "u1", "u2", "request", "activity", "jw1", "a1");
    NotificationActivityDTO wrongActivity4 =
        new NotificationActivityDTO("n3", "u1", "u2", "request", "activity", "j1", "aw1");
    String wJson1 = objectMapper.writeValueAsString(wrongActivity1);
    this.mockMvc
        .perform(
            post("/notification/activity").contentType(MediaType.APPLICATION_JSON).content(wJson1))
        .andExpect(status().isNotFound());
    String wJson2 = objectMapper.writeValueAsString(wrongActivity2);
    this.mockMvc
        .perform(
            post("/notification/activity").contentType(MediaType.APPLICATION_JSON).content(wJson2))
        .andExpect(status().isNotFound());
    String wJson3 = objectMapper.writeValueAsString(wrongActivity3);
    this.mockMvc
        .perform(
            post("/notification/activity").contentType(MediaType.APPLICATION_JSON).content(wJson3))
        .andExpect(status().isNotFound());
    String wJson4 = objectMapper.writeValueAsString(wrongActivity4);
    this.mockMvc
        .perform(
            post("/notification/activity").contentType(MediaType.APPLICATION_JSON).content(wJson4))
        .andExpect(status().isNotFound());
    String correct = objectMapper.writeValueAsString(notificationActivityDTO);
    MvcResult result =
        this.mockMvc
            .perform(
                post("/notification/activity")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(correct))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    NotificationActivityDTO output =
        objectMapper.readValue(response, NotificationActivityDTO.class);
    assertEquals(output.getTo(), notificationActivityDTO.getTo());
  }

  @Test
  public void testGetUserNotification() throws Exception {
    this.mockMvc.perform(get("/notification/w1")).andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc.perform(get("/notification/u1")).andExpect(status().isOk()).andReturn();
    String response = result.getResponse().getContentAsString();
    List<NotificationDTO> notificationDTOList =
        objectMapper.readValue(response, new TypeReference<List<NotificationDTO>>() {});

    given(friendshipService.getFriendshipByRecAndSend(any(), any()))
        .willReturn(Optional.of(newFriendShip));
    result = this.mockMvc.perform(get("/notification/u1")).andExpect(status().isOk()).andReturn();
    response = result.getResponse().getContentAsString();
    notificationDTOList =
        objectMapper.readValue(response, new TypeReference<List<NotificationDTO>>() {});
    assertEquals(notificationDTOList.size(), 2);
  }

  @Test
  public void testGetLikedJourney() throws Exception {
    this.mockMvc.perform(get("/notification/journey/w1/u2/j1")).andExpect(status().isNotFound());
    this.mockMvc.perform(get("/notification/journey/u1/w2/j1")).andExpect(status().isNotFound());
    this.mockMvc.perform(get("/notification/journey/u1/u2/jw1")).andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc
            .perform(get("/notification/journey/u1/u2/j1"))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    List<NotificationDTO> output =
        objectMapper.readValue(response, new TypeReference<List<NotificationDTO>>() {});
    assertEquals(output.size(), 1);
  }

  @Test
  public void testDeleteNotification() throws Exception {
    this.mockMvc.perform(delete("/notification/nw1")).andExpect(status().isNotFound());
    this.mockMvc.perform(delete("/notification/n1")).andExpect(status().isOk());
  }

  @Test
  public void testHideNotification() throws Exception {
    this.mockMvc.perform(put("/notification/hide/nw1")).andExpect(status().isNotFound());
    this.mockMvc.perform(put("/notification/hide/n1")).andExpect(status().isOk());
  }

  @Test
  public void testAcceptFriend() throws Exception {
    NotificationDTO wrongNotification =
        new NotificationDTO("nw1", "u1", "w2", "request", "friendrequest");
    String jsonWrong = objectMapper.writeValueAsString(wrongNotification);
    this.mockMvc
        .perform(
            post("/notification/acceptFriendRequest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWrong))
        .andExpect(status().isNotFound());
    NotificationDTO wrongNotification1 =
        new NotificationDTO("n1", "w1", "u2", "request", "friendrequest");
    String jsonWrong1 = objectMapper.writeValueAsString(wrongNotification1);
    this.mockMvc
        .perform(
            post("/notification/acceptFriendRequest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWrong1))
        .andExpect(status().isNotFound());
    NotificationDTO wrongNotification2 =
        new NotificationDTO("n1", "u1", "w2", "request", "friendrequest");
    String jsonWrong2 = objectMapper.writeValueAsString(wrongNotification2);
    this.mockMvc
        .perform(
            post("/notification/acceptFriendRequest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWrong2))
        .andExpect(status().isNotFound());
    String correct = objectMapper.writeValueAsString(notificationDTO);
    MvcResult result =
        this.mockMvc
            .perform(
                post("/notification/acceptFriendRequest")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(correct))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    FriendshipDTO newFriend = objectMapper.readValue(response, FriendshipDTO.class);
    assertNotNull(newFriend);
  }

  @Test
  public void testPendingFriendRequest() throws Exception {
    this.mockMvc.perform(get("/notification/isPending/w1/u2")).andExpect(status().isNotFound());
    this.mockMvc.perform(get("/notification/isPending/u1/w2")).andExpect(status().isNotFound());
    given(notificationService.checkExistingFriendRequest(any(), any()))
        .willReturn(Optional.empty());
    this.mockMvc.perform(get("/notification/isPending/u1/u2")).andExpect(status().isNotFound());
    given(notificationService.checkExistingFriendRequest(any(), any()))
        .willReturn(Optional.of(friendRequest));
    MvcResult result =
        this.mockMvc
            .perform(get("/notification/isPending/u1/u2"))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    NotificationDTO pending = objectMapper.readValue(response, NotificationDTO.class);
    assertNotNull(pending);
  }

  @Test
  public void testGetNotificationSender() throws Exception {
    this.mockMvc.perform(get("/notification/nw1/getSender")).andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc
            .perform(get("/notification/n1/getSender"))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    UserDTO userDTO = objectMapper.readValue(response, UserDTO.class);
    assertNotNull(userDTO);
  }

  @Test
  public void testGetReactionUser() throws Exception {
    this.mockMvc
        .perform(get("/notification/w1/u2/j1/a1/reaction"))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(get("/notification/u1/w2/j1/a1/reaction"))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(get("/notification/u1/u2/jw1/a1/reaction"))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(get("/notification/u1/u2/j1/aw1/reaction"))
        .andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc
            .perform(get("/notification/u1/u2/j1/a1/reaction"))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    List<NotificationDTO> reactionsDTO =
        objectMapper.readValue(response, new TypeReference<List<NotificationDTO>>() {});
    assertNotNull(reactionsDTO);
  }

  @Test
  public void testGetReactionJourney() throws Exception {
    this.mockMvc.perform(get("/notification/w1/u2/j1/reaction")).andExpect(status().isNotFound());
    this.mockMvc.perform(get("/notification/u1/w2/j1/reaction")).andExpect(status().isNotFound());
    this.mockMvc.perform(get("/notification/u1/u2/jw1/reaction")).andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc
            .perform(get("/notification/u1/u2/j1/reaction"))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    List<NotificationDTO> reactionsDTO =
        objectMapper.readValue(response, new TypeReference<List<NotificationDTO>>() {});
    assertNotNull(reactionsDTO);
  }

  @Test
  public void testGetJourneyLikes() throws Exception {
    this.mockMvc.perform(get("/notification/journeyLikes/jw1")).andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc
            .perform(get("/notification/journeyLikes/j1"))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    Integer journeyLikes = objectMapper.readValue(response, Integer.class);
    assertEquals(journeyLikes, 1);
  }

  @Test
  public void testGetActivityLikes() throws Exception {
    this.mockMvc.perform(get("/notification/journeyLikes/j1/aw1")).andExpect(status().isNotFound());
    this.mockMvc.perform(get("/notification/journeyLikes/jw1/a1")).andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc
            .perform(get("/notification/journeyLikes/j1/a1"))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    Integer activityLikes = objectMapper.readValue(response, Integer.class);
    assertEquals(activityLikes, 1);
  }
}
