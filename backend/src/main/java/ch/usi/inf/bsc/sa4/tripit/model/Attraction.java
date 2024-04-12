package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.AttractionDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.PlaceDTO;

/**
 * This class represent an Attraction as a Place with a link. It inherits from abstract class Place.
 */
public class Attraction extends Place {

  /** a string containing a link to a web page with information about this Attraction */
  private String link;

  /**
   * Creates an Attraction with the given name, location and link
   *
   * @param name the name of the attraction
   * @param location the location of the attraction
   * @param link the link of the attraction
   */
  public Attraction(String name, Location location, String link) {
    super(name, location);
    this.link = link;
  }

  /**
   * Returns the link to a web page containing information about this attraction
   *
   * @return the link of this attraction
   */
  public String getLink() {
    return link;
  }

  /**
   * Sets the link of this attraction to the given link
   *
   * @param link the link to a web page containing information about this attraction
   */
  public void setLink(String link) {
    this.link = link;
  }

  /**
   * Returns a PlaceDTO representing this attraction
   *
   * @return a PlaceDTO representing this attraction
   */
  public PlaceDTO toPlaceDTO() {
    return new AttractionDTO(
        this.getName(),
        this.getLocation().toLocationDTO(),
        this.getLink(),
        "Attraction",
        this.getId());
  }
}
