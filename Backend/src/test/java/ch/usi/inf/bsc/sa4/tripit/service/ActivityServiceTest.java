package ch.usi.inf.bsc.sa4.tripit.service;

import static com.mongodb.assertions.Assertions.assertNull;
import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.*;
import ch.usi.inf.bsc.sa4.tripit.model.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("The Activity Service")
public class ActivityServiceTest {

  @Autowired private ActivityService activityService;
  @Autowired private PlaceService placeService;
  @Autowired private JourneyService journeyService;
  @Autowired private LocationService locationService;
  @Autowired private ImageService imageService;
  private JourneyDTO journeyDTO;
  private VisitDTO visitDTO;
  private PlaneTripDTO planeTripDTO;
  private TrainTripDTO trainTripDTO;

  private VisitDTO wrongVisitDTO;
  private PlaneTripDTO wrongPlaneTripDTO;
  private TrainTripDTO wrongTrainTripDTO;

  private Journey journey;
  @Autowired private UserService userService;
  private User user;
  private CreateUserDTO userDTO;
  private ZonedDateTime start, end;
  private Attraction attraction;

  private Attraction attraction2;
  private Airport luganoAirport;
  private Airport luganoAirport2;
  private Station luganoCentro;
  private Station luganoCentro2;

  private Station wrongLuganoCentro;
  private Airport wrongLuganoAirport;
  private Attraction wrongAttraction;
  private ZoneId zone;

  private EditVisitDTO editVisitDTO;

  private EditTrainTripDTO editTrainTripDTO;

  private EditPlaneTripDTO editPlaneTripDTO;
  private Image image;

  static boolean compareDTOAndObject(Activity activity, ActivityDTO activityDTO) {
    if (activity == null || activityDTO == null) return false;

    if (!(activity.getDescription().equals(activityDTO.getDescription())
        && activity.getTitle().equals(activityDTO.getTitle())
        && activity.getStart().equals(activityDTO.getStart())
        && activity.getEnd().equals(activityDTO.getEnd())
        && activity.getJourney().getId().equals(activityDTO.getJourneyId()))) return false;

    if (activity instanceof Visit visit && activityDTO instanceof VisitDTO visitDTO) {
      return visit.getAttraction().getId().equals(visitDTO.getAttraction().getId());
    }

    if (activity instanceof TrainTrip trainTrip
        && activityDTO instanceof TrainTripDTO trainTripDTO) {
      return trainTrip.getFrom().getId().equals(trainTripDTO.getFrom().getId())
          && trainTrip.getTo().getId().equals(trainTripDTO.getTo().getId());
    }

    if (activity instanceof PlaneTrip planeTrip
        && activityDTO instanceof PlaneTripDTO planeTripDTO) {
      return planeTrip.getFrom().getId().equals(planeTripDTO.getFrom().getId())
          && planeTrip.getTo().getId().equals(planeTripDTO.getTo().getId())
          && planeTrip.getFlightNumber().equals(planeTripDTO.getFlightNumber());
      //              && Math.abs(planeTrip.getCo2Estimate() - planeTripDTO.getCo2Estimate()) <
      // epsilon;
    }

    return false;
  }

