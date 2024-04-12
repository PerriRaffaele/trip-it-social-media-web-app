package ch.usi.inf.bsc.sa4.tripit.model;

import static org.junit.jupiter.api.Assertions.*;

import ch.usi.inf.bsc.sa4.tripit.service.JourneyService;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("The Journey Test")
public class JourneyTest {
  private Journey journey;

  private User user;
  private Visit visit;
  private Visit visit2;
  private Visit visit3;
  private ZonedDateTime start, end, start2, end2, start3, end3;
  private TrainTrip tript;
  private PlaneTrip tripp;
  private Image image;

  private JourneyService journeyService;

  @BeforeEach
  void setJourney() {
    image = new Image("gggg", 100, 100, "desc", "url", "cropping");
    this.user = User.from("1", "testname");
    this.journey = new Journey("myJourney", this.user, image);

    ZoneId zone = ZoneId.of("America/Los_Angeles");
    start = ZonedDateTime.of(2023, 3, 22, 9, 0, 0, 0, zone);
    end = ZonedDateTime.of(2023, 3, 23, 9, 0, 0, 0, zone);
    start2 = ZonedDateTime.of(2023, 3, 24, 9, 0, 0, 0, zone);
    start3 = ZonedDateTime.of(2023, 4, 24, 9, 0, 0, 0, zone);
    end2 = ZonedDateTime.of(2023, 3, 25, 9, 0, 0, 0, zone);
    end3 = ZonedDateTime.of(2023, 4, 25, 9, 0, 0, 0, zone);

    String description = "it was good";
    double a = 11.11;
    double b = 12.19;

    Location loc = new Location("china", "Beijing", a, b);
    Attraction attraction = new Attraction("peking", loc, "city");
    visit = new Visit(this.journey, start, end, "myTrip1", description, attraction, image);
    visit2 = new Visit(this.journey, start, end2, "myvisit2", description, attraction, image);
    visit3 = new Visit(this.journey, start3, end3, "myvisit3", description, attraction, image);

    Station from = new Station("Central Station", loc);
    Station to = new Station("Next Station", loc);
    tript = new TrainTrip(this.journey, start, end, "train trip", "train ride", from, to, image);

    Airport fromA = new Airport("Peking airport", loc, "PEK");
    Airport toA = new Airport("terminal 2", loc, "SHA");
    tripp =
        new PlaneTrip(
            this.journey, start2, end2, "plane trip", "plane ride", fromA, toA, "CH111", image);
    journey.addActivity(0, tripp);
  }

  @DisplayName("after setting up")
  @Nested
  class AfterSettingUpTests {
    @DisplayName("Testing addActivity function with getActivity function, pt.1")
    @Test
    void addActivityTest1() {
      assertEquals(
          journey.getActivity(0),
          tripp,
          "Correctly implemented add and get function for activities");
    }

    @DisplayName("Testing addActivity function with getActivity function, pt.2")
    @Test
    void addActivityTest2() {
      journey.addActivity(1, visit);
      journey.addActivity(2, tript);
      assertEquals(journey.getActivity(1), visit, "Further test of correct implementation");
    }

    @DisplayName("Testing addActivity function with getActivity function, pt.3")
    @Test
    void addActivityTest3() {
      journey.addActivity(1, visit);
      journey.addActivity(2, tript);
      assertEquals(journey.getActivity(2), tript, "Success");
    }

    @DisplayName("Testing startTime pt.1")
    @Test
    void testStartTime1() {
      assertEquals(journey.startTime(), start2, "Correct Start time");
    }

    @DisplayName("Testing startTime pt.2")
    @Test
    void testStartTime2() {
      journey.addActivity(1, visit3);
      assertEquals(journey.startTime(), start2);
    }

    @DisplayName("Testing endTime pt.1")
    @Test
    void testEndTime1() {
      assertEquals(journey.endTime(), end2, "Correct End Time");
    }

    @DisplayName("Testing endTime pt.2")
    @Test
    void testEndTime2() {
      journey.addActivity(1, visit3);
      assertEquals(journey.endTime(), end3);
    }

    @DisplayName("Testing remove activity and numberOfActivities function")
    @Test
    void removeActivityTest() {
      journey.removeActivity(0);
      assertEquals(journey.numberOfActivities(), 0, "successfully removed the embedded activity");
    }

    @DisplayName(
        "editActivity function is tested but it is not proper as it will be tested correctly in controller")
    @Test
    void editActivityTest() {
      journey.editActivity(0, tript);
      assertEquals(journey.getActivity(0), tript);
    }

    // tests isValidActivity, checkInvalidDate, and numberOfActivities functions
    @DisplayName("Testing isValidActivity with new starting activity")
    @Test
    void testIsValidActivityStart() {
      assertEquals(journey.isValidActivity(tript), 0, "Inserted as the new starting activity");
    }

    @DisplayName("Testing isValidActivity with an invalid activity")
    @Test
    void testIsValidActivityInvalid() {
      assertEquals(journey.isValidActivity(visit2), -1, "Invalid activity detected");
    }

