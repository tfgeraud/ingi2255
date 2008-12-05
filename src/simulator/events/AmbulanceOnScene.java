package simulator.events;

import events.Event;

public class AmbulanceOnScene extends Event {

	public AmbulanceOnScene(String senderName) {
		super(senderName);
	}
	
	public String getIncidentID() {
		return this.senderName;
	}

}
