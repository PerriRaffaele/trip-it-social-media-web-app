package ch.usi.inf.bsc.sa4.tripit.repository;

import ch.usi.inf.bsc.sa4.tripit.model.Notification;
import ch.usi.inf.bsc.sa4.tripit.model.NotificationType;
import ch.usi.inf.bsc.sa4.tripit.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** This class represents the repository of notifications in the mongo database. */
@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

  /**
   * Returns a list of all the notifications sent by the provided user.
   *
   * @param user the user who sent the notifications
   * @return a list of all the notifications sent by the provided user
   */
  List<Notification> findAllByFrom(User user);

  /**
   * Returns a list of all the notifications in the database that match the provided visibility and
   * the given user as the receiver of the notification.
   *
   * @param user the user who sent the notifications
   * @param visualize true if the notifications are visible, false if they aren't
   * @return a list of all the notifications that match the provided user (as the receiver) and
   *     visibility
   */
  List<Notification> findAllByToAndVisible(User user, boolean visualize);

  /**
   * Returns a list of all the notifications of the given notification type, that are sent from the
   * provided `userFrom` user to the provided `userTo` user.
   *
   * @param userFrom the user who sent the notifications
   * @param userTo the user who received the notifications
   * @param notificationType the type of the notifications
   * @return a list of all the notifications of the given type sent from the given sender to the
   *     given receiver
   */
  Optional<Notification> findByFromAndToAndNotificationType(
      User userFrom, User userTo, NotificationType notificationType);
}
