package ch.usi.inf.bsc.sa4.tripit.service;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationActivityDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.NotificationJourneyDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Activity;
import ch.usi.inf.bsc.sa4.tripit.model.Journey;
import ch.usi.inf.bsc.sa4.tripit.model.Notification;
import ch.usi.inf.bsc.sa4.tripit.model.NotificationActivity;
import ch.usi.inf.bsc.sa4.tripit.model.NotificationJourney;
import ch.usi.inf.bsc.sa4.tripit.model.NotificationType;
import ch.usi.inf.bsc.sa4.tripit.model.User;
import ch.usi.inf.bsc.sa4.tripit.repository.NotificationActivityRepository;
import ch.usi.inf.bsc.sa4.tripit.repository.NotificationJourneyRepository;
import ch.usi.inf.bsc.sa4.tripit.repository.NotificationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The service for notifications. Communicates to the repository. */
@Service
public class NotificationService {
  /** The notification repository */
  private final NotificationRepository notificationRepository;

  /** The notification activity repository */
  private final NotificationActivityRepository notificationActivityRepository;

  /** The notification journey repository */
  private final NotificationJourneyRepository notificationJourneyRepository;

  /**
   * Initializes a notification service with the given repositories.
   *
   * @param notificationRepository the notification repository of the service
   * @param notificationJourneyRepository the notification journey repository of the service
   * @param notificationActivityRepository the notification activity repository of the service
   */
  @Autowired
  public NotificationService(
      NotificationRepository notificationRepository,
      NotificationJourneyRepository notificationJourneyRepository,
      NotificationActivityRepository notificationActivityRepository) {
    this.notificationJourneyRepository = notificationJourneyRepository;
    this.notificationActivityRepository = notificationActivityRepository;
    this.notificationRepository = notificationRepository;
  }

  /**
   * Returns a NotificationType depending on the given string.
   *
   * @param myEnum the string representing the notification type
   * @return the NotificationType corresponding to the given string
   */
  public NotificationType getEnum(String myEnum) {
    switch (myEnum) {
      case "SMILY_FACE":
        return NotificationType.SMILY_FACE;
      case "ANGRY_FACE":
        return NotificationType.ANGRY_FACE;
      case "SAD_FACE":
        return NotificationType.SAD_FACE;
      case "FRIEND_REQUEST":
        return NotificationType.FRIEND_REQUEST;
      case "LIKE":
        return NotificationType.LIKE;
      default:
        return NotificationType.ACCEPTED_FRIEND_REQUEST;
    }
  }

  /**
   * Saves a friend request notification between the given users, based on the given
   * notificationDTO's type.
   *
   * @param notificationDTO represents the notification
   * @param from the user who sent the notification
   * @param to the user who received the notification
   * @return the saved friend notification
   */
  public Notification createNotificationFriend(
      NotificationDTO notificationDTO, User from, User to) {
    final var notification =
        new Notification(from, to, this.getEnum(notificationDTO.getNotificationType()));
    return notificationRepository.save(notification);
  }

  /**
   * Saves a journey notification between the given users on the given journey, based on the given
   * notificationDTO's type.
   *
   * @param notificationJourneyDTO represents the notification
   * @param from the user who sent the notification
   * @param to the user who received the notification
   * @param journey the journey of the notification
   * @return the saved journey notification
   */
  public NotificationJourney createNotificationJourney(
      NotificationJourneyDTO notificationJourneyDTO, User from, User to, Journey journey) {
    final NotificationJourney notification =
        new NotificationJourney(
            from, to, this.getEnum(notificationJourneyDTO.getNotificationType()), journey);
    return notificationJourneyRepository.save(notification);
  }

  /**
   * @param notificationActivityDTO
   * @param from
   * @param to
   * @param journey
   * @param activity
   * @return
   */
  public NotificationActivity createNotificationActivity(
      NotificationActivityDTO notificationActivityDTO,
      User from,
      User to,
      Journey journey,
      Activity activity) {
    final NotificationActivity notification =
        new NotificationActivity(
            from,
            to,
            this.getEnum(notificationActivityDTO.getNotificationType()),
            journey,
            activity);
    return notificationActivityRepository.save(notification);
  }

  /**
   * Looks for a friend request between the provided users in the database.
   *
   * @param userFrom the user who sent the friend request
   * @param userTo the user who received the friend request
   * @return The friend request notification between the provided users, if found
   */
  public Optional<Notification> checkExistingFriendRequest(User userFrom, User userTo) {
    return this.notificationRepository.findByFromAndToAndNotificationType(
        userFrom, userTo, NotificationType.FRIEND_REQUEST);
  }

  /**
   * Returns a list of all the visible notifications sent to the provided user.
   *
   * @param user the receiver of the notifications we are looking for
   * @return a list of all the visible notifications sent to the provided user
   */
  public List<Notification> getUserNotifications(User user) {
    return this.notificationRepository.findAllByToAndVisible(user, true);
  }

  /**
   * Returns a list of all the notifications between the provided sender and receiver users, on the
   * given journey.
   *
   * @param from the sender of the notifications we are looking for
   * @param to the receiver of the notifications we are looking for
   * @param journey the journey whose notifications we are looking for
   * @return a list of all the notifications corresponding to the given users and journey
   */
  public List<NotificationJourney> getFromNotificationJourney(User from, User to, Journey journey) {
    return this.notificationJourneyRepository.getAllByFromAndToAndJourney(from, to, journey);
  }

  public List<NotificationActivity> getFromNotificationActivity(
      User from, User to, Journey journey, Activity activity) {
    return this.notificationActivityRepository.getAllByFromAndToAndJourneyAndActivity(
        from, to, journey, activity);
  }

  /**
   * Returns the notification corresponding to the provided ID, if found.
   *
   * @param notificationId the id of the notification in the database
   * @return the notification corresponding to the provided ID, if found
   */
  public Optional<Notification> getById(String notificationId) {
    return notificationRepository.findById(notificationId);
  }

  /**
   * Saves the given notification in the database.
   *
   * @param notification the notification we want to save
   */
  public void updateNotification(Notification notification) {
    notificationRepository.save(notification);
  }

  /**
   * Deletes the given notification from the database.
   *
   * @param notification the notification we want to delete
   */
  public void deleteNotification(Notification notification) {
    notificationRepository.delete(notification);
  }

  public List<NotificationActivity> getActivityNotifications(Activity activity) {
    return notificationActivityRepository.getAllByActivity(activity);
  }

  public List<NotificationJourney> getJourneyNotifications(Journey journey) {
    return notificationJourneyRepository.getAllByJourney(journey);
  }

  public List<NotificationJourney> getJourneyLikes(Journey journey) {
    return notificationJourneyRepository.getAllByJourneyAndNotificationType(
        journey, NotificationType.LIKE);
  }

  public List<NotificationActivity> getActivityLikes(Journey journey, Activity activity) {
    return notificationActivityRepository.getAllByJourneyAndActivityAndNotificationType(
        journey, activity, NotificationType.LIKE);
  }
}
