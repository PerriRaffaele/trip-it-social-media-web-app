package ch.usi.inf.bsc.sa4.tripit.service;

import ch.usi.inf.bsc.sa4.tripit.model.Friendship;
import ch.usi.inf.bsc.sa4.tripit.model.User;
import ch.usi.inf.bsc.sa4.tripit.repository.FriendshipRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The type Friendship service. */
@Service
public class FriendshipService {

  private final FriendshipRepository friendshipRepository;

  /**
   * Instantiates a new Friendship service.
   *
   * @param friendshipRepository the friendship repository
   */
  @Autowired
  public FriendshipService(FriendshipRepository friendshipRepository) {
    this.friendshipRepository = friendshipRepository;
  }

  /**
   * Update friendship friendship.
   *
   * @param fr the friendship to update
   * @return the friendship
   */
  public Friendship updateFriendShip(Friendship fr) {
    return friendshipRepository.save(fr);
  }

  /**
   * Gets friendship by rec and send.
   *
   * @param rec the rec
   * @param sen the sen
   * @return Optional friendship by rec and send
   */
  public Optional<Friendship> getFriendshipByRecAndSend(User rec, User sen) {
    return friendshipRepository
        .findByRequestReceiverAndRequestSender(rec, sen)
        .or(() -> friendshipRepository.findByRequestReceiverAndRequestSender(sen, rec));
  }

  /**
   * Delete friendship.
   *
   * @param friendship the friendship
   */
  public void deleteFriendShip(Friendship friendship) {
    friendshipRepository.delete(friendship);
  }

  /**
   * Gets all friends by user.
   *
   * @param user the user
   * @return List of friendships
   */
  public List<Friendship> getAllFriendsByUser(User user) {
    return friendshipRepository.findAllByRequestReceiverOrRequestSender(user, user);
  }
}
