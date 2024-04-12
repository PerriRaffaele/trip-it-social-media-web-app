package ch.usi.inf.bsc.sa4.tripit.service;

import ch.usi.inf.bsc.sa4.tripit.model.Image;
import ch.usi.inf.bsc.sa4.tripit.repository.ImageRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
  private final ImageRepository imageRepository;

  @Autowired
  public ImageService(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  public Optional<Image> getById(String id) {
    return imageRepository.findById(id);
  }

  public Optional<Image> getByIdImage(String idImage) {
    return imageRepository.findByIdImage(idImage);
  }

  public Image save(Image image) {
    return imageRepository.save(image);
  }
}
