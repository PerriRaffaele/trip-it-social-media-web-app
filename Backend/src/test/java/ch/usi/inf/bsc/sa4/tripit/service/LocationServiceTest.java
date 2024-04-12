package ch.usi.inf.bsc.sa4.tripit.service;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.LocationDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Location;
import ch.usi.inf.bsc.sa4.tripit.repository.LocationRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("The Location Service test")
public class LocationServiceTest {

  @Autowired private LocationService locationService;

  @Autowired private LocationRepository locationRepository;
  private LocationDTO locationDTO;
  private LocationDTO locationDTO1;
  private LocationDTO locationDTO2;
  private LocationDTO locationDTO3;
  private Location location;

  @BeforeEach
  void setup() {
    locationDTO = new LocationDTO(46.003677, 8.951052, "Switzerland", "Ticino");
    locationDTO1 = new LocationDTO(51.4700223, -0.4542955, "United Kingdom", "London");
    locationDTO2 = new LocationDTO(40.6397511, -73.7789256, "United States", "New York");
    locationDTO3 = new LocationDTO(48.8566969, 2.3514616, "France", "Paris");
    location = new Location("Switzerland", "Ticino", 8.951052, 46.003677);
  }

  public static boolean equals(Location location, LocationDTO locationDTO) {
    double epsilon = 0.0001d;

    if (locationDTO == null) return false;

    return location.getCountry().equals(locationDTO.getCountry())
        && location.getRegion().equals(locationDTO.getRegion())
        && Math.abs(location.getLongitude() - locationDTO.getLongitude()) < epsilon
        && Math.abs(location.getLatitude() - locationDTO.getLatitude()) < epsilon;
  }

  @DisplayName("after creating a new location")
  @Nested
  class WhenCreatingANewLocation {

    @DisplayName("New location should not be null")
    @Test
    public void testNewLocationNotNull() {
      Location newLocation = locationService.saveLocation(locationDTO);
      assertNotNull(newLocation);
    }

    @DisplayName("New location should not be null")
    @Test
    public void testNewLocationAddOne() {
      List<Location> getAll = locationService.getAll();
      int originalSize = getAll.size();
      locationService.saveLocation(locationDTO);
      getAll = locationService.getAll();
      assertEquals(getAll.size(), originalSize + 1, "New location saved so it should not be null");
    }

    @DisplayName("New location should be equal to the one saved")
    @Test
    public void testNewLocationEquals() {
      Location newLocation = locationService.saveLocation(locationDTO);
      boolean result = LocationServiceTest.equals(newLocation, locationDTO);
      assertTrue(result);
    }

    @Test
    public void testGetAll() {
      List<Location> getAll = locationService.getAll();
      int originalSize = getAll.size();
      locationService.saveLocation(locationDTO);
      getAll = locationService.getAll();
      assertEquals(getAll.size(), originalSize + 1, "New location saved so it should not be null");
    }

    @Test
    public void testSaveLocation() {
      Location newLocation = locationService.saveLocation(locationDTO);
      boolean result = LocationServiceTest.equals(newLocation, locationDTO);
      assertTrue(result);
    }
  }
}
