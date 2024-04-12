package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents users in our application. Users have profile data like name bio and email.
 * Users can be private or public. Users have journeys and can be friends with eachother. User
 * profiles are connected to their gitlab profile.
 */
@Document(collection = "users")
public class User {
  /** The id of the user in the database */
  @Id private String id;
  /** The id of the user on gitlab */
  private String gitlabId;
  /** The username of the user */
  private String username;
  /** The name of the user */
  private final String name;
  /** The bio of the user */
  private String bio;
  /** The email of the user */
  private String email;
  /** The path to the profile picture of the user */
  private String picturePath;
  /** Tells whether the user is public or private */
  private boolean isPublic;

  /**
   * Initializes a user with the given id, name, bio, email and username. Users are initialized as
   * private.
   *
   * @param id the id of the user
   * @param name the name of the user
   * @param bio the bio of the user
   * @param email the email of the user
   * @param username the username of the user
   */
  @PersistenceCreator
  public User(String id, String name, String bio, String email, String username) {
    this.id = id;
    this.name = name;
    this.bio = bio;
    this.email = email;
    this.isPublic = false;
    this.username = username;
  }

  /**
   * Initializes a Use with the given name, gitlab id, bio, email, picture path and username. Users
   * are initialized as private.
   *
   * @param name the name of the user
   * @param gitlabId the id of the user on gitlab
   * @param bio the bio of the user
   * @param email the email of the user
   * @param picturePath the path to the profile picture of the user
   * @param username the username of the user
   */
  public User(
      String name, String gitlabId, String bio, String email, String picturePath, String username) {
    this.name = name;
    this.bio = bio;
    this.gitlabId = gitlabId;
    this.email = email;
    this.picturePath = picturePath;
    this.isPublic = false;
    this.username = username;
  }

  /**
   * Initializes a user with the given name Users are initialized as private. The picture path is
   * initialized as empty.
   *
   * @param name the name of the user
   */
  public User(String name) {
    this.name = name;
    this.isPublic = false;
    this.picturePath = "";
  }

  /**
   * Initializes a user with the given name Users are initialized as private. The picture path is
   * initialized as empty.
   *
   * @param id the id of the user
   * @param name the name of the user
   */
  public User(String id, String name) {
    this.id = id;
    this.name = name;
    this.isPublic = false;
    this.picturePath = "";
  }

  // Constructor used for cloning. modify cloneUser() method in case you change the fields.
  /**
   * Initializes a user with the given name, privacy, picture path and id
   *
   * @param name the name of the user
   * @param isPublic true if the user is public, otherwise false
   * @param picturePath the path to the profile picture of the user
   * @param id the id of the user
   */
  public User(String name, boolean isPublic, String picturePath, String id) {
    this.name = name;
    this.isPublic = isPublic;
    this.picturePath = picturePath;
    this.id = id;
  }

  /**
   * User constructor used for testing.
   *
   * @param id the id of the user
   * @param name the name of the user
   * @param picturePath the path to the profile picture of the user
   * @param username the username of the user
   * @param bio the bio of the user
   * @param email the email of the user
   * @param isPublic true if the user is public, otherwise false
   */
  @JsonCreator
  public User(
      @JsonProperty("id") String id,
      @JsonProperty("name") String name,
      @JsonProperty("picturePath") String picturePath,
      @JsonProperty("username") String username,
      @JsonProperty("bio") String bio,
      @JsonProperty("email") String email,
      @JsonProperty("public") boolean isPublic) {
    this.id = id;
    this.name = name;
    this.picturePath = picturePath;
    this.username = username;
    this.bio = bio;
    this.email = email;
    this.isPublic = isPublic;
  }

  /**
   * Getter method for id field.
   *
   * @return String id.
   */
  public String getId() {
    return id;
  }

  /**
   * Getter method for name field.
   *
   * @return String name.
   */
  public String getName() {
    return name;
  }

  /**
   * Getter method for isPublic field.
   *
   * @return boolean isPublic.
   */
  public boolean isPublic() {
    return isPublic;
  }

  /**
   * Getter method for picturePath field.
   *
   * @return String picturePath.
   */
  public String getPicturePath() {
    return picturePath;
  }

  /** Method to set profile privacy to public. */
  public void makePublic() {
    isPublic = true;
  }

  /** Method to set profile privacy to private. */
  public void makePrivate() {
    isPublic = false;
  }

  /**
   * Returns the gitlab ID of the user.
   *
   * @return the gitlab ID of the user
   */
  public String getGitlabId() {
    return gitlabId;
  }

  /**
   * Returns the bio of the user
   *
   * @return the bio of the user
   */
  public String getBio() {
    return bio;
  }

  /**
   * Returns the email of the user.
   *
   * @return the email of the user
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns the username of the user.
   *
   * @return the username of the user
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the bio of the user to the one provided.
   *
   * @param bio the provided bio
   */
  public void setBio(String bio) {
    this.bio = bio;
  }

  /** Method to change profile privacy. */
  public void changePrivacy() {
    if (isPublic) {
      makePrivate();
    } else {
      makePublic();
    }
  }

  /**
   * Setter method for picturePath field.
   *
   * @param picturePath new value for the field picturePath.
   */
  public void setPicturePath(String picturePath) {
    this.picturePath = picturePath;
  }

  /**
   * Returns a UserDTO representing this user.
   *
   * @return a UserDTO representing this user
   */
  public UserDTO toUserDTO() {
    return new UserDTO(
        this.getId(),
        this.getName(),
        this.getPicturePath(),
        this.isPublic(),
        this.getUsername(),
        this.getBio(),
        this.getEmail());
  }

  /**
   * Returns a new User with the given name
   *
   * @param name the name of the user
   * @return a new User with the given name
   */
  public static User from(String name) {
    return new User(name);
  }

  /**
   * Returns a new User with the given id and name
   *
   * @param id the id of the user
   * @param name the name of the user
   * @return a new User with the given id and name
   */
  public static User from(String id, String name) {
    return new User(id, name);
  }
}
