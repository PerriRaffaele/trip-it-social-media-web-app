package ch.usi.inf.bsc.sa4.tripit.model;

import static com.mongodb.assertions.Assertions.*;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.FriendshipDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class FriendshipTest {
  private Friendship friendship, friendship1, friendship2;
  private User sender;
  private User receiver;

  @BeforeEach
  void setUp() {
    this.sender = User.from("1", "testname");
    this.receiver = User.from("2", "testname2");
    this.friendship = new Friendship();
    this.friendship1 = new Friendship(sender, receiver);
    this.friendship2 = new Friendship(sender, true, receiver, true);
  }

  @DisplayName("Friendship test")
  @Nested
  class AfterCreation {
    @DisplayName("setMuteById 1")
    @Test
    void testSetMute1() {
      friendship.setRequestReceiver(receiver);
      friendship.setRequestSender(sender);
      friendship.setSenderMuted(true);
      assertTrue(friendship.isSenderMuted());
    }

    @DisplayName("setMuteById 2")
    @Test
    void testSetMute2() {
      friendship.setRequestReceiver(receiver);
      friendship.setRequestSender(sender);
      friendship.setReceiverMuted(true);
    }

    @DisplayName("setMuteById 3")
    @Test
    void testSetMute3() {
      friendship.setRequestReceiver(receiver);
      friendship.setRequestSender(sender);
      friendship.setSenderMuted(true);
      String senderId = friendship.getRequestSender().getId();
      friendship.setMuteById(senderId);
      assertFalse(friendship.isSenderMuted());
    }

    @DisplayName("setMuteById 4")
    @Test
    void testSetMute4() {
      friendship.setRequestReceiver(receiver);
      friendship.setRequestSender(sender);
      friendship.setReceiverMuted(true);
      String receiverId = friendship.getRequestReceiver().getId();
      friendship.setMuteById(receiverId);
      assertFalse(friendship.isReceiverMuted());
    }

    @DisplayName("Get mute status through id 1")
    @Test
    void testGetMuteByID1() {
      String senderId = friendship1.getRequestSender().getId();
      assertFalse(friendship1.isMutedById(senderId));
    }

    @DisplayName("Get mute status through id 2")
    @Test
    void testGetMuteByID2() {
      String receiverId = friendship2.getRequestReceiver().getId();
      assertTrue(friendship2.isMutedById(receiverId));
    }

    @DisplayName("To Friendship DTO")
    @Test
    void testToFriendship() {
      FriendshipDTO friendshipDTO = friendship1.toFriendshipDTO();
      assertTrue(FriendshipDTO.class.isInstance(friendshipDTO));
    }
  }
}
