package ch.usi.inf.bsc.sa4.tripit.repository;

import ch.usi.inf.bsc.sa4.tripit.model.Image;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends MongoRepository<Image, String> {

  Optional<Image> findById(String id);

  Optional<Image> findByIdImage(String idImage);
}
