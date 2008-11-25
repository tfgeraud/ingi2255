package simulator.events;

import events.Event;

public class EventNotUnderstood extends Event {
	public String eventName;
	
	public EventNotUnderstood(String simObjectName, Event event) {
		super(simObjectName);
		this.eventName = event.getClass().getName();
	}
	
	public String getEventName() {
		return this.eventName;
	}

}
