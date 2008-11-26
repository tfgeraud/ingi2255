package events;

/**
 * Generic Event used both by the simulator and the LAS.
 * 
 * @author Erick Lavoie
 *
 */
public class Event {
	protected String senderName = "Undefined";
	
	public Event(String senderName) {
		this.senderName = senderName;
	}
	
	public String getSenderName() {
		return this.senderName;
	}
	
	public String toString() {
		return this.getClass().getName() + " sentBy:" + this.senderName; 
	}
}
