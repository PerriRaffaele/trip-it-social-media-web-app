package ch.usi.inf.bsc.sa4.tripit.model;

import static org.junit.jupiter.api.Assertions.*;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotificationTest {

  private String id;
  private User from;
  private User to;
  private NotificationType notificationType;
  private boolean visible;

  private boolean compareNotificationDTOFields(
      NotificationDTO notificationDTO,
      String from,
      String to,
      String notificationType,
      String type) {
    return notificationDTO.getFrom().equals(from)
        && notificationDTO.getTo().equals(to)
        && notificationDTO.getNotificationType().equals(notificationType)
        && notificationDTO.getType().equals(type);
  }

  @BeforeEach
  void setUp() {
    id = "1";
    from = new User("2", "Frank", "", "frank@usi.ch", "franky");
    to = new User("2", "Gio", "", "gio@usi.ch", "giogio");
    notificationType = NotificationType.FRIEND_REQUEST;
    visible = true;
  }

  @Test
  public void testGetUserFrom() {
    Notification notification = new Notification(from, to, notificationType);
    assertEquals(from, notification.getUserFrom());
  }

  @Test
  public void testGetUserTo() {
    Notification notification = new Notification(from, to, notificationType);
    assertEquals(to, notification.getUserTo());
  }

  @Test
  public void testGetNotificationType() {
    Notification notification = new Notification(from, to, notificationType);
    assertEquals(notificationType, notification.getNotificationType());
  }

  @Test
  public void testGetId() {
    assertEquals("2", from.getId());
  }

  @Test
  public void testIsVisible() {
    Notification notification = new Notification(from, to, notificationType);
    assertTrue(notification.isVisible());
  }

  @Test
  public void testSetVisualize() {
    Notification notification = new Notification(from, to, notificationType);
    notification.setVisualize(false);
    assertFalse(notification.isVisible());
  }

  @Test
  public void testToNotificationDTO() {
    Notification notification = new Notification(from, to, notificationType);
    NotificationDTO notificationDTO = notification.toNotificationDTO();
    assertTrue(
        compareNotificationDTOFields(notificationDTO, "2", "2", "FRIEND_REQUEST", "Notification"));
  }
}
