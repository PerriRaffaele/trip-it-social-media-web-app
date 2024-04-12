package ch.usi.inf.bsc.sa4.tripit.controller.dto;

import java.time.ZonedDateTime;

/** This class represents the DTO for a Visit object. */
public class VisitDTO extends ActivityDTO {

  /** The DTOs of the attraction object of the related visit */
  private AttractionDTO attraction;
  /** Class empty constructor */
  public VisitDTO() {
    super();
  }
  /**
   * Instantiates a new VisitDTO
   *
   * @param id the id of the related visit object
   * @param journeyId the id of the journey which contains the related visit object
   * @param start the starting time of the activity
   * @param end the ending time of the activity
   * @param title the title of the activity
   * @param description the description of the activity
   * @param attraction the attraction visited
   * @param type the type of the activity ("visit")
   * @param imageLink the image link of the activity
   */
  public VisitDTO(
      String id,
      String journeyId,
      ZonedDateTime start,
      ZonedDateTime end,
      String title,
      String description,
      AttractionDTO attraction,
      String type,
      String imageLink) {
    super(id, journeyId, start, end, title, description, type, imageLink);
    this.attraction = attraction;
  }
  /**
   * Gets the DTO of the attraction of the related visit object
   *
   * @return the attraction of this visitDTO
   */
  public PlaceDTO getAttraction() {
    return attraction;
  }
  /**
   * Sets the DTO of the attraction of this visitDTO.
   *
   * @param attraction the wanted attraction for this visitDTO
   */
  public void setAttraction(AttractionDTO attraction) {
    this.attraction = attraction;
  }
}
