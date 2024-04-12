package ch.usi.inf.bsc.sa4.tripit.controller.dto;

import org.springframework.data.annotation.Id;

/** This class represents a DTO for a Notification object. */
public class NotificationDTO {

  /** The id of the related notification */
  @Id private String id;
  /** The id of the user sending the notification */
  private String from;
  /** The id of the user receiving the notification */
  private String to;
  /** The type of content of the notification (friend request, like, ...) */
  private String notificationType;
  /** The type of class of the notification (journey, activity, ...) */
  private String type;

  /** Class empty constructor */
  public NotificationDTO() {}

  /**
   * Instantiates a new NotificationDTO
   *
   * @param id the id of the notification
   * @param from the id of the user sending the notification
   * @param to the id of the user receiving the notification
   * @param notificationType the content type
   * @param type the class type
   */
  public NotificationDTO(String id, String from, String to, String notificationType, String type) {
    this.id = id;
    this.from = from;
    this.to = to;
    this.notificationType = notificationType;
    this.type = type;
  }

  /**
   * Gets the type of class of the notification
   *
   * @return the class type of this NotificationDTO
   */
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
  /**
   * Gets the id of the user sending the notification
   *
   * @return the id of the sender of this NotificationDTO
   */
  public String getFrom() {
    return this.from;
  }
  /**
   * Gets the id of the user receiveing the notification
   *
   * @return the id of the receiver of this NotificationDTO
   */
  public String getTo() {
    return this.to;
  }
  /**
   * Gets the type of content of the notification
   *
   * @return the content type of this NotificationDTO
   */
  public String getNotificationType() {
    return notificationType;
  }
  /**
   * Gets the id of the notification
   *
   * @return the id of this NotificationDTO
   */
  public String getId() {
    return this.id;
  }
}
