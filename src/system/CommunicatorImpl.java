package system;

import system.event.Event;

public class CommunicatorImpl implements Communicator {
	
	/* (non-Javadoc)
	 * @see system.Communicator#send(system.MobOrderImpl, int)
	 */
	public void send(MobOrder mobOrder, int ambulanceId) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see system.Communicator#waitForAck(system.MobOrderImpl, int)
	 */
	public boolean waitForAck(MobOrder mobOrder, int ambulanceId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see system.Communicator#send(system.DemobOrderImpl, int)
	 */
	public void send(DemobOrder demobOrder, int ambulanceId) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see system.Communicator#waitForAck(system.DemobOrderImpl, int)
	 */
	public boolean waitForAck(DemobOrder demobOrder, int ambulanceId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see system.Communicator#waitForEvent(int)
	 */
	public Event waitForEvent(int incidentInfoId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see system.Communicator#mainLoop()
	 */
	public void mainLoop() {
		
	}

}
