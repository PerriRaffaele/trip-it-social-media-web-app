package ch.usi.inf.bsc.sa4.tripit.controller.dto;

/** This class represents a DTO for a Journey object. */
public class JourneyDTO {
  /** The title of the related journey */
  private String title;
  /** The id of the user that created the related journey */
  private String userId;
  /** The id of the related journey */
  private String journeyId;
  /** The total co2 emissions estimate of the related journey */
  private double co2Estimate;

  /** The image cover for this Journey */
  private String coverId;

  // Add an empty constructor to all DTOs in POST/PUT/PATCH requests.
  /** Class empty constructor */
  private JourneyDTO() {}

  /**
   * Instantiates a new JourneyDTO
   *
   * @param title the title of the journey
   * @param userId the id of the user that created the journey
   * @param journeyId the id of the journey
   * @param co2Estimate the co2 emissions estimate of the journey
   * @param coverId the link to the cover image
   */
  public JourneyDTO(
      String title, String userId, String journeyId, double co2Estimate, String coverId) {
    this.title = title;
    this.userId = userId;
    this.journeyId = journeyId;
    this.co2Estimate = co2Estimate;
    this.coverId = coverId;
  }

  /**
   * Gets the title of the related journey
   *
   * @return the title of this JourneyDTO
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of the journey
   *
   * @param title the wanted title for this JourneyDTO
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the id of the user that created the related journey
   *
   * @return the userId of this JourneyDTO
   */
  public String getUserId() {
    return userId;
  }

  /**
   * Sets the id of the user for this journey
   *
   * @param userId the wanted userId for this JourneyDTO
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * Gets the id of the related journey
   *
   * @return the journeyId of this JourneyDTO
   */
  public String getJourneyId() {
    return journeyId;
  }

  /**
   * Sets the id of the journey
   *
   * @param journeyId the wanted journeyId for this JourneyDTO
   */
  public void setJourneyId(String journeyId) {
    this.journeyId = journeyId;
  }

  /**
   * Gets the total co2 emissions estimate of the related journey
   *
   * @return the co2 emissions estimate of this JourneyDTO
   */
  public double getCo2Estimate() {
    return co2Estimate;
  }

  /**
   * Sets the total co2 emissions estimate of the journey
   *
   * @param co2Estimate the wanted co2 estimate for this JourneyDTO
   */
  public void setCo2Estimate(double co2Estimate) {
    this.co2Estimate = co2Estimate;
  }

  /**
   * Gets cover link.
   *
   * @return the cover link
   */
  public String getCoverId() {
    return coverId;
  }
}
