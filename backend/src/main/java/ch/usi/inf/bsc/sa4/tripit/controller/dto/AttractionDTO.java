package ch.usi.inf.bsc.sa4.tripit.controller.dto;

import ch.usi.inf.bsc.sa4.tripit.model.Attraction;

/** This class represents the DTO for an Attraction object. */
public class AttractionDTO extends PlaceDTO {
  /** The link to the attraction */
  private String link;
  /** Class empty constructor */
  public AttractionDTO() {
    super();
  }
  /**
   * Instantiates a new AttractionDTO
   *
   * @param name the name of the place
   * @param locationDTO the DTO of the location of the attraction
   * @param link the link related to the attraction
   * @param type the type of place ("attraction")
   * @param id the id of the related place
   */
  public AttractionDTO(String name, LocationDTO locationDTO, String link, String type, String id) {
    super(name, locationDTO, type, id);
    this.link = link;
  }
  /**
   * Gets the link of the related attraction
   *
   * @return the link of this attractionDTO
   */
  public String getLink() {
    return link;
  }

  /**
   * Sets the link of the attractionDTO.
   *
   * @param link the wanted link of the activityDTO
   */
  public void setLink(String link) {
    this.link = link;
  }
  /**
   * Converts this attractionDTO into a new Attraction object
   *
   * @return a new Attraction object
   */
  public Attraction toAttraction() {
    return new Attraction(this.getName(), this.getLocationDTO().toLocation(), this.getLink());
  }
}
