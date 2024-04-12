package ch.usi.inf.bsc.sa4.tripit.controller;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.FriendshipDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Friendship;
import ch.usi.inf.bsc.sa4.tripit.model.User;
import ch.usi.inf.bsc.sa4.tripit.service.FriendshipService;
import ch.usi.inf.bsc.sa4.tripit.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friends")
public class FriendshipController {

  private final FriendshipService friendshipService;

  private final UserService userService;

  @Autowired
  public FriendshipController(FriendshipService friendshipService, UserService userService) {
    this.friendshipService = friendshipService;
    this.userService = userService;
  }

  @DeleteMapping("/{reqId}/{recId}")
  public ResponseEntity<FriendshipDTO> unfollowFriend(
      @PathVariable("reqId") String reqId, @PathVariable("recId") String recId) {
    Optional<User> sender = userService.getById(reqId);
    Optional<User> rec = userService.getById(recId);
    if (sender.isPresent() && rec.isPresent()) {
      Optional<Friendship> fr =
          friendshipService.getFriendshipByRecAndSend(rec.get(), sender.get());
      if (fr.isPresent()) {
        friendshipService.deleteFriendShip(fr.get());
        return ResponseEntity.ok().build();
      }
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/{reqId}/{recId}")
  public ResponseEntity<FriendshipDTO> getFriendshipByRecAndSend(
      @PathVariable("reqId") String reqId, @PathVariable("recId") String recId) {
    var sender = userService.getById(reqId);
    var rec = userService.getById(recId);
    if (sender.isPresent() && rec.isPresent()) {
      Optional<Friendship> fr =
          friendshipService.getFriendshipByRecAndSend(rec.get(), sender.get());
      if (fr.isPresent()) {
        return ResponseEntity.ok(fr.get().toFriendshipDTO());
      }
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<List<User>> getAllFriendsByUser(@PathVariable("id") String id) {
    Optional<User> user = userService.getById(id);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var friendships = friendshipService.getAllFriendsByUser(user.get());
    ArrayList<User> friends = new ArrayList<>();
    for (var f : friendships) {
      if (f.getRequestReceiver().getId().equals(id)) {
        friends.add(f.getRequestSender());
      } else {
        friends.add(f.getRequestReceiver());
      }
    }
    return ResponseEntity.ok(friends);
  }

  @GetMapping("/{id}/unmuted")
  public ResponseEntity<List<User>> getAllUnmutedFriends(@PathVariable("id") String id) {
    var user = userService.getById(id);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var friendships = friendshipService.getAllFriendsByUser(user.get());
    ArrayList<User> friends = new ArrayList<>();
    for (var f : friendships) {
      if (f.getRequestReceiver().getId().equals(id)) {
        if (!f.isSenderMuted()) {
          friends.add(f.getRequestSender());
        }
      } else {
        if (!f.isReceiverMuted()) {
          friends.add(f.getRequestReceiver());
        }
      }
    }
    return ResponseEntity.ok(friends);
  }

  @PutMapping("/{reqId}/{recId}/muteOrUnmute")
  public ResponseEntity<FriendshipDTO> muteFriend(
      @PathVariable("reqId") String reqId, @PathVariable("recId") String recId) {
    Optional<User> sender = userService.getById(reqId);
    Optional<User> rec = userService.getById(recId);
    if (sender.isPresent() && rec.isPresent()) {
      Optional<Friendship> fr =
          friendshipService.getFriendshipByRecAndSend(sender.get(), rec.get());
      if (fr.isPresent()) {
        Friendship friendship = fr.get();
        friendship.setMuteById(recId);
        return ResponseEntity.ok(friendshipService.updateFriendShip(friendship).toFriendshipDTO());
      }
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/{reqId}/{recId}/isMuted")
  public ResponseEntity<Boolean> isMutedFriends(
      @PathVariable("reqId") String reqId, @PathVariable("recId") String recId) {
    var sender = userService.getById(reqId);
    var rec = userService.getById(recId);
    if (sender.isPresent() && rec.isPresent()) {
      Optional<Friendship> fr =
          friendshipService.getFriendshipByRecAndSend(sender.get(), rec.get());
      if (fr.isPresent()) {
        Friendship friendship = fr.get();
        Boolean isMuted = friendship.isMutedById(recId);
        return ResponseEntity.ok(isMuted);
      }
    }
    return ResponseEntity.notFound().build();
  }
}
