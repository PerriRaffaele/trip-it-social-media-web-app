package ch.usi.inf.bsc.sa4.tripit.controller.dto;

/** This class represents a DTO for a NotificationActivity object. */
public class NotificationActivityDTO extends NotificationJourneyDTO {

  /** The id of the activity related to the notification */
  private String activity;

  /** Class empty constructor */
  public NotificationActivityDTO() {
    super();
  }

  /**
   * Instantiates a new NotificationActivityDTO
   *
   * @param id the id of the notification
   * @param from the id of the user sending the notification
   * @param to the id of the user receiving the notification
   * @param notificationType the content type
   * @param type the class type
   * @param journey the id of the journey related to the notification
   * @param activity the id of the activity related to the notification
   */
  public NotificationActivityDTO(
      String id,
      String from,
      String to,
      String notificationType,
      String type,
      String journey,
      String activity) {
    super(id, from, to, notificationType, type, journey);
    this.activity = activity;
  }

  /**
   * Gets the id of the activity related to the notification
   *
   * @return the id of the activity of this NotificationActivityDTO
   */
  public String getActivity() {
    return activity;
  }
}
