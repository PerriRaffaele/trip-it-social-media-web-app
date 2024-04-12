package ch.usi.inf.bsc.sa4.tripit.repository;

import ch.usi.inf.bsc.sa4.tripit.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** This class represents the location repository in the mongo database. */
@Repository
public interface LocationRepository extends MongoRepository<Location, String> {}
