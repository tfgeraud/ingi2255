package simulator;

import event.Event;

/**
 * Implement the observer pattern
 * 
 * @author Erick Lavoie
 *
 */
public interface Observer {
	
	/**
	 * Accept an event when notified by an observable object
	 * @param event
	 */
	public void accept(Event event);
	
	/**
	 * Add an observer to the object
	 * @Pre observer is valid
	 * @Post observer is added to the list of observers
	 */
	public void attach(Observer observer);
	
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

}
