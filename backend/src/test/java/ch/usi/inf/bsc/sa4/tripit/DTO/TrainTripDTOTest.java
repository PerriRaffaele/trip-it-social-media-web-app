package ch.usi.inf.bsc.sa4.tripit.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.LocationDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.StationDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.TrainTripDTO;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TrainTripDTOTest {

  LocationDTO location1;
  LocationDTO location2;
  LocationDTO location3;
  StationDTO station1;
  StationDTO station2;
  StationDTO station3;
  String title;
  String description;
  ZonedDateTime end;
  ZonedDateTime start;

  @BeforeEach
  void setUp() {
    location1 = new LocationDTO(47.376887, 8.541694, "Switzerland", "Zurigo");
    location2 = new LocationDTO(46.0036778, 8.951052, "Switzerland", "Lugano");
    location3 = new LocationDTO(45.630359759052105, 8.72567499527149, "Italy", "Milano");
    station1 = new StationDTO("Zurigo", location1, "station", "1");
    station2 = new StationDTO("Lugano", location2, "station", "2");
    station1 = new StationDTO("Milano", location3, "station", "3");
    title = "";
    description = "";
    // Define the time zone
    ZoneId zone = ZoneId.of("America/Los_Angeles");
    // Define the start and end date-time
    start = ZonedDateTime.of(2023, 3, 23, 9, 0, 0, 0, zone);
    end = ZonedDateTime.of(2023, 3, 23, 17, 0, 0, 0, zone);
  }

  @DisplayName("Get StationDTO From")
  @Test
  void getStationFrom() {

    TrainTripDTO trip =
        new TrainTripDTO(
            "1", "1", start, end, title, description, station1, station2, "train", 3, "img.png");
    assertEquals(station1, trip.getFrom());
  }

  @DisplayName("Set StationDTO From")
  @Test
  void setStationFrom() {

    TrainTripDTO trip =
        new TrainTripDTO(
            "1", "1", start, end, title, description, station1, station1, "train", 4, "img.png");
    trip.setFrom(station3);
    assertEquals(station3, trip.getFrom());
  }

  @DisplayName("Get StationDTO To")
  @Test
  void getStationTo() {
    TrainTripDTO trip =
        new TrainTripDTO(
            "1", "1", start, end, title, description, station1, station2, "train", 2, "img.png");
    assertEquals(station2, trip.getTo());
  }

  @DisplayName("Set StationDTO To")
  @Test
  void setStationTo() {
    TrainTripDTO trip =
        new TrainTripDTO(
            "1", "1", start, end, title, description, station1, station1, "train", 1, "img.png");
    trip.setTo(station3);
    assertEquals(station3, trip.getTo());
  }
}
