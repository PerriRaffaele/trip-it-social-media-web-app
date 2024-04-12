package ch.usi.inf.bsc.sa4.tripit.controller.dto;

import ch.usi.inf.bsc.sa4.tripit.model.Station;

/** This class represents the DTO for a Station object. */
public class StationDTO extends PlaceDTO {

  /** Class empty constructor */
  public StationDTO() {
    super();
  }

  /**
   * Instantiates a new StationDTO
   *
   * @param name the name of the place
   * @param locationDTO the DTO of the location of the station
   * @param type the type of place ("station")
   * @param id the id of the related place
   */
  public StationDTO(String name, LocationDTO locationDTO, String type, String id) {
    super(name, locationDTO, type, id);
  }
  /**
   * Converts this stationDTO into a new Station object
   *
   * @return a new Station object
   */
  public Station toStation() {
    return new Station(this.getName(), this.getLocationDTO().toLocation());
  }
}
