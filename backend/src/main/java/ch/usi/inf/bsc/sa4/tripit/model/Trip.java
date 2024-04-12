package ch.usi.inf.bsc.sa4.tripit.model;

/** The interface Trip. Subclasses of Trip must implement the getFrom and getTo methods. */
public interface Trip {

  /**
   * Gets the departure place of the trip.
   *
   * @return the departure place of the trip
   */
  Place getFrom();

  /**
   * Gets the arrival place of the trip.
   *
   * @return the arrival place of the trip
   */
  Place getTo();
}
