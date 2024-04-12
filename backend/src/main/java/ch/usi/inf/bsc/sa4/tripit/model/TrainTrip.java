package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.ActivityDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.TrainTripDTO;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * The class TrainTrip inherits from the class Activity, and contains a station of departure and a
 * station of arrival.
 */
@Document(collection = "Activities")
@TypeAlias("TrainTrip")
public class TrainTrip extends Activity implements Trip {

  /** The radius of the Earth */
  public static final double EARTH_RADIUS = 6371;
  /** The average co2 emissions of a train, taken from https://www.carbonindependent.org/22.html */
  public static final double AVERAGE_CO2_EMISSIONS_TRAIN = 0.049;
  /** Constant */
  public static final int CONST2 = 2;
  /** Number used for rounding final number */
  public static final double ROUNDING1000 = 1000.0;

  /** The station where the train trip starts */
  @DocumentReference private Station from;
  /** The station where the train trip ends */
  @DocumentReference private Station to;
  /** The estimated co2 emissions where the plane trip */
  private double co2Estimate;

  /**
   * Instantiates a train trip.
   *
   * @param start the starting time of the activity
   * @param end the ending time of the activity
   * @param title the title of the activity
   * @param description the description of the activity
   * @param from the station of departure
   * @param to the station of arrival
   * @param imageLink the image link
   */
  public TrainTrip(
      Journey journey,
      ZonedDateTime start,
      ZonedDateTime end,
      String title,
      String description,
      Station from,
      Station to,
      Image imageLink)
      throws IllegalArgumentException {
    super(journey, start, end, title, description, imageLink);
    this.from = from;
    this.to = to;
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
                    * AVERAGE_CO2_EMISSIONS_TRAIN
                    * ROUNDING1000)
            / ROUNDING1000;
  }

  /** Gets co2Estimate. */
  public double getCo2Estimate() {
    return this.co2Estimate;
  }

  /**
   * Gets station of departure.
   *
   * @return the station of departure
   */
  @Override
  public Place getFrom() {
    return from;
  }

  /**
   * Gets station of departure.
   *
   * @return the station of departure
   */
  public Station getFromStation() {
    return from;
  }

  /**
   * Sets station of departure.
   *
   * @param from the station of departure
   */
  public void setFrom(Station from) {
    this.from = from;
  }

  /**
   * Gets station of arrival.
   *
   * @return the station of arrival
   */
  public Station getTo() {
    return to;
  }

  /**
   * Gets station of arrival.
   *
   * @return the station of arrival
   */
  public Station getToStation() {
    return to;
  }

  /**
   * Sets station of arrival.
   *
   * @param to the station of arrival
   */
  public void setTo(Station to) {
    this.to = to;
  }

  /**
   * Converts this TrainTrip to a TrainTripDTO
   *
   * @return a new TrainTripDTO
   */
  public ActivityDTO toActivityDTO() {
    String imgLink = "0000";
    if (this.getImageLink() != null) {
      imgLink = this.getImageLink().getId();
    }
    return new TrainTripDTO(
        this.getId(),
        this.getJourney().getId(),
        this.getStart(),
        this.getEnd(),
        this.getTitle(),
        this.getDescription(),
        this.getFromStation().toStationDTO(),
        this.getToStation().toStationDTO(),
        "train",
        this.getCo2Estimate(),
        imgLink);
  }
}
