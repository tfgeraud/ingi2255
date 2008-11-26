package events;

/**
 * Sent by the system to a simulated ambulance.
 * 
 * @author Erick Lavoie
 *
 */
public class DemobilisationOrder extends Event {
	private String incidentID;
	private String incidentPos;
	private String ambulanceID;

	public DemobilisationOrder(String incidentID, String incidentPos, String ambulanceID ) {
		super("LAS");
		
		this.incidentID = incidentID;
		this.incidentPos = incidentPos;
		this.ambulanceID = ambulanceID;
	}

	public String getIncidentID() {
		return incidentID;
	}

	public String getIncidentPos() {
		return incidentPos;
	}

	public String getAmbulanceID() {
		return ambulanceID;
	}
	
	public String toString() {
		return super.toString() + " incidentID: " + incidentID + " pos: " + incidentPos + " ambulanceID: " + ambulanceID;
	}
}
