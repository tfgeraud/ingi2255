package test.simulator;

import java.util.HashSet;
import java.util.Set;

import events.Event;
import simulator.Observable;
import simulator.Observer;

public class DummyObservable implements Observable {
	public Set<Observer> observerSet = new HashSet<Observer>();

	public void attach(Observer observer) {
		observerSet.add(observer);
		observer.observing(this);
	}

	public void detach(Observer observer) {
		observerSet.remove(observer);
		observer.disconnect(this);
	}

	public void notify(Event event) {
		// TODO Auto-generated method stub
		
	}

}
