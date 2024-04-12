package ch.usi.inf.bsc.sa4.tripit.service;

import static org.junit.jupiter.api.Assertions.*;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.CreateUserDTO;
import ch.usi.inf.bsc.sa4.tripit.model.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("The User Service")
public class UserServiceTests {

  @Autowired UserService userService;

  CreateUserDTO newUserDTO;

  @BeforeEach
  void setup() {
    newUserDTO = new CreateUserDTO("user", "usertest", "bio", "email", "", "username");
  }

  @DisplayName(" after creating a new user ")
  @Nested
  class WhenCreatingANewUser {

    @DisplayName(" the new user should be non null")
    @Test
    public void testNotNull() {
      User newUser = userService.createUser(newUserDTO);
      assertNotNull(newUser, "user should be not null");
    }

    @DisplayName(" the new user have the specified name")
    @Test
    public void testCreateUser() {
      User newUser = userService.createUser(newUserDTO);
      assertEquals(newUser.getName(), newUserDTO.getName(), "user should have the specified name");
    }

    @DisplayName(" the new user should be contained in the list of users")
    @Test
    public void testUserContained() {
      User newUser = userService.createUser(newUserDTO);
      var listOfUsers = userService.getAll();
      var userExists =
          listOfUsers.stream()
              .anyMatch(
                  user ->
                      user.getName().equals(newUser.getName())
                          && user.getId().equals(newUser.getId()));
      assertTrue(userExists, "new user is not contained in the list of users");
    }
  }

  @DisplayName(" to update the ")
  @Nested
  class UserState {

    @DisplayName(" privacy setting")
    @Test
    public void testChangePrivacy() {
      var optionalUserEmpty = userService.changePrivacy("");
      assertEquals(false, optionalUserEmpty.isPresent());
      User newUser = userService.createUser(newUserDTO);
      var optionalUser = userService.changePrivacy(newUser.getId());
      assertTrue(optionalUser.isPresent(), "user should be present");
      var user = optionalUser.get();
      assertTrue(user.isPublic(), "user should be private");
    }

    @DisplayName(" public user list")
    @Test
    public void testUserNotContained() {
      User newUser = userService.createUser(newUserDTO);
      var optionalUser = userService.changePrivacy(newUser.getId());
      assertTrue(optionalUser.isPresent(), "user should be present");
      var publicUser = optionalUser.get();
      var listOfUsers = userService.getAllPublic();
      var userExists =
          listOfUsers.stream()
              .anyMatch(
                  user ->
                      user.getName().equals(publicUser.getName())
                          && user.getId().equals(publicUser.getId()));
      assertTrue(userExists, "new user is not contained in the list of public users");
    }
  }

  @DisplayName(" delete user at the end of the test")
  @Test
  public void testDeleteUser() {
    User newUser = userService.createUser(newUserDTO);
    userService.deleteUser(newUser);
    var optionalUser = userService.getById(newUser.getId());
    assertTrue(optionalUser.isEmpty(), "user should be deleted");
  }

  @DisplayName("Update existing user")
  @Test
  public void testUpdateUser() {
    User user = userService.createUser(newUserDTO);
    assertNotNull(user);
    String newName = "2";
    String newGitName = "NewName";
    String newBio = "newBio";
    user.setPicturePath("wayToPic");
    user.changePrivacy();
    User newUser = userService.updateUser(user);
    assertEquals(newUser.getGitlabId(), user.getGitlabId());
  }

  @DisplayName("Get User through gitlabId")
  @Test
  public void testGetByGitId() {
    CreateUserDTO newUserDTO1 =
        new CreateUserDTO("21", "gitID1111", "none", "a@usi.ch", "pic", "ddd");
    User user = userService.createUser(newUserDTO1);
    String gitID = user.getGitlabId();
    String git1 = user.getGitlabId();
    assertEquals(git1, gitID);
    Optional<User> gitUser = userService.getByGitId(gitID);
    assertEquals(user.getBio(), gitUser.get().getBio());
    assertEquals(user.getEmail(), gitUser.get().getEmail());
    assertEquals(user.getUsername(), gitUser.get().getUsername());
  }

  @DisplayName("Get user by their name")
  @Test
  public void testGetByName() {
    User user = userService.createUser(newUserDTO);
    System.out.println(user.getName());
    user.makePublic();
    userService.updateUser(user);
    List<User> users = userService.searchByNameStartingWith("21");
    assertNotNull(users);
  }

  @DisplayName("Get user by their email")
  @Test
  public void testGetByMail() {
    CreateUserDTO newUserDTO1 = new CreateUserDTO("21", "gitID1", "none", "a@usi.ch", "pic", "ddd");
    User user = userService.createUser(newUserDTO);
    System.out.println(user.getName());
    user.makePublic();
    userService.updateUser(user);
    User user2 = userService.createUser(newUserDTO1);
    user2.makePublic();
    userService.updateUser(user2);
    List<User> users = userService.searchByMailStartingWith("a@");
    assertNotNull(users);
  }

  @DisplayName("Search by name contained")
  @Test
  public void testSearchNameContaining() {
    CreateUserDTO newUserDTO1 = new CreateUserDTO("21", "gitID1", "none", "a@usi.ch", "pic", "ddd");
    User user = userService.createUser(newUserDTO);
    System.out.println(user.getName());
    user.makePublic();
    userService.updateUser(user);
    User user2 = userService.createUser(newUserDTO1);
    user2.makePublic();
    userService.updateUser(user2);
    List<User> users = userService.searchByNameContaining("1");
    assertNotNull(users);
  }
}
