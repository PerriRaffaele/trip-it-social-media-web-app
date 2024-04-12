package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.JourneyDTO;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * This class represents a journey, which has a title, a list of activities and belongs to a user. A
 * CO2 estimate of the journey can be calculated.
 */
@Document(collection = "journeys")
public class Journey implements Comparable<Journey> {

  /** The title of the journey */
  private String title;

  /** The list of activities of the journey */
  @DBRef(lazy = true)
  private List<Activity> activities;

  /** The id of the journey in the database */
  @Id private String id;

  /** The user that owns the journey */
  @DocumentReference private final User user;

  /** the CO2 estimate of this journey */
  private double co2Estimate;

  /** The image cover for this Journey */
  @DocumentReference private Image cover;

  /**
   * Initializes a journey with the given title, and user. This constructor does not use the user
   * image link (aka he did not upload any image)
   *
   * @param title the title of the journey
   * @param user the user who owns the journey
   * @param cover the image cover for this journey
   */
  @PersistenceCreator
  public Journey(String title, User user, Image cover) {
    this.title = title;
    this.user = user;
    this.activities = new ArrayList<>();
    this.co2Estimate = 0;
    this.cover = cover;
  }
  /**
   * Initializes a journey with the given title, owner and list of activities.
   *
   * @param title the title of the journey
   * @param user the user who owns the journey
   * @param activities the list of activities that belong to the journey
   */
  public Journey(String title, User user, Collection<Activity> activities) {
    this.title = title;
    this.user = user;
    this.activities = new ArrayList<>(activities);
  }

  /**
   * Adds up the co2 emission estimates of all activities.
   *
   * @return the total co2 emission estimation of the journey
   */
  public void recalculateCo2Estimate() {
    double sum = 0;
    for (final Activity activity : this.activities) {
      if (activity.getClass().equals(PlaneTrip.class)) {
        sum += ((PlaneTrip) activity).getCo2Estimate();
      } else if (activity.getClass().equals(TrainTrip.class)) {
        sum += ((TrainTrip) activity).getCo2Estimate();
      } else {
        // visits don't need to calculate CO2 estimate
      }
    }
    final double ROUNDING_FACTOR = 1000.0;
    this.co2Estimate = Math.round(sum * ROUNDING_FACTOR) / ROUNDING_FACTOR;
  }

  /** Gets co2Estimate. */
  public double getCo2Estimate() {
    return this.co2Estimate;
  }

  /**
   * Returns the title of this journey.
   *
   * @return the title of this journey
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Changes the title of this journey to the given title
   *
   * @param newTitle the new title of this journey
   */
  public void setTitle(String newTitle) {
    this.title = newTitle;
  }

  /**
   * Gives the starting time of this journey, which is the starting time of the first activity.
   * Assumes the activities list is organized chronologically.
   *
   * @return the starting time of this journey
   */
  public ZonedDateTime startTime() {
    return this.activities.get(0).getStart();
  }

  /**
   * Gives the ending time of this journey, which is the ending time of the last activity. Assumes
   * the activities list is organized chronologically.
   *
   * @return the ending time of this journey
   */
  public ZonedDateTime endTime() {
    return this.activities.get(numberOfActivities() - 1).getEnd();
  }

  /**
   * Returns the duration of this journey
   *
   * @throws DateTimeException – if the seconds between the temporals cannot be obtained
   * @throws ArithmeticException – if the calculation exceeds the capacity of Duration
   * @return the duration of this journey
   */
  public Duration getDuration() {
    return Duration.between(startTime(), endTime());
  }

  /**
   * Returns the number of activities in this journey
   *
   * @return the number of activities in this journey
   */
  public int numberOfActivities() {
    return this.activities.size();
  }

  /**
   * returns true if the duration of the activity at index i overlaps with the given activity.
   *
   * @param i the index of the activity we want to compare
   * @param other the other activity we want to compare
   * @return true if the duration of the activity at the provided index overlaps with the other
   *     activity
   */
  public boolean checkInvalidDate(int i, Activity other) {
    Activity myActivity = activities.get(i);
    boolean myActivityEndsAfterOtherStarts =
        myActivity.getStart().toEpochSecond() <= other.getStart().toEpochSecond()
            && activities.get(i).getEnd().toEpochSecond() >= other.getStart().toEpochSecond();
    boolean myActivityStartsBeforeOtherEnds =
        myActivity.getStart().toEpochSecond() <= other.getEnd().toEpochSecond()
            && activities.get(i).getEnd().toEpochSecond() >= other.getEnd().toEpochSecond();
    boolean myActivityIsContainedInOther =
        other.getStart().toEpochSecond() <= myActivity.getStart().toEpochSecond()
            && myActivity.getEnd().toEpochSecond() <= other.getEnd().toEpochSecond();
    return myActivityEndsAfterOtherStarts
        || myActivityStartsBeforeOtherEnds
        || myActivityIsContainedInOther;
  }

