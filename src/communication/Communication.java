package communication;

/**
 * This interface represents all communication channels to and from
 * the software system.
 * 
 * @author Simon Busard
 *
 */
import system.event.Event;

public interface Communication {

	/**
	 * Send an event to the software system.
	 * 
	 * @pre		event a valid event
	 * @post	event is sent to the system
	 */
	public void send(Event event);
	
	/**
	 * Receive an event from the system.
	 *
	 * @pre		-
	 * @post	return the received event
	 */
	public Event receive();
	
	/**
	 * Plug communication channel with simulation objects (implement
	 * observer interface).
	 * 
	 * @pre		event a valid event
	 * @post	communication channel is plugged
	 */
	public void accept(Event event);
}
