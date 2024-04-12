package ch.usi.inf.bsc.sa4.tripit.controller.dto;

/** This class represents a DTO for a NotificationJourney object. */
public class NotificationJourneyDTO extends NotificationDTO {

  /** The id of the journey related to the notification */
  private String journey;

  /** Class empty constructor */
  public NotificationJourneyDTO() {
    super();
  }

  /**
   * Instantiates a new NotificationJourneyDTO
   *
   * @param id the id of the notification
   * @param from the id of the user sending the notification
   * @param to the id of the user receiving the notification
   * @param notificationType the content type
   * @param type the class type
   * @param journey the id of the journey related to the notification
   */
  public NotificationJourneyDTO(
      String id, String from, String to, String notificationType, String type, String journey) {
    super(id, from, to, notificationType, type);
    this.journey = journey;
  }

  /**
   * Gets the id of the journey related to the notification
   *
   * @return the id of the journey of this NotificationJourneyDTO
   */
  public String getJourney() {
    return journey;
  }
}
