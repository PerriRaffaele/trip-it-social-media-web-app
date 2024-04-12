package ch.usi.inf.bsc.sa4.tripit.controller;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.EditPlaneTripDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.EditTrainTripDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.EditVisitDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.JourneyDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.PlaneTripDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.TrainTripDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.UserDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.VisitDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Activity;
import ch.usi.inf.bsc.sa4.tripit.model.Airport;
import ch.usi.inf.bsc.sa4.tripit.model.Attraction;
import ch.usi.inf.bsc.sa4.tripit.model.Journey;
import ch.usi.inf.bsc.sa4.tripit.model.Place;
import ch.usi.inf.bsc.sa4.tripit.model.PlaneTrip;
import ch.usi.inf.bsc.sa4.tripit.model.Station;
import ch.usi.inf.bsc.sa4.tripit.model.TrainTrip;
import ch.usi.inf.bsc.sa4.tripit.model.Visit;
import ch.usi.inf.bsc.sa4.tripit.service.ActivityService;
import ch.usi.inf.bsc.sa4.tripit.service.JourneyService;
import ch.usi.inf.bsc.sa4.tripit.service.NotificationService;
import ch.usi.inf.bsc.sa4.tripit.service.PlaceService;
import ch.usi.inf.bsc.sa4.tripit.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journey")
public class JourneyController {

  private final JourneyService journeyService;
  private final UserService userService;

  // to be uncommented once integrated with activity service
  private final ActivityService activityService;
  private final PlaceService placeService;
  private final NotificationService notificationService;

