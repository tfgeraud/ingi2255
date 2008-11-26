package simulator;

import events.Event;

/**
 * Implement the observer design pattern
 * @author Erick Lavoie
 *
 */
public interface Observable {
	
	/**
	 * Add an observer to the object
	 * @pre observer is valid
	 * @post observer is added to the list of observers
	 */
	public void attach(Observer observer);
	
	/**
	 * Remove an observer from the object
	 * @pre observer is valid
	 * @post observer is removed from the list of observers
	 */
	public void detach(Observer observer);
	
	/**
	 * Notify every registered observer that an Event has occured
	 * @pre Event is valid
	 * @post Every observer had received the event through its accept method
	 */
	public void notify(Event event);

}
