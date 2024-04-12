package ch.usi.inf.bsc.sa4.tripit.DTO;

import static com.mongodb.assertions.Assertions.assertFalse;
import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("UserDTO test class")
public class UserDTOTest {

  String id;
  String name;
  String picturePath;
  boolean isPublic;
  String username;
  String bio;
  String email;

  private UserDTO user;

  @BeforeEach
  public void setUp() {
    id = "123";
    name = "Valentino";
    picturePath = null;
    isPublic = true;
    username = "Belotcaci";
    bio = "I'm a traveler";
    email = "belval@example.com";
    user = new UserDTO(id, name, picturePath, isPublic, username, bio, email);
    user.setNumberOfJourneys(4);
    user.setTotalNumberOfActivities(4);
  }

  @Test
  public void testGetId() {
    assertEquals("123", user.getId());
  }

  @Test
  public void testSetId() {
    user.setId("");
    assertEquals("", user.getId());
  }

  @Test
  public void testGetName() {
    assertEquals("Valentino", user.getName());
  }

  @Test
  public void testSetName() {
    user.setName("John");
    assertEquals("John", user.getName());
  }

  @Test
  public void testGetPicturePath() {
    assertEquals(null, user.getPicturePath());
  }

  @Test
  public void testSetPicturePath() {
    user.setPicturePath(null);
    assertEquals(null, user.getPicturePath());
  }

  @Test
  public void testGetNumberOfJourneys() {
    assertEquals(4, user.getNumberOfJourneys());
  }

  @Test
  public void testSetNumberOfJourneys() {
    user.setNumberOfJourneys(5);
    assertEquals(5, user.getNumberOfJourneys());
  }

  @Test
  public void testGetTotalNumberOfActivities() {
    assertEquals(4, user.getTotalNumberOfActivities());
  }

  @Test
  public void testSetTotalNumberOfActivities() {
    user.setTotalNumberOfActivities(5);
    assertEquals(5, user.getTotalNumberOfActivities());
  }

  @Test
  public void testIsPublic() {
    assertTrue(user.isPublic());
  }

  @Test
  public void testSetPublic() {
    user.setPublic(false);
    assertFalse(user.isPublic());
  }

  @Test
  public void testGetUsername() {
    assertEquals("Belotcaci", user.getUsername());
  }

  @Test
  public void testGetBio() {
    assertEquals("I'm a traveler", user.getBio());
  }

  @Test
  public void testGetEmail() {
    assertEquals("belval@example.com", user.getEmail());
  }
}
