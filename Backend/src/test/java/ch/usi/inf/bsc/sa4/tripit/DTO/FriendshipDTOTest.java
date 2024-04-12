package ch.usi.inf.bsc.sa4.tripit.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.FriendshipDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FriendshipDTOTest {

  String id;
  UserDTO requestSender;
  UserDTO requestReceiver;
  UserDTO requestSender2;
  UserDTO requestReceiver2;
  boolean senderMuted;
  boolean receiverMuted;

  @BeforeEach
  void setUp() {
    id = "0";
    requestSender = new UserDTO("1", "sender", "path", true, "username", "bio", "email");
    requestReceiver = new UserDTO("2", "receiver", "path", true, "username", "bio", "email");
    requestSender2 = new UserDTO("3", "sender", "path", true, "username", "bio", "email");
    requestReceiver2 = new UserDTO("4", "receiver", "path", true, "username", "bio", "email");
    senderMuted = false;
    receiverMuted = false;
  }

  @DisplayName("Get id")
  @Test
  void testGetId() {
    FriendshipDTO friendshipDTO =
        new FriendshipDTO(id, requestSender, senderMuted, requestReceiver, receiverMuted);
    assertEquals("0", friendshipDTO.getId());
  }

  @DisplayName("Set id")
  @Test
  void testSetId() {
    FriendshipDTO friendshipDTO =
        new FriendshipDTO(id, requestSender, senderMuted, requestReceiver, receiverMuted);
    friendshipDTO.setId("1");
    assertEquals("1", friendshipDTO.getId());
  }

  @DisplayName("Get request sender")
  @Test
  void testGetRequestSender() {
    FriendshipDTO friendshipDTO =
        new FriendshipDTO(id, requestSender, senderMuted, requestReceiver, receiverMuted);
    assertEquals(requestSender.getId(), friendshipDTO.getRequestSender().getId());
  }

  @DisplayName("Set request sender")
  @Test
  void testSetRequestSender() {
    FriendshipDTO friendshipDTO =
        new FriendshipDTO(id, requestSender, senderMuted, requestReceiver, receiverMuted);
    friendshipDTO.setRequestSender(requestSender2);
    assertEquals(requestSender2.getId(), friendshipDTO.getRequestSender().getId());
  }

  @DisplayName("Get request receiver")
  @Test
  void testGetRequestReceiver() {
    FriendshipDTO friendshipDTO =
        new FriendshipDTO(id, requestSender, senderMuted, requestReceiver, receiverMuted);
    assertEquals(requestReceiver.getId(), friendshipDTO.getRequestReceiver().getId());
  }

  @DisplayName("Set request receiver")
  @Test
  void testSetRequestReceiver() {
    FriendshipDTO friendshipDTO =
        new FriendshipDTO(id, requestSender, senderMuted, requestReceiver, receiverMuted);
    friendshipDTO.setRequestReceiver(requestReceiver2);
    assertEquals(requestReceiver2.getId(), friendshipDTO.getRequestReceiver().getId());
  }

  @DisplayName("Get sender muted state")
  @Test
  void testIsSenderMuted() {
    FriendshipDTO friendshipDTO =
        new FriendshipDTO(id, requestSender, senderMuted, requestReceiver, receiverMuted);
    assertEquals(senderMuted, friendshipDTO.isSenderMuted());
  }

  @DisplayName("Set sender muted state")
  @Test
  void testSetSenderMuted() {
    FriendshipDTO friendshipDTO =
        new FriendshipDTO(id, requestSender, senderMuted, requestReceiver, receiverMuted);
    friendshipDTO.setSenderMuted(false);
    assertFalse(friendshipDTO.isSenderMuted());
  }

  @DisplayName("Get receiver muted state")
  @Test
  void testIsReceiverMuted() {
    FriendshipDTO friendshipDTO =
        new FriendshipDTO(id, requestSender, senderMuted, requestReceiver, receiverMuted);
    assertEquals(receiverMuted, friendshipDTO.isReceiverMuted());
  }

  @DisplayName("Set receiver muted state")
  @Test
  void testSetReceiverMuted() {
    FriendshipDTO friendshipDTO =
        new FriendshipDTO(id, requestSender, senderMuted, requestReceiver, receiverMuted);
    friendshipDTO.setReceiverMuted(false);
    assertFalse(friendshipDTO.isReceiverMuted());
  }
}
