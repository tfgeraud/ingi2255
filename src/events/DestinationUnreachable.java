package events;

/**
 * This class represent an event corresponding to a destination unreachable.
 * This event is sent by simulated ambulance to the system
 * 
 * @author Erick Lavoie
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public class DestinationUnreachable extends Event {

	/**
	 * The unique identifier of the incident
	 */
	private String incidentID;

	/**
	 * The unique identifier of the ambulance
	 */
	private String ambulanceID;

	/**
	 * Create a new 'destination unreachable' event.
	 * 
	 * @param incidentID
	 *            The unique identifier of the incident
	 * @param ambulanceID
	 *            The unique identifier of the ambulance
	 */
	public DestinationUnreachable(String incidentID, String ambulanceID) {
		super(ambulanceID);

		this.incidentID = incidentID;
		this.ambulanceID = ambulanceID;
	}

	/**
	 * Return the unique identifier of the incident
	 * 
	 * @return the unique identifier
	 */
	public String getIncidentID() {
		return this.incidentID;
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
	 * @see events.Event#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " incidentID: " + this.incidentID
				+ " ambulanceID: " + this.ambulanceID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see events.Event#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof DestinationUnreachable
				&& arg0.getClass().equals(this.getClass())) {
			DestinationUnreachable du = ((DestinationUnreachable) arg0);
			return this.incidentID.equals(du.incidentID)
					&& this.ambulanceID.equals(du.ambulanceID);
		} else {
			return super.equals(arg0);
		}
	}
}
