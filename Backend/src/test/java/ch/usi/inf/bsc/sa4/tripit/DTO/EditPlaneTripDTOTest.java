package ch.usi.inf.bsc.sa4.tripit.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.EditPlaneTripDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Image;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EditPlaneTripDTOTest {

  private String id;
  private String journeyId;
  private ZonedDateTime start;
  private ZonedDateTime start2;
  private ZonedDateTime end;
  private ZonedDateTime end2;
  private String title;
  private String description;
  private String type;
  private EditPlaneTripDTO editPlaneTripDTO;
  private Image image;

  @BeforeEach
  void setUp() {
    start = ZonedDateTime.parse("2011-12-03T10:15:30+01:00");
    start2 = ZonedDateTime.parse("2011-12-03T10:15:30+03:00");
    end = ZonedDateTime.parse("2011-12-04T10:15:30+01:00");
    end2 = ZonedDateTime.parse("2011-12-05T10:15:30+01:00");
    image = new Image("gggg", 100, 100, "desc", "url", "cropping");
    editPlaneTripDTO =
        new EditPlaneTripDTO(
            "1", "2", start, end, "edit planeTrip", "description", "plane", "abc", "ID001");
  }

  @DisplayName("Get id")
  @Test
  void testGetId() {
    assertEquals(editPlaneTripDTO.getId(), "1");
  }

  @DisplayName("Get journey id")
  @Test
  void testGetJourneyId() {
    assertEquals(editPlaneTripDTO.getJourneyId(), "2");
  }

  @DisplayName("Get start")
  @Test
  void testGetStart() {
    assertEquals(editPlaneTripDTO.getStart(), start);
  }

  @DisplayName("Get end")
  @Test
  void testGetEnd() {
    assertEquals(editPlaneTripDTO.getEnd(), end);
  }

  @DisplayName("Get title")
  @Test
  void testGetTitle() {
    assertEquals(editPlaneTripDTO.getTitle(), "edit planeTrip");
  }

  @DisplayName("Get description")
  @Test
  void testGetDescription() {
    assertEquals(editPlaneTripDTO.getDescription(), "description");
  }

  @DisplayName("Get type")
  @Test
  void testGetType() {
    assertEquals(editPlaneTripDTO.getType(), "plane");
  }

  @DisplayName("Get flight number")
  @Test
  void testGetFlightNumber() {
    assertEquals(editPlaneTripDTO.getFlightNumber(), "abc");
  }

  @DisplayName("Set end")
  @Test
  void testSetEnd() {
    editPlaneTripDTO.setEnd(end2);
    assertEquals(editPlaneTripDTO.getEnd(), end2);
  }

  @DisplayName("Set start")
  @Test
  void testSetStart() {
    editPlaneTripDTO.setStart(start2);
    assertEquals(editPlaneTripDTO.getStart(), start2);
  }

  @DisplayName("Set description")
  @Test
  void testSetDescription() {
    editPlaneTripDTO.setDescription("ABC");
    assertEquals(editPlaneTripDTO.getDescription(), "ABC");
  }

  @DisplayName("Set ID")
  @Test
  void testSetId() {
    editPlaneTripDTO.setId("3");
    assertEquals(editPlaneTripDTO.getId(), "3");
  }

  @DisplayName("Set flight number")
  @Test
  void testSetFlightNumber() {
    editPlaneTripDTO.setFlightNumber("xyz");
    assertEquals(editPlaneTripDTO.getFlightNumber(), "xyz");
  }

  @DisplayName("Get image")
  @Test
  void testGetImage() {
    assertEquals(editPlaneTripDTO.getCoverId(), "ID001");
  }

  @DisplayName("Set image")
  @Test
  void testSetImage() {
    editPlaneTripDTO.setCoverId("ID002");
    assertEquals(editPlaneTripDTO.getCoverId(), "ID002");
  }
}
