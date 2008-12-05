package events;

/**
 * Used both by the simulator to make an ambulance change its state to 
 * broken and to notify the LAS from the ambulance that it is broken.
 * 
 * @author Erick Lavoie
 *
 */
public class AmbulanceBroken extends Event {
	private String ambulanceID;

	public AmbulanceBroken(String ambulanceID) {
		super(ambulanceID);
		this.ambulanceID = ambulanceID;
	}
	
	public String getAmbulanceID() {
		return this.ambulanceID;
	}

}
