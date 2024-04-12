package ch.usi.inf.bsc.sa4.tripit.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.AirportDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.LocationDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.PlaneTripDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Image;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlaneTripDTOTest {

  LocationDTO location1;
  LocationDTO location2;
  LocationDTO location3;
  AirportDTO airport1;
  AirportDTO airport2;
  AirportDTO airport3;
  String title;
  String description;
  ZonedDateTime end;
  ZonedDateTime start;
  Image image;

  @BeforeEach
  void setUp() {
    location1 = new LocationDTO(47.376887, 8.541694, "Switzerland", "Zurigo");
    location2 = new LocationDTO(46.0036778, 8.951052, "Switzerland", "Lugano");
    location3 = new LocationDTO(45.630359759052105, 8.72567499527149, "Italy", "Milano");
    airport1 = new AirportDTO("Zurigo", location1, "ZRH", "airport", "1");
    airport2 = new AirportDTO("Lugano", location2, "LUG", "airport", "2");
    airport3 = new AirportDTO("Milano", location2, "MPX", "airport", "3");
    title = "";
    description = "";
    // Define the time zone
    ZoneId zone = ZoneId.of("America/Los_Angeles");
    // Define the start and end date-time
    start = ZonedDateTime.of(2023, 3, 23, 9, 0, 0, 0, zone);
    end = ZonedDateTime.of(2023, 3, 23, 17, 0, 0, 0, zone);
    image = new Image("gggg", 100, 100, "desc", "url", "cropping");
  }

  @DisplayName("Get AirportDTO From")
  @Test
  void getAirportFrom() {

    PlaneTripDTO trip =
        new PlaneTripDTO(
            "1",
            "1",
            start,
            end,
            title,
            description,
            airport1,
            airport2,
            "BA113",
            "plane",
            0.2,
            "ID001");
    assertEquals(airport1, trip.getFrom());
  }

  @DisplayName("Set AirportDTO From")
  @Test
  void setAirportFrom() {

    PlaneTripDTO trip =
        new PlaneTripDTO(
            "1",
            "1",
            start,
            end,
            title,
            description,
            airport1,
            airport2,
            "BA113",
            "plane",
            0.4,
            "ID001");
    trip.setFrom(airport3);
    assertEquals(airport3, trip.getFrom());
  }

  @DisplayName("Get AirportDTO To")
  @Test
  void getAirportTo() {
    PlaneTripDTO trip =
        new PlaneTripDTO(
            "1",
            "1",
            start,
            end,
            title,
            description,
            airport1,
            airport2,
            "BA113",
            "plane",
            3,
            "ID001");
    assertEquals(airport2, trip.getTo());
  }

  @DisplayName("Set AirportDTO To")
  @Test
  void setAirportTo() {
    PlaneTripDTO trip =
        new PlaneTripDTO(
            "1",
            "1",
            start,
            end,
            title,
            description,
            airport1,
            airport2,
            "BA113",
            "plane",
            2,
            "ID001");
    trip.setTo(airport3);
    assertEquals(airport3, trip.getTo());
  }

  @DisplayName("Get Flight Number")
  @Test
  void getFlightNumber() {
    PlaneTripDTO trip =
        new PlaneTripDTO(
            "1",
            "1",
            start,
            end,
            title,
            description,
            airport1,
            airport2,
            "BA113",
            "plane",
            3,
            "ID001");
    assertEquals("BA113", trip.getFlightNumber());
  }

  @DisplayName("Set Flight Number")
  @Test
  void setFlightNumber() {
    PlaneTripDTO trip =
        new PlaneTripDTO(
            "1",
            "1",
            start,
            end,
            title,
            description,
            airport1,
            airport2,
            "BA113",
            "plane",
            2,
            "ID001");
    trip.setFlightNumber("BA114");
    assertEquals("BA114", trip.getFlightNumber());
  }

  @DisplayName("Get Image")
  @Test
  void getImage() {
    PlaneTripDTO trip =
        new PlaneTripDTO(
            "1",
            "1",
            start,
            end,
            title,
            description,
            airport1,
            airport2,
            "BA113",
            "plane",
            3,
            "ID001");
    assertEquals("ID001", trip.getCoverId());
  }

  @DisplayName("Set Image")
  @Test
  void setImage() {
    PlaneTripDTO trip =
        new PlaneTripDTO(
            "1",
            "1",
            start,
            end,
            title,
            description,
            airport1,
            airport2,
            "BA113",
            "plane",
            2,
            "ID001");
    trip.setCoverId("ID002");
    assertEquals("ID002", trip.getCoverId());
  }
}
