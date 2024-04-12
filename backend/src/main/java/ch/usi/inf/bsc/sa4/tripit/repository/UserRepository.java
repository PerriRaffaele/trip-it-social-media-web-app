package ch.usi.inf.bsc.sa4.tripit.repository;

import ch.usi.inf.bsc.sa4.tripit.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** This class represents the user repository in the mongo database. */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
  // You can implement complex "predefined" logic with specific conventions by specific method names
  // Documentation link:
  // https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongodb.repositories.queries

  /**
   * Returns a list of all users in the database whose name contains the provided string.
   *
   * @param string the partial name of the user
   * @return a list of all users whose name contains the provided string
   */
  List<User> findAllByNameContaining(String string);

  /**
   * Returns a list of all users in the database whose name starts with the provided string.
   *
   * @param string the beginning of the name of the user
   * @return a list of all users whose name starts with the provided string
   */
  List<User> findAllByNameStartingWith(String string);

  /**
   * Returns a list of all users in the database whose email starts with the provided string.
   *
   * @param string the beginning of the email of the user
   * @return a list of all users whose email starts with the provided string
   */
  List<User> findAllByEmailStartingWith(String string);

  /**
   * Returns a list of all the users in the database whose privacy corresponds to the provided
   * privacy
   *
   * @param isPublic the privacy of the users we want to find
   * @return a list of all the users whose privacy corresponds to the provided privacy
   */
  List<User> findAllByIsPublic(boolean isPublic);

  /**
   * If possible, returns the user in the database whose gitlab ID corresponds to the given ID
   *
   * @param gitlabId the gitlab ID of the wanted user
   * @return the user with the given gitlab ID, if found
   */
  Optional<User> findByGitlabId(String gitlabId);
}
