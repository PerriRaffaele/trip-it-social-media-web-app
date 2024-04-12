package ch.usi.inf.bsc.sa4.tripit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.*;
import ch.usi.inf.bsc.sa4.tripit.model.*;
import ch.usi.inf.bsc.sa4.tripit.service.ActivityService;
import ch.usi.inf.bsc.sa4.tripit.service.JourneyService;
import ch.usi.inf.bsc.sa4.tripit.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("The Activity controller")
public class ActivityControllerTest {

  @MockBean private static ActivityService activityService;
  @MockBean private JourneyService journeyService;
  @MockBean private NotificationService notificationService;
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  private static Journey journey;
  private static Visit visit;

  private static PlaneTrip planeTrip;

  private static NotificationDTO notificationDTO;

  private static List<Activity> activities;

  private static ArrayList<Activity> activities1;

  private static TrainTrip trainTrip;
  private static ZonedDateTime start, end;
  private static Attraction attraction;

  private static Attraction attraction2;
  private static Airport luganoAirport;
  private static Station luganoCentro;
  private static ZoneId zone;
  private static Journey journey3;

  private static User user1, user2;

  @BeforeAll
  public static void dataSetUp() {
    // Define the start and end date-time
    ActivityControllerTest.zone = ZoneId.of("America/Los_Angeles");
    ActivityControllerTest.start = ZonedDateTime.of(2023, 3, 23, 9, 0, 0, 0, zone);
    ActivityControllerTest.end = ZonedDateTime.of(2023, 3, 23, 17, 0, 0, 0, zone);

    double a = 11.11;
    double b = 12.13;

    Image image = new Image("skmfke", 100, 100, "desc", "url", "cropping");

    Location airportLocLugano = new Location("Switzerland", "Lugano", 46.00455, 8.91037);
    Location stationLocLugano = new Location("Switzerland", "Lugano", 46.00455, 8.91037);
    Location attractionLocChina = new Location("china", "Beijing", a, b);

    ActivityControllerTest.luganoCentro = new Station("Lugano", stationLocLugano);
    ActivityControllerTest.luganoAirport = new Airport("Lugano Airport", airportLocLugano, "LUG");
    ActivityControllerTest.attraction = new Attraction("peking", attractionLocChina, "city");
    ActivityControllerTest.attraction2 = new Attraction("Milano", stationLocLugano, "milano.com");
    ActivityControllerTest.journey = new Journey("journey1", new User("gina"), image);
    ActivityControllerTest.visit =
        new Visit(journey, start, end, "activity1", "no", attraction, image);

    visit.setId("1");
    ActivityControllerTest.planeTrip =
        new PlaneTrip(
            journey, start, end, "airtrip", "", luganoAirport, luganoAirport, "LUG", image);
    planeTrip.setId("2");
    ActivityControllerTest.trainTrip =
        new TrainTrip(journey, start, end, "trainTrip", "", luganoCentro, luganoCentro, image);
    trainTrip.setId("3");
    activities1 = new ArrayList<Activity>();
    activities1.add(visit);
    user1 = new User("1", "Gio", "BIO", "B@usi.ch", "Bonetto");
    user2 = new User("2", "Gionni", "BIO", "Busi@usi.ch", "Cattivetto");
    journey3 = new Journey("myJourney3", user1, activities1);
    activities = new ArrayList<Activity>();
    activities.add(visit);
    activities.add(planeTrip);
    activities.add(trainTrip);
    notificationDTO = new NotificationDTO("n1", "u1", "u2", "request", "friendrequest");
  }

  @BeforeEach
  void setUp() throws JsonProcessingException {
    doNothing().when(activityService).deleteActivity(any(Activity.class));
    given(activityService.getAllActivities()).willReturn(activities1);
    given(activityService.getById("1")).willReturn(Optional.of(visit));
    given(activityService.getById("activityNotFound")).willReturn(Optional.empty());
    given(activityService.getById("activityNotFound")).willReturn(Optional.empty());
    given(journeyService.getById("journey1")).willReturn(Optional.of(journey));
    given(journeyService.getById("myJourney3")).willReturn(Optional.of(journey3));
    given(journeyService.getById("journeyNotFound")).willReturn(Optional.empty());
    given(activityService.getActivitiesByJourney(journey3)).willReturn(activities1);
  }

  @Test
  @DisplayName("method get all")
  public void testGetAll() throws Exception {
    MvcResult result =
        this.mockMvc
            .perform(get("/activities"))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = result.getResponse().getContentAsString();
    List<Visit> activities1 = objectMapper.readValue(content, new TypeReference<List<Visit>>() {});
    assertEquals(activities1.get(0).getTitle(), "activity1");
  }

  @Test
  @DisplayName("get activity by id, first not found while second found")
  public void testGetById() throws Exception {
    this.mockMvc
        .perform(get("/activities/{id}", "activityNotFound"))
        .andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc
            .perform(get("/activities/1"))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    String content = result.getResponse().getContentAsString();
    Visit visit1 = objectMapper.readValue(content, Visit.class);
    assertEquals(attraction.getId(), visit1.getAttraction().getId());
  }

  @Test
  @DisplayName("method delete activity, with a non-existing activity")
  public void testDeleteNonExActivity() throws Exception {
    this.mockMvc.perform(delete("/activities/activityNotFound")).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("method delete activity, with a correct activity")
  public void testDeleteActivity() throws Exception {
    this.mockMvc.perform(delete("/activities/1")).andExpect(status().isOk());
  }

  @Test
  @DisplayName("method get activities of journey, first not found, second yes")
  public void testGetActivitiesOfJourney() throws Exception {
    this.mockMvc
        .perform(get("/activities/journey/journeyNotFound"))
        .andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc
            .perform(get("/activities/journey/myJourney3"))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = result.getResponse().getContentAsString();
    List<VisitDTO> activities1 =
        objectMapper.readValue(content, new TypeReference<List<VisitDTO>>() {});

    assertEquals(1, activities1.size());
  }
}
