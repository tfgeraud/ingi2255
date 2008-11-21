package system;

import system.event.Event;

public interface Communicator {

	public void send(Order order, int ambulanceId);

	public boolean waitForAck(Order order, int ambulanceId);

	public Event waitForEvent(int incidentInfoId);

	public void mainLoop();

}