  @BeforeEach
  void setup() {
    Image img = new Image("gggg", 100, 100, "desc", "url", "cropping");
    this.image = imageService.save(img);
    userDTO = new CreateUserDTO("1", "usertest", "bio", "email", "", "username");
    user = userService.createUser(userDTO);
    journeyDTO = new JourneyDTO("myJourney", user.getId(), "", 2.0, image.getId());
    journey = journeyService.createJourney(journeyDTO);
    journey = journeyService.updateJourney(journey);
    zone = ZoneId.of("America/Los_Angeles");
    start = ZonedDateTime.parse("2011-12-03T10:15:30+01:00");
    end = ZonedDateTime.parse("2011-12-04T10:15:30+01:00");

    double a = 11.11;
    double b = 12.13;

    double attractionLat = 2;
    double attractionLong = 1;

    Location airportLocLugano = new Location("Switzerland", "Lugano", 46.00455, 8.91037);
    Location stationLocLugano = new Location("Switzerland", "Lugano", 46.00455, 8.91037);
    Location attractionLocChina = new Location("china", "Beijing", a, b);

    airportLocLugano = locationService.saveLocation(airportLocLugano.toLocationDTO());
    stationLocLugano = locationService.saveLocation(stationLocLugano.toLocationDTO());
    attractionLocChina = locationService.saveLocation(attractionLocChina.toLocationDTO());

    luganoCentro = new Station("Lugano", stationLocLugano);
    luganoCentro2 = new Station("Bellinzona", stationLocLugano);
    luganoAirport = new Airport("Lugano Airport", airportLocLugano, "LUG");
    luganoAirport2 = new Airport("Lugano Airport", attractionLocChina, "LUG");
    attraction = new Attraction("peking", attractionLocChina, "city");
    attraction2 = new Attraction("Milano", stationLocLugano, "milano.com");

    wrongLuganoCentro = new Station("Lugano", stationLocLugano);
    wrongLuganoAirport = new Airport("Lugano Airport", airportLocLugano, "LUG");
    wrongAttraction = new Attraction("peking", attractionLocChina, "city");

    wrongLuganoCentro.setId("wrong id");
    wrongLuganoAirport.setId("wrong id");
    wrongAttraction.setId("wrong id");

    luganoCentro =
        placeService.saveStation((StationDTO) luganoCentro.toPlaceDTO(), stationLocLugano);
    luganoAirport =
        placeService.saveAirport((AirportDTO) luganoAirport.toPlaceDTO(), airportLocLugano);
    attraction =
        placeService.saveAttraction((AttractionDTO) attraction.toPlaceDTO(), attractionLocChina);

    // Define the start and end date-time
    ZonedDateTime start = ZonedDateTime.of(2023, 3, 23, 9, 0, 0, 0, zone);
    ZonedDateTime end = ZonedDateTime.of(2023, 3, 23, 17, 0, 0, 0, zone);
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
    planeTripDTO =
        new PlaneTripDTO(
            "",
            journey.getId(),
            start,
            end,
            "airtrip",
            "",
            luganoAirport.toPlaceDTO(),
            luganoAirport.toPlaceDTO(),
            "LUG",
            "plane",
            1.0,
            image.getId());
    trainTripDTO =
        new TrainTripDTO(
            "",
            journey.getId(),
            start,
            end,
            "trainTrip",
            "",
            luganoCentro.toStationDTO(),
            luganoCentro.toStationDTO(),
            "train",
            1.0,
            image.getId());
    wrongVisitDTO =
        new VisitDTO(
            "",
            "not an id ",
            start,
            end,
            "activity1",
            "no",
            (AttractionDTO) wrongAttraction.toPlaceDTO(),
            "Visit",
            image.getId());
    wrongPlaneTripDTO =
        new PlaneTripDTO(
            "",
            "not an id ",
            start,
            end,
            "airtrip",
            "",
            wrongLuganoAirport.toPlaceDTO(),
            wrongLuganoAirport.toPlaceDTO(),
            "LUG",
            "plane",
            0.3,
            image.getId());
    wrongTrainTripDTO =
        new TrainTripDTO(
            "",
            "not an id ",
            start,
            end,
            "trainTrip",
            "",
            wrongLuganoCentro.toStationDTO(),
            wrongLuganoCentro.toStationDTO(),
            "train",
            0.3,
            image.getId());

    editVisitDTO =
        new EditVisitDTO(
            "1", journey.getId(), start, end, "activity1", "no", "Visit", image.getId());
    editPlaneTripDTO =
        new EditPlaneTripDTO(
            "2", journey.getId(), start, end, "airtrip", "", "LUG", "plane", image.getId());
    editTrainTripDTO =
        new EditTrainTripDTO(
            "3", journey.getId(), start, end, "trainTrip", "", "train", image.getId());
  }

  @DisplayName("after creating a new journey")
  @Nested
  class WhenCreatingNewActivity {

    // Not checking for id since we are creating a new visit from the DTO that is not yet in the
    // database.

    @Test
    @DisplayName("In case journey and place not found toVisit should fill the fields with null")
    public void testingToVisitNotFound() {
      Visit visit = (Visit) activityService.toVisit(wrongVisitDTO);
      assertTrue(visit.getJourney() == null && visit.getAttraction() == null);
    }

    @Test
    @DisplayName("In case journey and place not found toTrainTrip should fill the fields with null")
    public void testingToTrainTripNotFound() {
      TrainTrip trainTrip = (TrainTrip) activityService.toTrainTrip(wrongTrainTripDTO);
      assertTrue(
          trainTrip.getJourney() == null
              && trainTrip.getFrom() == null
              && trainTrip.getTo() == null);
    }

