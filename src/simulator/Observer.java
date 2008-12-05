package simulator;

import events.Event;

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
	 * Add to the observer object the reference to the object it is observing.
	 */
	public void observing(Observable observable);
	
	/**
	 * Disconnect the observer from every object it is observing.
	 */
	public void disconnect(Observable observable);
	
	/**
	 * Disconnect the observer from every object it is observing.
	 */
	public void disconnect();
	
}
