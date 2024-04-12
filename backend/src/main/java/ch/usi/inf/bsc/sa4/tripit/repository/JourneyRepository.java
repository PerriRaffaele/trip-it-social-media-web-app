package ch.usi.inf.bsc.sa4.tripit.repository;

import ch.usi.inf.bsc.sa4.tripit.model.Journey;
import ch.usi.inf.bsc.sa4.tripit.model.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** This class represents the journey repository in the mongo database. */
@Repository
public interface JourneyRepository extends MongoRepository<Journey, String> {

  /**
   * Returns a list of all the journeys in the database that contain the provided string in their
   * title.
   *
   * @param string the provided partial title
   * @return a list of all the journeys in the database that contain the provided string in their
   *     title
   */
  List<Journey> findAllByTitleContaining(String string);

  /**
   * Returns a list of all the journeys in the database owned by the provided user.
   *
   * @param user the owner of the wanted journeys
   * @return a list of all the journeys owned by the provided user
   */
  List<Journey> findAllByUser(User user);

  /**
   * Returns the number of journeys in the database owned by the provided user.
   *
   * @param user the user whose journeys we want to count
   * @return the number of journeys owned by the provided user
   */
  int countAllByUser(User user);
}
