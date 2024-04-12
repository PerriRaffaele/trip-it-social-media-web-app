package ch.usi.inf.bsc.sa4.tripit.controller.dto;

import ch.usi.inf.bsc.sa4.tripit.serializer.CustomDateTimeZoneDeserializer;
import ch.usi.inf.bsc.sa4.tripit.serializer.CustomDateTimeZoneSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.ZonedDateTime;

/** This class represents the DTO of an Activity. */
public abstract class ActivityDTO {

  /** The id of the related activity */
  private String id;

  /** Starting time of the activity */
  @JsonSerialize(using = CustomDateTimeZoneSerializer.class)
  @JsonDeserialize(using = CustomDateTimeZoneDeserializer.class)
  private ZonedDateTime start;

  /** Ending time of the activity */
  @JsonDeserialize(using = CustomDateTimeZoneDeserializer.class)
  @JsonSerialize(using = CustomDateTimeZoneSerializer.class)
  private ZonedDateTime end;
  /** The title of the related activity */
  private String title;
  /** The description of the related activity */
  private String description;
  /** The id of the journey which contains the related activity */
  private String journeyId;
  /** The type ("plane", "train" or "visit") of the related activity */
  private String type;

  private String coverId;

  /** Class empty constructor */
  protected ActivityDTO() {}

  /**
   * Instantiates a new ActivityDTO.
   *
   * @param id the id of the related Activity in the database
   * @param journeyId the id of the journey that this activity belongs to
   * @param start the starting time
   * @param end the ending time
   * @param title the title
   * @param description the description
   * @param type the type of the activity
   * @param coverId the image link
   */
  protected ActivityDTO(
      String id,
      String journeyId,
      ZonedDateTime start,
      ZonedDateTime end,
      String title,
      String description,
      String type,
      String coverId)
      throws IllegalArgumentException {

    if (start.isAfter(end)) {
      throw new IllegalArgumentException();
    }
    this.id = id;
    this.journeyId = journeyId;
    this.start = start;
    this.end = end;
    this.title = title;
    this.description = description;
    this.type = type;
    this.coverId = coverId;
  }

  /**
   * Gets the id that the related activity has in the database.
   *
   * @return the id of the related activity
   */
  public String getId() {
    return id;
  }
  /**
   * Sets the id of the activityDTO.
   *
   * @param id the wanted id of the activityDTO
   */
  public void setId(String id) {
    this.id = id;
  }
  /**
   * Gets the id of the journey which contains the related activity
   *
   * @return the id of the journey of this activityDTO
   */
  public String getJourneyId() {
    return journeyId;
  }
  /**
   * Sets the id of the journey for this activityDTO.
   *
   * @param journeyId the wanted id of the journey for this activityDTO
   */
  public void setJourneyId(String journeyId) {
    this.journeyId = journeyId;
  }
  /**
   * Gets the starting time the related activity
   *
   * @return the starting time of the activityDTO
   */
  public ZonedDateTime getStart() {
    return start;
  }
  /**
   * Sets the starting time for this activityDTO.
   *
   * @param start the wanted starting time of this activityDTO
   */
  public void setStart(ZonedDateTime start) {
    this.start = start;
  }
  /**
   * Gets the ending time of the related activity
   *
   * @return the ending time of the activityDTO
   */
  public ZonedDateTime getEnd() {
    return end;
  }
  /**
   * Sets the ending time for this activityDTO.
   *
   * @param end the wanted ending time of this activityDTO
   */
  public void setEnd(ZonedDateTime end) {
    this.end = end;
  }
  /**
   * Gets the title of the related activity
   *
   * @return the title of the activityDTO
   */
  public String getTitle() {
    return title;
  }
  /**
   * Sets the title for this activityDTO.
   *
   * @param title the wanted title of this activityDTO
   */
  public void setTitle(String title) {
    this.title = title;
  }
  /**
   * Gets the description of the related activity
   *
   * @return the description of the activityDTO
   */
  public String getDescription() {
    return description;
  }
  /**
   * Sets the description for this activityDTO.
   *
   * @param description the wanted description of this activityDTO
   */
  public void setDescription(String description) {
    this.description = description;
  }
  /**
   * Gets the type of the related activity
   *
   * @return the type of the activityDTO
   */
  public String getType() {
    return type;
  }
  /**
   * Sets the type for this activityDTO.
   *
   * @param type the wanted type of this activityDTO
   */
  public void setType(String type) {
    this.type = type;
  }

  public String getCoverId() {
    return coverId;
  }

  public void setCoverId(String coverId) {
    this.coverId = coverId;
  }
}
