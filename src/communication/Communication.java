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
	 * @pre event a valid event
	 * @post event is sent to the legacy software
	 * 
	 * @param event
	 *            The event to send to the device
	 */
	public void send(Event event);

	/**
	 * Receive an event from the legacy software.
	 * 
	 * @pre -
	 * @post return the received event, null if no event is pending
	 * 
	 * @return The event should be delivered to the application
	 */
	public Event receive();
}
