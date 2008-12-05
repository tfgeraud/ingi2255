package events;

/**
 * This event represent a demobilization order. This is sent by the system to a
 * simulated ambulance.
 * 
 * @author Erick Lavoie
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public class DemobilisationOrder extends Event {

	/**
	 * The unique identifier of the incident
	 */
	private String incidentID;

	/**
	 * The position of the incident identified by the unique identifier
	 */
	private String incidentPos;

	/**
	 * The unique identifier of the ambulance
	 */
	private String ambulanceID;

	/**
	 * Create a new incident order
	 * 
	 * @param incidentID
	 *            The unique identifier of the incident
	 * @param incidentPos
	 *            The position of the incident
	 * @param ambulanceID
	 *            The unique identifier of the ambulance
	 */
	public DemobilisationOrder(String incidentID, String incidentPos,
			String ambulanceID) {
		super("LAS");

		this.incidentID = incidentID;
		this.incidentPos = incidentPos;
		this.ambulanceID = ambulanceID;
	}

	/**
	 * Return the unique identifier of the incident
	 * 
	 * @return the unique identifier of the incident
	 */
	public String getIncidentID() {
		return this.incidentID;
	}

	/**
	 * Return the position of the incident
	 * 
	 * @return the position of the incident
	 */
	public String getIncidentPos() {
		return this.incidentPos;
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
		return super.toString() + " incidentID: " + this.incidentID + " pos: "
				+ this.incidentPos + " ambulanceID: " + this.ambulanceID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see events.Event#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof DemobilisationOrder
				&& arg0.getClass().equals(this.getClass())) {
			DemobilisationOrder dmob = ((DemobilisationOrder) arg0);
			return this.incidentID.equals(dmob.incidentID)
					&& this.incidentPos.equals(dmob.incidentPos)
					&& this.ambulanceID.equals(dmob.ambulanceID);
		} else {
			return super.equals(arg0);
		}
	}
}