  @Autowired
  public JourneyController(
      JourneyService journeyService,
      UserService userService,
      ActivityService activityService,
      PlaceService placeService,
      NotificationService notificationService) {
    this.journeyService = journeyService;
    this.userService = userService;
    this.activityService = activityService;
    this.placeService = placeService;
    this.notificationService = notificationService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<JourneyDTO>> getAll() {
    var journeyDTOs = new ArrayList<JourneyDTO>();
    for (var journey : journeyService.getAll()) {
      journeyDTOs.add(journey.toJourneyDTO());
    }
    return ResponseEntity.ok(journeyDTOs);
  }

  @GetMapping("/{userId}/journeys")
  public ResponseEntity<List<JourneyDTO>> getUserJourneys(@PathVariable("userId") String id) {
    var targetUser = userService.getById(id);
    if (targetUser.isPresent()) {
      var unboxedUser = targetUser.get();

      List<JourneyDTO> journeyDTOs = new ArrayList<>();
      for (var journey : journeyService.getJourneysOfUser(unboxedUser)) {
        journeyDTOs.add(journey.toJourneyDTO());
      }
      return ResponseEntity.ok(journeyDTOs);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<JourneyDTO> getById(@PathVariable("id") String id) {
    var targetJourney = journeyService.getById(id);
    if (targetJourney.isPresent()) {
      return ResponseEntity.ok(targetJourney.get().toJourneyDTO());
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/search")
  public ResponseEntity<List<JourneyDTO>> byPartialTitle(
      @RequestParam("partialTitle") String partialTitle) {
    var allJourneyDTO =
        journeyService.searchByTitleContaining(partialTitle).stream()
            .map(journey -> journey.toJourneyDTO())
            .collect(Collectors.toList());
    return ResponseEntity.ok(allJourneyDTO);
  }

  @PostMapping("/{id}")
  public ResponseEntity<JourneyDTO> addJourney(
      @PathVariable("id") String id, @RequestBody JourneyDTO journeyDTO) {
    var targetUser = userService.getById(id);
    Journey savedJourney;
    if (targetUser.isPresent()) {
      var unboxedUser = targetUser.get();
      journeyDTO.setUserId(unboxedUser.getId());
      savedJourney = journeyService.createJourney(journeyDTO);
    } else {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(savedJourney.toJourneyDTO());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteJourney(@PathVariable("id") String id) {
    var optionalJourney = journeyService.getById(id);
    if (optionalJourney.isPresent()) {
      List<Activity> activities = activityService.getActivitiesByJourney(optionalJourney.get());
      for (var a : activities) {
        activityService.deleteActivity(a);
        var activityNotifications = notificationService.getActivityNotifications(a);
        for (var n : activityNotifications) {
          notificationService.deleteNotification(n);
        }
      }
      var journeyNotifications = notificationService.getJourneyNotifications(optionalJourney.get());
      for (var n : journeyNotifications) {
        notificationService.deleteNotification(n);
      }
      journeyService.deleteJourney(optionalJourney.get());
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}/user")
  public ResponseEntity<UserDTO> userOfJourney(@PathVariable("id") String id) {
    var optionalJourney = journeyService.getById(id);
    if (optionalJourney.isPresent()) {
      var user = optionalJourney.get().getUser();
      return ResponseEntity.ok(user.toUserDTO());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/{JourneyId}/activities/train")
  public ResponseEntity<JourneyDTO> addTrainTripToJourney(
      @PathVariable("JourneyId") String journeyId, @RequestBody TrainTripDTO trainTripDTO) {
    var selectedJourney = journeyService.getById(journeyId);
    if (selectedJourney.isPresent()) {
      trainTripDTO.setJourneyId(journeyId);
      TrainTrip trainTrip = (TrainTrip) activityService.toTrainTrip(trainTripDTO);
      Journey unboxedJourney = selectedJourney.get();
      int checker = unboxedJourney.isValidActivity(trainTrip);
      if (checker != -1) {
        trainTrip = (TrainTrip) activityService.saveTrainTrip(trainTripDTO);
        unboxedJourney.addActivity(checker, trainTrip);
        journeyService.updateJourney(unboxedJourney);
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(unboxedJourney.toJourneyDTO());
      }
      return ResponseEntity.ok(unboxedJourney.toJourneyDTO());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/{JourneyId}/activities/plane")
  public ResponseEntity<JourneyDTO> addPlaneTripToJourney(
      @PathVariable("JourneyId") String journeyId, @RequestBody PlaneTripDTO planeTripDTO) {
    var selectedJourney = journeyService.getById(journeyId);
    if (selectedJourney.isPresent()) {
      planeTripDTO.setJourneyId(journeyId);
      PlaneTrip planeTrip = (PlaneTrip) activityService.toPlaneTrip(planeTripDTO);
      Journey unboxedJourney = selectedJourney.get();
      int checker = unboxedJourney.isValidActivity(planeTrip);
      if (checker != -1) {
        planeTrip = (PlaneTrip) activityService.savePlaneTrip(planeTripDTO);
        unboxedJourney.addActivity(checker, planeTrip);
        journeyService.updateJourney(unboxedJourney);
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(unboxedJourney.toJourneyDTO());
      }
      return ResponseEntity.ok(unboxedJourney.toJourneyDTO());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/{JourneyId}/activities/visit")
  public ResponseEntity<JourneyDTO> addVisitToJourney(
      @PathVariable("JourneyId") String journeyId, @RequestBody VisitDTO visitDTO) {
    var selectedJourney = journeyService.getById(journeyId);
    if (selectedJourney.isPresent()) {
      visitDTO.setJourneyId(journeyId);
      Visit visit = (Visit) activityService.toVisit(visitDTO);
      Journey unboxedJourney = selectedJourney.get();
      int checker = unboxedJourney.isValidActivity(visit);
      if (checker != -1) {
        visit = (Visit) activityService.saveVisit(visitDTO);
        unboxedJourney.addActivity(checker, visit);
        journeyService.updateJourney(unboxedJourney);
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(unboxedJourney.toJourneyDTO());
      }
      return ResponseEntity.ok(unboxedJourney.toJourneyDTO());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/planeTrip/{fromId}/{toId}/edit")
  public ResponseEntity<JourneyDTO> editPlaneTrip(
      @PathVariable("fromId") String fromId,
      @PathVariable("toId") String toId,
      @RequestBody EditPlaneTripDTO editPlaneTripDTO) {
    String journeyId = editPlaneTripDTO.getJourneyId();
    var selectedFrom = placeService.getById(fromId);
    var selectedTo = placeService.getById(toId);
    var selectedJourney = journeyService.getById(journeyId);
    boolean selectedFromNotFound =
        !selectedFrom.isPresent() || !Airport.class.isInstance(selectedFrom.get());
    boolean selectedToNotFound =
        !selectedTo.isPresent() || !Airport.class.isInstance(selectedTo.get());
    if (selectedFromNotFound || selectedToNotFound || !selectedJourney.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    String activityId = editPlaneTripDTO.getId();
    var selectedActivity = activityService.getById(activityId);
    PlaneTrip planeTrip = (PlaneTrip) activityService.editToPlaneTrip(editPlaneTripDTO);
    Journey unboxedJourney = selectedJourney.get();
    PlaneTrip unboxedActivity = (PlaneTrip) selectedActivity.get();
    int checker = unboxedJourney.isValidEdit(unboxedActivity, planeTrip);
    if (checker != -1) {
      String flightNumber = editPlaneTripDTO.getFlightNumber();
      unboxedActivity.changeData(planeTrip);
      unboxedActivity.setFrom((Airport) selectedFrom.get());
      unboxedActivity.setTo((Airport) selectedTo.get());
      unboxedActivity.setFlightNumber(flightNumber);
      planeTrip = (PlaneTrip) activityService.updateActivity(unboxedActivity);
      unboxedJourney.editActivity(checker, planeTrip);
      journeyService.updateJourney(unboxedJourney);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(unboxedJourney.toJourneyDTO());
    }
    return ResponseEntity.ok(unboxedJourney.toJourneyDTO());
  }

  @PutMapping("/trainTrip/{fromId}/{toId}/edit")
  public ResponseEntity<JourneyDTO> editTrainTrip(
      @PathVariable("fromId") String fromId,
      @PathVariable("toId") String toId,
      @RequestBody EditTrainTripDTO editTrainTripDTO) {
    String journeyId = editTrainTripDTO.getJourneyId();
    var selectedJourney = journeyService.getById(journeyId);
    var selectedFrom = placeService.getById(fromId);
    var selectedTo = placeService.getById(toId);
    boolean selectedFromNotFound =
        !selectedFrom.isPresent() || !Station.class.isInstance(selectedFrom.get());
    boolean selectedToNotFound =
        !selectedTo.isPresent() || !Station.class.isInstance(selectedTo.get());
    if (selectedFromNotFound || selectedToNotFound || !selectedJourney.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    String activityId = editTrainTripDTO.getId();
    var selectedActivity = activityService.getById(activityId);
    TrainTrip trainTrip = (TrainTrip) activityService.editToTrainTrip(editTrainTripDTO);
    Journey unboxedJourney = selectedJourney.get();
    TrainTrip unboxedActivity = (TrainTrip) selectedActivity.get();
    int checker = unboxedJourney.isValidEdit(unboxedActivity, trainTrip);
    if (checker != -1) {
      unboxedActivity.changeData(trainTrip);
      unboxedActivity.setFrom((Station) selectedFrom.get());
      unboxedActivity.setTo((Station) selectedTo.get());
      trainTrip = (TrainTrip) activityService.updateActivity(unboxedActivity);
      unboxedJourney.editActivity(checker, trainTrip);
      journeyService.updateJourney(unboxedJourney);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(unboxedJourney.toJourneyDTO());
    }
    return ResponseEntity.ok(unboxedJourney.toJourneyDTO());
  }

  @PutMapping("/visit/{placeId}/edit")
  public ResponseEntity<JourneyDTO> editVisit(
      @PathVariable("placeId") String placeId, @RequestBody EditVisitDTO editVisitDTO) {
    String journeyId = editVisitDTO.getJourneyId();
    var selectedJourney = journeyService.getById(journeyId);
    var selectedPlace = placeService.getById(placeId);
    boolean selectedPlaceNotFound = !selectedPlace.isPresent();
    if (!selectedPlaceNotFound) {
      Place place = selectedPlace.get();
      selectedPlaceNotFound = !Attraction.class.isInstance(place);
    }
    if (selectedJourney.isPresent() && !selectedPlaceNotFound) {
      String activityId = editVisitDTO.getId();
      var selectedActivity = activityService.getById(activityId);
      Visit visit = (Visit) activityService.editToVisit(editVisitDTO);
      Journey unboxedJourney = selectedJourney.get();
      Visit unboxedActivity = (Visit) selectedActivity.get();
      int checker = unboxedJourney.isValidEdit(unboxedActivity, visit);
      if (checker != -1) {
        unboxedActivity.changeData(visit);
        unboxedActivity.setAttraction((Attraction) selectedPlace.get());
        visit = (Visit) activityService.updateActivity(unboxedActivity);
        unboxedJourney.editActivity(checker, visit);
        unboxedJourney = journeyService.updateJourney(unboxedJourney);
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(unboxedJourney.toJourneyDTO());
      }
      return ResponseEntity.ok(unboxedJourney.toJourneyDTO());

    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
