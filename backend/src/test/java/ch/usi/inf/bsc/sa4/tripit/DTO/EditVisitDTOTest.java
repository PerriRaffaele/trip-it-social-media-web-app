package ch.usi.inf.bsc.sa4.tripit.DTO;

import static com.mongodb.assertions.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.EditVisitDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Image;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("The EditVisitDTO class")
public class EditVisitDTOTest {
  private String id;
  private String journeyId;
  private ZonedDateTime start;
  private ZonedDateTime start2;
  private ZonedDateTime end;
  private ZonedDateTime end2;
  private String title;
  private String description;
  private String type;
  private EditVisitDTO editVisitDTO;
  private Image image;

  @BeforeEach
  void setUp() {
    start = ZonedDateTime.parse("2011-12-03T10:15:30+01:00");
    start2 = ZonedDateTime.parse("2011-12-03T10:15:30+03:00");
    end = ZonedDateTime.parse("2011-12-04T10:15:30+01:00");
    end2 = ZonedDateTime.parse("2011-12-05T10:15:30+01:00");
    image = new Image("gggg", 100, 100, "desc", "url", "cropping");
    editVisitDTO =
        new EditVisitDTO("1", "2", start, end, "edit visit", "description", "visit", "ID001");
  }

  @DisplayName("Get id")
  @Test
  void testGetId() {
    assertEquals(editVisitDTO.getId(), "1");
  }

  @DisplayName("Get journey id")
  @Test
  void testGetJourneyId() {
    assertEquals(editVisitDTO.getJourneyId(), "2");
  }

  @DisplayName("Get start")
  @Test
  void testGetStart() {
    assertEquals(editVisitDTO.getStart(), start);
  }

  @DisplayName("Get end")
  @Test
  void testGetEnd() {
    assertEquals(editVisitDTO.getEnd(), end);
  }

  @DisplayName("Get title")
  @Test
  void testGetTitle() {
    assertEquals(editVisitDTO.getTitle(), "edit visit");
  }

  @DisplayName("Get description")
  @Test
  void testGetDescription() {
    assertEquals(editVisitDTO.getDescription(), "description");
  }

  @DisplayName("Get type")
  @Test
  void testGetType() {
    assertEquals(editVisitDTO.getType(), "visit");
  }

  @DisplayName("Get image")
  @Test
  void testGetImage() {
    assertEquals(editVisitDTO.getCoverId(), "ID001");
  }

  @DisplayName("Set end")
  @Test
  void testSetEnd() {
    editVisitDTO.setEnd(end2);
    assertEquals(editVisitDTO.getEnd(), end2);
  }

  @DisplayName("Set start")
  @Test
  void testSetStart() {
    editVisitDTO.setStart(start2);
    assertEquals(editVisitDTO.getStart(), start2);
  }

  @DisplayName("Set description")
  @Test
  void testSetDescription() {
    editVisitDTO.setDescription("ABC");
    assertEquals(editVisitDTO.getDescription(), "ABC");
  }

  @DisplayName("Set ID")
  @Test
  void testSetId() {
    editVisitDTO.setId("3");
    assertEquals(editVisitDTO.getId(), "3");
  }

  @DisplayName("Set image")
  @Test
  void testSetImage() {
    editVisitDTO.setCoverId("ID002");
    assertEquals(editVisitDTO.getCoverId(), "ID002");
  }
}
