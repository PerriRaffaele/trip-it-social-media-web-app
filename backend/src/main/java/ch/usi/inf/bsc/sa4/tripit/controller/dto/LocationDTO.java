package ch.usi.inf.bsc.sa4.tripit.controller.dto;

import ch.usi.inf.bsc.sa4.tripit.model.Location;

/** This class represents a DTO for a Location object. */
public class LocationDTO {

  /** The latitude coordinate of the location. */
  private double latitude;
  /** The longitude coordinate of the location. */
  private double longitude;
  /** The country where the location is. */
  private String country;
  /** The region where the location is. */
  private String region;
  /** Class empty constructor */
  public LocationDTO() {}

  /**
   * Instantiates a new LocationDTO.
   *
   * @param latitude the latitude coordinate of the location
   * @param longitude the longitude coordinate of the location
   * @param country country where the location is
   * @param region country where the location is
   */
  public LocationDTO(double latitude, double longitude, String country, String region) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.country = country;
    this.region = region;
  }

  /**
   * Gets the latitude of the location
   *
   * @return the latitude of this LocationDTO
   */
  public double getLatitude() {
    return latitude;
  }
  /**
   * Gets the longitude of the location
   *
   * @return the longitude of this LocationDTO
   */
  public double getLongitude() {
    return longitude;
  }
  /**
   * Gets the country where the location is
   *
   * @return the country of this LocationDTO
   */
  public String getCountry() {
    return country;
  }
  /**
   * Gets the region where the location is
   *
   * @return the region of this LocationDTO
   */
  public String getRegion() {
    return region;
  }
  /**
   * Sets the latitude of the location
   *
   * @param latitude the wanted latitude for this LocationDTO
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
   * @param country the wanted country for this LocationDTO
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
   * Converts this LocationDTO into a new Location object
   *
   * @return a new Location object
   */
  public Location toLocation() {
    return new Location(country, region, latitude, longitude);
  }
}
