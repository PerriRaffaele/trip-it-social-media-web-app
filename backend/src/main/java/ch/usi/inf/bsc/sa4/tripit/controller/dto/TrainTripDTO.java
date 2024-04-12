package ch.usi.inf.bsc.sa4.tripit.controller.dto;

import java.time.ZonedDateTime;

/** This class represents the DTO for a TrainTrip object. */
public class TrainTripDTO extends ActivityDTO {

  /** The DTO of the station object representing the starting place of the train trip */
  private StationDTO from;
  /** The DTO of the station object representing the ending place of the train trip */
  private StationDTO to;
  /** The co2 emissions estimate of the related train trip */
  private double co2Estimate;

  /** Class empty constructor */
  public TrainTripDTO() {
    super();
  }

  /**
   * Instantiates a new TrainTripDTO
   *
   * @param id the id of the related train trip object
   * @param journeyId the id of the journey which contains the related train trip object
   * @param start the starting time of the activity
   * @param end the ending time of the activity
   * @param title the title of the activity
   * @param description the description of the activity
   * @param from the starting station of the train trip
   * @param to the starting station of the train trip
   * @param type the type of the activity ("train")
   * @param co2Estimate the co2 emissions estimate of the trip
   * @param imageLink the image link of the trip
   * @throws IllegalArgumentException
   */
  public TrainTripDTO(
      String id,
      String journeyId,
      ZonedDateTime start,
      ZonedDateTime end,
      String title,
      String description,
      StationDTO from,
      StationDTO to,
      String type,
      double co2Estimate,
      String imageLink) {
    super(id, journeyId, start, end, title, description, type, imageLink);
    this.from = from;
    this.to = to;
    this.co2Estimate = co2Estimate;
  }
  /**
   * Gets the DTO of the starting station of the related train trip object
   *
   * @return the starting station of this TrainTripDTO
   */
  public StationDTO getFrom() {
    return from;
  }
  /**
   * Sets the DTO of the starting station of this trainTripDTO.
   *
   * @param from the wanted starting station for this trainTripDTO
   */
  public void setFrom(StationDTO from) {
    this.from = from;
  }
  /**
   * Gets the DTO of the ending station of the related train trip object
   *
   * @return the ending station of this TrainTripDTO
   */
  public StationDTO getTo() {
    return to;
  }
  /**
   * Sets the DTO of the ending station of this trainTripDTO.
   *
   * @param to the wanted ending station for this trainTripDTO
   */
  public void setTo(StationDTO to) {
    this.to = to;
  }
  /**
   * Gets the co2 emissions estimate of the related train trip object
   *
   * @return the co2 emissions estimate of this TrainTripDTO
   */
  public double getCo2Estimate() {
    return co2Estimate;
  }
  /**
   * Sets the co2 estimate of this trainTripDTO.
   *
   * @param co2Estimate the wanted co2 estimate for this trainTripDTO
   */
  public void setCo2Estimate(double co2Estimate) {
    this.co2Estimate = co2Estimate;
  }
}
