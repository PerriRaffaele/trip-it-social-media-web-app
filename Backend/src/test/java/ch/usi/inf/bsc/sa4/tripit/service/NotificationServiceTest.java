package ch.usi.inf.bsc.sa4.tripit.service;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationActivityDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationJourneyDTO;
import ch.usi.inf.bsc.sa4.tripit.model.*;
import ch.usi.inf.bsc.sa4.tripit.repository.NotificationActivityRepository;
import ch.usi.inf.bsc.sa4.tripit.repository.NotificationJourneyRepository;
import ch.usi.inf.bsc.sa4.tripit.repository.NotificationRepository;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotificationServiceTest {

  @Autowired private NotificationService notificationService;

  @Autowired private NotificationRepository notificationRepository;

  @Autowired private NotificationJourneyRepository notificationJourneyRepository;

  @Autowired private NotificationActivityRepository notificationActivityRepository;

  @Autowired private UserService userService;

  @Autowired private JourneyService journeyService;

  @Autowired private ActivityService activityService;

  private ZonedDateTime start, end;
  private Activity activity;

  @BeforeEach
  public void setUp() {
    notificationRepository.deleteAll();
    notificationJourneyRepository.deleteAll();
    notificationActivityRepository.deleteAll();
    notificationService =
        new NotificationService(
            notificationRepository, notificationJourneyRepository, notificationActivityRepository);
  }

  @Test
  public void testGetEnum() {
    assertEquals(NotificationType.SMILY_FACE, notificationService.getEnum("SMILY_FACE"));
    assertEquals(NotificationType.ANGRY_FACE, notificationService.getEnum("ANGRY_FACE"));
    assertEquals(NotificationType.SAD_FACE, notificationService.getEnum("SAD_FACE"));
    assertEquals(NotificationType.FRIEND_REQUEST, notificationService.getEnum("FRIEND_REQUEST"));
    assertEquals(NotificationType.LIKE, notificationService.getEnum("LIKE"));
    assertEquals(
        NotificationType.ACCEPTED_FRIEND_REQUEST,
        notificationService.getEnum("ACCEPTED_FRIEND_REQUEST"));
  }

  @Test
  public void testCreateNotificationFriend() {
    NotificationDTO notificationDTO = new NotificationDTO("", "", "", "FRIEND_REQUEST", "");
    User from = new User("1", "FrankFrank", "Frank", "frank@usi.ch", "", "Franky");
    User to = new User("2", "GioGio", "Gio", "gio@usi.ch", "", "GioGio");
    Notification notification =
        notificationService.createNotificationFriend(notificationDTO, from, to);
    assertNotNull(notification);
  }

  @Test
  public void testCreateNotificationJourney() {
    User from = new User("1", "FrankFrank", "Frank", "frank@usi.ch", "", "Franky");
    User to = new User("2", "GioGio", "Gio", "gio@usi.ch", "", "GioGio");
    Journey journey = new Journey("Milan", from, new ArrayList<Activity>());
    NotificationJourneyDTO notificationJourneyDTO =
        new NotificationJourneyDTO("1", "Frank", "Gio", "SMILY_FACE", "", "Milan");
    Notification notification =
        notificationService.createNotificationJourney(notificationJourneyDTO, from, to, journey);
    assertNotNull(notification);
  }

  @Test
  public void testCreateNotificationActivity() {
    User from = new User("1", "FrankFrank", "Frank", "frank@usi.ch", "", "Franky");
    User to = new User("2", "GioGio", "Gio", "gio@usi.ch", "", "GioGio");
    Journey journey = new Journey("Milan", from, new ArrayList<Activity>());
    NotificationActivityDTO notificationActivityDTO =
        new NotificationActivityDTO("1", "Frank", "Gio", "SMILY_FACE", "", "Milan", "Plane");
    Notification notification =
        notificationService.createNotificationActivity(
            notificationActivityDTO, from, to, journey, activity);
    assertNotNull(notification);
  }

  @Test
  public void testCheckExistingFriendRequest() {
    NotificationDTO notificationDTO = new NotificationDTO("", "Frank", "Gio", "FRIEND_REQUEST", "");
    User from = new User("Frank", "FrankFrank", "Frank", "frank@usi.ch", "", "Franky");
    User to = new User("Gio", "GioGio", "Gio", "gio@usi.ch", "", "GioGio");
    Notification notification =
        notificationService.createNotificationFriend(notificationDTO, from, to);
    Optional<Notification> notificationOptional = Optional.of(notification);
    assertNotEquals(notificationOptional, notificationService.checkExistingFriendRequest(from, to));
  }

  @Test
  public void testGetUserNotifications() {
    User from = new User("1", "FrankFrank", "Frank", "frank@usi.ch", "", "Franky");
    User to = new User("2", "GioGio", "Gio", "gio@usi.ch", "", "GioGio");
    Journey journey = new Journey("Milan", from, new ArrayList<Activity>());
    NotificationActivityDTO notificationActivityDTO =
        new NotificationActivityDTO("1", "Frank", "Gio", "SMILY_FACE", "", "Milan", "Plane");
    Notification notification =
        notificationService.createNotificationActivity(
            notificationActivityDTO, from, to, journey, activity);
    List<Notification> notifications = new ArrayList<>();
    notifications.add(notification);
    assertNotEquals(notifications, notificationService.getUserNotifications(from));
  }

  @Test
  public void testGetFromNotificationJourney() {
    User from = new User("1", "FrankFrank", "Frank", "frank@usi.ch", "", "Franky");
    User to = new User("2", "GioGio", "Gio", "gio@usi.ch", "", "GioGio");
    Journey journey = new Journey("Milan", from, new ArrayList<Activity>());
    NotificationJourneyDTO notificationJourneyDTO =
        new NotificationJourneyDTO("1", "Frank", "Gio", "SMILY_FACE", "", "Milan");
    Notification notification =
        notificationService.createNotificationJourney(notificationJourneyDTO, from, to, journey);
    List<NotificationJourney> notifications = new ArrayList<>();
    notifications.add((NotificationJourney) notification);
    assertNotEquals(
        notifications, notificationService.getFromNotificationJourney(from, to, journey));
  }

  @Test
  public void testGetFromNotificationActivity() {
    User from = new User("1", "FrankFrank", "Frank", "frank@usi.ch", "", "Franky");
    User to = new User("2", "GioGio", "Gio", "gio@usi.ch", "", "GioGio");
    Journey journey = new Journey("Milan", from, new ArrayList<Activity>());
    NotificationActivityDTO notificationActivityDTO =
        new NotificationActivityDTO("1", "Frank", "Gio", "SMILY_FACE", "", "Milan", "Plane");
    Notification notification =
        notificationService.createNotificationActivity(
            notificationActivityDTO, from, to, journey, activity);
    List<NotificationActivity> notifications = new ArrayList<>();
    notifications.add((NotificationActivity) notification);
    assertNotEquals(
        notifications,
        notificationService.getFromNotificationActivity(from, to, journey, activity));
  }

  @Test
  public void testGetById() {
    User from = new User("1", "FrankFrank", "Frank", "frank@usi.ch", "", "Franky");
    User to = new User("2", "GioGio", "Gio", "gio@usi.ch", "", "GioGio");
    Journey journey = new Journey("Milan", from, new ArrayList<Activity>());
    NotificationJourneyDTO notificationJourneyDTO =
        new NotificationJourneyDTO("1", "Frank", "Gio", "SMILY_FACE", "", "Milan");
    Notification notification =
        notificationService.createNotificationJourney(notificationJourneyDTO, from, to, journey);
    Optional<Notification> notificationOptional = Optional.of(notification);
    assertNotEquals(notificationOptional, notificationService.getById(notification.getId()));
  }

  @Test
  public void testUpdateNotification() {
    User from = new User("1", "FrankFrank", "Frank", "frank@usi.ch", "", "Franky");
    User to = new User("2", "GioGio", "Gio", "gio@usi.ch", "", "GioGio");
    Journey journey = new Journey("Milan", from, new ArrayList<Activity>());
    NotificationJourneyDTO notificationJourneyDTO =
        new NotificationJourneyDTO("1", "Frank", "Gio", "SMILY_FACE", "", "Milan");
    Notification notification =
        notificationService.createNotificationJourney(notificationJourneyDTO, from, to, journey);
    notificationService.updateNotification(notification);
  }

  @Test
  public void testDeleteNotification() {
    User from = new User("1", "FrankFrank", "Frank", "frank@usi.ch", "", "Franky");
    User to = new User("2", "GioGio", "Gio", "gio@usi.ch", "", "GioGio");
    Journey journey = new Journey("Milan", from, new ArrayList<Activity>());
    NotificationJourneyDTO notificationJourneyDTO =
        new NotificationJourneyDTO("1", "Frank", "Gio", "SMILY_FACE", "", "Milan");
    Notification notification =
        notificationService.createNotificationJourney(notificationJourneyDTO, from, to, journey);
    notificationService.deleteNotification(notification);
  }
}
