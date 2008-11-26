package events;

/**
 * Sent by simulated ambulance to the system
 * 
 * @author Erick Lavoie
 *
 */
public class DestinationUnreachable extends Event {
	private String incidentID;
	private String ambulanceID;
	
	public DestinationUnreachable(String incidentID, String ambulanceID) {
		super(ambulanceID);
		
		this.incidentID = incidentID;
		this.ambulanceID = ambulanceID;
	}
	
	public String getIncidentID() {
		return incidentID;
	}

	public String getAmbulanceID() {
		return ambulanceID;
	}
	
	public String toString() {
		return super.toString() + " incidentID: " + incidentID +  " ambulanceID: " + ambulanceID;
	}

}
