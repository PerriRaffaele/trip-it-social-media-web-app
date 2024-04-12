package ch.usi.inf.bsc.sa4.tripit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Train Trip Test")
public class TrainTripTest {
  Location location1;
  Location location2;
  Station station1;
  Station station2;

  String id;

  Journey journey;

  String title;

  String description;

  ZonedDateTime end;

  ZonedDateTime start;
  Image image;

  @BeforeEach
  void setUp() {
    location1 = new Location("Switzerland", "Zurigo", 47.376887, 8.541694);
    location2 = new Location("Switzerland", "Lugano", 46.0036778, 8.951052);
    station1 = new Station("Zurigo", location1);
    station2 = new Station("Lugano", location2);
    description = "";
    title = "";
    id = "";
    journey = null;
    // Define the time zone
    ZoneId zone = ZoneId.of("America/Los_Angeles");
    // Define the start and end date-time
    start = ZonedDateTime.of(2023, 3, 23, 9, 0, 0, 0, zone);
    end = ZonedDateTime.of(2023, 3, 23, 17, 0, 0, 0, zone);
    image = new Image("gggg", 100, 100, "desc", "url", "cropping");
  }

  @DisplayName("Get Station From")
  @Test
  void getStationFrom() {

    TrainTrip Trip =
        new TrainTrip(journey, start, end, title, description, station1, station2, image);

    assertEquals(station1, Trip.getFrom());
  }

  @DisplayName("Get Station To")
  @Test
  void getStationTo() {
    TrainTrip Trip =
        new TrainTrip(journey, start, end, title, description, station1, station2, image);

    assertEquals(station2, Trip.getTo());
  }

  @DisplayName("Get CO2 estimate")
  @Test
  void getCo2EstimateTest() {
    TrainTrip trip =
        new TrainTrip(journey, start, end, title, description, station1, station2, image);
    trip.recalculateCo2Estimate();
    double lat1 = station1.getLocation().getLatitude();
    ;
    double lat2 = station2.getLocation().getLatitude();
    ;
    double lon1 = station1.getLocation().getLongitude();
    ;
    double lon2 = station2.getLocation().getLongitude();
    ;
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    lat1 = Math.toRadians(lat1);
    lat2 = Math.toRadians(lat2);
    double a =
        Math.pow(Math.sin(dLat / 2), 2)
            + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
    double rad = 6371;
    double c = 2 * Math.asin(Math.sqrt(a));
    double averageCO2EmissionsTrain = 0.049;
    double co2Estimate1 = Math.round(rad * c * averageCO2EmissionsTrain * 1000.0) / 1000.0;
    assertEquals(trip.getCo2Estimate(), co2Estimate1);
  }

  @DisplayName("Testing CO2 emissions getter and recalculation")
  @Test
  void testRecalculateCo2Estimate() {
    TrainTrip trip =
        new TrainTrip(journey, start, end, title, description, station1, station2, image);
    trip.recalculateCo2Estimate();
    double co2Estimate1 = trip.getCo2Estimate();
    trip.setTo(station1);
    trip.setFrom(station2);
    trip.recalculateCo2Estimate();
    assertEquals(
        trip.getCo2Estimate(),
        co2Estimate1,
        "the estimate from airport2 to airport1 should be the same as the estimate from airport1 to airport2");
  }

  @DisplayName(
      "Testing CO2 emissions getter and recalculation, with departure and arrival in the same place")
  @Test
  void testRecalculateCo2EstimateZero() {
    TrainTrip trip =
        new TrainTrip(journey, start, end, title, description, station1, station2, image);
    trip.recalculateCo2Estimate();
    trip.setTo(station1);
    trip.recalculateCo2Estimate();
    assertEquals(
        trip.getCo2Estimate(), 0, "if from and to are the same the co2 estimate should be 0");
  }
}
