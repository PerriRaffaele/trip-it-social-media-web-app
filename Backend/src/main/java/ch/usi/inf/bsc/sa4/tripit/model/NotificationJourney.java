package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationJourneyDTO;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * This class represents a notification for a journey. It contains the users involved and the type
 * of content of the notification. It is a subclass of Notification.
 */
@Document(collection = "notifications")
@TypeAlias("NotificationJourney")
public class NotificationJourney extends Notification {

  /** The journey involved in the notification */
  @DocumentReference private final Journey journey;

  /**
   * Instantiates a new NotificationJourney
   *
   * @param from the user sending the notification
   * @param to the user receiving the notification
   * @param notificationType the type of notification
   * @param journey the journey involved in the notification
   */
  public NotificationJourney(
      User from, User to, NotificationType notificationType, Journey journey) {
    super(from, to, notificationType);
    this.journey = journey;
  }

  /**
   * Gets the journey involved in the notification
   *
   * @return the Journey of this NotificationJourney
   */
  public Journey getJourney() {
    return journey;
  }

  /**
   * Converts this NotificationJourney into a NotificationJourneyDTO (overrides method in
   * Notification, overridden by method in NotificationActivity)
   *
   * @return a new NotificationJourneyDTO
   */
  @Override
  public NotificationDTO toNotificationDTO() {
    return new NotificationJourneyDTO(
        this.getId(),
        this.getUserFrom().getId(),
        this.getUserTo().getId(),
        this.getNotificationType().toString(),
        "Journey",
        this.getJourney().getId());
  }
}
