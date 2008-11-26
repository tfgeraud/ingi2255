package events;

/**
 * Sent by simulated ambulance to the LAS
 * 
 * @author Erick Lavoie
 *
 */
public class AmbulanceRepaired extends Event {

	private String ambulanceID;

	public AmbulanceRepaired(String ambulanceID) {
		super(ambulanceID);
		this.ambulanceID = ambulanceID;
	}
	
	public String getAmbulanceID() {
		return ambulanceID;
	}

}
