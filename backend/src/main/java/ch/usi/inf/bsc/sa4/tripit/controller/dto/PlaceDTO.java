package ch.usi.inf.bsc.sa4.tripit.controller.dto;

/** This class represents the DTO for a Place object. */
// @JsonDeserialize(using = PlaceDTODeserializer.class)
public class PlaceDTO {

  /** The name of the place */
  private String name;
  /** The DTO of the location object of this place */
  private LocationDTO locationDTO;
  /** The type of the place ("airport", "station" or "attraction") */
  private String type;
  /** The id of the related place object in the database */
  private String id;
  /** Class empty constructor */
  public PlaceDTO() {}
  /**
   * Instantiates a new PlaceDTO
   *
   * @param name the name of the place
   * @param locationDTO the DTO of the location of the place
   * @param type the type of place ("airport", "station" or "attraction")
   * @param id the id of the related place
   */
  public PlaceDTO(String name, LocationDTO locationDTO, String type, String id) {
    this.name = name;
    this.locationDTO = locationDTO;
    this.type = type;
    this.id = id;
  }
  /**
   * Gets the name of the related place
   *
   * @return the name of this placeDTO
   */
  public String getName() {
    return name;
  }
  /**
   * Sets the name of this placeDTO.
   *
   * @param name the wanted name for this placeDTO
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * Gets the DTO of the location of the related place
   *
   * @return the locationDTO of this placeDTO
   */
  public LocationDTO getLocationDTO() {
    return locationDTO;
  }
  /**
   * Sets the locationDTO of this placeDTO.
   *
   * @param locationDTO the wanted locationDTO for this placeDTO
   */
  public void setLocationDTO(LocationDTO locationDTO) {
    this.locationDTO = locationDTO;
  }
  /**
   * Gets the type of the related place ("airport", "station" or "attraction")
   *
   * @return the type of this placeDTO
   */
  public String getType() {
    return type;
  }
  /**
   * Sets the type of this placeDTO.
   *
   * @param type the wanted type for this placeDTO
   */
  public void setType(String type) {
    this.type = type;
  }
  /**
   * Sets the id of this placeDTO.
   *
   * @param id the wanted id for this placeDTO
   */
  public void setId(String id) {
    this.id = id;
  }
  /**
   * Gets the id that the related place has in the database
   *
   * @return the id of the related place
   */
  public String getId() {
    return this.id;
  }
}
