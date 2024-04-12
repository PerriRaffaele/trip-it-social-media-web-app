package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.LocationDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents a location in the world. Each location is defined by latitude and longitude
 * coordinates and has the country and region where it is situated.
 */
@Document(collection = "locations")
public class Location {

  /** The id of the location object in the database */
  @Id private String id;
  /** The latitude coordinate of the location */
  private double latitude;
  /** The longitude coordinate of the location */
  private double longitude;
  /** The country where the location is */
  private String country;
  /** The region where the location is */
  private String region;

  /**
   * Instantiates a new Location.
   *
   * @param country the country where the location is
   * @param region the region where the location is
   * @param latitude latitude of the location
   * @param longitude longitude of the location
   */
  public Location(String country, String region, double latitude, double longitude) {
    this.country = country;
    this.region = region;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * Gets the latitude of the location
   *
   * @return the latitude of this Location
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Gets the longitude of the location
   *
   * @return the longitude of this Location
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * Gets the country where the location is
   *
   * @return the country of this Location
   */
  public String getCountry() {
    return country;
  }

  /**
   * Gets the region where the location is
   *
   * @return the region of this Location
   */
  public String getRegion() {
    return region;
  }

  /**
   * Sets the latitude of the location
   *
   * @param latitude the wanted latitude for this Location
   */
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  /**
   * Sets the longitude of the location
   *
   * @param longitude the wanted longitude for this LocationDTO
   */
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  /**
   * Sets the country where the location is
   *
   * @param country the wanted country for this Location
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Sets the region where the location is
   *
   * @param region the wanted region for this LocationDTO
   */
  public void setRegion(String region) {
    this.region = region;
  }

  /**
   * Converts a LocationDTO into a new Location object
   *
   * @return a new Location object
   */
  public static Location toLocation(LocationDTO dto) {
    return new Location(dto.getCountry(), dto.getRegion(), dto.getLatitude(), dto.getLongitude());
  }

  /**
   * Converts this Location into a new LocationDTO object
   *
   * @return a new LocationDTO object
   */
  public LocationDTO toLocationDTO() {
    return new LocationDTO(this.latitude, this.longitude, this.country, this.region);
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(String id) {
    this.id = id;
  }
}