    @DisplayName("Testing isValidActivity with an activity that goes to the end")
    @Test
    void testIsValidActivityEnd() {
      assertEquals(journey.isValidActivity(visit3), 1, "Inserted at the end of the list");
    }

    @DisplayName("isValidEdit function with correct activity")
    @Test
    void testIsValidEditCorrect() {
      tripp.setId("1");
      tript.setId("1");
      visit.setId("1");
      visit2.setId("1");
      assertEquals(journey.isValidEdit(tripp, tript), 0, "Correct Position");
    }

    @DisplayName("isValidEdit function with empty journey")
    @Test
    void testIsValidEditEmpty() {
      tripp.setId("1");
      tript.setId("1");
      visit.setId("1");
      visit2.setId("1");
      journey.removeActivity(0);
      assertEquals(journey.isValidEdit(tripp, tript), -1, "Empty so uneditable");
    }

    @DisplayName("isValidEdit function with invalid date")
    @Test
    void testIsValidEditInvalid() {
      tripp.setId("1");
      tript.setId("1");
      visit.setId("1");
      visit2.setId("1");
      journey.removeActivity(0);
      journey.addActivity(0, tripp);
      assertEquals(journey.isValidEdit(visit, visit2), 0, "Invalid Date");
    }

    @DisplayName("test get duration of journey pt.1")
    @Test
    void testGetDurationTest1() {
      Duration duration = Duration.between(start2, end2);
      assertEquals(journey.getDuration(), duration);
    }

    @DisplayName("test get duration of journey pt.2")
    @Test
    void testGetDurationTest2() {
      journey.addActivity(1, visit3);
      Duration duration = Duration.between(start2, end3);
      assertEquals(journey.getDuration(), duration);
    }

    @DisplayName("test get duration of activity")
    @Test
    void testGetDurationActivity() {
      Duration duration = Duration.between(start2, end2);
      assertEquals(journey.getActivity(0).getDuration(), duration);
    }

    @Test
    void testCompareToEqual() {
      Journey journey2 = new Journey("myJourney2", user, image);
      journey2.addActivity(0, tripp);
      assertEquals(journey.compareTo(journey2), 0, "Start at the same time");
    }

    @Test
    void testCompareToBefore() {
      Journey journey3 = new Journey("myJourney2", user, image);
      journey3.addActivity(0, visit2);
      assertEquals(journey.compareTo(journey3), -1, "Journey starts before our journey");
    }

    @Test
    void testCompareToAfter() {
      Journey journey2 = new Journey("myJourney2", user, image);
      journey2.addActivity(0, visit2);
      assertEquals(journey2.compareTo(journey), 1, "Journey 2 starts after our journey");
    }

    @Test
    void testCheckInvalidDateValid() {
      assertFalse(journey.checkInvalidDate(0, visit3), "Valid Date");
    }

    @Test
    void testCheckInvalidDateInvalid() {
      assertTrue(journey.checkInvalidDate(0, visit2), "Invalid Date");
    }

    @DisplayName("Get CO2 estimate empty")
    @Test
    void testGetCo2EstimateEmpty() {
      assertEquals(journey.getCo2Estimate(), 0, "if journey is empty the co2 estimate should be 0");
    }

    @DisplayName("Get CO2 estimate")
    @Test
    void testGetCo2Estimate() {
      tripp.recalculateCo2Estimate();
      double co2Plane = tripp.getCo2Estimate();
      journey.addActivity(0, tripp);
      journey.recalculateCo2Estimate();
      assertEquals(
          journey.getCo2Estimate(),
          co2Plane,
          "if there is only one trip, the co2 estimate should be equal to that of the trip");
    }

    @DisplayName("Testing CO2 emissions recalculation on a journey with one activity")
    @Test
    void testRecalculateCo2EstimateOneActivity() {
      tript.recalculateCo2Estimate();
      double co2Train = tript.getCo2Estimate();
      journey.addActivity(0, tript);
      journey.recalculateCo2Estimate();
      assertEquals(
          journey.getCo2Estimate(),
          co2Train,
          "the co2 estimate should be equal to that of the one trip");
    }

    @DisplayName("Testing CO2 emissions recalculation on a journey with two activities")
    @Test
    void testRecalculateCo2EstimateTwoActivities() {
      tripp.recalculateCo2Estimate();
      double co2Plane = tripp.getCo2Estimate();
      tript.recalculateCo2Estimate();
      double co2Train = tript.getCo2Estimate();
      journey.addActivity(0, tripp);
      journey.recalculateCo2Estimate();
      journey.addActivity(1, tript);
      journey.recalculateCo2Estimate();
      double co2Sum = Math.round((co2Plane + co2Train) * 1000.0) / 1000.0;
      assertEquals(
          journey.getCo2Estimate(),
          co2Sum,
          "after adding a second trip, the co2 estimate should be the sum of the estimates of the trips");
    }
  }
}
