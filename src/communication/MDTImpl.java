package communication;

import java.util.concurrent.ConcurrentLinkedQueue;

import events.Event;

/**
 * This class represent a MDT. See {@link MDT} for more details.
 * 
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public class MDTImpl implements MDT {

	/**
	 * This list is used to store event that should be delivered to the system
	 */
	ConcurrentLinkedQueue<Event> inList;

	/**
	 * This list is used to store event that should be delivered to the external
	 * stub
	 */
	ConcurrentLinkedQueue<Event> outList;

	/**
	 * Create a new MDT, usable by the system
	 */
	public MDTImpl() {
		this.inList = new ConcurrentLinkedQueue<Event>();
		this.outList = new ConcurrentLinkedQueue<Event>();
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
		this.outList.add(event);
	}

}
