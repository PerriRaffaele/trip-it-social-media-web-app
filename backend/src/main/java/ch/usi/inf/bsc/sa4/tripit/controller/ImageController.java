package ch.usi.inf.bsc.sa4.tripit.controller;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.ImageDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Image;
import ch.usi.inf.bsc.sa4.tripit.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class ImageController {
  private final ImageService imageService;

  @Autowired
  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }

  @PostMapping("/new")
  public ResponseEntity<ImageDTO> postImage(@RequestBody ImageDTO image) {
    final Image img = imageService.save(image.toImage());
    final ImageDTO createdImageDTO = img.toImageDTO();
    System.out.println("ID DTO : " + createdImageDTO.getId());
    return ResponseEntity.ok(createdImageDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ImageDTO> getImage(@PathVariable("id") String id) {
    final var img = imageService.getByIdImage(id);
    if (img.isPresent()) {
      final Image image = img.get();
      final ImageDTO imageDTO = image.toImageDTO();
      return ResponseEntity.ok(imageDTO);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}/id")
  public ResponseEntity<ImageDTO> getImageId(@PathVariable("id") String id) {
    final var img = imageService.getById(id);
    if (img.isPresent()) {
      final Image image = img.get();
      final ImageDTO imageDTO = image.toImageDTO();
      return ResponseEntity.ok(imageDTO);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