  /**
   * Checks if the provided activity overlaps with any of the activities of this journey and returns
   * the index at which it should be inserted in order for the list of activities to be
   * chronologically organized. Returns -1 for an invalid activity.
   *
   * @param newActivity the new activity
   * @return the index at which the activity should be inserted or -1
   */
  public int isValidActivity(Activity newActivity) {
    for (int i = 0; i < numberOfActivities(); i++) {
      if (checkInvalidDate(i, newActivity)) {
        return -1;
      }
      if (this.activities.get(i).getStart().toEpochSecond() > newActivity.getStart().toEpochSecond()
          && this.activities.get(i).getStart().toEpochSecond()
              > newActivity.getEnd().toEpochSecond()) {
        return i;
      }
    }
    return numberOfActivities();
  }

  /**
   * Checks if the edit of the old activity to the new activity overlaps with any of the activities
   * of this journey and returns the index at which it should be inserted in order for the list of
   * activities to be chronologically organized. Returns -1 for an invalid activity.
   *
   * @param oldActivity the old activity
   * @param newActivity the new activity
   * @return the index at which the activity should be inserted or -1
   */
  public int isValidEdit(Activity oldActivity, Activity newActivity) {
    int index = -1;
    for (int i = 0; i < numberOfActivities(); i++) {
      if (!oldActivity.getId().equals(activities.get(i).getId())) {
        if (checkInvalidDate(i, newActivity)) {
          return -1;
        }
      } else {
        index = i;
      }
    }
    return index;
  }

  /**
   * If possible, edits the activity at the given index to the provided activity
   *
   * @param index the index of the old activity
   * @param newActivity the edited activity
   */
  public void editActivity(int index, Activity newActivity) {
    this.removeActivity(index);
    this.addActivity(isValidActivity(newActivity), newActivity);
  }

  /**
   * Removes the activity at the given index.
   *
   * @param index the index of the activity to be removed
   */
  public void removeActivity(int index) {
    this.activities.remove(index);
  }

  /**
   * Adds the provided activity at the provided index in the activity list
   *
   * @param index the index at which the activity should be inserted
   * @param activity the activity to add
   */
  public void addActivity(int index, Activity activity) {
    this.activities.add(index, activity);
  }

  /**
   * Returns the activity at the given index.
   *
   * @param index the index of the activity to be returned
   * @return the activity at the given index
   */
  public Activity getActivity(int index) {
    return this.activities.get(index);
  }

  /**
   * Returns the User object that owns this journey.
   *
   * @return the user who owns this journey
   */
  public User getUser() {
    return this.user;
  }

  /**
   * Returns the unique identifier of this journey.
   *
   * @return the identifier of this journey.
   */
  public String getId() {
    return this.id;
  }

  /**
   * Converts this journey to a Data Transfer Object (DTO) representation.
   *
   * @return a JourneyDTO object that encapsulates the title and user of this journey.
   */
  public JourneyDTO toJourneyDTO() {
    if (this.cover == null) {
      return new JourneyDTO(
          this.title, this.user.getId(), this.getId(), this.getCo2Estimate(), "0000");
    } else {
      return new JourneyDTO(
          this.title, this.user.getId(), this.getId(), this.getCo2Estimate(), this.cover.getId());
    }
  }

  /**
   * Returns the list of activities of the journey.
   *
   * @return the list of activities of the journey
   */
  public List<Activity> getActivities() {
    return new ArrayList<>(activities);
  }

  public Image getCover() {
    return cover;
  }

  /**
   * returns 1 if this journey starts before the provided journey, 0 if they start at the same time
   * and -1 if it starts after the provided journey
   *
   * @param journey the journey to be compared.
   * @return 1 if this journey starts before the other journey, otherwise 0 or -1
   */
  @Override
  public int compareTo(Journey journey) {
    if (this.activities.get(0).getStart().isBefore(journey.getActivities().get(0).getStart())) {
      return 1;
    } else if (this.activities
        .get(0)
        .getStart()
        .isAfter(journey.getActivities().get(0).getStart())) {
      return -1;
    } else {
      return 0;
    }
  }
}
