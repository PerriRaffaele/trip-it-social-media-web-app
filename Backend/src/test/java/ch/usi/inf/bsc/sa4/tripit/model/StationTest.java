package ch.usi.inf.bsc.sa4.tripit.model;

import static com.mongodb.assertions.Assertions.assertFalse;
import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.LocationDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.PlaceDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.StationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StationTest {

  Station station;
  PlaceDTO dataPlaceDTO =
      new PlaceDTO(
          "Zurich City",
          new LocationDTO(47.376887, 8.541694, "Switzerland", "Zurich"),
          "Station",
          "");

  @BeforeEach
  void setUp() {
    station = new Station(dataPlaceDTO.getName(), dataPlaceDTO.getLocationDTO().toLocation());
  }

  boolean checkDTOContent(PlaceDTO placeDTO) {

    return placeDTO.getName().equals(dataPlaceDTO.getName())
        && placeDTO.getLocationDTO().getCountry().equals(dataPlaceDTO.getLocationDTO().getCountry())
        && placeDTO.getLocationDTO().getRegion().equals(dataPlaceDTO.getLocationDTO().getRegion())
        && placeDTO.getLocationDTO().getLatitude() == dataPlaceDTO.getLocationDTO().getLatitude()
        && placeDTO.getLocationDTO().getLongitude() == dataPlaceDTO.getLocationDTO().getLongitude()
        && placeDTO.getType().equals(dataPlaceDTO.getType());
  }

  @Test
  public void testToPlaceDTO() {
    PlaceDTO placeDTO = station.toPlaceDTO();
    assertTrue(checkDTOContent(placeDTO));
  }

  @Test
  public void testToStationDTO() {
    StationDTO stationDTO = station.toStationDTO();
    assertTrue(checkDTOContent(stationDTO));
  }

  @Test
  public void testEqualsStationNull() {
    Location location1 = new Location("country1", "region1", 12.5, 3.2);
    Station station1 = new Station("name1", location1);
    boolean result = station1.equalsStation(null);
    assertFalse(result);
  }

  @Test
  public void testEqualsStationFalse() {
    Location location1 = new Location("country1", "region1", 12.5, 3.2);
    location1.setId("1");
    Station station1 = new Station("name1", location1);
    station1.setId("12");
    Station station2 = new Station("name2", location1);
    station2.setId("23");
    boolean result = station1.equalsStation(station2);
    assertFalse(result);
  }

  @Test
  public void testEqualsStationTrue() {
    Location location1 = new Location("country1", "region1", 12.5, 3.2);
    location1.setId("1");
    Station station1 = new Station("name1", location1);
    station1.setId("12");
    Station station2 = new Station("name1", location1);
    station2.setId("12");
    boolean result = station1.equalsStation(station2);
    assertTrue(result);
  }
}
