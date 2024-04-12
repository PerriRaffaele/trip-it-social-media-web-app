package ch.usi.inf.bsc.sa4.tripit.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.AttractionDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.LocationDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.VisitDTO;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VisitDTOTest {

  LocationDTO location1;
  LocationDTO location2;
  AttractionDTO attraction1;
  AttractionDTO attraction2;
  String title;
  String description;
  ZonedDateTime end;
  ZonedDateTime start;

  @BeforeEach
  void setUp() {
    location1 = new LocationDTO(47.376887, 8.541694, "Switzerland", "Zurigo");
    location2 = new LocationDTO(46.0036778, 8.951052, "Switzerland", "Lugano");
    attraction1 = new AttractionDTO("name1", location1, "link", "attraction", "1");
    attraction2 = new AttractionDTO("name2", location2, "link", "attraction", "2");
    title = "";
    description = "";
    // Define the time zone
    ZoneId zone = ZoneId.of("America/Los_Angeles");
    // Define the start and end date-time
    start = ZonedDateTime.of(2023, 3, 23, 9, 0, 0, 0, zone);
    end = ZonedDateTime.of(2023, 3, 23, 17, 0, 0, 0, zone);
  }

  @DisplayName("Get AttractionDTO")
  @Test
  void getAttractionDTO() {

    VisitDTO visit =
        new VisitDTO("1", "1", start, end, title, description, attraction1, "visit", "img.png");
    assertEquals(attraction1, visit.getAttraction());
  }

  @DisplayName("Set AttractionDTO")
  @Test
  void setAttractionDTO() {

    VisitDTO visit =
        new VisitDTO("1", "1", start, end, title, description, attraction1, "visit", "img.png");
    visit.setAttraction(attraction2);
    assertEquals(attraction2, visit.getAttraction());
  }
}
