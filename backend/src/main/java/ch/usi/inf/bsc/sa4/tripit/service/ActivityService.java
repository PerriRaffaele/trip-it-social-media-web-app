package ch.usi.inf.bsc.sa4.tripit.service;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.EditPlaneTripDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.EditTrainTripDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.EditVisitDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.PlaceDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.PlaneTripDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.TrainTripDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.VisitDTO;
import ch.usi.inf.bsc.sa4.tripit.model.Activity;
import ch.usi.inf.bsc.sa4.tripit.model.Airport;
import ch.usi.inf.bsc.sa4.tripit.model.Attraction;
import ch.usi.inf.bsc.sa4.tripit.model.Image;
import ch.usi.inf.bsc.sa4.tripit.model.Journey;
import ch.usi.inf.bsc.sa4.tripit.model.Place;
import ch.usi.inf.bsc.sa4.tripit.model.PlaneTrip;
import ch.usi.inf.bsc.sa4.tripit.model.Station;
import ch.usi.inf.bsc.sa4.tripit.model.TrainTrip;
import ch.usi.inf.bsc.sa4.tripit.model.Visit;
import ch.usi.inf.bsc.sa4.tripit.repository.ActivityRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The type Activity service. */
@Service
public class ActivityService {

  /** the activity repository */
  private final ActivityRepository activityRepository;
  /** the place service */
  private final PlaceService placeService;

  /** the journey service */
  private final JourneyService journeyService;

  private final ImageService imageService;

  /**
   * Instantiates a new Activity service.
   *
   * @param activityRepository the activity repository
   */
  @Autowired
  public ActivityService(
      ActivityRepository activityRepository,
      PlaceService placeService,
      JourneyService journeyService,
      ImageService imageService) {
    this.activityRepository = activityRepository;
    this.placeService = placeService;
    this.journeyService = journeyService;
    this.imageService = imageService;
  }

  /**
   * Get all the activities from the repository.
   *
   * @return the list of all the activities from the repository
   */
  public List<Activity> getAllActivities() {
    return this.activityRepository.findAll();
  }

  /**
   * Get all the activities of the given journey in chronologically sorted order.
   *
   * @param journey the journey whose activities we want
   * @return all the activities of the given journey
   */
  public List<Activity> getActivitiesByJourney(Journey journey) {
    return this.journeyService.getActivitiesById(journey.getId());
  }
  /**
   * returns the activity with the given id from the repository if present.
   *
   * @param activityId the activity id
   * @return the activity with the given id from the repository if present
   */
  public Optional<Activity> getById(String activityId) {
    return activityRepository.findById(activityId);
  }

  /**
   * Delete activity.
   *
   * @param activity the activity
   */
  public void deleteActivity(Activity activity) {
    activityRepository.delete(activity);
  }

  /**
   * Saves the given activity in the database with the calculated CO2 estimate if necessary.
   *
   * @param activity the activity to be saved in the database
   * @return the saved activity
   */
  public Activity updateActivity(Activity activity) {
    if (activity.getClass() == TrainTrip.class) {
      ((TrainTrip) activity).recalculateCo2Estimate();
    } else if (activity.getClass() == PlaneTrip.class) {
      ((PlaneTrip) activity).recalculateCo2Estimate();
    } else {
      // Visits don't need to calculate CO2
    }
    return activityRepository.save(activity);
  }

  /**
   * Returns an activity representing the given EditVisitDTO
   *
   * @param editVisitDTO the EditVisitDTO we want to convert into an activity
   * @return an activity representing the given EditVisitDTO
   */
  public Activity editToVisit(EditVisitDTO editVisitDTO) {
    final Optional<Journey> optionalJourney = journeyService.getById(editVisitDTO.getJourneyId());
    final Journey journey = optionalJourney.orElse(null);
    var targetImage = imageService.getById(editVisitDTO.getCoverId());
    final Visit visit =
        new Visit(
            journey,
            editVisitDTO.getStart(),
            editVisitDTO.getEnd(),
            editVisitDTO.getTitle(),
            editVisitDTO.getDescription(),
            null,
            targetImage.get());
    visit.setId(editVisitDTO.getId());
    return visit;
  }

  /**
   * Returns an activity representing the given EditTrainTripDTO
   *
   * @param editTrainTripDTO the EditTrainTripDTO we want to convert into an Activity
   * @return an activity representing the given EditTrainTripDTO
   */
  public Activity editToTrainTrip(EditTrainTripDTO editTrainTripDTO) {
    final Optional<Journey> optionalJourney =
        journeyService.getById(editTrainTripDTO.getJourneyId());
    final Journey journey = optionalJourney.orElse(null);
    var targetImage = imageService.getById(editTrainTripDTO.getCoverId());
    final TrainTrip trainTrip =
        new TrainTrip(
            journey,
            editTrainTripDTO.getStart(),
            editTrainTripDTO.getEnd(),
            editTrainTripDTO.getTitle(),
            editTrainTripDTO.getDescription(),
            null,
            null,
            targetImage.get());
    trainTrip.setId(editTrainTripDTO.getId());
    return trainTrip;
  }

