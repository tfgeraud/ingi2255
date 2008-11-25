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
	
	
	
	

}
