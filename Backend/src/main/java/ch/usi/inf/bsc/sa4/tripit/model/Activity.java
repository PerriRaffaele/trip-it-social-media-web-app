package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.ActivityDTO;
import java.time.Duration;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * This class represents activities that can be done in Journeys. Each activity has a starting time,
 * an ending time, a title and a description.
 */
@Document(collection = "Activities")
public abstract class Activity {
  /** The id of the activity */
  @Id private String id;

  /** The Journey that contains this activity */
  @DocumentReference private Journey journey;
  /** The time at which the activity starts */
  private ZonedDateTime start;
  /** The time at which the activity ends */
  private ZonedDateTime end;
  /** The title of the activity */
  private String title;
  /** The description of the activity */
  private String description;

  @DocumentReference private Image imageLink;
  public static final int CONST2 = 2;

  /**
   * Instantiates a new Activity.
   *
   * @param journey the id of the journey that this activity belongs to
   * @param start the starting time
   * @param end the ending time
   * @param title the title
   * @param description the description
   * @param imageLink the image link
   */
  protected Activity(
      Journey journey,
      ZonedDateTime start,
      ZonedDateTime end,
      String title,
      String description,
      Image imageLink) {
    this.journey = journey;
    this.start = start;
    this.end = end;
    if (start.isAfter(end)) {
      throw new IllegalArgumentException("Activity cannot end before it started");
    }
    this.title = title;
    this.description = description;
    this.imageLink = imageLink;
  }

  /**
   * Gets the id that the activity has in the database.
   *
   * @return the id of the activity
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id of the activity.
   *
   * @param id the wanted id of the activity
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Gets the of that the journey tho whom this activity belongs to.
   *
   * @return the id of the journey of this activity
   */
  public Journey getJourney() {
    return this.journey;
  }

  /**
   * Sets the id of the journey to whom this activity belongs to the given string.
   *
   * @param journey the new id of the journey
   */
  public void setJourney(Journey journey) {
    this.journey = journey;
  }

  /**
   * Gets starting time of the activity.
   *
   * @return the starting time of the activity
   */
  public ZonedDateTime getStart() {
    return start;
  }

  /**
   * Sets starting time of the activity.
   *
   * @param start the wanted starting time
   */
  public void setStart(ZonedDateTime start) {
    this.start = start;
  }

  /**
   * Gets ending time of the activit.
   *
   * @return the ending time of the activity
   */
  public ZonedDateTime getEnd() {
    return end;
  }

  /**
   * Sets the ending time of the activity.
   *
   * @param end the wanted ending time
   */
  public void setEnd(ZonedDateTime end) {
    this.end = end;
  }

  /**
   * Gets the title of this activity.
   *
   * @return the title of this activity
   */
  public String getTitle() {
    return title;
  }

  public double midCalculationCO2(Location from, Location to) {
    double lat1 = from.getLatitude();
    double lat2 = to.getLatitude();
    final double lon1 = from.getLongitude();
    final double lon2 = to.getLongitude();
    final double dLat = Math.toRadians(lat2 - lat1);
    final double dLon = Math.toRadians(lon2 - lon1);
    lat1 = Math.toRadians(lat1);
    lat2 = Math.toRadians(lat2);
    final double a =
        Math.pow(Math.sin(dLat / CONST2), CONST2)
            + Math.pow(Math.sin(dLon / CONST2), CONST2) * Math.cos(lat1) * Math.cos(lat2);
    return CONST2 * Math.asin(Math.sqrt(a));
  }

  /**
   * Sets the title of the activity.
   *
   * @param title the wanted title for the activity
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the description of the activity.
   *
   * @return the description of the activity
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the activity.
   *
   * @param description the description of the activity
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Get the duration of the activity.
   *
   * @return the duration of the activity
   */
  public Duration getDuration() {
    return Duration.between(start, end);
  }

  public Image getImageLink() {
    return imageLink;
  }

  public void setImageLink(Image imageLink) {
    this.imageLink = imageLink;
  }

  /**
   * returns the DTO of this activity
   *
   * @return the DTO of this activity
   */
  public abstract ActivityDTO toActivityDTO();

  /**
   * Changes the data of this activity to match the start, end, title ana description of the given
   * one
   *
   * @param other the activity whose fields should be copied in this one
   */
  public void changeData(Activity other) {
    this.start = other.getStart();
    this.end = other.getEnd();
    this.title = other.getTitle();
    this.description = other.getDescription();
    this.imageLink = other.getImageLink();
  }
}
