package ch.usi.inf.bsc.sa4.tripit.service;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.AirportDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.AttractionDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.StationDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Airport;
import ch.usi.inf.bsc.sa4.tripit.model.Attraction;
import ch.usi.inf.bsc.sa4.tripit.model.Location;
import ch.usi.inf.bsc.sa4.tripit.model.Place;
import ch.usi.inf.bsc.sa4.tripit.model.Station;
import ch.usi.inf.bsc.sa4.tripit.repository.PlaceRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service class for Place */
@Service
public class PlaceService {
  /** Repository of Place */
  private final PlaceRepository placeRepository;

  /** Class constructor */
  @Autowired
  public PlaceService(PlaceRepository placeRepository) {
    this.placeRepository = placeRepository;
  }

  /**
   * Gets all places in the repository
   *
   * @return a List of Places
   */
  public List<Place> getAll() {
    return this.placeRepository.findAll();
  }

  /**
   * Gets the places in the repository whose names contains the given string
   *
   * @return a List of Places
   */
  public List<Place> searchByNameStartingWith(String partialName) {
    return this.placeRepository.findAllByNameStartingWith(partialName);
  }

  /**
   * Gets the place in the repository with the given id
   *
   * @return an Optional Place
   */
  public Optional<Place> getById(String placeId) {
    return placeRepository.findById(placeId);
  }

  /**
   * Saves the given attraction in the repository
   *
   * @return the saved Attraction
   */
  public Attraction saveAttraction(AttractionDTO attractionDTO, Location savedLocation) {
    final Attraction attraction = attractionDTO.toAttraction();
    attraction.setLocation(savedLocation);
    return placeRepository.save(attraction);
  }

  /**
   * Saves the given station in the repository
   *
   * @return the saved Station
   */
  public Station saveStation(StationDTO stationDTO, Location savedLocation) {
    final Station station = stationDTO.toStation();
    station.setLocation(savedLocation);
    return placeRepository.save(station);
  }

  /**
   * Saves the given airport in the repository
   *
   * @return the saved Airport
   */
  public Airport saveAirport(AirportDTO airportDTO, Location savedLocation) {
    final Airport airport = airportDTO.toAirport();
    airport.setLocation(savedLocation);
    return placeRepository.save(airport);
  }

  /** Deletes all the places in the repository */
  public void deleteAll() {
    placeRepository.deleteAll();
  }
}
