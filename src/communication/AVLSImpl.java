package communication;

import java.util.concurrent.ConcurrentLinkedQueue;

import events.Event;

/**
 * This class represent an AVLS. See {@link AVLS} for more details.
 * 
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public class AVLSImpl implements AVLS {

	/**
	 * This list is used to store event that should be delivered to the system
	 */
	ConcurrentLinkedQueue<Event> inList;

	/**
	 * Create a new AVLS, usable by the system
	 */
	public AVLSImpl() {
		this.inList = new ConcurrentLinkedQueue<Event>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see communication.Communication#receive()
	 */
	public Event receive() {
		if (this.inList.size() >= 0) {
			return this.inList.poll();
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see communication.Communication#send(events.Event)
	 */
	public void send(Event event) {
		throw new Error("Unable to send a message through AVLS");
	}

}
