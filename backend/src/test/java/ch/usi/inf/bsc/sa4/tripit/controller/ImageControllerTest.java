package ch.usi.inf.bsc.sa4.tripit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.ImageDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Image;
import ch.usi.inf.bsc.sa4.tripit.service.ImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Image Controller Tests")
public class ImageControllerTest {
  @MockBean private ImageService imageService;
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  private static Image img;
  private static ImageDTO imgDTO;

  @BeforeAll
  public static void dataSetUp() {
    img = new Image("idImage1", 10, 10, "backgroundPic", "image/url/1", "cropped");
    imgDTO = new ImageDTO("1", "idImage1", 10, 10, "backgroundPic", "image/url/1", "cropped");
  }

  @BeforeEach
  void setUp() throws JsonProcessingException {
    given(imageService.save(any())).willReturn(img);
    given(imageService.getById("i1")).willReturn(Optional.of(img));
    given(imageService.getById("iw1")).willReturn(Optional.empty());
    given(imageService.getByIdImage("idImage111")).willReturn(Optional.empty());
    given(imageService.getByIdImage("idImage1")).willReturn(Optional.of(img));
  }

  @Test
  @DisplayName("Testing post request to save a new image to DB")
  public void testSave() throws Exception {
    String json = objectMapper.writeValueAsString(imgDTO);
    MvcResult result =
        this.mockMvc
            .perform(post("/images/new").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk())
            .andReturn();
    String response = result.getResponse().getContentAsString();
    ImageDTO imageDTO = objectMapper.readValue(response, ImageDTO.class);
    assertEquals(imageDTO.getIdImage(), "idImage1");
  }

  @Test
  @DisplayName("Get request for an image through their ID")
  public void testGetImage() throws Exception {
    this.mockMvc.perform(get("/images/iw1/id")).andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc.perform(get("/images/i1/id")).andExpect(status().isOk()).andReturn();
    String response = result.getResponse().getContentAsString();
    ImageDTO imageDTO = objectMapper.readValue(response, ImageDTO.class);
    assertEquals(imageDTO.getUrl(), "image/url/1");
  }

  @Test
  @DisplayName("Get request for an image through their image ID")
  public void testGetImageThroughID() throws Exception {
    this.mockMvc.perform(get("/images/idImage111")).andExpect(status().isNotFound());
    MvcResult result =
        this.mockMvc.perform(get("/images/idImage1")).andExpect(status().isOk()).andReturn();
    String response = result.getResponse().getContentAsString();
    ImageDTO imageDTO = objectMapper.readValue(response, ImageDTO.class);
    assertEquals(imageDTO.getCropping(), "cropped");
  }
}
