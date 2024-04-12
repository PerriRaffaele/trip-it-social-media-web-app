package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.LocationDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.PlaceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AttractionTest {

  String name;
  Location location;
  String link;
  Attraction attraction;

  @BeforeEach
  public void setUp() {
    name = "name";
    location = new Location("Switzerland", "Zurigo", 47.376887, 8.541694);
    link = "link";
    attraction = new Attraction(name, location, link);
  }

  @Test
  public void testGetLink() {
    assert (attraction.getLink().equals(link));
  }

  @Test
  public void testSetLink() {
    String newLink = "newLink";
    attraction.setLink(newLink);
    assert (newLink.equals(attraction.getLink()));
  }

  @Test
  public void testToPlaceDTO() {
    PlaceDTO result = attraction.toPlaceDTO();
    assert ("Attraction".equals(result.getType()));
    assert (result.getName().equals(name));
    LocationDTO location = result.getLocationDTO();
    assert ("Switzerland".equals(location.getCountry()));
    assert ("Zurigo".equals(location.getRegion()));
    assert (location.getLatitude() == 47.376887);
    assert (location.getLongitude() == 8.541694);
  }
}
