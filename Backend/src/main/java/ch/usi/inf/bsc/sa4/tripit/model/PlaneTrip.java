package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.ActivityDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.PlaneTripDTO;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * The class PlaneTrip inherits from the class Activity, and contains an airport of departure and an
 * airport of arrival.
 */
@Document(collection = "Activities")
@TypeAlias("PlaneTrip")
public class PlaneTrip extends Activity implements Trip {

  /** The radius of the Earth */
  public static final double EARTH_RADIUS = 6371;
  /** The average co2 emissions of a train, taken from https://www.carbonindependent.org/22.html */
  public static final double AVERAGE_CO2_EMISSIONS_PLANE = 0.275;
  /** Constant */
  public static final int CONST2 = 2;
  /** Number used for rounding final number */
  public static final double ROUNDING1000 = 1000.0;

  /** The airport where the plane trip starts */
  @DocumentReference private Airport from;
  /** The airport where the plane trip ends */
  @DocumentReference private Airport to;
  /** The flight number of the plane trip */
  private String flightNumber;
  /** The estimated co2 emissions where the plane trip */
  private double co2Estimate;

  /**
   * Instantiates a new Plane trip.
   *
   * @param start the starting time of the activity
   * @param end the ending time of the activity
   * @param title the title of the activity
   * @param description the description of the activity
   * @param from the departure airport
   * @param to the arrival airport
   * @param flightNumber the flight number
   * @param imageLink the image link
   */
  public PlaneTrip(
      Journey journey,
      ZonedDateTime start,
      ZonedDateTime end,
      String title,
      String description,
      Airport from,
      Airport to,
      String flightNumber,
      Image imageLink)
      throws IllegalArgumentException {
    super(journey, start, end, title, description, imageLink);
    this.from = from;
    this.to = to;
    this.flightNumber = flightNumber;
  }

  /**
   * Calculates the estimated co2 emission.
   *
   * @return the calculated estimation of the co2 emission
   */
  public void recalculateCo2Estimate() {
    Location fromLocation = this.from.getLocation();
    Location toLocation = this.to.getLocation();
    this.co2Estimate =
        Math.round(
                EARTH_RADIUS
                    * midCalculationCO2(fromLocation, toLocation)
                    * AVERAGE_CO2_EMISSIONS_PLANE
                    * ROUNDING1000)
            / ROUNDING1000;
  }

  /** Gets co2Estimate. */
  public double getCo2Estimate() {
    return this.co2Estimate;
  }

  /**
   * Gets airport of departure.
   *
   * @return the airport of departure
   */
  @Override
  public Airport getFrom() {
    return from;
  }

  /**
   * Sets airport of departure.
   *
   * @param airportFrom the airport of departure
   */
  public void setFrom(Airport airportFrom) {
    this.from = airportFrom;
  }

  /**
   * Gets airport of arrival.
   *
   * @return the airport of arrival
   */
  public Airport getTo() {
    return to;
  }

  /**
   * Sets airport of arrival.
   *
   * @param to the airport of arrival
   */
  public void setTo(Airport to) {
    this.to = to;
  }

  /**
   * Gets flight number.
   *
   * @return the flight number
   */
  public String getFlightNumber() {
    return flightNumber;
  }

  /**
   * Sets the flight number.
   *
   * @param flightNumber the flight number
   */
  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  /**
   * Converts this PlaneTrip to a PlaneTripDTO
   *
   * @return a new PlaneTripDTO
   */
  public ActivityDTO toActivityDTO() {
    String imgLink = "0000";
    if (this.getImageLink() != null) {
      imgLink = this.getImageLink().getId();
    }
    return new PlaneTripDTO(
        this.getId(),
        this.getJourney().getId(),
        this.getStart(),
        this.getEnd(),
        this.getTitle(),
        this.getDescription(),
        this.getFrom().toPlaceDTO(),
        this.getTo().toPlaceDTO(),
        this.getFlightNumber(),
        "plane",
        this.getCo2Estimate(),
        imgLink);
  }
}
