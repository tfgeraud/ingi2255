package test.simulator;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import events.Event;
import simulator.Observable;
import simulator.Observer;
import simulator.simobjects.SimObject;

public class DummySimObject implements SimObject {
	public Event receivedEvent = null;
	public Set<Observer> observerSet = new HashSet<Observer>();
	@SuppressWarnings("unchecked")
	public Map<Class, Observer> observerMap = new Hashtable<Class, Observer>();
	public String name = "dummySimObject";

	public void accept(Event event) {
		this.receivedEvent = event;
	}

	public void attach(Observer observer) {
		observerSet.add(observer);
		observer.observing(this);

	}

	@SuppressWarnings("unchecked")
	public void attach(Observer observer, Class eventClass) {
		observerMap.put(eventClass, observer);
		observer.observing(this);
	}


	@SuppressWarnings("unchecked")
	public void detach(Observer observer) {
		observerSet.remove(observer);
		for(Map.Entry<Class, Observer> e : observerMap.entrySet()) {
			if(e.getValue() == observer) {
				observerMap.remove(e.getKey());
			}
		}
		observer.disconnect(this);

	}

	public void dispatch(Event e) {
		// TODO Auto-generated method stub

	}

	public Set<String> getCurrentStateNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return this.name;
	}

	public void notify(Event event) {
		// TODO Auto-generated method stub

	}

	public void step() {
		// TODO Auto-generated method stub

	}

	public void disconnect(Observable observable) {
		// TODO Auto-generated method stub

	}

	public void disconnect() {
		// TODO Auto-generated method stub

	}

	public void observing(Observable observable) {
		// TODO Auto-generated method stub

	}

}