    @Test
    @DisplayName("In case journey and place not found toPlaneTrip should fill the fields with null")
    public void testingToPlaneTripNotFound() {
      PlaneTrip planeTrip = (PlaneTrip) activityService.toPlaneTrip(wrongPlaneTripDTO);
      assertTrue(
          planeTrip.getJourney() == null
              && planeTrip.getFrom() == null
              && planeTrip.getTo() == null);
    }

    @Test
    @DisplayName("should convert VisitDTO to Visit and save it")
    public void testingToVisit() {
      Visit visit = (Visit) activityService.toVisit(visitDTO);
      boolean result = ActivityServiceTest.compareDTOAndObject(visit, visitDTO);
      assertTrue(result);
    }

    @Test
    @DisplayName("should convert VisitDTO to Visit and save it")
    public void testingSavingVisit() {
      Visit visit1 = (Visit) activityService.saveVisit(visitDTO);
      Optional<Activity> visitOptional = activityService.getById(visit1.getId());
      Visit visit2 = (Visit) visitOptional.orElse(null);
      boolean result =
          ActivityServiceTest.compareDTOAndObject(visit2, visitDTO)
              && visit1.getId().equals(visit2.getId());
      assertTrue(result);
    }

    @Test
    @DisplayName("should convert planeTripDTO to planeTrip and save it")
    public void testingSavingPlaneTrip() {

      PlaneTrip planeTrip1 = (PlaneTrip) activityService.savePlaneTrip(planeTripDTO);
      Optional<Activity> planeTripOptional = activityService.getById(planeTrip1.getId());

      PlaneTrip planeTrip2 = (PlaneTrip) planeTripOptional.orElse(null);
      boolean result =
          ActivityServiceTest.compareDTOAndObject(planeTrip2, planeTripDTO)
              && planeTrip2.getId().equals(planeTrip1.getId());
      assertTrue(result);
    }

    @Test
    @DisplayName("should convert planeTripDTO to planeTrip and save it")
    public void testingSavingTrainTrip() {
      TrainTrip trainTrip1 = (TrainTrip) activityService.saveTrainTrip(trainTripDTO);
      Optional<Activity> trainTripOptional = activityService.getById(trainTrip1.getId());
      TrainTrip trainTrip2 = (TrainTrip) trainTripOptional.get();
      boolean result =
          ActivityServiceTest.compareDTOAndObject(trainTrip2, trainTripDTO)
              && trainTrip2.getId().equals(trainTrip1.getId());
      assertTrue(result);
    }

    @Test
    @DisplayName("should convert EditVisitDTO to Visit and update Visit")
    public void testingEditVisit() {
      // put initial activity in the DB
      Visit initialVisit = (Visit) activityService.saveVisit(visitDTO);
      // set up the edited version
      editVisitDTO =
          new EditVisitDTO(
              initialVisit.getId(),
              initialVisit.getJourney().getId(),
              initialVisit.getStart(),
              initialVisit.getEnd(),
              "new title",
              "new description",
              "Visit",
              initialVisit.getImageLink().getId());
      visitDTO =
          new VisitDTO(
              initialVisit.getId(),
              initialVisit.getJourney().getId(),
              initialVisit.getStart(),
              initialVisit.getEnd(),
              "new title",
              "new description",
              (AttractionDTO) initialVisit.getAttraction().toPlaceDTO(),
              "Visit",
              initialVisit.getImageLink().getId());
      Visit visit = (Visit) activityService.editToVisit(editVisitDTO);
      visit.setAttraction(initialVisit.getAttraction());
      // check correctness of editToVisit
      boolean result =
          ActivityServiceTest.compareDTOAndObject(visit, visitDTO)
              && visit.getId().equals(initialVisit.getId());
      assertTrue(result);
    }

