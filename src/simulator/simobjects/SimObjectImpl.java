package simulator.simobjects;

import java.util.HashSet;
import java.util.Map;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;


import simulator.Observable;
import simulator.Observer;
import simulator.events.ChangingToState;
import simulator.events.EventNotUnderstood;
import simulator.events.StepDelimiter;
import simulator.simobjects.EventNotUnderstoodException;
import simulator.simobjects.SimObject;
import events.Event;

public class SimObjectImpl implements SimObject {
	
	private String name = "default";
	private Queue<Event> eventQueue = new ConcurrentLinkedQueue<Event>();
	private List<Observer> observerList = new Vector<Observer>();
	private Map<Class, List<Observer>> eventObserverMap = new Hashtable<Class, List<Observer>>();
	public List<StateMachine> stateMachineList = new Vector<StateMachine>();
	public Set<Observable> observingSet = new HashSet<Observable>();
	
	/**
	 * Ctor, initialisation of the state machine should go here
	 */
	protected SimObjectImpl(String name) {
		this.name = name;
	}

	public void accept(Event event) {
		this.eventQueue.add(event);
	}

	public void attach(Observer observer) {
		this.observerList.add(observer);
		observer.observing(this);
	}
	
	@SuppressWarnings("unchecked")
	public void attach(Observer observer, Class eventClass) {
		if(this.eventObserverMap.containsKey(eventClass)) {
			this.eventObserverMap.get(eventClass).add(observer);
		} else {
			this.eventObserverMap.put(eventClass, new Vector<Observer>());
			this.eventObserverMap.get(eventClass).add(observer);
		}
		observer.observing(this);
	}

	public void detach(Observer observer) {
		this.observerList.remove(observer);
		for(List<Observer> l: this.eventObserverMap.values()) {
			l.remove(observer);
		}
		observer.disconnect(this);
	}

	public void notify(Event event) {
		for(Observer o: this.observerList) {
			o.accept(event);
		}
		
		if(this.eventObserverMap.containsKey(event.getClass())) {
			for(Observer o: this.eventObserverMap.get(event.getClass())) {
				o.accept(event);
			}
		}

	}
	
	public void disconnect() {
		for(Observable o : this.observingSet) {
			o.detach(this);
		}
	}

	public void observing(Observable observable) {
		if(!this.observingSet.contains(observable)) {
			this.observingSet.add(observable);
		}
	}
	
	public void disconnect(Observable observable) {
		this.observingSet.remove(observable);
	}

	public void step() {
		Event e = null;
		while((e=this.eventQueue.poll()) != null) {
			if(e != StepDelimiter.getInstance()) {
				this.dispatch(e);
			} else {
				break;
			}
		}
	}
	
	public void dispatch(Event e) {
		/**
		 * If at least one state machine understand the event,
		 * it is considered understood and won't throw a new 
		 * EventNotUnderstood
		 */
		boolean allEventsNotUnderstood = true;
		
		for(StateMachine sm: this.stateMachineList) {
			try {
				sm.dispatch(e);
				allEventsNotUnderstood = allEventsNotUnderstood && false;
			} catch (EventNotUnderstoodException e1) {}
		}
		if(allEventsNotUnderstood) {
			SimObjectImpl.this.notify(new EventNotUnderstood(SimObjectImpl.this.getName(), e));
		}
	}
	
	public Set<String> getCurrentStateNames() {
		Set<String> names = new HashSet<String>();
		
		for(StateMachine sm: this.stateMachineList) {
			names.add(sm.getCurrentState().getName());
		}
		return names;
	}
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * State Machine implementing the State Pattern, states are intended to be defined 
	 * in the constructor as inner classes inheriting from the State class
	 * @author Erick Lavoie
	 *
	 */
	protected class StateMachine {
		private State currentState;
		protected Set<Event> understoodEvents;
		
		public StateMachine(State initial) {
			this.currentState=initial;
		}
		
		
		/**
		 * Dispatch the event to the current state
		 * @pre StateMachine has been initialized and current state is valid
		 * @post The new state resulting from the execution of the event is stored in currentState
		 */
		public void dispatch(Event e) throws EventNotUnderstoodException {
			State newState = this.currentState.execute(e);
			
			if (newState == null) {
				throw new EventNotUnderstoodException();
			} else if(newState != this.currentState) {
				SimObjectImpl.this.notify(new ChangingToState(SimObjectImpl.this.getName(), newState.getName()));
				this.currentState=newState;
			} 
			
		}
		
		public void setCurrentState(State state) {
			this.currentState = state;
		}
		
		public State getCurrentState() {
			return this.currentState;
		}
		
	}
	
	protected class State {
		protected String name = "";
		
		public State(String name) {
			this.name = name;
		}
		
		/** 
		 * Process the event and return the new state,
		 * by default, the event returns null, meaning
		 * event not understood
		 * 
		 * @pre Event is valid
		 * @post A new state is returned
		 */
		public State execute(Event e) {
			return null;
		}
		
		
		/** 
		 * Name used for debugging purposes
		 */		
		public String getName() {
			return this.name;
		}
		
		@Override
		public String toString() {
			return this.getClass() + " " + this.name;
		}
		
	}


}
