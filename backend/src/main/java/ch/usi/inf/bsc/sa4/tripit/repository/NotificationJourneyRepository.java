package ch.usi.inf.bsc.sa4.tripit.repository;

import ch.usi.inf.bsc.sa4.tripit.model.Journey;
import ch.usi.inf.bsc.sa4.tripit.model.NotificationJourney;
import ch.usi.inf.bsc.sa4.tripit.model.NotificationType;
import ch.usi.inf.bsc.sa4.tripit.model.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** This class represents the repository of NotificationJourneys in the mongo database. */
@Repository
public interface NotificationJourneyRepository
    extends MongoRepository<NotificationJourney, String> {

  /**
   * Returns a list of all the journey notifications sent from the provided `from` user to the
   * provided `to` user on the provided journey.
   *
   * @param from the user who sent the notifications
   * @param to the user who received the notifications
   * @param journey the journey that received the notifications
   * @return a list of all the journey notifications that match the given users and journey
   */
  List<NotificationJourney> getAllByFromAndToAndJourney(User from, User to, Journey journey);

  public List<NotificationJourney> getAllByJourneyAndNotificationType(
      Journey journey, NotificationType notificationType);

  public List<NotificationJourney> getAllByJourney(Journey journey);
}