    @Test
    @DisplayName("should convert EditVisitDTO to Visit and update Attraction accordingly")
    public void testingEditAttractionOfVisit() {
      // put initial activity in the DB
      Visit initialVisit = (Visit) activityService.saveVisit(visitDTO);
      // set up the edited version
      editVisitDTO =
          new EditVisitDTO(
              initialVisit.getId(),
              initialVisit.getJourney().getId(),
              initialVisit.getStart(),
              initialVisit.getEnd(),
              initialVisit.getTitle(),
              initialVisit.getDescription(),
              "Visit",
              initialVisit.getImageLink().getId());
      Visit visit = (Visit) activityService.editToVisit(editVisitDTO);
      boolean result = visit.getAttraction() == null;
      // change the attraction
      visit.setAttraction(attraction2);
      // update the visit
      Visit updatedVisit = (Visit) activityService.updateActivity(visit);
      // check if the place changed
      result =
          result
              && !initialVisit
                  .getAttraction()
                  .getName()
                  .equals(updatedVisit.getAttraction().getName());
      assertTrue(result);
    }

    @Test
    @DisplayName("should convert EditPlaneTrip to PlaneTrip ")
    public void testingEditPlaneTrip() {

      // put initial activity in the DB
      PlaneTrip initialPlaneTrip = (PlaneTrip) activityService.savePlaneTrip(planeTripDTO);

      // Define the time zone
      ZoneId zone = ZoneId.of("Africa/Addis_Ababa");
      // Define the start and end date-time
      ZonedDateTime start = ZonedDateTime.of(2023, 3, 23, 9, 0, 0, 0, zone);
      ZonedDateTime end = ZonedDateTime.of(2023, 3, 23, 17, 0, 0, 0, zone);

      // set up the edited version
      editPlaneTripDTO =
          new EditPlaneTripDTO(
              initialPlaneTrip.getId(),
              journey.getId(),
              start,
              end,
              initialPlaneTrip.getTitle(),
              initialPlaneTrip.getDescription(),
              "plane",
              "LUG",
              initialPlaneTrip.getImageLink().getId());
      planeTripDTO =
          new PlaneTripDTO(
              initialPlaneTrip.getId(),
              journey.getId(),
              start,
              end,
              initialPlaneTrip.getTitle(),
              initialPlaneTrip.getDescription(),
              (AirportDTO) initialPlaneTrip.getFrom().toPlaceDTO(),
              (AirportDTO) initialPlaneTrip.getTo().toPlaceDTO(),
              "LUG",
              "plane",
              1.0,
              initialPlaneTrip.getImageLink().getId());

      PlaneTrip planeTrip = (PlaneTrip) activityService.editToPlaneTrip(editPlaneTripDTO);
      planeTrip.setFrom(initialPlaneTrip.getFrom());
      planeTrip.setTo(initialPlaneTrip.getTo());
      planeTrip.setStart(start);
      planeTrip.setEnd(end);

      // check correctness of editToPlaneTrip
      boolean result =
          ActivityServiceTest.compareDTOAndObject(planeTrip, planeTripDTO)
              && planeTrip.getId().equals(initialPlaneTrip.getId());
      assertTrue(result);
    }

    @Test
    @DisplayName("should convert EditPlaneTrip to PlaneTrip and edit from and to")
    public void testingEditFromAndToOfPlaneTrip() {

      // put initial activity in the DB
      PlaneTrip initialPlaneTrip = (PlaneTrip) activityService.savePlaneTrip(planeTripDTO);
      PlaneTripDTO temp = planeTripDTO;
      temp.setId(initialPlaneTrip.getId());
      PlaneTrip updatedPlaneTrip = (PlaneTrip) activityService.savePlaneTrip(temp);

      boolean result =
          initialPlaneTrip.getFrom().equalsAirport(updatedPlaneTrip.getFrom())
              && initialPlaneTrip.getTo().equalsAirport(updatedPlaneTrip.getTo());

      updatedPlaneTrip.setFrom(luganoAirport2);
      updatedPlaneTrip.setTo(luganoAirport2);

      // update the planeTrip in the DB
      updatedPlaneTrip = (PlaneTrip) activityService.updateActivity(updatedPlaneTrip);

      result =
          result
              && !initialPlaneTrip.getFrom().equals(updatedPlaneTrip.getFrom())
              && !initialPlaneTrip.getTo().equals(updatedPlaneTrip.getTo());
      assertTrue(result);
    }

