package communication;

/**
 * This interface represents all communication channels to and from
 * the software system.
 * 
 * @author Simon Busard
 *
 */
import events.Event;

public interface Communication {

	/**
	 * Send an event to the legacy software.
	 * 
	 * @pre		event a valid event
	 * @post	event is sent to the legacy software
	 */
	public void send(Event event);
	
	/**
	 * Receive an event from the legacy software.
	 *
	 * @pre		-
	 * @post	return the received event
	 */
	public Event receive();
}