  /**
   * Returns an activity representing the given EditPlaneTripDTO
   *
   * @param editPlaneTripDTO the EditPlaneTripDTO we want to convert into an Activity
   * @return an activity representing the given EditPlaneTripDTO
   */
  public Activity editToPlaneTrip(EditPlaneTripDTO editPlaneTripDTO) {
    final Optional<Journey> optionalJourney =
        journeyService.getById(editPlaneTripDTO.getJourneyId());
    final Journey journey = optionalJourney.orElse(null);
    var targetImage = imageService.getById(editPlaneTripDTO.getCoverId());
    final PlaneTrip planeTrip =
        new PlaneTrip(
            journey,
            editPlaneTripDTO.getStart(),
            editPlaneTripDTO.getEnd(),
            editPlaneTripDTO.getTitle(),
            editPlaneTripDTO.getDescription(),
            null,
            null,
            editPlaneTripDTO.getFlightNumber(),
            targetImage.get());
    planeTrip.setId(editPlaneTripDTO.getId());
    return planeTrip;
  }

  /**
   * Returns an activity representing the given VisitDTO
   *
   * @param visitDTO the VisitDTO we want to convert into an Activity
   * @return an activity representing the given VisitDTO
   */
  public Activity toVisit(VisitDTO visitDTO) {
    final PlaceDTO attract = visitDTO.getAttraction();
    final Optional<Place> targetAttraction = placeService.getById(attract.getId());
    final Attraction attraction = (Attraction) targetAttraction.orElse(null);
    final Optional<Journey> optionalJourney = journeyService.getById(visitDTO.getJourneyId());
    final Journey journey = optionalJourney.orElse(null);
    final Optional<Image> optionalImage = imageService.getById(visitDTO.getCoverId());
    return new Visit(
        journey,
        visitDTO.getStart(),
        visitDTO.getEnd(),
        visitDTO.getTitle(),
        visitDTO.getDescription(),
        attraction,
        optionalImage.get());
  }

  /**
   * Saves into the database the visit represented by the given VisitDTO
   *
   * @param visitDTO represents the Visit we want to save
   * @return the saved Activity
   */
  public Activity saveVisit(VisitDTO visitDTO) {
    return activityRepository.save(this.toVisit(visitDTO));
  }

  /**
   * Saves into the database the PlaneTrip represented by the given PlaneTripDTO
   *
   * @param planeTripDTO represents the PlaneTrip we want to save
   * @return the saved Activity
   */
  public Activity savePlaneTrip(PlaneTripDTO planeTripDTO) {
    final var planeTrip = this.toPlaneTrip(planeTripDTO);
    ((PlaneTrip) planeTrip).recalculateCo2Estimate();
    return activityRepository.save(planeTrip);
  }

  /**
   * Returns an activity representing the given PlaneTripDTO
   *
   * @param planeTripDTO the PlaneTripDTO we want to convert into an Activity
   * @return an activity representing the given PlaneTripDTO
   */
  public Activity toPlaneTrip(PlaneTripDTO planeTripDTO) {
    final PlaceDTO fromDtO = planeTripDTO.getFrom();
    final PlaceDTO toDTO = planeTripDTO.getTo();

    final Optional<Place> targetFrom = placeService.getById(fromDtO.getId());
    final Optional<Place> targetTo = placeService.getById(toDTO.getId());

    final Airport from = (Airport) targetFrom.orElse(null);
    final Airport to = (Airport) targetTo.orElse(null);

    final Optional<Journey> optionalJourney = journeyService.getById(planeTripDTO.getJourneyId());
    final Journey journey = optionalJourney.orElse(null);

    final Optional<Image> optionalImage = imageService.getById(planeTripDTO.getCoverId());

    return new PlaneTrip(
        journey,
        planeTripDTO.getStart(),
        planeTripDTO.getEnd(),
        planeTripDTO.getTitle(),
        planeTripDTO.getDescription(),
        from,
        to,
        planeTripDTO.getFlightNumber(),
        optionalImage.get());
  }

  /**
   * Saves into the database the TrainTrip represented by the given TrainTripDTO
   *
   * @param trainTripDTO represents the TrainTrip we want to save
   * @return the saved Activity
   */
  public Activity saveTrainTrip(TrainTripDTO trainTripDTO) {
    final var trainTrip = this.toTrainTrip(trainTripDTO);
    ((TrainTrip) trainTrip).recalculateCo2Estimate();
    return activityRepository.save(trainTrip);
  }

  /**
   * Returns an activity representing the given TrainTripDTO
   *
   * @param trainTripDTO the TrainTripDTO we want to convert into an Activity
   * @return an activity representing the given TrainTripDTO
   */
  public Activity toTrainTrip(TrainTripDTO trainTripDTO) {
    final PlaceDTO fromDTO = trainTripDTO.getFrom();
    final PlaceDTO toDTO = trainTripDTO.getTo();
    final Optional<Place> targetFrom = placeService.getById(fromDTO.getId());
    final Optional<Place> targetTo = placeService.getById(toDTO.getId());

    final Station from = (Station) targetFrom.orElse(null);
    final Station to = (Station) targetTo.orElse(null);

    final Optional<Journey> optionalJourney = journeyService.getById(trainTripDTO.getJourneyId());
    final Journey journey = optionalJourney.orElse(null);

    final Optional<Image> optionalImage = imageService.getById(trainTripDTO.getCoverId());
    return new TrainTrip(
        journey,
        trainTripDTO.getStart(),
        trainTripDTO.getEnd(),
        trainTripDTO.getTitle(),
        trainTripDTO.getDescription(),
        from,
        to,
        optionalImage.get());
  }

  // Places and locations are not deleted

  /** Deletes all the activities in the database. */
  public void deleteAll() {
    activityRepository.deleteAll();
  }
}
