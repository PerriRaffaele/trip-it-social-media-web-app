package ch.usi.inf.bsc.sa4.tripit.controller.dto;

/** This class represents a DTO for a User object. */
public class UserDTO {
  /** The id of the user */
  private String id;
  /** The name of the user */
  private String name;
  /** The path to the avatar picture of the user */
  private String picturePath;
  /** The total number of journeys of the user */
  private int numberOfJourneys;
  /** The total number of activities of the user */
  private int totalNumberOfActivities;
  /** The state of the public/private setting of the user */
  private boolean isPublic;
  /** The username of the user */
  private String username;
  /** The bio of the user */
  private String bio;
  /** The email of the user */
  private String email;

  /** Class empty constructor */
  public UserDTO() {}

  /**
   * Instantiates a new UserDTO
   *
   * @param id the gitlab id of the user
   * @param name the name of the created user
   * @param picturePath the path to the avatar picture of the user
   * @param isPublic the state of the public/private setting of the user
   * @param username the Gitlab username of the user
   * @param bio the gitlab bio of the user
   * @param email the email address of the Gitlab account of the user
   */
  public UserDTO(
      String id,
      String name,
      String picturePath,
      boolean isPublic,
      String username,
      String bio,
      String email) {
    this.id = id;
    this.name = name;
    this.picturePath = picturePath;
    this.isPublic = isPublic;

    this.username = username;
    this.bio = bio;
    this.email = email;
  }

  /**
   * Gets the id of the user
   *
   * @return the idd of this UserDTO
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id of the user
   *
   * @param id the wanted id for this user
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Gets the name of the user
   *
   * @return the name of this UserDTO
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the path to the avatar picture of the user
   *
   * @return the picturePath of this UserDTO
   */
  public String getPicturePath() {
    return picturePath;
  }

  /**
   * Sets the path to the avatar picture of the user
   *
   * @param picturePath the wanted path to the avatar picture for this user
   */
  public void setPicturePath(String picturePath) {
    this.picturePath = picturePath;
  }

  /**
   * Sets the name of the user
   *
   * @param name the wanted name for this user
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the number of journeys of the user
   *
   * @return the number of journeys of this UserDTO
   */
  public int getNumberOfJourneys() {
    return numberOfJourneys;
  }

  /**
   * Sets the number of journeys of the user
   *
   * @param numberOfJourneys the wanted number of journeys for this user
   */
  public void setNumberOfJourneys(int numberOfJourneys) {
    this.numberOfJourneys = numberOfJourneys;
  }

  /**
   * Gets the total number of activities of the user
   *
   * @return the number of activities of this UserDTO
   */
  public int getTotalNumberOfActivities() {
    return totalNumberOfActivities;
  }

  /**
   * Sets the total number of activities of the user
   *
   * @param totalNumberOfActivities the wanted number of activities for this user
   */
  public void setTotalNumberOfActivities(int totalNumberOfActivities) {
    this.totalNumberOfActivities = totalNumberOfActivities;
  }

  /**
   * Gets the state of the public/private setting of the user
   *
   * @return True if the user profile is public, False otherwise
   */
  public boolean isPublic() {
    return isPublic;
  }

  /**
   * Sets the state of the public/private setting of the user
   *
   * @param aPublic the wanted public/private state for this user
   */
  public void setPublic(boolean aPublic) {
    isPublic = aPublic;
  }

  /**
   * Gets the username of the user
   *
   * @return the username of this UserDTO
   */
  public String getUsername() {
    return username;
  }

  /**
   * Gets the bio of the user
   *
   * @return the bio of this UserDTO
   */
  public String getBio() {
    return bio;
  }

  /**
   * Gets the email of the user
   *
   * @return the email of this UserDTO
   */
  public String getEmail() {
    return email;
  }

  /**
   * Clone user dto user dto.
   *
   * @return the user dto
   */
  public UserDTO cloneUserDTO() {
    return new UserDTO(
        this.getId(),
        this.getName(),
        this.getPicturePath(),
        this.isPublic(),
        this.getUsername(),
        this.getBio(),
        this.getEmail());
  }
}
