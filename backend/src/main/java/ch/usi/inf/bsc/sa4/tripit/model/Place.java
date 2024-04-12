package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.PlaceDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * This class represents a place. Each place is either a station, an airport or an attraction and
 * has a defined location.
 */
@Document(collection = "places")
public abstract class Place {

  /** The id of the place in the database */
  @Id private String id;
  /** The name of the place */
  private String name;
  /** The location of the place */
  @DocumentReference private Location location;

  /**
   * Instantiates a new Place.
   *
   * @param name the name of the place
   * @param location the location of the place
   */
  @PersistenceCreator
  protected Place(String name, Location location) {
    this.name = name;
    this.location = location;
  }

  /**
   * Gets the name of the place
   *
   * @return the name of this Place
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the location of the place
   *
   * @return the Location of this Place
   */
  public Location getLocation() {
    return location;
  }

  /**
   * Sets the name of the place
   *
   * @param name the wanted name for this Place
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the location of the place
   *
   * @param location the wanted Location for this Place
   */
  public void setLocation(Location location) {
    this.location = location;
  }

  /**
   * Gets the id of the place
   *
   * @return the id of this Place
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id of the place
   *
   * @param id the wanted id for this Place
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Converts a Place into a PlaceDTO (implemented in subclasses)
   *
   * @return a new PlaceDTO
   */
  public abstract PlaceDTO toPlaceDTO();
}
