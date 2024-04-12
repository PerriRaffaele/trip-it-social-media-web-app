package ch.usi.inf.bsc.sa4.tripit.repository;

import ch.usi.inf.bsc.sa4.tripit.model.Activity;
import ch.usi.inf.bsc.sa4.tripit.model.Journey;
import ch.usi.inf.bsc.sa4.tripit.model.NotificationActivity;
import ch.usi.inf.bsc.sa4.tripit.model.NotificationType;
import ch.usi.inf.bsc.sa4.tripit.model.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** This class represents the repository of NotificationActivities in the mongo database. */
@Repository
public interface NotificationActivityRepository
    extends MongoRepository<NotificationActivity, String> {

  /**
   * Returns a list of all the activity notifications exchanged from the given `from` user to the
   * given `to` user on the specific activity in the specific journey.
   *
   * @param from the user who sends the notifications
   * @param to the user who receives the notifications
   * @param journey the journey that contains the activity
   * @param activity the activity that receives the notifications
   * @return a list of all the activity notifications that match the given users, journey and
   *     activity
   */
  List<NotificationActivity> getAllByFromAndToAndJourneyAndActivity(
      User from, User to, Journey journey, Activity activity);

  public List<NotificationActivity> getAllByActivity(Activity activity);

  public List<NotificationActivity> getAllByJourneyAndActivityAndNotificationType(
      Journey journey, Activity activity, NotificationType notificationType);
}
