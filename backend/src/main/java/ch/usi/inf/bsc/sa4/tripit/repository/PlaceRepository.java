package ch.usi.inf.bsc.sa4.tripit.repository;

import ch.usi.inf.bsc.sa4.tripit.model.Place;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** This class represents the place repository in the mongo database. */
@Repository
public interface PlaceRepository extends MongoRepository<Place, String> {

  /**
   * Finds all Place objects whose name contains the specified string.
   *
   * @param string the string to search for in the names of the Place objects.
   * @return a list of Place objects whose name contains the specified string.
   */
  List<Place> findAllByNameStartingWith(String string);
}
