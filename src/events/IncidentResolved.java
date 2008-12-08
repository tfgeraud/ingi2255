package events;

/**
 * This class represent the event incident resolved. This event is sent by
 * simulated ambulance to LAS
 * 
 * @author Erick Lavoie
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public class IncidentResolved extends Event {

	/**
	 * The unique identifier of the incident
	 */
	private String incidentID;

	/**
	 * The unique identifier of the ambulance
	 */
	private String ambulanceID;

	/**
	 * Create a new incident resolved event
	 * 
	 * @param incidentID
	 *            the unique identifier of the incident
	 * @param ambulanceID
	 *            the unique identifier of the ambulance
	 */
	public IncidentResolved(String incidentID, String ambulanceID) {
		super(ambulanceID);

		this.incidentID = incidentID;
		this.ambulanceID = ambulanceID;
	}

	/**
	 * Return unique identifier of the incident
	 * 
	 * @return the unique identifier of the incident
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
		if (arg0 instanceof IncidentResolved
				&& arg0.getClass().equals(this.getClass())) {
			IncidentResolved ir = ((IncidentResolved) arg0);
			return this.incidentID.equals(ir.incidentID)
					&& this.ambulanceID.equals(ir.ambulanceID);
		} else {
			return super.equals(arg0);
		}
	}

}
