package ch.usi.inf.bsc.sa4.tripit.model;

import ch.usi.inf.bsc.sa4.tripit.controller.dto.ActivityDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.AttractionDTO;
import ch.usi.inf.bsc.sa4.tripit.controller.dto.VisitDTO;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 *
 *
 * <h1>Visit</h1>
 *
 * The Class Visit inherits from the Class Activity, and contains the Attraction of the Visit.
 *
 * @author Julian Müller
 * @version 2023-03-17
 * @since 2023-03-17
 */
@Document(collection = "Activities")
@TypeAlias("Visit")
public class Visit extends Activity {

  /** The attraction visited */
  @DocumentReference private Attraction attraction;

  /**
   * The constructor of this class.
   *
   * @param attraction The attraction of the new visit.
   * @param start The starting time of the new visit activity.
   * @param end The ending time of the new visit activity.
   * @param title The title of the new visit activity.
   * @param description The description of the new visit activity.
   * @param imageLink The image link of the new visit activity.
   */
  public Visit(
      Journey journey,
      ZonedDateTime start,
      ZonedDateTime end,
      String title,
      String description,
      Attraction attraction,
      Image imageLink)
      throws IllegalArgumentException {
    super(journey, start, end, title, description, imageLink);
    this.attraction = attraction;
  }

  /**
   * Gets the attraction of the visit.
   *
   * @return returns the attraction of the visit
   */
  public Attraction getAttraction() {
    return attraction;
  }

  /**
   * Sets the attraction of the visit. setter of attraction
   *
   * @param attraction
   * @throws IllegalArgumentException when the passed attraction is null
   */
  public void setAttraction(Attraction attraction) throws IllegalArgumentException {
    if (attraction == null) {
      throw new IllegalArgumentException("attraction cannot be null");
    }
    this.attraction = attraction;
  }

  /**
   * Converts this Visit to a VisitDTO
   *
   * @return a new VisitDTO
   */
  public ActivityDTO toActivityDTO() {
    if (this.getImageLink() == null) {
      return new VisitDTO(
          this.getId(),
          this.getJourney().getId(),
          this.getStart(),
          this.getEnd(),
          this.getTitle(),
          this.getDescription(),
          (AttractionDTO) this.getAttraction().toPlaceDTO(),
          "visit",
          "0000");
    } else {
      return new VisitDTO(
          this.getId(),
          this.getJourney().getId(),
          this.getStart(),
          this.getEnd(),
          this.getTitle(),
          this.getDescription(),
          (AttractionDTO) this.getAttraction().toPlaceDTO(),
          "visit",
          this.getImageLink().getId());
    }
  }
}
