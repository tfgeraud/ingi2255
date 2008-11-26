package events;

/**
 * Sent by simulated ambulance to LAS
 * 
 * @author Erick Lavoie
 *
 */
public class MobilisationConfirmation extends Event {
	private String incidentID;
	private String ambulanceID;
	private boolean mobilizationAccepted;
	
	public MobilisationConfirmation(String incidentID, String ambulanceID, boolean mobilizationAccepted) {
		super(ambulanceID);
		
		this.incidentID = incidentID;
		this.ambulanceID = ambulanceID;
		this.mobilizationAccepted = mobilizationAccepted;
	}
	
	public String getIncidentID() {
		return incidentID;
	}

	public String getAmbulanceID() {
		return ambulanceID;
	}
	
	public boolean getMobilizationAccepted() {
		return mobilizationAccepted;
	}
	
	public String toString() {
		return super.toString() + " incidentID: " + incidentID +  " ambulanceID: " + ambulanceID + " mobilizationAccepted: " + Boolean.toString(mobilizationAccepted);
	}

}
