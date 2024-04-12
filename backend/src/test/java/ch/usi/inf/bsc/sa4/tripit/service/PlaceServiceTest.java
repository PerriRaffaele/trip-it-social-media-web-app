package ch.usi.inf.bsc.sa4.tripit.service;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.*;
import ch.usi.inf.bsc.sa4.tripit.model.*;
import ch.usi.inf.bsc.sa4.tripit.repository.PlaceRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlaceServiceTest {

  @Autowired private PlaceService placeService;

  @Autowired private PlaceRepository placeRepository;

  private PlaceDTO placeDTO;

  private Place place;

  private LocationDTO locationDTO;

  private Location location;

  private StationDTO stationDTO;

  private AirportDTO airportDTO;

  private AttractionDTO attractionDTO;

  @BeforeEach
  void setup() {
    locationDTO = new LocationDTO(46.003677, 8.951052, "Switzerland", "Ticino");
    stationDTO = new StationDTO("Lugano", locationDTO, "Train", "");
    airportDTO = new AirportDTO("Lugano", locationDTO, "", "Plane", "");
    placeDTO = new PlaceDTO("Lugano", locationDTO, "Place", "");
    attractionDTO = new AttractionDTO("Lugano", locationDTO, "", "Attraction", "");
  }

  @Test
  public void testGetAll() {
    List<Place> getAll = placeService.getAll();
    int originalSize = getAll.size();
    placeService.saveAttraction(attractionDTO, location);
    getAll = placeService.getAll();
    assertEquals(originalSize + 1, getAll.size());
  }

  @Test
  public void testGetByIdAddOne() {
    List<Place> getAll = placeService.getAll();
    int originalSize = getAll.size();
    placeService.saveAttraction(attractionDTO, location);
    getAll = placeService.getAll();
    assertEquals(originalSize + 1, getAll.size());
  }

  @Test
  public void testGetById() {
    Place newPlace = placeService.saveAttraction(attractionDTO, location);
    Optional<Place> search = placeService.getById(newPlace.getId());
    assertEquals(newPlace.getId(), search.get().getId());
  }

  @Test
  public void testSaveAttractionNotNull() {
    Attraction newAttraction = placeService.saveAttraction(attractionDTO, location);
    assertNotNull(newAttraction);
  }

  @Test
  public void testSaveAttractionAddsOne() {
    List<Place> getAll = placeService.getAll();
    int originalSize = getAll.size();
    placeService.saveAttraction(attractionDTO, location);
    getAll = placeService.getAll();
    assertEquals(originalSize + 1, getAll.size());
  }

  @Test
  public void testSaveStationNotNull() {
    Station newStation = placeService.saveStation(stationDTO, location);
    assertNotNull(newStation);
  }

  @Test
  public void testSaveStationAddsOne() {
    List<Place> getAll = placeService.getAll();
    int originalSize = getAll.size();
    placeService.saveStation(stationDTO, location);
    getAll = placeService.getAll();
    assertEquals(originalSize + 1, getAll.size());
  }

  @Test
  public void testSaveAirportNotNull() {
    Airport newAirport = placeService.saveAirport(airportDTO, location);
    assertNotNull(newAirport);
  }

  @Test
  public void testSaveAirportAddsOne() {
    List<Place> getAll = placeService.getAll();
    int originalSize = getAll.size();
    placeService.saveAirport(airportDTO, location);
    getAll = placeService.getAll();
    assertEquals(originalSize + 1, getAll.size());
  }

  @Test
  public void testSearchByNameStartingWith() {
    placeService.deleteAll();
    placeService.saveAttraction(attractionDTO, location);
    placeService.saveAttraction(attractionDTO, location);
    placeService.saveAttraction(attractionDTO, location);
    placeService.saveAttraction(attractionDTO, location);
    placeService.saveAttraction(attractionDTO, location);
    List<Place> search = placeService.searchByNameStartingWith("Lugano");
    assertEquals(5, search.size());
  }
}
