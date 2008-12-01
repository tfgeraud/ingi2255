package simulator.simobjects;

import java.util.HashSet;
import java.util.Map;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;


import simulator.Observer;
import simulator.events.ChangingToState;
import simulator.events.EventNotUnderstood;
import simulator.events.StepDelimiter;
import events.Event;

public class SimObjectImpl implements SimObject {
	
	private String name = "default";
	private Queue<Event> eventQueue = new ConcurrentLinkedQueue<Event>();
	private List<Observer> observerList = new Vector<Observer>();
	@SuppressWarnings("unchecked")
	private Map<Class, List<Observer>> eventObserverMap = new Hashtable<Class, List<Observer>>();
	public List<StateMachine> stateMachineList = new Vector<StateMachine>();
	
	/**
	 * Ctor, initialisation of the state machine should go here
	 */
	protected SimObjectImpl(String name) {
		this.name = name;
	}

	public void accept(Event event) {
		eventQueue.add(event);
	}

	public void attach(Observer observer) {
		observerList.add(observer);
	}
	
	@SuppressWarnings("unchecked")
	public void attach(Observer object, Class eventClass) {
		if(eventObserverMap.containsKey(eventClass)) {
			eventObserverMap.get(eventClass).add(object);
		} else {
			eventObserverMap.put(eventClass, new Vector<Observer>());
			eventObserverMap.get(eventClass).add(object);
		}
	}

	public void detach(Observer observer) {
		observerList.remove(observer);
		for(List<Observer> l: eventObserverMap.values()) {
			l.remove(observer);
		}
	}

	public void notify(Event event) {
		for(Observer o: observerList) {
			o.accept(event);
		}
		
		if(eventObserverMap.containsKey(event.getClass())) {
			for(Observer o: eventObserverMap.get(event.getClass())) {
				o.accept(event);
			}
		}

	}

	public void step() {
		Event e = null;
		while((e=eventQueue.poll()) != null) {
			if(e != StepDelimiter.getInstance()) {
				dispatch(e);
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
		
		for(StateMachine sm: stateMachineList) {
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
		
		for(StateMachine sm: stateMachineList) {
			names.add(sm.getCurrentState().getName());
		}
		return names;
	}
	
	public String getName() {
		return name;
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
			currentState=initial;
		}
		
		
		/**
		 * Dispatch the event to the current state
		 * @pre StateMachine has been initialized and current state is valid
		 * @post The new state resulting from the execution of the event is stored in currentState
		 */
		public void dispatch(Event e) throws EventNotUnderstoodException {
			State newState = currentState.execute(e);
			
			if (newState == null) {
				throw new EventNotUnderstoodException();
			} else if(newState != currentState) {
				SimObjectImpl.this.notify(new ChangingToState(SimObjectImpl.this.getName(), newState.getName()));
				currentState=newState;
			} 
			
		}
		
		public void setCurrentState(State state) {
			currentState = state;
		}
		
		public State getCurrentState() {
			return currentState;
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
		 * Process the event and return the new state
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
			return name;
		}
		
		public String toString() {
			return this.getClass() + " " + this.name;
		}
		
	}
}
