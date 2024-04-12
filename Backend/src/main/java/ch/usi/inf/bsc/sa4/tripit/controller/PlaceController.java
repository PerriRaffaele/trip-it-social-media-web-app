package ch.usi.inf.bsc.sa4.tripit.controller;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.AirportDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.AttractionDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.PlaceDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.StationDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Airport;
import ch.usi.inf.bsc.sa4.tripit.model.Attraction;
import ch.usi.inf.bsc.sa4.tripit.model.Location;
import ch.usi.inf.bsc.sa4.tripit.model.Place;
import ch.usi.inf.bsc.sa4.tripit.model.Station;
import ch.usi.inf.bsc.sa4.tripit.service.LocationService;
import ch.usi.inf.bsc.sa4.tripit.service.PlaceService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** The Place controller. */
@RestController
@RequestMapping("/places")
public class PlaceController {

  /** place service to interact with the place repository. */
  private final PlaceService placeService;

  /** location service to interact with the location repository. */
  private final LocationService locationService;

  /**
   * Instantiates a new Place controller.
   *
   * @param placeService the place service
   * @param locationService the location service
   */
  @Autowired
  public PlaceController(PlaceService placeService, LocationService locationService) {
    this.placeService = placeService;
    this.locationService = locationService;
  }

  /**
   * Gets all.
   *
   * @return the all
   */
  @GetMapping
  public ResponseEntity<List<Place>> getAll() {
    return ResponseEntity.ok(placeService.getAll());
  }

  /**
   * Gets by name containing.
   *
   * @param partialName the partial name
   * @return the by name containing
   */
  @GetMapping("/search")
  public ResponseEntity<List<PlaceDTO>> getByNameContaining(
      @RequestParam("partialName") String partialName) {
    final var placesDTO = new ArrayList<PlaceDTO>();

    for (final var place : placeService.searchByNameStartingWith(partialName)) {
      placesDTO.add(place.toPlaceDTO());
    }

    return ResponseEntity.ok(placesDTO);
  }

  /**
   * Gets place by id.
   *
   * @param placeId the place id
   * @return the place by id
   */
  @GetMapping("/{id}")
  public ResponseEntity<PlaceDTO> getPlaceById(@PathVariable("id") String placeId) {
    final var targetPlace = placeService.getById(placeId);
    if (targetPlace.isPresent()) {
      final Place place = targetPlace.get();
      final PlaceDTO placeDTO = place.toPlaceDTO();
      return ResponseEntity.ok(placeDTO);
    } else {
      ResponseEntity.HeadersBuilder response = ResponseEntity.notFound();
      return response.build();
    }
  }

  /**
   * Add attraction response entity.
   *
   * @param attractionDTO the attraction dto
   * @return the response entity
   */
  @PostMapping("/attraction")
  public ResponseEntity<PlaceDTO> addAttraction(@RequestBody AttractionDTO attractionDTO) {
    final Location savedLocation = locationService.saveLocation(attractionDTO.getLocationDTO());
    final Attraction createdAttraction = placeService.saveAttraction(attractionDTO, savedLocation);
    final PlaceDTO createdAttractionDTO = createdAttraction.toPlaceDTO();
    return ResponseEntity.ok(createdAttractionDTO);
  }

  /**
   * Add attraction response entity.
   *
   * @param stationDTO the station dto
   * @return the response entity
   */
  @PostMapping("/station")
  public ResponseEntity<PlaceDTO> addAttraction(@RequestBody StationDTO stationDTO) {
    final Location savedLocation = locationService.saveLocation(stationDTO.getLocationDTO());
    final Station createdStation = placeService.saveStation(stationDTO, savedLocation);
    final PlaceDTO createdStationDTO = createdStation.toPlaceDTO();
    return ResponseEntity.ok(createdStationDTO);
  }

  /**
   * Add attraction response entity.
   *
   * @param airportDTO the airport dto
   * @return the response entity
   */
  @PostMapping("/airport")
  public ResponseEntity<PlaceDTO> addAttraction(@RequestBody AirportDTO airportDTO) {
    final Location savedLocation = locationService.saveLocation(airportDTO.getLocationDTO());
    final Airport createdAirport = placeService.saveAirport(airportDTO, savedLocation);
    final PlaceDTO createdAirportDTO = createdAirport.toPlaceDTO();
    return ResponseEntity.ok(createdAirportDTO);
  }
}
