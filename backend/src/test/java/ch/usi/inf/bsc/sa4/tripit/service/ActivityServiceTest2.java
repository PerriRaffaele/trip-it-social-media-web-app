package ch.usi.inf.bsc.sa4.tripit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.*;
import ch.usi.inf.bsc.sa4.tripit.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ActivityServiceTest2 {

  @Autowired private JourneyService journeyService;

  @Autowired private ActivityService activityService;

  @Autowired private PlaceService placeService;

  @Autowired private LocationService locationService;

  @Autowired private ImageService imageService;
  private CreateUserDTO userDTO;
  private User user;
  private ObjectMapper mapper;
  @Autowired private UserService userService;
  private JourneyDTO journeyDTO;
  private Journey journey;
  private ZoneId zone;
  private ZonedDateTime start, end;
  private Station luganoCentro;
  private Station zurigoCentro;
  private Airport luganoAirportFrom;
  private Airport luganoAirportTo;
  private Attraction attraction;
  private VisitDTO visitDTO;
  private PlaneTripDTO planeDTO;
  private TrainTripDTO trainDTO;
  private Image image;

  @BeforeEach
  void setup() {
    activityService.deleteAll();
    Image img = new Image("gggg", 100, 100, "desc", "url", "cropping");
    this.image = imageService.save(img);
    userDTO = new CreateUserDTO("1", "usertest", "bio", "email", "", "username");
    user = userService.createUser(userDTO);
    journeyDTO = new JourneyDTO("myJourney", user.getId(), "1", 2, image.getId());
    journey = journeyService.createJourney(journeyDTO);
    journey = journeyService.updateJourney(journey);
    double a = 11.11;
    double b = 12.13;
    Location airport = new Location("Switzerland", "Lugano", 46.00455, 8.91037);
    Location loc = new Location("china", "Beijing", a, b);
    airport = locationService.saveLocation(airport.toLocationDTO());
    luganoCentro = new Station("Lugano", airport);
    zurigoCentro = new Station("Zurigo", airport);
    luganoAirportFrom = new Airport("Lugano Airport", airport, "ZUR");
    luganoAirportTo = new Airport("Lugano Airport", airport, "LUG");
    attraction = new Attraction("peking", loc, "city");

    luganoCentro = placeService.saveStation((StationDTO) luganoCentro.toPlaceDTO(), airport);
    zurigoCentro = placeService.saveStation((StationDTO) zurigoCentro.toPlaceDTO(), airport);
    luganoAirportFrom =
        placeService.saveAirport((AirportDTO) luganoAirportFrom.toPlaceDTO(), airport);
    luganoAirportTo = placeService.saveAirport((AirportDTO) luganoAirportTo.toPlaceDTO(), airport);
    attraction = placeService.saveAttraction((AttractionDTO) attraction.toPlaceDTO(), loc);

    zone = ZoneId.of("America/Los_Angeles");
    // Define the start and end date-time
    start = ZonedDateTime.of(2023, 3, 23, 9, 0, 0, 0, zone);
    end = ZonedDateTime.of(2023, 3, 23, 17, 0, 0, 0, zone);
    visitDTO =
        new VisitDTO(
            "",
            journey.getId(),
            start,
            end,
            "activity1",
            "no",
            (AttractionDTO) attraction.toPlaceDTO(),
            "Visit",
            image.getId());
    trainDTO =
        new TrainTripDTO(
            "",
            journey.getId(),
            start,
            end,
            "activity1",
            "no",
            luganoCentro.toStationDTO(),
            zurigoCentro.toStationDTO(),
            "TrainTrip",
            2,
            image.getId());
    planeDTO =
        new PlaneTripDTO(
            "",
            journey.getId(),
            start,
            end,
            "activity1",
            "no",
            luganoAirportFrom.toPlaceDTO(),
            luganoAirportTo.toPlaceDTO(),
            "PlaneTrip",
            "111",
            3,
            image.getId());
    System.out.println(visitDTO.getStart().toString());
    System.out.println(visitDTO.getEnd().toString());
  }

  @Nested
  class testingActivityService {

    @Test
    public void testGetActivitiesByJourney() {
      Activity visit = activityService.toVisit(visitDTO);
      visit = activityService.saveVisit(visitDTO);
      journey.addActivity(0, visit);
      journey = journeyService.updateJourney(journey);

      List<Activity> activities = activityService.getActivitiesByJourney(journey);

      assertEquals(activities.size(), 1);
    }

    @Test
    public void testGetById() {
      Activity activity = activityService.saveVisit(visitDTO);
      Optional<Activity> activity1 = activityService.getById(activity.getId());
      assertEquals(activity1.get().getId(), activity.getId());
    }

    @Test
    public void testDeleteActivity() {
      Activity activity = activityService.saveVisit(visitDTO);
      activityService.deleteActivity(activity);
      List<Activity> activities = activityService.getAllActivities();
      assertEquals(0, activities.size());
    }

    @Test
    public void testUpdateActivity() {
      Activity activity = activityService.saveVisit(visitDTO);
      activity.setTitle("activity2");
      activityService.updateActivity(activity);
      Optional<Activity> activity1 = activityService.getById(activity.getId());
      assertEquals(activity1.get().getTitle(), "activity2");
    }

    @Test
    public void testEditToVisit() {
      Activity activity = activityService.saveVisit(visitDTO);
      EditVisitDTO visitDTO1 =
          new EditVisitDTO(
              "", journey.getId(), start, end, "activity2", "no", "Visit", image.getId());
      activityService.editToVisit(visitDTO1);
      Optional<Activity> activity1 = activityService.getById(activity.getId());
      assertEquals(activity1.get().getTitle(), "activity1");
    }

    @Test
    public void testEditToTrainTrip() {
      Activity activity = activityService.saveTrainTrip(trainDTO);
      EditTrainTripDTO trainDTO1 =
          new EditTrainTripDTO(
              "", journey.getId(), start, end, "activity2", "no", "TrainTrip", image.getId());
      activityService.editToTrainTrip(trainDTO1);
      Optional<Activity> activity1 = activityService.getById(activity.getId());
      assertEquals(activity1.get().getTitle(), "activity1");
    }

    @Test
    public void testEditToPlaneTrip() {
      Activity activity = activityService.savePlaneTrip(planeDTO);
      EditPlaneTripDTO planeDTO1 =
          new EditPlaneTripDTO(
              "",
              journey.getId(),
              start,
              end,
              "activity2",
              "no",
              "PlaneTrip",
              "111",
              image.getId());
      activityService.editToPlaneTrip(planeDTO1);
      Optional<Activity> activity1 = activityService.getById(activity.getId());
      assertEquals(activity1.get().getTitle(), "activity1");
    }

    @Test
    public void testToVisit() {
      Activity activity = activityService.saveVisit(visitDTO);
      VisitDTO visitDTO1 =
          new VisitDTO(
              "",
              journey.getId(),
              start,
              end,
              "activity2",
              "no",
              (AttractionDTO) attraction.toPlaceDTO(),
              "Visit",
              image.getId());
      activityService.toVisit(visitDTO1);
      Optional<Activity> activity1 = activityService.getById(activity.getId());
      assertEquals(activity1.get().getTitle(), "activity1");
    }

    @Test
    public void testSaveVisit() {
      Activity activity = activityService.saveVisit(visitDTO);
      Optional<Activity> activity1 = activityService.getById(activity.getId());
      assertEquals(activity1.get().getTitle(), "activity1");
    }

    @Test
    public void testSavePlaneTrip() {
      Activity activity = activityService.savePlaneTrip(planeDTO);
      Optional<Activity> activity1 = activityService.getById(activity.getId());
      assertEquals(activity1.get().getTitle(), "activity1");
    }

    @Test
    public void testToPlaneTrip() {
      Activity activity = activityService.savePlaneTrip(planeDTO);
      PlaneTripDTO planeDTO1 =
          new PlaneTripDTO(
              "",
              journey.getId(),
              start,
              end,
              "activity2",
              "no",
              luganoAirportFrom.toPlaceDTO(),
              luganoAirportTo.toPlaceDTO(),
              "PlaneTrip",
              "111",
              2,
              image.getId());
      activityService.toPlaneTrip(planeDTO1);
      Optional<Activity> activity1 = activityService.getById(activity.getId());
      assertEquals(activity1.get().getTitle(), "activity1");
    }

    @Test
    public void testSaveTrainTrip() {
      Activity activity = activityService.saveTrainTrip(trainDTO);
      Optional<Activity> activity1 = activityService.getById(activity.getId());
      assertEquals(activity1.get().getTitle(), "activity1");
    }

    @Test
    public void testToTrainTrip() {
      Activity activity = activityService.saveTrainTrip(trainDTO);
      TrainTripDTO trainDTO1 =
          new TrainTripDTO(
              "",
              journey.getId(),
              start,
              end,
              "activity2",
              "no",
              luganoCentro.toStationDTO(),
              zurigoCentro.toStationDTO(),
              "TrainTrip",
              3,
              image.getId());
      activityService.toTrainTrip(trainDTO1);
      Optional<Activity> activity1 = activityService.getById(activity.getId());
      assertEquals(activity1.get().getTitle(), "activity1");
    }

    @Test
    public void testGetAllActivities() {
      activityService.saveVisit(visitDTO);
      List<Activity> activities = activityService.getAllActivities();
      assertEquals(1, activities.size());
    }
  }
}
