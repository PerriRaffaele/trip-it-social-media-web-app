package ch.usi.inf.bsc.sa4.tripit.repository;

import ch.usi.inf.bsc.sa4.tripit.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** This class represents the friendship repository in the mongo database. */
@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {}