    @Test
    @DisplayName("should convert EditTrainTrip to TrainTrip ")
    public void testingEditTrainTrip() {

      // put initial activity in the DB
      TrainTrip initialTraintrip = (TrainTrip) activityService.saveTrainTrip(trainTripDTO);

      // set up the edited version
      editTrainTripDTO =
          new EditTrainTripDTO(
              initialTraintrip.getId(),
              journey.getId(),
              initialTraintrip.getStart(),
              initialTraintrip.getEnd(),
              initialTraintrip.getTitle(),
              initialTraintrip.getDescription(),
              "train",
              initialTraintrip.getImageLink().getId());
      trainTripDTO =
          new TrainTripDTO(
              initialTraintrip.getId(),
              journey.getId(),
              initialTraintrip.getStart(),
              initialTraintrip.getEnd(),
              initialTraintrip.getTitle(),
              initialTraintrip.getDescription(),
              (StationDTO) initialTraintrip.getFrom().toPlaceDTO(),
              (StationDTO) initialTraintrip.getTo().toPlaceDTO(),
              "train",
              1.0,
              initialTraintrip.getImageLink().getId());

      TrainTrip trainTrip = (TrainTrip) activityService.editToTrainTrip(editTrainTripDTO);
      trainTrip.setFrom(initialTraintrip.getFromStation());
      trainTrip.setTo(initialTraintrip.getToStation());
      // check correctness of editTrainTrip
      boolean result =
          ActivityServiceTest.compareDTOAndObject(trainTrip, trainTripDTO)
              && trainTrip.getId().equals(initialTraintrip.getId());
      assertTrue(result);
    }

    @Test
    @DisplayName("should convert EditTrainTripDTO to TrainTrip and edit from and to")
    public void testingEditFromAndToOfTrainTrip() {

      // put initial activity in the DB
      TrainTrip initialTrainTrip = (TrainTrip) activityService.saveTrainTrip(trainTripDTO);
      TrainTripDTO temp = trainTripDTO;
      temp.setId(initialTrainTrip.getId());
      TrainTrip updatedTrainTrip = (TrainTrip) activityService.saveTrainTrip(temp);

      boolean result =
          initialTrainTrip.getFromStation().equalsStation(updatedTrainTrip.getFromStation())
              && initialTrainTrip.getToStation().equalsStation(updatedTrainTrip.getToStation());

      updatedTrainTrip.setFrom(luganoCentro2);
      updatedTrainTrip.setTo(luganoCentro2);

      // update the trainTrip in the DB
      updatedTrainTrip = (TrainTrip) activityService.updateActivity(updatedTrainTrip);
      result =
          result
              && !initialTrainTrip.getFrom().equals(updatedTrainTrip.getFrom())
              && !initialTrainTrip.getTo().equals(updatedTrainTrip.getTo());
      assertTrue(result);
    }

    @Test
    @DisplayName("should get all activities in DB")
    public void testingGetAllActivities() {

      activityService.saveVisit(visitDTO);
      activityService.savePlaneTrip(planeTripDTO);
      activityService.saveTrainTrip(trainTripDTO);

      List<Activity> activities = activityService.getAllActivities();

      assertEquals(activities.size(), 3);
    }

    @Test
    @DisplayName("should get all activities of a journey")
    public void testingGetActivitiesByJourney() {
      Activity visit = activityService.saveVisit(visitDTO);
      Activity plane = activityService.savePlaneTrip(planeTripDTO);
      Activity train = activityService.saveTrainTrip(trainTripDTO);

      journey.addActivity(journey.getActivities().size(), visit);
      journey.addActivity(journey.getActivities().size(), plane);
      journey.addActivity(journey.getActivities().size(), train);
      journey = journeyService.updateJourney(journey);

      List<Activity> activities = activityService.getActivitiesByJourney(journey);
      assertEquals(activities.size(), 3);
    }

    @Test
    public void testingEmptyJourney() {
      EditVisitDTO editVisitDTO1 =
          new EditVisitDTO("1", "", start, end, "activity1", "no", "Visit", image.getId());
      EditPlaneTripDTO editPlaneTripDTO1 =
          new EditPlaneTripDTO("2", "", start, end, "airtrip", "", "LUG", "plane", image.getId());
      EditTrainTripDTO editTrainTripDTO1 =
          new EditTrainTripDTO("3", "", start, end, "trainTrip", "", "train", image.getId());
      Activity visitNull = activityService.editToVisit(editVisitDTO1);
      Activity trainNull = activityService.editToTrainTrip(editTrainTripDTO1);
      Activity planeNull = activityService.editToPlaneTrip(editPlaneTripDTO1);
      assertNull(visitNull.getJourney());
      assertNull(trainNull.getJourney());
      assertNull(planeNull.getJourney());
    }
  }
}
