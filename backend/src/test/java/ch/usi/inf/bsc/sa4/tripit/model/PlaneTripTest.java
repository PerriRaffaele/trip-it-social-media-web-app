package ch.usi.inf.bsc.sa4.tripit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Plane Trip Test")
public class PlaneTripTest {
  Location location1;
  Location location2;
  Airport airport1;
  Airport airport2;

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
    airport1 = new Airport("Zurigo", location1, "ZRH");
    airport2 = new Airport("Lugano", location2, "LUG");
    journey = null;
    // Define the time zone
    ZoneId zone = ZoneId.of("America/Los_Angeles");
    // Define the start and end date-time
    start = ZonedDateTime.of(2023, 3, 23, 9, 0, 0, 0, zone);
    end = ZonedDateTime.of(2023, 3, 23, 17, 0, 0, 0, zone);
    image = new Image("gggg", 100, 100, "desc", "url", "cropping");
  }

  @DisplayName("Get Airport From")
  @Test
  void getAirportFrom() {

    PlaneTrip Trip =
        new PlaneTrip(journey, start, end, title, description, airport1, airport2, "BA113", image);
    assertEquals(airport1, Trip.getFrom());
  }

  @DisplayName("Get Airport To")
  @Test
  void getAirportTo() {
    PlaneTrip Trip =
        new PlaneTrip(journey, start, end, title, description, airport1, airport2, "BA113", image);
    assertEquals(airport2, Trip.getTo());
  }

  @DisplayName("Get Flight Number")
  @Test
  void getFlightNumber() {
    PlaneTrip Trip =
        new PlaneTrip(journey, start, end, title, description, airport1, airport2, "BA113", image);
    assertEquals("BA113", Trip.getFlightNumber());
  }

  @DisplayName("Get CO2 estimate")
  @Test
  void getCo2EstimateTest() {
    PlaneTrip trip =
        new PlaneTrip(journey, start, end, title, description, airport1, airport2, "BA113", image);
    trip.recalculateCo2Estimate();
    double lat1 = airport1.getLocation().getLatitude();
    ;
    double lat2 = airport2.getLocation().getLatitude();
    ;
    double lon1 = airport1.getLocation().getLongitude();
    ;
    double lon2 = airport2.getLocation().getLongitude();
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
    double averageCO2EmissionsPlane = 0.275;
    double co2Estimate1 = Math.round(rad * c * averageCO2EmissionsPlane * 1000.0) / 1000.0;
    assertEquals(trip.getCo2Estimate(), co2Estimate1);
  }

  @DisplayName("Testing CO2 emissions recalculation")
  @Test
  void recalculateCo2EstimateTest() {
    PlaneTrip trip =
        new PlaneTrip(journey, start, end, title, description, airport1, airport2, "BA113", image);
    trip.recalculateCo2Estimate(); // should be not 0
    double co2Estimate1 = trip.getCo2Estimate();

    trip.setTo(airport1); // now from and to are the same airport
    trip.recalculateCo2Estimate(); // should be 0
    double co2Estimate2 = trip.getCo2Estimate();

    trip.setFrom(airport2); // now from and to are like before but switched
    trip.recalculateCo2Estimate(); // should be the same as co2Estimate1
    double co2Estimate3 = trip.getCo2Estimate();

    assertTrue(co2Estimate1 != 0 && co2Estimate2 == 0 && co2Estimate3 == co2Estimate1);
  }
}
