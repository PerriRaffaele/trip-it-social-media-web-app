package ch.usi.inf.bsc.sa4.tripit.controller;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.*;
import ch.usi.inf.bsc.sa4.tripit.model.*;
import ch.usi.inf.bsc.sa4.tripit.service.ActivityService;
import ch.usi.inf.bsc.sa4.tripit.service.JourneyService;
import ch.usi.inf.bsc.sa4.tripit.service.PlaceService;
import ch.usi.inf.bsc.sa4.tripit.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
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
@DisplayName("The journey controller")
class JourneyControllerTest {

  @MockBean private JourneyService journeyService;

  @MockBean private UserService userService;

  @MockBean private ActivityService activityService;

  @MockBean private PlaceService placeService;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private static Journey journey1,
      updatedJourney,
      updatedJourney2,
      journey2,
      journey3,
      journey4,
      journey5;
  private static JourneyDTO journeyDTO;
  private static User user1, user2;
  private static List<Journey> journeys, user1Journeys, searchResult;
  private static ArrayList<Activity> activities, newActivities;
  private static ZoneId zone;
  private static ZonedDateTime start, end;
  private static Location airport;
  private static Station luganoCentro, zurigoCentro;
  private static Airport luganoAirportFrom, luganoAirportTo;
  public static Attraction attraction;

  private static Visit visit, visitDB, visitPut;

  private static EditVisitDTO visitDTO2;

  private static PlaneTrip planeTrip, planeDB, planePut;
  private static TrainTrip trainTrip, trainPut, trainDB;
  private static EditTrainTripDTO trainTripDTO;
  private static EditPlaneTripDTO planeTripDTO;
  private static Image image;

  @BeforeAll
  public static void dataSetUp() {
    user1 = new User("1", "Gio", "BIO", "B@usi.ch", "Bonetto");
    user2 = new User("2", "Hun", "BIO1", "H@usi.ch", "TKartist");
    image = new Image("gggg", 100, 100, "desc", "url", "cropping");
    image.setId("img1");
    journey1 = new Journey("myJourney1", user1, image);
    journey2 = new Journey("myJourney2", user2, image);
    journeys = new ArrayList<Journey>();
    journeys.add(journey1);
    journeys.add(journey2);
    user1Journeys = new ArrayList<Journey>();
    user1Journeys.add(journey1);
    searchResult = new ArrayList<Journey>();
    searchResult.add(journey1);
    searchResult.add(journey2);
    zone = ZoneId.of("America/Los_Angeles");
    // Define the start and end date-time
    start = ZonedDateTime.of(2023, 3, 23, 9, 0, 0, 0, zone);
    end = ZonedDateTime.of(2023, 3, 23, 17, 0, 0, 0, zone);
    airport = new Location("Switzerland", "Lugano", 46.00455, 8.91037);
    luganoCentro = new Station("Lugano", airport);
    zurigoCentro = new Station("Zurigo", airport);
    luganoAirportFrom = new Airport("Lugano Airport", airport, "ZUR");
    luganoAirportTo = new Airport("Lugano Airport", airport, "LUG");
    attraction = new Attraction("peking", airport, "city");
    journeyDTO = new JourneyDTO("myJourney1", "1", "", 0.1, "ID001");
    visit = new Visit(journey2, start, end, "visit1", "good", attraction, image);
    planeTrip =
        new PlaneTrip(
            journey2,
            start,
            end,
            "plane1",
            "goodplane",
            luganoAirportFrom,
            luganoAirportTo,
            "JKL111",
            image);
    planeDB =
        new PlaneTrip(
            journey2,
            start,
            end,
            "plane1",
            "goodplane",
            luganoAirportFrom,
            luganoAirportTo,
            "JKL111",
            image);
    planePut =
        new PlaneTrip(
            journey2,
            start,
            end,
            "plane1",
            "goodplane",
            luganoAirportFrom,
            luganoAirportTo,
            "JKL111",
            image);
    planeDB.setId("a100");
    planePut.setId("a101");
    trainTrip =
        new TrainTrip(
            journey2, start, end, "train1", "goodtrain", luganoCentro, zurigoCentro, image);
    trainDB =
        new TrainTrip(
            journey2, start, end, "train1", "goodtrain", luganoCentro, zurigoCentro, image);
    trainDB.setId("a10");
    trainPut =
        new TrainTrip(
            journey2, start, end, "train1", "goodtrain", luganoCentro, zurigoCentro, image);
    trainPut.setId("a11");
    visitDB = new Visit(journey2, start, end, "visit1", "good", attraction, image);
    visitDB.setId("a1");
    visitPut = new Visit(journey2, start, end, "visitPut", "goodPut", attraction, image);
    visitPut.setId("a2");
    activities = new ArrayList<Activity>();
    activities.add(visitDB);
    newActivities = new ArrayList<Activity>();
    journey3 = new Journey("myJourney3", user1, activities);
    journey4 = new Journey("myJourney4", user1, image);
    journey5 = new Journey("myJourney5", user2, image);
    updatedJourney = new Journey("myJourney2", user2, activities);
    updatedJourney2 = new Journey("myJourney2", user2, newActivities);
    visitDTO2 =
        new EditVisitDTO("a3", "j3", start, end, "visitEdit1", "goodVisit", "visit", "img1");
    trainTripDTO =
        new EditTrainTripDTO(
            "a4", "j3", start, end, "trainTripEdit1", "goodTrainEdit1", "train", "img1");
    planeTripDTO =
        new EditPlaneTripDTO(
            "a5", "j3", start, end, "planeTripEdit1", "goodPlaneEdit1", "JKL111", "plane", "img1");
  }

