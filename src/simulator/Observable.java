package simulator;

import event.Event;

/**
 * Implement the observer design pattern
 * @author Erick Lavoie
 *
 */
public interface Observable {
	
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
	 * Notify every registered observer that an Event has occured
	 * @Pre Event is valid
	 * @Post Every observer had received the event through its accept method
	 */
	public void notify(Event event);

}
