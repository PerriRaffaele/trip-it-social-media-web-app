package ch.usi.inf.bsc.sa4.tripit.service;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.LocationDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Location;
import ch.usi.inf.bsc.sa4.tripit.repository.LocationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The service for locations. Communicates to the repository. */
@Service
public class LocationService {

  /** The location repository */
  private final LocationRepository locationRepository;

  /**
   * Initializes a location service with the given location repository
   *
   * @param locationRepository the location repository of the service
   */
  @Autowired
  public LocationService(LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  /**
   * Returns a list of all the locations in the repository
   *
   * @return a list of all the locations in the repository
   */
  public List<Location> getAll() {
    return this.locationRepository.findAll();
  }

  /**
   * Saves in the database the location represented by the given locationDTO
   *
   * @param locationDTO represents the location to be saved
   * @return the saved location
   */
  public Location saveLocation(LocationDTO locationDTO) {
    return locationRepository.save(locationDTO.toLocation());
  }
}
