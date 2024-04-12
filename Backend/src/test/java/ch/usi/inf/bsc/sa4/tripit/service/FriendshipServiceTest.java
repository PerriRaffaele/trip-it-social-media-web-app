package ch.usi.inf.bsc.sa4.tripit.service;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import ch.usi.inf.bsc.sa4.tripit.model.Friendship;
import ch.usi.inf.bsc.sa4.tripit.model.User;
import ch.usi.inf.bsc.sa4.tripit.repository.FriendshipRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("The Friendship Service test")
public class FriendshipServiceTest {

  @Autowired private FriendshipService friendshipService;

  @Autowired private FriendshipRepository friendshipRepository;

  private User user1;
  private User user2;
  private Friendship friendship, updatedFriendShip;

  @BeforeEach
  void setup() {
    user1 = new User("1", "John");
    user2 = new User("2", "dasdas");
    friendship = new Friendship(user1, user2);
    updatedFriendShip = friendshipService.updateFriendShip(friendship);
  }

  @DisplayName("after updating a friendship")
  @Nested
  class WhenUpdatingAFriendship {

    @DisplayName("Updated friendship should not be null")
    @Test
    public void testUpdateFriendshipNotNull() {
      assertNotNull(updatedFriendShip);
    }

    @DisplayName("Updated friendship should be equal to friendship")
    @Test
    public void testUpdateFriendshipEqual() {
      assertEquals(updatedFriendShip, friendship);
    }
  }

  @DisplayName("when getting a friendship by two users")
  @Nested
  class WhenGettingAFriendshipByTwoUsers {
    @DisplayName("Friendship should not be null when it exists")
    @Test
    public void testGetFriendshipByRecAndSendExists() {
      User us = new User("7", "ds");
      Optional<Friendship> retrievedFriendship =
          friendshipService.getFriendshipByRecAndSend(us, user2);
      assertFalse(retrievedFriendship.isPresent());
    }
  }

  @DisplayName("after deleting a friendship")
  @Nested
  class WhenDeletingAFriendship {
    @Test
    public void testDeleteFriendship() {
      friendshipService.deleteFriendShip(updatedFriendShip);
      Optional<Friendship> retrievedFriendship =
          friendshipRepository.findById(updatedFriendShip.toFriendshipDTO().getId());
      assertFalse(retrievedFriendship.isPresent());
    }
  }

  @DisplayName("when getting all friends of a user")
  @Nested
  class WhenGettingAllFriendsOfAUser {
    @Test
    public void testGetAllFriendsByUser1() {
      Friendship friendship1 = new Friendship(user1, new User("5", "anna"));
      Friendship friendship2 = new Friendship(user2, new User("10", "Bob"));
      friendshipService.updateFriendShip(friendship);
      friendshipService.updateFriendShip(friendship1);
      friendshipService.updateFriendShip(friendship2);
      List<Friendship> allFriendsOfUser1 = friendshipService.getAllFriendsByUser(user1);
      assertNotNull(allFriendsOfUser1.size());
    }

    @Test
    public void testGetAllFriendsByUser2() {
      List<Friendship> allFriendsOfUser2 = friendshipService.getAllFriendsByUser(user2);
      assertNotNull(allFriendsOfUser2.size());
    }
  }
}
