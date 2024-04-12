package ch.usi.inf.bsc.sa4.tripit.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("The user")
public class UserTest {

  private User user;

  @BeforeEach
  void setup() {
    // this test preparation method is invoked right before each test invocation.
    this.user = User.from("1", "testname");
  }

  @DisplayName("after creation")
  @Nested
  class AfterCreationTests {

    @DisplayName("Constructor test")
    @Test
    void testConstructor() {
      User newUser = new User("name", true, "pic", "id0111");
      assertNotNull(newUser);
    }

    @DisplayName("has the same name provided in the constructor")
    @Test
    void testGetName() {
      var actualName = /*UserTests.this.*/ user.getName();
      var expectedName = "testname";
      assertEquals(expectedName, actualName, "name is not the one provided in the constructor");
    }

    @DisplayName("has the same id provided in the constructor")
    @Test
    void testGetId() {
      var actualId = /*UserTests.this.*/ user.getId();
      var expectedId = "1";
      assertEquals(expectedId, actualId, "id is not the one provided in the constructor");
    }

    @DisplayName("has a private settings by default")
    @Test
    void testIsPublic() {
      var actualIsPublic = /*UserTests.this.*/ user.isPublic();
      var expectedIsPublic = false;
      assertEquals(expectedIsPublic, actualIsPublic, "isPublic is not false by default");
    }

    @DisplayName("can change the privacy to public")
    @Test
    void testMakePublic() {
      user.makePublic();
      var actualIsPublic = /*UserTests.this.*/ user.isPublic();
      var expectedIsPublic = true;
      assertEquals(expectedIsPublic, actualIsPublic, "isPublic is not true after makePublic");
    }

    @DisplayName("can change the privacy to private")
    @Test
    void testMakePrivate() {
      user.makePrivate();
      var actualIsPublic = /*UserTests.this.*/ user.isPublic();
      var expectedIsPublic = false;
      assertEquals(expectedIsPublic, actualIsPublic, "isPublic is not false after makePrivate");
    }

    @DisplayName("has picturePath empty by default")
    @Test
    void testGetPicturePath() {
      var actualPicturePath = /*UserTests.this.*/ user.getPicturePath();
      var expectedPicturePath = "";
      assertEquals(expectedPicturePath, actualPicturePath, "picturePath is not empty by default");
    }

    @DisplayName("can change their picturePath")
    @Test
    void testPicturePathChange() {
      var newPicturePath = "picturePath2";
      user.setPicturePath(newPicturePath);
      var actualPicturePath = /*UserTests.this.*/ user.getPicturePath();
      assertEquals(
          newPicturePath,
          actualPicturePath,
          "picturePath is not the one provided in the constructor");
    }
  }
}
