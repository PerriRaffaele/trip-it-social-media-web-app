package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationActivityDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationDTO;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * This class represents a notification for an activity. It contains the users involved and the type
 * of content of the notification. It is a subclass of NotificationJourney.
 */
@Document(collection = "notifications")
@TypeAlias("NotificationActivity")
public class NotificationActivity extends NotificationJourney {

  /** The activity involved in the notification */
  @DocumentReference private final Activity activity;

  /**
   * Instantiates a new NotificationJourney
   *
   * @param from the user sending the notification
   * @param to the user receiving the notification
   * @param notificationType the type of notification
   * @param journey the journey containing the activity involved in the notification
   * @param activity the activity involved in the notification
   */
  public NotificationActivity(
      User from, User to, NotificationType notificationType, Journey journey, Activity activity) {
    super(from, to, notificationType, journey);
    this.activity = activity;
  }

  /**
   * Gets the activity involved in the notification
   *
   * @return the Activity of this NotificationActivity
   */
  public Activity getActivity() {
    return this.activity;
  }

  /**
   * Converts this NotificationActivity into a NotificationActivityDTO (overrides method in
   * NotificationJourney)
   *
   * @return a new NotificationActivityDTO
   */
  @Override
  public NotificationDTO toNotificationDTO() {
    return new NotificationActivityDTO(
        this.getId(),
        this.getUserFrom().getId(),
        this.getUserTo().getId(),
        this.getNotificationType().toString(),
        "Activity",
        this.getJourney().getId(),
        this.getActivity().getId());
  }
}
