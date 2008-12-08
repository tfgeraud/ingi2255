package events;

/**
 * This class represent a confirmation of a mobilization order. This event is
 * sent by simulated ambulance to LAS
 * 
 * @author Erick Lavoie
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public class MobilisationConfirmation extends Event {

	/**
	 * The unique identifier of the incident
	 */
	private String incidentID;

	/**
	 * The unique identifer of the ambulance
	 */
	private String ambulanceID;

	/**
	 * Wheter the mobilization is accepted or not
	 */
	private boolean mobilizationAccepted;

	/**
	 * Create a new confirmation of a mobilization order
	 * 
	 * @param incidentID
	 *            The unique identifier of the incident
	 * @param ambulanceID
	 *            The unique identifier of the ambulance
	 * @param mobilizationAccepted
	 *            Wheter the mobilization where accepted
	 */
	public MobilisationConfirmation(String incidentID, String ambulanceID,
			boolean mobilizationAccepted) {
		super(ambulanceID);

		this.incidentID = incidentID;
		this.ambulanceID = ambulanceID;
		this.mobilizationAccepted = mobilizationAccepted;
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
	 * Return the unique identifier of the ambulance
	 * 
	 * @return The unique identifier of the ambulance
	 */
	public String getAmbulanceID() {
		return this.ambulanceID;
	}

	/**
	 * Return wheter the mobilization is accepted or not
	 * 
	 * @return wheter the mobilization is accepted
	 */
	public boolean getMobilizationAccepted() {
		return this.mobilizationAccepted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see events.Event#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " incidentID: " + this.incidentID
				+ " ambulanceID: " + this.ambulanceID
				+ " mobilizationAccepted: "
				+ Boolean.toString(this.mobilizationAccepted);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see events.Event#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof MobilisationConfirmation
				&& arg0.getClass().equals(this.getClass())) {
			MobilisationConfirmation mc = ((MobilisationConfirmation) arg0);
			return this.incidentID.equals(mc.incidentID)
					&& this.ambulanceID.equals(mc.ambulanceID)
					&& this.mobilizationAccepted == mc.mobilizationAccepted;
		} else {
			return super.equals(arg0);
		}
	}

}
