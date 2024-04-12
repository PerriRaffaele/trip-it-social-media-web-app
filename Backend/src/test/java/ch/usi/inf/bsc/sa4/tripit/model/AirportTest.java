package ch.usi.inf.bsc.sa4.tripit.model;

import static com.mongodb.assertions.Assertions.assertFalse;
import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.LocationDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.PlaceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Airport test class")
public class AirportTest {

  String code;
  Location location;
  String name;
  Airport airport;

  PlaceDTO dataPlaceDTO =
      new PlaceDTO(
          "Zürich Airport",
          new LocationDTO(47.376887, 8.541694, "Switzerland", "Zürich"),
          "Airport",
          "1");

  private boolean checkDTOContent(PlaceDTO placeDTO) {

    return placeDTO.getName().equals(dataPlaceDTO.getName())
        && placeDTO.getLocationDTO().getCountry().equals(dataPlaceDTO.getLocationDTO().getCountry())
        && placeDTO.getLocationDTO().getRegion().equals(dataPlaceDTO.getLocationDTO().getRegion())
        && placeDTO.getLocationDTO().getLatitude() == dataPlaceDTO.getLocationDTO().getLatitude()
        && placeDTO.getLocationDTO().getLongitude() == dataPlaceDTO.getLocationDTO().getLongitude()
        && placeDTO.getType().equals(dataPlaceDTO.getType());
  }

  @BeforeEach
  void setUp() {
    code = "ZRH";
    location = new Location("Switzerland", "Zürich", 47.376887, 8.541694);
    name = "Zürich Airport";
    airport = new Airport(name, location, code);
  }

  @DisplayName("Airport get code")
  @Test
  public void testGetCode() {
    assertEquals("ZRH", airport.getCode());
  }

  @DisplayName("Airport set code")
  @Test
  public void testSetCode() {
    airport.setCode("MPX");
    assertEquals("MPX", airport.getCode());
  }

  @DisplayName("Airport to place DTO")
  @Test
  public void testToPlaceDTO() {
    PlaceDTO result = airport.toPlaceDTO();
    assertTrue(checkDTOContent(result));
  }

  @DisplayName("Airport equals null")
  @Test
  public void testEqualsAirportNull() {
    Location location1 = new Location("country1", "region1", 12.5, 3.2);
    Airport airport1 = new Airport("name1", location1, "xyz");
    boolean result = airport1.equalsAirport(null);
    assertFalse(result);
  }

  @DisplayName("Airport not equal")
  @Test
  public void testEqualsAirportFalse() {
    Location location1 = new Location("country1", "region1", 12.5, 3.2);
    location1.setId("1");
    Airport airport1 = new Airport("name1", location1, "xyz");
    airport1.setId("12");
    Airport airport2 = new Airport("name2", location1, "xyz");
    airport2.setId("23");
    assertFalse(airport1.equalsAirport(airport2));
  }

  @DisplayName("Airport equal")
  @Test
  public void testEqualsAirportTrue() {
    Location location1 = new Location("country1", "region1", 12.5, 3.2);
    location1.setId("1");
    Airport airport1 = new Airport("name1", location1, "xyz");
    airport1.setId("12");
    Airport airport2 = new Airport("name1", location1, "xyz");
    airport2.setId("12");
    boolean result = airport1.equalsAirport(airport2);
    assertTrue(result);
  }
}
