package ch.usi.inf.bsc.sa4.tripit.controller.dto;

import ch.usi.inf.bsc.sa4.tripit.model.Airport;

/** This class represents the DTO for an Airport object. */
public class AirportDTO extends PlaceDTO {

  /** The three letters code that identifies the related airport */
  private String code;
  /** Class empty constructor */
  public AirportDTO() {
    super();
  }

  /**
   * Instantiates a new AirportDTO
   *
   * @param name the name of the place
   * @param locationDTO the DTO of the location of the airport
   * @param code the code of the airport
   * @param type the type of place ("airport")
   * @param id the id of the related place
   */
  public AirportDTO(String name, LocationDTO locationDTO, String code, String type, String id) {
    super(name, locationDTO, type, id);
    this.code = code;
  }

  /**
   * Gets the code of the related airport
   *
   * @return the code of this airportDTO
   */
  public String getCode() {
    return this.code;
  }

  /**
   * Converts this airportDTO into a new Airport object
   *
   * @return a new Airport object
   */
  public Airport toAirport() {
    return new Airport(this.getName(), this.getLocationDTO().toLocation(), this.getCode());
  }
}
