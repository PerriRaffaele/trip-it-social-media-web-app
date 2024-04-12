package ch.usi.inf.bsc.sa4.tripit.service;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.*;
import ch.usi.inf.bsc.sa4.tripit.model.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("The Journey Service")
public class JourneyServiceTest {

  @Autowired private JourneyService journeyService;

  @Autowired private UserService userService;

  @Autowired private ActivityService activityService;
  @Autowired private PlaceService placeService;
  @Autowired private ImageService imageService;
  private JourneyDTO journeyDTO, journeyDTO1, journeyDTO2, journeyDTO3, journeyDTO4;
  private Journey journey4;
  private Visit visit;
  private Activity activity;
  private User user;
  private ZonedDateTime start, end;
  private ZoneId zone;
  private Attraction attraction;
  private Image image;

  @BeforeEach
  void setup() {
    CreateUserDTO userDTO = new CreateUserDTO("1", "usertest", "bio", "email", "", "username");
    this.user = userService.createUser(userDTO);
    this.user.makePublic();
    this.user = userService.updateUser(user);
    Image img = new Image("gggg", 100, 100, "desc", "url", "cropping");
    this.image = imageService.save(img);
    journeyDTO = new JourneyDTO("testJourney", this.user.getId(), "", 0, image.getId());
    journeyDTO1 = new JourneyDTO("testJourney1", this.user.getId(), "", 0, image.getId());
    journeyDTO2 = new JourneyDTO("random", this.user.getId(), "", 0, image.getId());
    journeyDTO3 = new JourneyDTO("Test", this.user.getId(), "", 0, image.getId());
    journeyDTO4 = new JourneyDTO("Test4", this.user.getId(), "", 0, image.getId());

    zone = ZoneId.of("America/Los_Angeles");
    start = ZonedDateTime.of(2023, 3, 23, 9, 0, 0, 0, zone);
    end = ZonedDateTime.of(2023, 3, 23, 17, 0, 0, 0, zone);

    double a = 11.11;
    double b = 12.13;

    Location loc = new Location("china", "Beijing", a, b);
    attraction = new Attraction("peking", loc, "city");
    Attraction attraction1 =
        placeService.saveAttraction((AttractionDTO) attraction.toPlaceDTO(), loc);
    journey4 = journeyService.createJourney(journeyDTO4);
    visit = new Visit(journey4, start, end, "visit", "good visit", attraction1, image);
    activity = activityService.saveVisit((VisitDTO) visit.toActivityDTO());
    journey4.addActivity(0, activity);
    journeyService.updateJourney(journey4);
  }

  @DisplayName("Testing getAll function")
  @Test
  public void testGetAll() {
    List<Journey> getAll = journeyService.getAll();
    Journey newJourney = journeyService.createJourney(journeyDTO);
    newJourney.addActivity(0, activity);
    newJourney = journeyService.updateJourney(newJourney);
    List<Journey> getAll2 = journeyService.getAll();
    boolean correctRes = getAll2.size() == 2 && getAll.size() == 1;
    assertTrue(correctRes);
    journeyService.deleteJourney(newJourney);
  }

  @DisplayName("after creating a new journey")
  @Nested
  class WhenCreatingANewJourney {

    @DisplayName("New journey should not be null")
    @Test
    public void testNewJourneyNotNull() {
      Journey newJourney = journeyService.createJourney(journeyDTO);
      assertNotNull(newJourney);
    }

    @DisplayName("New journey should not be null")
    @Test
    public void testNewJourneyNotNull2() {
      Journey newJourney2 = journeyService.createJourney(journeyDTO1);
      assertNotNull(newJourney2);
    }

    @Test
    public void testGetByID() {
      Journey newJourney = journeyService.createJourney(journeyDTO);
      String Id = newJourney.getId();
      journeyService.createJourney(journeyDTO1);
      journeyService.createJourney(journeyDTO2);
      Optional<Journey> idSearch = journeyService.getById(Id);
      assertEquals(idSearch.get().getId(), newJourney.getId());
    }

    @Test
    public void testSearchByTitleContaining() {
      journeyService.createJourney(journeyDTO1);
      journeyService.createJourney(journeyDTO2); // instance which doesn't contain the param
      journeyService.createJourney(journeyDTO); // Same keyword but has capital case
      List<Journey> containing = journeyService.searchByTitleContaining("test");
      assertEquals(2, containing.size());
    }

    @Test
    public void testGetUserById() {
      Journey newJourney = journeyService.createJourney(journeyDTO);
      Optional<User> readUser = journeyService.getUserById(newJourney.getId());
      assertEquals(readUser.get().getId(), user.getId());
    }

    @Test
    public void testEmptyUser() {
      Optional<User> emptyUser = journeyService.getUserById("1111");
      assertEquals(emptyUser, Optional.empty());
    }

    @Test
    public void testDeletedJourney() {
      Journey newJourney1 = journeyService.createJourney(journeyDTO1);
      journeyService.createJourney(journeyDTO2);
      journeyService.createJourney(journeyDTO3);
      journeyService.deleteJourney(newJourney1);
      assertEquals(journeyService.countNumberOfJourneys(user), 3);
    }

    @Test
    public void testGetJourneysOfUser() {
      List<Journey> journeys = journeyService.getJourneysOfUser(user);
      assertEquals(journeys.size(), 1);
    }

    @Test
    public void testCountNumberOfJourneys() {
      List<Journey> journeys = journeyService.getJourneysOfUser(user);
      assertEquals(journeys.size(), journeyService.countNumberOfJourneys(user));
    }

    @DisplayName("Count Total Number of Activities For User")
    @Test
    public void testCTNAFU() {
      int count = journeyService.countTotalNumberOfActivitiesForUser(user);
      assertEquals(count, 1);
    }
  }
}
