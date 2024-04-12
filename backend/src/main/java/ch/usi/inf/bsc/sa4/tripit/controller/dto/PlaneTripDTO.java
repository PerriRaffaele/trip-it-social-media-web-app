package ch.usi.inf.bsc.sa4.tripit.controller.dto;

import java.time.ZonedDateTime;

/** This class represents the DTO for a PlaneTrip object. */
public class PlaneTripDTO extends ActivityDTO {

  /** The DTO of the airport object representing the starting place of the plane trip */
  private PlaceDTO from;
  /** The DTO of the airport object representing the ending place of the plane trip */
  private PlaceDTO to;
  /** The flight number of the related train trip */
  private String flightNumber;
  /** The co2 emissions estimate of the related plane trip */
  private double co2Estimate;
  /** Class empty constructor */
  public PlaneTripDTO() {
    super();
  }

  /**
   * Instantiates a new PlaneTripDTO
   *
   * @param id the id of the related train trip object
   * @param journeyId the id of the journey which contains the related plane trip object
   * @param start the starting time of the activity
   * @param end the ending time of the activity
   * @param title the title of the activity
   * @param description the description of the activity
   * @param from the starting airport of the plane trip
   * @param to the starting airport of the plane trip
   * @param type the type of the activity ("plane")
   * @param co2Estimate the co2 emissions estimate of the trip
   * @param imageLink the image link of the trip
   * @throws IllegalArgumentException
   */
  public PlaneTripDTO(
      String id,
      String journeyId,
      ZonedDateTime start,
      ZonedDateTime end,
      String title,
      String description,
      PlaceDTO from,
      PlaceDTO to,
      String flightNumber,
      String type,
      double co2Estimate,
      String imageLink) {
    super(id, journeyId, start, end, title, description, type, imageLink);
    this.from = from;
    this.to = to;
    this.flightNumber = flightNumber;
    this.co2Estimate = co2Estimate;
  }
  /**
   * Gets the DTO of the starting airport of the related plane trip object
   *
   * @return the starting airport of this PlaneTripDTO
   */
  public PlaceDTO getFrom() {
    return from;
  }
  /**
   * Sets the DTO of the starting airport of this planeTripDTO.
   *
   * @param from the wanted starting airport for this planeTripDTO
   */
  public void setFrom(PlaceDTO from) {
    this.from = from;
  }
  /**
   * Gets the DTO of the ending airport of the related plane trip object
   *
   * @return the ending airport of this PlaneTripDTO
   */
  public PlaceDTO getTo() {
    return to;
  }
  /**
   * Sets the DTO of the ending airport of this planeTripDTO.
   *
   * @param to the wanted ending airport for this planeTripDTO
   */
  public void setTo(PlaceDTO to) {
    this.to = to;
  }
  /**
   * Gets the flight number of the related plane trip object
   *
   * @return the flight number of this PlaneTripDTO
   */
  public String getFlightNumber() {
    return flightNumber;
  }

  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }
  /**
   * Gets the co2 emissions estimate of the related plane trip object
   *
   * @return the co2 emissions estimate of this PlaneTripDTO
   */
  public double getCo2Estimate() {
    return co2Estimate;
  }
  /**
   * Sets the co2 estimate of this planeTripDTO.
   *
   * @param co2Estimate the wanted co2 estimate for this planeTripDTO
   */
  public void setCo2Estimate(double co2Estimate) {
    this.co2Estimate = co2Estimate;
  }
}
