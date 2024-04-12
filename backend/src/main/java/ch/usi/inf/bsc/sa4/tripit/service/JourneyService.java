package ch.usi.inf.bsc.sa4.tripit.service;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.JourneyDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Activity;
import ch.usi.inf.bsc.sa4.tripit.model.Journey;
import ch.usi.inf.bsc.sa4.tripit.model.User;
import ch.usi.inf.bsc.sa4.tripit.repository.JourneyRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JourneyService {
  public final JourneyRepository journeyRepository;
  public final UserService userService;
  public final ImageService imageService;

  @Autowired
  public JourneyService(
      JourneyRepository journeyRepository, UserService userService, ImageService imageService) {
    this.journeyRepository = journeyRepository;
    this.userService = userService;
    this.imageService = imageService;
  }

  // filter out the journeys belonging to private users
  public List<Journey> getAll() {
    List<Journey> journeys = this.journeyRepository.findAll();
    journeys.removeIf(journey -> !journey.getUser().isPublic());
    if (journeys.size() > 1) {
      Collections.sort(journeys, Journey::compareTo);
    }
    return journeys;
  }

  public Optional<Journey> getById(String journeyId) {
    return journeyRepository.findById(journeyId);
  }

  public List<Journey> searchByTitleContaining(String title) {
    return journeyRepository.findAllByTitleContaining(title);
  }

  public Journey createJourney(JourneyDTO journeyDTO) {
    var targetUser = userService.getById(journeyDTO.getUserId());
    var targetImage = imageService.getById(journeyDTO.getCoverId());
    var journey = new Journey(journeyDTO.getTitle(), targetUser.get(), targetImage.get());
    journey.recalculateCo2Estimate();

    return journeyRepository.save(journey);
  }

  public Optional<User> getUserById(String journeyId) {
    Optional<Journey> journey = getById(journeyId);
    if (journey.isPresent()) {
      return Optional.of(journey.get().getUser());
    } else {
      return Optional.empty();
    }
  }

  public List<Activity> getActivitiesById(String journeyId) {
    Optional<Journey> journey = getById(journeyId);
    if (journey.isPresent()) {
      return journey.get().getActivities();
    } else {
      // empty list of activities when journey id is invalid
      return new ArrayList<>();
    }
  }

  public Journey updateJourney(Journey journey) {
    journey.recalculateCo2Estimate();
    return journeyRepository.save(journey);
  }

  public void deleteJourney(Journey journey) {

    journeyRepository.delete(journey);
  }

  public List<Journey> getJourneysOfUser(User user) {
    List<Journey> journeys = journeyRepository.findAllByUser(user);
    for (var journey : journeys) {
      if (journey.numberOfActivities() == 0) {
        deleteJourney(journey);
      }
    }
    Collections.sort(journeys, Journey::compareTo);
    return journeys;
  }

  public int countNumberOfJourneys(User user) {
    return journeyRepository.countAllByUser(user);
  }

  public int countTotalNumberOfActivitiesForUser(User user) {
    List<Journey> journeys = getJourneysOfUser(user);
    int total = 0;
    for (Journey journey : journeys) {
      total += journey.numberOfActivities();
    }
    return total;
  }
}
