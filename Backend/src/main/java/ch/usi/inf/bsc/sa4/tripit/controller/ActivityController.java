package ch.usi.inf.bsc.sa4.tripit.controller;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.ActivityDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Activity;
import ch.usi.inf.bsc.sa4.tripit.model.Journey;
import ch.usi.inf.bsc.sa4.tripit.service.ActivityService;
import ch.usi.inf.bsc.sa4.tripit.service.JourneyService;
import ch.usi.inf.bsc.sa4.tripit.service.NotificationService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Activity controller. */
@RestController
@RequestMapping("/activities")
public class ActivityController {

  private final ActivityService activityService;

  private final JourneyService journeyService;
  private final NotificationService notificationService;

  /**
   * Instantiates a new Activity controller.
   *
   * @param activityService the activity service
   */
  @Autowired
  public ActivityController(
      ActivityService activityService,
      JourneyService journeyService,
      NotificationService notificationService) {
    this.activityService = activityService;
    this.journeyService = journeyService;
    this.notificationService = notificationService;
  }

  /**
   * Gets all the activities in the database
   *
   * @return all the activities
   */
  @GetMapping
  public ResponseEntity<List<ActivityDTO>> getAll() {
    var activities = activityService.getAllActivities();
    var activitiesDTO =
        activities.stream().map(Activity::toActivityDTO).collect(Collectors.toList());
    return ResponseEntity.ok(activitiesDTO);
  }

  /**
   * Gets activities by their id.
   *
   * @param id the id
   * @return the activity id
   */
  @GetMapping("/{id}")
  public ResponseEntity<ActivityDTO> getById(@PathVariable("id") String id) {
    var optionalActivity = activityService.getById(id);
    return optionalActivity
        .map(activity -> ResponseEntity.ok(activity.toActivityDTO()))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Delete activity from activity id.
   *
   * @param id the activity id
   * @return the response entity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteActivity(@PathVariable("id") String id) {
    var optionalActivity = activityService.getById(id);
    if (optionalActivity.isPresent()) {
      Journey journey = optionalActivity.get().getJourney();
      var activityNotifications =
          notificationService.getActivityNotifications(optionalActivity.get());
      for (var n : activityNotifications) {
        notificationService.deleteNotification(n);
      }
      activityService.deleteActivity(optionalActivity.get());
      journeyService.updateJourney(journey);
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/journey/{journeyId}")
  public ResponseEntity<List<ActivityDTO>> getActivitiesOfJourney(
      @PathVariable("journeyId") String journeyId) {
    Optional<Journey> optionalJourney = journeyService.getById(journeyId);
    if (!optionalJourney.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    final Journey journey = optionalJourney.get();
    List<Activity> activities = activityService.getActivitiesByJourney(journey);
    var activitiesDTO =
        activities.stream().map(Activity::toActivityDTO).collect(Collectors.toList());
    return ResponseEntity.ok(activitiesDTO);
  }
}
