package ch.usi.inf.bsc.sa4.tripit.controller.dto;

import java.time.ZonedDateTime;

/** This class represents the DTO for an edited PlaneTrip object. */
public class EditPlaneTripDTO extends ActivityDTO {

  /** The number of the flight of this plane trip */
  private String flightNumber;

  /** Class empty constructor */
  public EditPlaneTripDTO() {
    super();
  }

  /**
   * Instantiates a new EditPlaneTripDTO
   *
   * @param id the id of the related plane trip object
   * @param journeyId the id of the journey which contains the related plane trip object
   * @param start the starting time of the activity
   * @param end the ending time of the activity
   * @param title the title of the activity
   * @param description the description of the activity
   * @param type the type of the activity ("plane")
   * @param flightNumber the number of the flight of this plane trip
   * @throws IllegalArgumentException
   */
  public EditPlaneTripDTO(
      String id,
      String journeyId,
      ZonedDateTime start,
      ZonedDateTime end,
      String title,
      String description,
      String type,
      String flightNumber,
      String imageLink)
      throws IllegalArgumentException {
    super(id, journeyId, start, end, title, description, type, imageLink);
    this.flightNumber = flightNumber;
  }

  /**
   * Gets the flight number of the related plane trip object
   *
   * @return the flight number of this editPlaneTripDTO
   */
  public String getFlightNumber() {
    return flightNumber;
  }

  /**
   * Sets the flight number of this editPlaneTripDTO.
   *
   * @param flightNumber the wanted flight number for this activityDTO
   */
  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }
}
