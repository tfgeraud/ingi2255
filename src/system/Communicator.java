package system;

import system.event.Event;

/**
 * The communicator represent the interface used to communicate
 * with the external world. 
 */
public interface Communicator {

	/**
	 * Send an order to an ambulance
	 * 
	 * @pre	order is the order to send to the ambulance
	 * 		ambulanceId is the id of the ambulance and must be known by the system
	 * @post the order is sent to the corresponding ambulance
	 */
	public void send(Order order, int ambulanceId);

	/**
	 * Wait for an acknowledgement
	 * 
	 * @pre order is the order for which we wait the ack
	 * 		ambulanceId is the ambulance that should send the ack
	 * @post true is the ack is received, false otherwise
	 */
	public boolean waitForAck(Order order, int ambulanceId);

	/**
	 * Wait for an incoming event
	 * 
	 * @pre incidentInfoId is the id of the incident we are listing the events
	 * @post the event received by one of the channel
	 */
	public Event waitForEvent(int incidentInfoId);

	/**
	 * Connect to external channel and wait for event
	 * @pre	-
	 * @post the events, once received, are forwarded.
	 */
	public void mainLoop();

}