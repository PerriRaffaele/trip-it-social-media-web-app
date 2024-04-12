package ch.usi.inf.bsc.sa4.tripit.model;

import static org.junit.jupiter.api.Assertions.*;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.LocationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocationTest {

  Location location;

  private boolean compareLocationFields(
      Location location, String country, String region, double latitude, double longitude) {
    return country.equals(location.getCountry())
        && region.equals(location.getRegion())
        && latitude == location.getLatitude()
        && longitude == location.getLongitude();
  }

  private boolean compareLocationDTOFields(
      LocationDTO locationDTO, String country, String region, double latitude, double longitude) {
    return country.equals(locationDTO.getCountry())
        && region.equals(locationDTO.getRegion())
        && latitude == locationDTO.getLatitude()
        && longitude == locationDTO.getLongitude();
  }

  @BeforeEach
  void setUp() {
    location = new Location("Switzerland", "Zurigo", 47.376887, 8.541694);
  }

  @Test
  public void testGetCountry() {
    assertEquals("Switzerland", location.getCountry());
  }

  @Test
  public void testSetCountry() {
    location.setCountry("Italy");
    assertEquals("Italy", location.getCountry());
  }

  @Test
  public void testGetRegion() {
    assertEquals("Zurigo", location.getRegion());
  }

  @Test
  public void testSetRegion() {
    location.setRegion("Milano");
    assertEquals("Milano", location.getRegion());
  }

  @Test
  public void testGetLatitude() {
    assertEquals(47.376887, location.getLatitude());
  }

  @Test
  public void testSetLatitude() {
    location.setLatitude(45.464664);
    assertEquals(45.464664, location.getLatitude());
  }

  @Test
  public void testGetLongitude() {
    assertEquals(8.541694, location.getLongitude());
  }

  @Test
  public void testSetLongitude() {
    location.setLongitude(9.188540);
    assertEquals(9.188540, location.getLongitude());
  }

  @Test
  public void testToLocation() {
    LocationDTO dto = new LocationDTO(47.376887, 8.541694, "Switzerland", "Zurigo");
    Location result = Location.toLocation(dto);
    assertTrue(compareLocationFields(result, "Switzerland", "Zurigo", 47.376887, 8.541694));
  }

  @Test
  public void testToLocationDTO() {
    LocationDTO result = location.toLocationDTO();
    assertTrue(compareLocationDTOFields(result, "Switzerland", "Zurigo", 47.376887, 8.541694));
  }
}
