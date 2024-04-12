package ch.usi.inf.bsc.sa4.tripit.controller.dto;

/** This class represents a DTO for a Friendship object. */
public class FriendshipDTO {

  /** The id of the related Friendship object in the database */
  private String id;
  /** The DTO of the User sending the request */
  private UserDTO requestSender;
  /** The state of the mute setting for the User sending the request */
  private boolean senderMuted;
  /** The DTO of the User receiving the request */
  private UserDTO requestReceiver;
  /** The state of the mute setting for the User receiving the request */
  private boolean receiverMuted;

  /**
   * Instantiates a new FriendshipDTO.
   *
   * @param id the id of the related friendship object
   * @param requestSender the user sending the request
   * @param senderMuted state of the mute setting for the user sending the request
   * @param requestReceiver the user sending the request
   * @param receiverMuted state of the mute setting for the user receiving the request
   */
  public FriendshipDTO(
      String id,
      UserDTO requestSender,
      boolean senderMuted,
      UserDTO requestReceiver,
      boolean receiverMuted) {
    this.id = id;
    this.requestReceiver = requestReceiver.cloneUserDTO();
    this.receiverMuted = receiverMuted;
    this.requestSender = requestSender.cloneUserDTO();
    this.senderMuted = senderMuted;
  }

  /**
   * Gets the id of the friendship.
   *
   * @return the id of this FriendshipDTO
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the DTO of the user that sends the request.
   *
   * @return the UserDTO of the request sender of this FriendshipDTO
   */
  public UserDTO getRequestSender() {
    return requestSender.cloneUserDTO();
  }
  /**
   * Gets the muted setting state for the sender of the request.
   *
   * @return True if the sender is muted, false otherwise
   */
  public boolean isSenderMuted() {
    return senderMuted;
  }
  /**
   * Gets the DTO of the user that receives the request.
   *
   * @return the UserDTO of the request receiver of this FriendshipDTO
   */
  public UserDTO getRequestReceiver() {
    return requestReceiver.cloneUserDTO();
  }
  /**
   * Gets the muted setting state for the receiver of the request.
   *
   * @return True if the receiver is muted, false otherwise
   */
  public boolean isReceiverMuted() {
    return receiverMuted;
  }

  /**
   * Sets the id of the friendship
   *
   * @param id the wanted id for this FriendshipDTO
   */
  public void setId(String id) {
    this.id = id;
  }
  /**
   * Sets the requestSender of the friendship
   *
   * @param requestSender the wanted requestSender for this FriendshipDTO
   */
  public void setRequestSender(UserDTO requestSender) {
    this.requestSender = requestSender.cloneUserDTO();
  }
  /**
   * Sets the muted state of the request sender
   *
   * @param senderMuted the wanted senderMuted state for this FriendshipDTO
   */
  public void setSenderMuted(boolean senderMuted) {
    this.senderMuted = senderMuted;
  }
  /**
   * Sets the requestReceiver of the friendship
   *
   * @param requestReceiver the wanted requestReceiver for this FriendshipDTO
   */
  public void setRequestReceiver(UserDTO requestReceiver) {
    this.requestReceiver = requestReceiver.cloneUserDTO();
  }
  /**
   * Sets the muted state of the request receiver
   *
   * @param receiverMuted the wanted receiverMuted state for this FriendshipDTO
   */
  public void setReceiverMuted(boolean receiverMuted) {
    this.receiverMuted = receiverMuted;
  }
}
