package test.simulator;

import events.Event;
import simulator.Observable;
import simulator.Observer;

class DummyObserver implements Observer {
	public Event receivedEvent = null;

	public void accept(Event event) {
		this.receivedEvent = event;
	}

	public void disconnect(Observable observable) {
		// Not used
		
	}

	public void disconnect() {
		// Not used
		
	}

	public void observing(Observable observable) {
		// Not used
		
	}
}
