package events;

/**
 * This class represent an event corresponding to a broken ambulance. 
 * Used both by the simulator to make an ambulance change its state to 
 * broken and to notify the LAS from the ambulance that it is broken.
 * 
 * @author Erick Lavoie
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public class AmbulanceBroken extends Event {
	
	/**
	 * The unique identifier of the ambulance
	 */
	private String ambulanceID;

	/**
	 * Create a new event corresponding to an ambulance broken
	 * 
	 * @param ambulanceID The unique identifier of the ambulance
	 */
	public AmbulanceBroken(String ambulanceID) {
		super(ambulanceID);
		this.ambulanceID = ambulanceID;
	}
	
	/**
	 * Return the unique identifier of the ambulance
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
		if (arg0 instanceof AmbulanceBroken
				&& arg0.getClass().equals(this.getClass())) {
			AmbulanceBroken ab = ((AmbulanceBroken) arg0);
			return this.ambulanceID.equals(ab.ambulanceID);
		} else {
			return super.equals(arg0);
		}
	}
	
}
