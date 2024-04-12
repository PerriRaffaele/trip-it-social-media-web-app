package ch.usi.inf.bsc.sa4.tripit.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.EditTrainTripDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Image;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EditTrainTripDTOTest {

  private String id;
  private String journeyId;
  private ZonedDateTime start;
  private ZonedDateTime start2;
  private ZonedDateTime end;
  private ZonedDateTime end2;
  private String title;
  private String description;
  private String type;
  private EditTrainTripDTO editTrainTripDTO;
  private Image image;

  @BeforeEach
  void setUp() {

    start = ZonedDateTime.parse("2011-12-03T10:15:30+01:00");
    start2 = ZonedDateTime.parse("2011-12-03T10:15:30+03:00");
    end = ZonedDateTime.parse("2011-12-04T10:15:30+01:00");
    end2 = ZonedDateTime.parse("2011-12-05T10:15:30+01:00");
    image = new Image("gggg", 100, 100, "desc", "url", "cropping");
    editTrainTripDTO =
        new EditTrainTripDTO(
            "1", "2", start, end, "edit train trip", "description", "train", "ID001");
  }

  @DisplayName("Get id")
  @Test
  void testGetId() {
    assertEquals(editTrainTripDTO.getId(), "1");
  }

  @DisplayName("Get journey id")
  @Test
  void testGetJourneyId() {
    assertEquals(editTrainTripDTO.getJourneyId(), "2");
  }

  @DisplayName("Get start")
  @Test
  void testGetStart() {
    assertEquals(editTrainTripDTO.getStart(), start);
  }

  @DisplayName("Get end")
  @Test
  void testGetEnd() {
    assertEquals(editTrainTripDTO.getEnd(), end);
  }

  @DisplayName("Get title")
  @Test
  void testGetTitle() {
    assertEquals(editTrainTripDTO.getTitle(), "edit train trip");
  }

  @DisplayName("Get description")
  @Test
  void testGetDescription() {
    assertEquals(editTrainTripDTO.getDescription(), "description");
  }

  @DisplayName("Get type")
  @Test
  void testGetType() {
    assertEquals(editTrainTripDTO.getType(), "train");
  }

  @DisplayName("Set end")
  @Test
  void testSetEnd() {
    editTrainTripDTO.setEnd(end2);
    assertEquals(editTrainTripDTO.getEnd(), end2);
  }

  @DisplayName("Set start")
  @Test
  void testSetStart() {
    editTrainTripDTO.setStart(start2);
    assertEquals(editTrainTripDTO.getStart(), start2);
  }

  @DisplayName("Set description")
  @Test
  void testSetDescription() {
    editTrainTripDTO.setDescription("ABC");
    assertEquals(editTrainTripDTO.getDescription(), "ABC");
  }

  @DisplayName("Set ID")
  @Test
  void testSetId() {
    editTrainTripDTO.setId("3");
    assertEquals(editTrainTripDTO.getId(), "3");
  }

  @DisplayName("Get Image")
  @Test
  void testGetImage() {
    assertEquals(editTrainTripDTO.getCoverId(), "ID001");
  }

  @DisplayName("Set Image")
  @Test
  void testSetImage() {
    editTrainTripDTO.setCoverId("ID002");
    assertEquals(editTrainTripDTO.getCoverId(), "ID002");
  }
}