  @BeforeEach
  void setUp() throws JsonProcessingException {
    given(journeyService.getAll()).willReturn(journeys);
    given(journeyService.getJourneysOfUser(user1)).willReturn(user1Journeys);
    given(journeyService.getById("j2")).willReturn(Optional.of(journey2));
    given(journeyService.getById("j3")).willReturn(Optional.of(journey3));
    given(journeyService.getById("j4")).willReturn(Optional.of(journey4));
    given(journeyService.getById("j5")).willReturn(Optional.of(journey5));
    given(journeyService.getById("j1")).willReturn(Optional.empty());
    given(journeyService.searchByTitleContaining("Journey")).willReturn(searchResult);
    given(journeyService.createJourney(any())).willReturn(journey1);
    given(journeyService.updateJourney(journey2)).willReturn(updatedJourney);
    given(journeyService.updateJourney(any())).willReturn(updatedJourney2);
    given(userService.getById("3")).willReturn(Optional.empty());
    given(userService.getById("1")).willReturn(Optional.of(user1));
    given(activityService.toVisit(any())).willReturn(visit);
    given(activityService.saveVisit(any())).willReturn(visit);
    given(activityService.toPlaneTrip(any())).willReturn(planeTrip);
    given(activityService.savePlaneTrip(any())).willReturn(planeTrip);
    given(activityService.toTrainTrip(any())).willReturn(trainTrip);
    given(activityService.saveTrainTrip(any())).willReturn(trainTrip);
    given(activityService.getById("a1")).willReturn(Optional.of(visit));
    given(activityService.getById("a2")).willReturn(Optional.of(visit));
    given(activityService.getById("a3")).willReturn(Optional.of(visitPut));
    given(activityService.getById("a4")).willReturn(Optional.of(trainPut));
    given(activityService.getById("a5")).willReturn(Optional.of(planePut));
    given(activityService.editToVisit(any())).willReturn(visitPut);
    given(activityService.editToTrainTrip(any())).willReturn(trainPut);
    given(activityService.editToPlaneTrip(any())).willReturn(planePut);
    given(activityService.getActivitiesByJourney(any())).willReturn(activities);
    Mockito.doNothing().when(activityService).deleteActivity(any());
    given(placeService.getById("e1")).willReturn(Optional.empty());
    given(placeService.getById("e2")).willReturn(Optional.of(luganoCentro));
    given(placeService.getById("e3")).willReturn(Optional.of(attraction));
    given(placeService.getById("e4")).willReturn(Optional.of(zurigoCentro));
    given(placeService.getById("e5")).willReturn(Optional.of(luganoAirportFrom));
    given(placeService.getById("e6")).willReturn(Optional.of(luganoAirportTo));
  }

