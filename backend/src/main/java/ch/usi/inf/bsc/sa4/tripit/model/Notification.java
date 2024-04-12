package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * This class represents a notification. It contains the users involved and the type of content of
 * the notification.
 */
@Document(collection = "notifications")
@TypeAlias("Notification")
public class Notification {
  /** The id of the notification in the database */
  @Id private String id;
  /** The user sending the notification */
  @DocumentReference private final User from;
  /** The user receiving the notification */
  @DocumentReference private final User to;
  /** The type of notification */
  private final NotificationType notificationType;
  /** The visibility state of the notification */
  private boolean visible;

  /**
   * Instantiates a new Notification.
   *
   * @param from the user sending the notification
   * @param to the user receiving the notification
   * @param notificationType the type of notification
   */
  public Notification(User from, User to, NotificationType notificationType) {
    this.from = from;
    this.to = to;
    this.visible = true;
    this.notificationType = notificationType;
  }

  /**
   * Gets the user that sent the notification
   *
   * @return the User that sent this Notification
   */
  public User getUserFrom() {
    return this.from;
  }

  /**
   * Gets the user that receives the notification
   *
   * @return the User that receives this Notification
   */
  public User getUserTo() {
    return this.to;
  }

  /**
   * Gets the type of notification
   *
   * @return the type of this Notification
   */
  public NotificationType getNotificationType() {
    return notificationType;
  }

  /**
   * Gets the id of the notification
   *
   * @return the id of this Notification
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the state of the visibility of the notification
   *
   * @return true if this notification is visible, false otherwise
   */
  public boolean isVisible() {
    return this.visible;
  }

  /**
   * Sets the state of the visibility of the notification
   *
   * @param visualize the wanted state of visibility for this Notification
   */
  public void setVisualize(boolean visualize) {
    this.visible = visualize;
  }

  /**
   * Converts this Notification into a NotificationDTO
   *
   * @return a new NotificationDTO
   */
  public NotificationDTO toNotificationDTO() {
    return new NotificationDTO(
        this.getId(),
        this.from.getId(),
        this.to.getId(),
        this.notificationType.toString(),
        "Notification");
  }
}
