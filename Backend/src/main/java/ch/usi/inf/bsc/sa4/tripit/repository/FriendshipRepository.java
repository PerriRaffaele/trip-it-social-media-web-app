package ch.usi.inf.bsc.sa4.tripit.repository;

import ch.usi.inf.bsc.sa4.tripit.model.Friendship;
import ch.usi.inf.bsc.sa4.tripit.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** This class represents the friendship repository in the mongo database. */
@Repository
public interface FriendshipRepository extends MongoRepository<Friendship, String> {

  /**
   * If possible, finds the friendship in the database between the provided requestReceiver as the
   * friendship receiver and the provided requestSender as the friendship sender
   *
   * @param requestReceiver the user who received the friend request of the wanted friendship
   * @param requestSender the user who sent the friend request of the wanted friendship
   * @return an optional of the friendship between the provided receiver and sender
   */
  Optional<Friendship> findByRequestReceiverAndRequestSender(
      User requestReceiver, User requestSender);

  /**
   * Returns a list containing all the friendships in the database where the provided receiver is
   * the receiver of the friend request or the provided sender is the sender of the friend request
   *
   * @param receiver the user who received the friend request of the wanted friendships
   * @param sender the user who sent the friend request of the wanted friendships
   * @return a list containing all the friendships of the provided receiver and the provided sender
   */
  List<Friendship> findAllByRequestReceiverOrRequestSender(User receiver, User sender);
}
