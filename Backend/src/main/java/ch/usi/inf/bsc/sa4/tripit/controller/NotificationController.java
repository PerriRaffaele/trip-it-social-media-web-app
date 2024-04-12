package ch.usi.inf.bsc.sa4.tripit.controller;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.FriendshipDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationActivityDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationJourneyDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.UserDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Friendship;
import ch.usi.inf.bsc.sa4.tripit.model.Notification;
import ch.usi.inf.bsc.sa4.tripit.model.NotificationActivity;
import ch.usi.inf.bsc.sa4.tripit.model.NotificationJourney;
import ch.usi.inf.bsc.sa4.tripit.model.NotificationType;
import ch.usi.inf.bsc.sa4.tripit.service.ActivityService;
import ch.usi.inf.bsc.sa4.tripit.service.FriendshipService;
import ch.usi.inf.bsc.sa4.tripit.service.JourneyService;
import ch.usi.inf.bsc.sa4.tripit.service.NotificationService;
import ch.usi.inf.bsc.sa4.tripit.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

  private final FriendshipService friendshipService;

  private final NotificationService notificationService;
  private final JourneyService journeyService;

  private final UserService userService;

  private final ActivityService activityService;

  @Autowired
  public NotificationController(
      FriendshipService friendshipService,
      NotificationService notificationService,
      JourneyService journeyService,
      UserService userService,
      ActivityService activityService) {
    this.notificationService = notificationService;
    this.journeyService = journeyService;
    this.activityService = activityService;
    this.userService = userService;
    this.friendshipService = friendshipService;
  }

  @PostMapping("/friend")
  public ResponseEntity<NotificationDTO> postFriendNotification(
      @RequestBody NotificationDTO notificationDTO) {
    var targetUserFrom = userService.getById(notificationDTO.getFrom());
    var targetUserTo = userService.getById(notificationDTO.getTo());
    Notification savedNotification;
    if (targetUserFrom.isPresent() && targetUserTo.isPresent()) {
      savedNotification =
          notificationService.createNotificationFriend(
              notificationDTO, targetUserFrom.get(), targetUserTo.get());
    } else {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(savedNotification.toNotificationDTO());
  }

  @PostMapping("/journey")
  public ResponseEntity<NotificationDTO> postJourneyNotification(
      @RequestBody NotificationJourneyDTO notificationJourneyDTO) {
    var targetUserFrom = userService.getById(notificationJourneyDTO.getFrom());
    var targetUserTo = userService.getById(notificationJourneyDTO.getTo());
    var targetJourney = journeyService.getById(notificationJourneyDTO.getJourney());
    NotificationJourney savedNotification;
    if (targetJourney.isPresent() && targetUserFrom.isPresent() && targetUserTo.isPresent()) {
      savedNotification =
          notificationService.createNotificationJourney(
              notificationJourneyDTO,
              targetUserFrom.get(),
              targetUserTo.get(),
              targetJourney.get());
    } else {
      ResponseEntity.HeadersBuilder response = ResponseEntity.notFound();
      return response.build();
    }
    return ResponseEntity.ok(savedNotification.toNotificationDTO());
  }

  @PostMapping("/activity")
  public ResponseEntity<NotificationDTO> postActivityNotification(
      @RequestBody NotificationActivityDTO notificationActivityDTO) {
    var targetUserFrom = userService.getById(notificationActivityDTO.getFrom());
    var targetUserTo = userService.getById(notificationActivityDTO.getTo());
    var targetJourney = journeyService.getById(notificationActivityDTO.getJourney());
    var targetActivity = activityService.getById(notificationActivityDTO.getActivity());
    NotificationActivity savedNotification;
    if (targetJourney.isPresent()
        && targetUserFrom.isPresent()
        && targetUserTo.isPresent()
        && targetActivity.isPresent()) {
      savedNotification =
          notificationService.createNotificationActivity(
              notificationActivityDTO,
              targetUserFrom.get(),
              targetUserTo.get(),
              targetJourney.get(),
              targetActivity.get());
    } else {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(savedNotification.toNotificationDTO());
  }

  @GetMapping("/{id}")
  public ResponseEntity<List<NotificationDTO>> getUserNotification(@PathVariable("id") String id) {
    var targetUser = userService.getById(id);
    if (!targetUser.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    var unboxedUser = targetUser.get();
    List<NotificationDTO> notificationDTOS = new ArrayList<>();
    for (var notification : notificationService.getUserNotifications(unboxedUser)) {
      var fr = friendshipService.getFriendshipByRecAndSend(notification.getUserFrom(), unboxedUser);
      if (fr.isPresent()) {
        var friendship = fr.get();
        if (!friendship.isMutedById(notification.getUserFrom().getId())) {
          notificationDTOS.add(notification.toNotificationDTO());
        }
      } else {
        notificationDTOS.add(notification.toNotificationDTO());
      }
    }
    Collections.reverse(notificationDTOS);
    return ResponseEntity.ok(notificationDTOS);
  }

  @GetMapping("/journey/{userFromId}/{userToId}/{journeyId}")
  public ResponseEntity<List<NotificationDTO>> likedJourney(
      @PathVariable("userFromId") String userFromId,
      @PathVariable("userToId") String userToId,
      @PathVariable("journeyId") String journeyId) {
    var targetUserFrom = userService.getById(userFromId);
    var targetUserTo = userService.getById(userToId);
    var targetJourney = journeyService.getById(journeyId);
    List<NotificationDTO> response;
    if (targetJourney.isPresent() && targetUserFrom.isPresent() && targetUserTo.isPresent()) {
      var userFrom = targetUserFrom.get();
      var userTo = targetUserTo.get();
      var journey = targetJourney.get();
      response =
          notificationService.getFromNotificationJourney(userFrom, userTo, journey).stream()
              .map(notificationJourney -> notificationJourney.toNotificationDTO())
              .collect(Collectors.toList());
    } else {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNotification(@PathVariable("id") String id) {
    var optionalNotification = notificationService.getById(id);
    if (optionalNotification.isPresent()) {
      notificationService.deleteNotification(optionalNotification.get());
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/hide/{id}")
  public ResponseEntity<Void> hideNotification(@PathVariable("id") String notificationId) {
    var optionalNotification = notificationService.getById(notificationId);
    if (optionalNotification.isPresent()) {
      Notification unboxedNotification = optionalNotification.get();
      unboxedNotification.setVisualize(false);
      notificationService.updateNotification(unboxedNotification);
    } else {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().build();
  }

  @PostMapping("/acceptFriendRequest")
  public ResponseEntity<FriendshipDTO> friendRequest(@RequestBody NotificationDTO notificationDTO) {
    Optional<Notification> notification = notificationService.getById(notificationDTO.getId());
    if (notification.isPresent()) {
      var userFrom = userService.getById(notificationDTO.getFrom());
      var userTo = userService.getById(notificationDTO.getTo());
      if (userFrom.isPresent() && userTo.isPresent()) {
        var friendship = new Friendship(userFrom.get(), userTo.get());
        notificationService.deleteNotification(notification.get());
        var acceptedNotification =
            new Notification(
                userFrom.get(), userTo.get(), NotificationType.ACCEPTED_FRIEND_REQUEST);
        notificationService.createNotificationFriend(
            acceptedNotification.toNotificationDTO(), userFrom.get(), userTo.get());
        return ResponseEntity.ok(friendshipService.updateFriendShip(friendship).toFriendshipDTO());
      }
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/isPending/{idFrom}/{idTo}")
  public ResponseEntity<NotificationDTO> pendingFriendRequest(
      @PathVariable("idFrom") String userFrom, @PathVariable("idTo") String userTo) {
    var userFromOpt = userService.getById(userFrom);
    var userToOpt = userService.getById(userTo);
    if (userFromOpt.isPresent() && userToOpt.isPresent()) {
      var notification =
          notificationService.checkExistingFriendRequest(userFromOpt.get(), userToOpt.get());
      if (notification.isPresent()) {
        return ResponseEntity.ok(notification.get().toNotificationDTO());
      }
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/{id}/getSender")
  public ResponseEntity<UserDTO> getNotificationSender(@PathVariable("id") String id) {
    var notification = notificationService.getById(id);
    if (notification.isPresent()) {
      var sender = notification.get().getUserFrom();
      return ResponseEntity.ok(sender.toUserDTO());
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/{from}/{to}/{journeyId}/{activityId}/reaction")
  public ResponseEntity<List<NotificationDTO>> getReactionUser(
      @PathVariable("from") String from,
      @PathVariable("to") String to,
      @PathVariable("journeyId") String journeyId,
      @PathVariable("activityId") String activityId) {
    var targetUserFrom = userService.getById(from);
    var targetUserTo = userService.getById(to);
    var targetJourney = journeyService.getById(journeyId);
    var targetActivity = activityService.getById(activityId);
    if (targetUserFrom.isPresent()
        && targetUserTo.isPresent()
        && targetJourney.isPresent()
        && targetActivity.isPresent()) {
      List<NotificationActivity> reactionsList =
          notificationService.getFromNotificationActivity(
              targetUserFrom.get(), targetUserTo.get(), targetJourney.get(), targetActivity.get());
      List<NotificationDTO> reactionsDTO = new ArrayList<>();
      reactionsList.forEach(element -> reactionsDTO.add(element.toNotificationDTO()));
      return ResponseEntity.ok(reactionsDTO);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/{from}/{to}/{journeyId}/reaction")
  public ResponseEntity<List<NotificationDTO>> getReactionUserJourney(
      @PathVariable("from") String from,
      @PathVariable("to") String to,
      @PathVariable("journeyId") String journeyId) {
    var targetUserFrom = userService.getById(from);
    var targetUserTo = userService.getById(to);
    var targetJourney = journeyService.getById(journeyId);
    if (targetUserFrom.isPresent() && targetUserTo.isPresent() && targetJourney.isPresent()) {
      List<NotificationJourney> reactionsList =
          notificationService.getFromNotificationJourney(
              targetUserFrom.get(), targetUserTo.get(), targetJourney.get());
      List<NotificationDTO> reactionsDTO = new ArrayList<>();
      for (var react : reactionsList) {
        var tmp = react.toNotificationDTO();
        if (tmp.getType().equals("Journey")) {
          reactionsDTO.add(tmp);
        }
      }
      return ResponseEntity.ok(reactionsDTO);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/journeyLikes/{journeyId}")
  public ResponseEntity<Integer> getJourneyLikes(@PathVariable("journeyId") String journeyId) {
    var targetJourney = journeyService.getById(journeyId);
    if (targetJourney.isPresent()) {
      var journey = targetJourney.get();
      return ResponseEntity.ok(notificationService.getJourneyLikes(journey).size());
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/journeyLikes/{journeyId}/{activityId}")
  public ResponseEntity<Integer> getActivityLikes(
      @PathVariable("journeyId") String journeyId, @PathVariable("activityId") String activityId) {
    var targetJourney = journeyService.getById(journeyId);
    var targetActivity = activityService.getById(activityId);
    if (targetJourney.isPresent() && targetActivity.isPresent()) {
      var journey = targetJourney.get();
      var activity = targetActivity.get();
      return ResponseEntity.ok(notificationService.getActivityLikes(journey, activity).size());
    }
    return ResponseEntity.notFound().build();
  }
}
