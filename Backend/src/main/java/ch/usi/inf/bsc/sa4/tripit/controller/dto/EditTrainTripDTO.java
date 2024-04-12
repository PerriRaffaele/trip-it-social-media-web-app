package ch.usi.inf.bsc.sa4.tripit.controller.dto;

import java.time.ZonedDateTime;

/** This class represents the DTO for an edited TrainTrip object. */
public class EditTrainTripDTO extends ActivityDTO {

  /** Class empty constructor */
  public EditTrainTripDTO() {
    super();
  }
  /**
   * Instantiates a new EditTrainTripDTO
   *
   * @param id the id of the related train trip object
   * @param journeyId the id of the journey which contains the related train trip object
   * @param start the starting time of the activity
   * @param end the ending time of the activity
   * @param title the title of the activity
   * @param description the description of the activity
   * @param type the type of the activity ("train")
   * @throws IllegalArgumentException
   */
  public EditTrainTripDTO(
      String id,
      String journeyId,
      ZonedDateTime start,
      ZonedDateTime end,
      String title,
      String description,
      String type,
      String imageLink) {
    super(id, journeyId, start, end, title, description, type, imageLink);
  }
}
