package simulator.simobjects;

import java.util.Set;

import simulator.Observable;
import simulator.Observer;
import events.Event;

/**
 * Simulation object which implement the observable and observer pattern
 * to be able to connect every objects together so that they react to 
 * both internal simulation events, external events from the scenario
 * and events received from the LAS.
 * 
 * @author erick
 *
 */
public interface SimObject extends Observer, Observable{
	
	/**
	 * Accept an event when notified by an observable object
	 * @param event
	 */
	public void accept(Event event);
	
	/**
	 * Attach an Observer to the object  which means an object implementing the
	 * accept method. All events sent by the object will be received.
	 * 
	 * @param object
	 */
	public void attach(Observer object);
	
	/**
	 * Attach an Observer to the object  which means an object implementing the
	 * accept method for a specific event class
	 * 
	 * @param object
	 */
	@SuppressWarnings("unchecked")
	public void attach(Observer object, Class eventClass);

	
	/**
	 * Remove an observer from the object
	 * @Pre observer is valid
	 * @Post observer is removed from the list of observers
	 */
	public void detach(Observer observer);
	
	/**
	 * Notify every registered observer that an Event has occurred
	 * @Pre Event is valid
	 * @Post Every observer had received the event through its accept method
	 */
	public void notify(Event event);
	
	/**
	 * Execute a simulation step by processing every events in its queue until the step delimiter is reached
	 * @Pre A step delimiter is in the event queue
	 * @Post The step delimiter has been consumed and every events preceding it have been processed
	 */
	public void step();
	
	/**
	 * Dispatch the event to the state machine of the object
	 * 
	 * @Pre The state machine has been correctly initialised
	 * @Post The event has been processed by the state machine
	 */
	public void dispatch(Event e);
	
	/**
	 * Retrieve the current states name (multiple names if there is concurrent state machines)
	 * 
	 * @Pre The state machine has been correctly initialised
	 */
	public Set<String> getCurrentStateNames();
	
	/**
	 * Getter and Setter for the name of the object, will be used as ID 
	 */
	public String getName();
	public void setName(String name);
	
	

}
