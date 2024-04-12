package ch.usi.inf.bsc.sa4.tripit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import ch.usi.inf.bsc.sa4.tripit.model.Image;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Image Service Class Tests")
public class ImageServiceTest {

  @Autowired private ImageService imageService;
  private Image imageBlank, image;
  public String id1, id2;

  @BeforeEach
  void setUp() {
    image = new Image("idImage1", 10, 10, "backgroundPic", "image/url/1", "cropped");
    imageBlank = new Image();
    id1 = "1";
  }

  @DisplayName("Save an image to the database")
  @Test
  public void saveImage() {
    Image image1 = imageService.save(imageBlank);
    Image image2 = imageService.save(image);
    id1 = image1.getId();
    id2 = image2.getId();
    assertNotEquals(id1, id2);
  }

  @DisplayName("After set up")
  @Nested
  class afterImageInit {

    @DisplayName("Get Image By imageId")
    @Test
    public void testGetByImgId() {
      Optional<Image> response = imageService.getByIdImage("idImage1");
      assertEquals(response.get().getCropping(), "cropped");
    }

    @DisplayName("Get Image by Id")
    @Test
    public void testGetById() {
      Image image3 =
          imageService.save(
              new Image("idImage2", 10, 10, "backgroundPic", "image/url/2", "cropped"));
      Optional<Image> response = imageService.getById(image3.getId());
      assertEquals(response.get().getUrl(), "image/url/2");
    }
  }
}
