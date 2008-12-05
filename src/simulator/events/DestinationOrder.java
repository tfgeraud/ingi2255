package simulator.events;

import simulator.Pos;
import events.Event;

public class DestinationOrder extends Event {
	private Pos destination;
	public DestinationOrder(String senderName, Pos destination) {
		super(senderName);
		this.destination = destination;
	}
	
	public Pos getDestination() {
		return destination;
	}

}
