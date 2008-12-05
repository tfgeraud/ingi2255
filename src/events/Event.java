package events;

/**
 * This class represent a generic Event used both by the simulator and the LAS.
 * 
 * @author Erick Lavoie
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public class Event {

	/**
	 * The name of the sender
	 */
	protected String senderName = "Undefined";

	/**
	 * Create a new event
	 * 
	 * @param senderName
	 *            the name of the sender
	 */
	public Event(String senderName) {
		this.senderName = senderName;
	}

	/**
	 * Return the name of the sender
	 * 
	 * @return the name of the sender
	 */
	public String getSenderName() {
		return this.senderName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getName() + " sentBy:" + this.senderName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof Event && arg0.getClass().equals(this.getClass())) {
			return this.senderName.equals(((Event) arg0).senderName);
		} else {
			return super.equals(arg0);
		}
	}

}