  @Test
  public void testGetAllJourney() throws Exception {
    MvcResult result =
        this.mockMvc
            .perform(get("/journey/all"))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = result.getResponse().getContentAsString();
    List<JourneyDTO> allJourney =
        objectMapper.readValue(content, new TypeReference<List<JourneyDTO>>() {});
    assertEquals(2, allJourney.size());
  }

  @Test
  public void testGetUserJourneys() throws Exception {
    this.mockMvc.perform(get("/journey/3/journeys")).andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc
            .perform(get("/journey/1/journeys"))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = result.getResponse().getContentAsString();
    List<JourneyDTO> u1Journey =
        objectMapper.readValue(content, new TypeReference<List<JourneyDTO>>() {});
    assertEquals(1, u1Journey.size());
  }

  @Test
  public void testGetById() throws Exception {
    this.mockMvc.perform(get("/journey/j1")).andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc
            .perform(get("/journey/j2"))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = result.getResponse().getContentAsString();
    JourneyDTO journeyDTO = objectMapper.readValue(content, JourneyDTO.class);
    assertEquals("2", journeyDTO.getUserId());
  }

  @Test
  public void testSearch() throws Exception {
    String partialTitle = "Journey";
    MvcResult result =
        this.mockMvc
            .perform(get("/journey/search").param("partialTitle", partialTitle))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = result.getResponse().getContentAsString();
    List<JourneyDTO> partialSearch =
        objectMapper.readValue(content, new TypeReference<List<JourneyDTO>>() {});
    assertEquals(partialSearch.size(), 2);
  }

  @Test
  public void testAddJourney() throws Exception {
    String jsonJourney = objectMapper.writeValueAsString(journeyDTO);
    this.mockMvc
        .perform(post("/journey/3").contentType(MediaType.APPLICATION_JSON).content(jsonJourney))
        .andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc
            .perform(
                post("/journey/1").contentType(MediaType.APPLICATION_JSON).content(jsonJourney))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = result.getResponse().getContentAsString();
    JourneyDTO journeyDTO1 = objectMapper.readValue(content, JourneyDTO.class);
    assertEquals(journeyDTO1.getTitle(), "myJourney1");
  }

