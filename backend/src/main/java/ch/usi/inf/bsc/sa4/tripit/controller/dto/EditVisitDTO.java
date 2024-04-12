package ch.usi.inf.bsc.sa4.tripit.controller.dto;

import java.time.ZonedDateTime;

/** This class represents the DTO for an edited Visit object. */
public class EditVisitDTO extends ActivityDTO {

  /** Class empty constructor */
  public EditVisitDTO() {
    super();
  }
  /**
   * Instantiates a new EditPlaneTripDTO
   *
   * @param id the id of the related visit object
   * @param journeyId the id of the journey which contains the related visit object
   * @param start the starting time of the activity
   * @param end the ending time of the activity
   * @param title the title of the activity
   * @param description the description of the activity
   * @param type the type of the activity ("visit")
   * @throws IllegalArgumentException
   */
  public EditVisitDTO(
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
