package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.AirportDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.PlaceDTO;

/**
 * This class represent an Airport as a Place with a 3-letter code. It inherits from abstract class
 * Place.
 */
public class Airport extends Place {

  /** The 3-letter code of the airport */
  private String code;

  /**
   * Creates an Airport with the given name, location and code.
   *
   * @param name the name of the airport
   * @param location the location of the airport
   * @param code the code of the airport
   */
  public Airport(String name, Location location, String code) {
    super(name, location);
    this.code = code;
  }

  /**
   * returns the 3-letter code of the airport
   *
   * @return the 3-letter code of the airport
   */
  public String getCode() {
    return code;
  }

  /**
   * Sets the code of the airport to the given 3-letter code string
   *
   * @param code the new code of the airport
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Returns true if the provided airport has the same field values as this airport and is not null.
   *
   * @param airport the airport to confront with this airport
   * @return true if the two airports match
   */
  public boolean equalsAirport(Airport airport) {
    if (airport == null) {
      return false;
    }
    String locationId = airport.getLocation().getId();
    Location loc = this.getLocation();
    return this.getId().equals(airport.getId())
        && this.getName().equals(airport.getName())
        && loc.getId().equals(locationId)
        && this.getCode().equals(airport.getCode());
  }

  /**
   * Returns a PlaceDTO representing this airport
   *
   * @return a PlaceDTO representing this airport
   */
  public PlaceDTO toPlaceDTO() {
    return new AirportDTO(
        this.getName(),
        this.getLocation().toLocationDTO(),
        this.getCode(),
        "Airport",
        this.getId());
  }
}
