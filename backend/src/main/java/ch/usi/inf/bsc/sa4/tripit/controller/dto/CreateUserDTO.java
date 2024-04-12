package ch.usi.inf.bsc.sa4.tripit.controller.dto;

import ch.usi.inf.bsc.sa4.tripit.service.GitUserDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/** This class represents a DTO for creating a new User. */
@JsonDeserialize(using = GitUserDeserializer.class)
public class CreateUserDTO {
  /** The Gitlab id of the user */
  private final String id;
  /** The name of the user */
  private final String name;
  /** The Gitlab username of the user */
  private final String username;
  /** The Gitlab bio of the user */
  private final String bio;
  /** The email address of the Gitlab account of the user */
  private final String email;
  /** The path to the avatar picture of the user */
  private final String picPath;

  /**
   * Instantiates a new CreateUserDTO
   *
   * @param name the name of the created user
   * @param id the gitlab id of the created user
   * @param bio the gitlab bio of the created user
   * @param email the email address of the Gitlab account of the created user
   * @param picPath the path to the avatar picture of the created user
   * @param username the Gitlab username of the created user
   */
  public CreateUserDTO(
      String name, String id, String bio, String email, String picPath, String username) {
    this.id = id;
    this.name = name;
    this.bio = bio;
    this.email = email;
    this.picPath = picPath;
    this.username = username;
  }

  /**
   * Gets the Gitlab id of the created user
   *
   * @return the gitlabId of this CreateUserDTO
   */
  public String getGitlabId() {
    return id;
  }
  /**
   * Gets the name of the created user
   *
   * @return the name of this CreateUserDTO
   */
  public String getName() {
    return name;
  }
  /**
   * Gets the Gitlab bio of the created user
   *
   * @return the bio of this CreateUserDTO
   */
  public String getBio() {
    return bio;
  }
  /**
   * Gets the email address of the Gitlab account of the created user
   *
   * @return the email of this CreateUserDTO
   */
  public String getEmail() {
    return email;
  }
  /**
   * Gets the path to the avatar picture of the created user
   *
   * @return the picturePath of this CreateUserDTO
   */
  public String getPicturePath() {
    return picPath;
  }
  /**
   * Gets the username of the Gitlab account of the created user
   *
   * @return the username of this CreateUserDTO
   */
  public String getUsername() {
    return username;
  }
}