  @Test
  public void testAddActivitiesWrongJourney() throws Exception {
    String jsonVisit = objectMapper.writeValueAsString(visit.toActivityDTO());
    this.mockMvc
        .perform(
            post("/journey/j1/activities/visit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonVisit))
        .andExpect(status().isNotFound());
    String jsonPlane = objectMapper.writeValueAsString(planeTrip.toActivityDTO());
    this.mockMvc
        .perform(
            post("/journey/j1/activities/plane")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPlane))
        .andExpect(status().isNotFound());
    String jsonTrain = objectMapper.writeValueAsString(trainTrip.toActivityDTO());
    this.mockMvc
        .perform(
            post("/journey/j1/activities/train")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTrain))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testAddVisit() throws Exception {
    String jsonVisit = objectMapper.writeValueAsString(visit.toActivityDTO());
    MvcResult result =
        this.mockMvc
            .perform(
                post("/journey/j2/activities/visit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonVisit))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = result.getResponse().getContentAsString();
    JourneyDTO journeyDTO1 = objectMapper.readValue(content, JourneyDTO.class);
    assertEquals(journeyDTO1.getTitle(), "myJourney2");
  }

  @Test
  public void testAddVisitV2() throws Exception {
    String jsonVisit = objectMapper.writeValueAsString(visit.toActivityDTO());
    MvcResult result =
        this.mockMvc
            .perform(
                post("/journey/j3/activities/visit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonVisit))
            .andExpectAll(
                status().isBadRequest(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = result.getResponse().getContentAsString();
    JourneyDTO journeyDTO2 = objectMapper.readValue(content, JourneyDTO.class);
    assertEquals(journeyDTO2.getTitle(), "myJourney3");
  }

  @Test
  public void testAddPlaneTrip() throws Exception {
    String jsonPlane = objectMapper.writeValueAsString(planeTrip.toActivityDTO());
    MvcResult result =
        this.mockMvc
            .perform(
                post("/journey/j4/activities/plane")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonPlane))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = result.getResponse().getContentAsString();
    JourneyDTO journeyDTO1 = objectMapper.readValue(content, JourneyDTO.class);
    assertEquals(journeyDTO1.getTitle(), "myJourney4");
  }

  @Test
  public void testAddPlaneTripV2() throws Exception {
    String jsonPlane = objectMapper.writeValueAsString(planeTrip.toActivityDTO());
    MvcResult result =
        this.mockMvc
            .perform(
                post("/journey/j3/activities/plane")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonPlane))
            .andExpectAll(
                status().isBadRequest(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = result.getResponse().getContentAsString();
    JourneyDTO journeyDTO2 = objectMapper.readValue(content, JourneyDTO.class);
    assertEquals(journeyDTO2.getTitle(), "myJourney3");
  }

  @Test
  public void testAddTrainTrip() throws Exception {
    String jsonTrain = objectMapper.writeValueAsString(trainTrip.toActivityDTO());
    MvcResult result =
        this.mockMvc
            .perform(
                post("/journey/j5/activities/train")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonTrain))
            .andExpectAll(
                status().isOk(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = result.getResponse().getContentAsString();
    JourneyDTO journeyDTO1 = objectMapper.readValue(content, JourneyDTO.class);
    assertEquals(journeyDTO1.getTitle(), "myJourney5");
  }

  @Test
  public void testAddTrainTripV2() throws Exception {
    String jsonTrain = objectMapper.writeValueAsString(trainTrip.toActivityDTO());
    MvcResult result =
        this.mockMvc
            .perform(
                post("/journey/j3/activities/train")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonTrain))
            .andExpectAll(
                status().isBadRequest(),
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    String content = result.getResponse().getContentAsString();

    JourneyDTO journeyDTO2 = objectMapper.readValue(content, JourneyDTO.class);
    assertEquals(journeyDTO2.getTitle(), "myJourney3");
  }

  @Test
  public void testDeleteJourney() throws Exception {
    this.mockMvc.perform(delete("/journey/j1")).andExpect(status().isNotFound());
    this.mockMvc.perform(delete("/journey/j2")).andExpect(status().isOk());
    this.mockMvc.perform(delete("/journey/j3")).andExpect(status().isOk());
  }

  @Test
  public void testEditVisit() throws Exception {
    VisitDTO visitDTO = (VisitDTO) visit.toActivityDTO();
    visitDTO.setId("a1");
    visitDTO.setJourneyId("j1");
    String jsonVisit = objectMapper.writeValueAsString(visitDTO);
    this.mockMvc
        .perform(
            put("/journey/visit/e1/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonVisit))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(
            put("/journey/visit/e2/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonVisit))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(
            put("/journey/visit/e3/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonVisit))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testEditTrainTrip() throws Exception {
    TrainTripDTO trainTripDTO = (TrainTripDTO) trainTrip.toActivityDTO();
    trainTripDTO.setId("a1");
    trainTripDTO.setJourneyId("j1");
    String jsonTrainTrip = objectMapper.writeValueAsString(trainTripDTO);
    this.mockMvc
        .perform(
            put("/journey/trainTrip/e1/e2/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTrainTrip))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(
            put("/journey/trainTrip/e2/e1/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTrainTrip))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(
            put("/journey/trainTrip/e3/e2/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTrainTrip))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(
            put("/journey/trainTrip/e2/e3/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTrainTrip))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(
            put("/journey/trainTrip/e2/e4/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTrainTrip))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testEditPlaneTrip() throws Exception {
    PlaneTripDTO planeTripDTO = (PlaneTripDTO) planeTrip.toActivityDTO();
    planeTripDTO.setId("a1");
    planeTripDTO.setJourneyId("j1");
    String jsonPlaneTrip = objectMapper.writeValueAsString(planeTripDTO);
    this.mockMvc
        .perform(
            put("/journey/planeTrip/e1/e5/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPlaneTrip))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(
            put("/journey/planeTrip/e5/e1/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPlaneTrip))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(
            put("/journey/planeTrip/e5/e2/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPlaneTrip))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(
            put("/journey/planeTrip/e2/e5/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPlaneTrip))
        .andExpect(status().isNotFound());
    this.mockMvc
        .perform(
            put("/journey/planeTrip/e5/e6/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPlaneTrip))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testEditVisitV2() throws Exception {
    given(activityService.updateActivity((Visit) any())).willReturn(visitPut);
    Activity temp = journey3.getActivities().get(0);
    journey3.removeActivity(0);
    journey3.addActivity(0, visitDB);
    String jsonVisit1 = objectMapper.writeValueAsString(visitDTO2);
    this.mockMvc
        .perform(
            put("/journey/visit/e3/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonVisit1))
        .andExpect(status().isBadRequest());
    journey3.getActivities().get(0).setId("a2");
    updatedJourney2.getActivities().add(0, visitPut);
    MvcResult result =
        this.mockMvc
            .perform(
                put("/journey/visit/e3/edit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonVisit1))
            .andExpect(status().isOk())
            .andReturn();
    String content = result.getResponse().getContentAsString();
    JourneyDTO newJourney = objectMapper.readValue(content, JourneyDTO.class);
    assertEquals("myJourney2", newJourney.getTitle());
    journey3.removeActivity(0);
    journey3.addActivity(0, temp);
  }

  @Test
  public void testEditTrainTripV2() throws Exception {
    given(activityService.updateActivity((TrainTrip) any())).willReturn(trainPut);
    journey3.removeActivity(0);
    journey3.addActivity(0, trainDB);
    String jsonTrain1 = objectMapper.writeValueAsString(trainTripDTO);
    this.mockMvc
        .perform(
            put("/journey/trainTrip/e2/e4/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTrain1))
        .andExpect(status().isBadRequest());
    if (!TrainTrip.class.isInstance(journey3.getActivities().get(0))) {
      journey3.getActivities().remove(0);
      journey3.getActivities().add(0, trainDB);
    }
    journey3.getActivities().get(0).setId("a11");
    if (!updatedJourney2.getActivities().isEmpty()) {
      updatedJourney2.getActivities().remove(0);
    }
    updatedJourney2.getActivities().add(0, trainPut);
    MvcResult result =
        this.mockMvc
            .perform(
                put("/journey/trainTrip/e2/e4/edit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonTrain1))
            .andExpect(status().isOk())
            .andReturn();
    String content = result.getResponse().getContentAsString();
    JourneyDTO newJourney = objectMapper.readValue(content, JourneyDTO.class);
    assertEquals("myJourney3", newJourney.getTitle());
  }

  @Test
  public void testEditPlaneTripV2() throws Exception {
    given(activityService.updateActivity((PlaneTrip) any())).willReturn(planePut);
    Activity temp = journey3.getActivities().get(0);
    journey3.removeActivity(0);
    journey3.addActivity(0, planeDB);
    String jsonPlane1 = objectMapper.writeValueAsString(planeTripDTO);
    this.mockMvc
        .perform(
            put("/journey/planeTrip/e5/e6/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPlane1))
        .andExpect(status().isBadRequest());
    if (!PlaneTrip.class.isInstance(journey3.getActivities().get(0))) {
      journey3.getActivities().remove(0);
      journey3.getActivities().add(0, planeDB);
    }
    journey3.getActivities().get(0).setId("a101");
    if (!updatedJourney2.getActivities().isEmpty()) {
      updatedJourney2.getActivities().remove(0);
    }
    updatedJourney2.getActivities().add(0, planePut);
    MvcResult result =
        this.mockMvc
            .perform(
                put("/journey/planeTrip/e5/e6/edit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonPlane1))
            .andExpect(status().isOk())
            .andReturn();
    String content = result.getResponse().getContentAsString();
    JourneyDTO newJourney = objectMapper.readValue(content, JourneyDTO.class);
    assertEquals("myJourney3", newJourney.getTitle());
    journey3.removeActivity(0);
    journey3.addActivity(0, temp);
  }

  @Test
  public void testUserOfJourney() throws Exception {
    this.mockMvc.perform(get("/journey/j1/user")).andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc.perform(get("/journey/j2/user")).andExpect(status().isOk()).andReturn();
    String response = result.getResponse().getContentAsString();
    UserDTO userDTO = objectMapper.readValue(response, UserDTO.class);
    assertNotNull(userDTO);
  }
}
