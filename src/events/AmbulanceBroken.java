package events;

/**
 * Sent by simulated ambulance to the LAS.
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
