package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.FriendshipDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * This class represents a friendship between two users, identifiable as the sender and receiver of
 * the friend request.
 */
@Document(collection = "friendship")
public class Friendship {

  /** the id of the friendship in the database */
  @Id private String id;

  /** the user that sent the friend request */
  @DocumentReference private User requestSender;

  /** tells whether the user that sent the friend request has been muted by the other one */
  private boolean senderMuted;

  /** the user that received the friend request */
  @DocumentReference private User requestReceiver;

  /** tells whether the user that received the friend request has been muted by the other one */
  private boolean receiverMuted;

  /**
   * Instantiates a new Friendship.
   *
   * @param requestSender the request sender
   * @param senderMuted the sender muted
   * @param requestReceiver the request receiver
   * @param receiverMuted the receiver muted
   */
  @PersistenceCreator
  public Friendship(
      User requestSender, boolean senderMuted, User requestReceiver, boolean receiverMuted) {
    this.requestSender = requestSender;
    this.senderMuted = senderMuted;
    this.requestReceiver = requestReceiver;
    this.receiverMuted = receiverMuted;
  }

  /**
   * Instantiates a new Friendship.
   *
   * @param requestSender the request sender
   * @param requestReceiver the request receiver
   */
  public Friendship(User requestSender, User requestReceiver) {
    this.requestSender = requestSender;
    this.requestReceiver = requestReceiver;
    this.senderMuted = false;
    this.receiverMuted = false;
  }

  /** Empty constructor for friendship */
  public Friendship() {}

  /**
   * Gets request sender.
   *
   * @return the request sender
   */
  public User getRequestSender() {
    return requestSender;
  }

  /**
   * Is sender muted boolean.
   *
   * @return the boolean
   */
  public boolean isSenderMuted() {
    return senderMuted;
  }

  /**
   * Gets request receiver.
   *
   * @return the request receiver
   */
  public User getRequestReceiver() {
    return requestReceiver;
  }

  /**
   * Is receiver muted boolean.
   *
   * @return the boolean
   */
  public boolean isReceiverMuted() {
    return receiverMuted;
  }

  /**
   * Sets request sender.
   *
   * @param requestSender the request sender
   */
  public void setRequestSender(User requestSender) {
    this.requestSender = requestSender;
  }

  /**
   * Sets sender muted.
   *
   * @param senderMuted the sender muted
   */
  public void setSenderMuted(boolean senderMuted) {
    this.senderMuted = senderMuted;
  }

  /**
   * Set mute by id.
   *
   * @param id the id
   */
  public void setMuteById(String id) {
    if (id.equals(this.requestSender.getId())) {
      this.senderMuted = !this.senderMuted;
    } else {
      this.receiverMuted = !this.receiverMuted;
    }
  }

  /**
   * Returns true if the user with the provided id has been muted
   *
   * @param id the id of the user to be checked as muted
   * @return true if the user with provided id has been muted
   */
  public boolean isMutedById(String id) {
    if (id.equals(this.requestSender.getId())) {
      return this.senderMuted;
    } else {
      return this.receiverMuted;
    }
  }

  /**
   * Sets request receiver.
   *
   * @param requestReceiver the request receiver
   */
  public void setRequestReceiver(User requestReceiver) {
    this.requestReceiver = requestReceiver;
  }

  /**
   * Sets receiver muted.
   *
   * @param receiverMuted the receiver muted
   */
  public void setReceiverMuted(boolean receiverMuted) {
    this.receiverMuted = receiverMuted;
  }

  /**
   * Returns a FriendshipDTO representing this friendship.
   *
   * @return a FriendshipDTO representing this friendship
   */
  public FriendshipDTO toFriendshipDTO() {
    return new FriendshipDTO(
        this.id,
        this.requestSender.toUserDTO(),
        this.senderMuted,
        this.requestReceiver.toUserDTO(),
        this.receiverMuted);
  }
}
