package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.PlaceDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.StationDTO;

/** This class represent a station. It inherits from abstract class Place. */
public class Station extends Place {
  /**
   * Instantiates a new Station.
   *
   * @param name the name of the station
   * @param location the location of the station
   */
  public Station(String name, Location location) {
    super(name, location);
  }

  /**
   * Converts this Station into a PlaceDTO (implements superclass method)
   *
   * @return a new PlaceDTO (specifically a StationDTO)
   */
  public PlaceDTO toPlaceDTO() {
    return new StationDTO(
        this.getName(), this.getLocation().toLocationDTO(), "Station", this.getId());
  }

  /**
   * Converts this Station into a PlaceDTO
   *
   * @return a new StationDTO
   */
  public StationDTO toStationDTO() {
    return new StationDTO(
        this.getName(), this.getLocation().toLocationDTO(), "Station", this.getId());
  }

  /**
   * Returns true if the provided station has the same field values as this station and is not null.
   *
   * @param station the station to confront with this station
   * @return true if the two stations match
   */
  public boolean equalsStation(Station station) {
    if (station == null) {
      return false;
    }
    return this.getId().equals(station.getId())
        && this.getLocation().getId().equals(station.getLocation().getId())
        && this.getName().equals(station.getName());
  }
}
