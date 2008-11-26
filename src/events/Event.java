package events;

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
