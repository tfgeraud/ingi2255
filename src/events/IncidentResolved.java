package events;

/**
 * Sent by simulated ambulance to LAS
 * @author Erick Lavoie
 *
 */
public class IncidentResolved extends Event {
	private String incidentID;
	private String ambulanceID;
	
	public IncidentResolved(String incidentID, String ambulanceID) {
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
