package events;

/**
 * This class represent the event corresponding to a repaired event. This event
 * is sent by simulated ambulance to the LAS
 * 
 * @author Erick Lavoie
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public class AmbulanceRepaired extends Event {

	/**
	 * The unique identifier of the ambulance
	 */
	private String ambulanceID;

	/**
	 * Create a new event correponding to an repaired ambulance
	 * 
	 * @param ambulanceID
	 *            The unique identifier of the ambulance
	 */
	public AmbulanceRepaired(String ambulanceID) {
		super(ambulanceID);
		this.ambulanceID = ambulanceID;
	}

	/**
	 * Return the unique identifier of the ambulance
	 * 
	 * @return the unique identifier of the ambulance
	 */
	public String getAmbulanceID() {
		return this.ambulanceID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see events.Event#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof AmbulanceRepaired
				&& arg0.getClass().equals(this.getClass())) {
			AmbulanceRepaired ar = ((AmbulanceRepaired) arg0);
			return this.ambulanceID.equals(ar.ambulanceID);
		} else {
			return super.equals(arg0);
		}
	}

}
