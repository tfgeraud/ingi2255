package system;

import events.DemobilisationOrder;
import events.Event;
import events.MobilisationOrder;


public class CommunicatorImpl implements Communicator {
	
	/* (non-Javadoc)
	 * @see system.Communicator#send(system.MobOrderImpl, int)
	 */
	public void send(MobilisationOrder mobOrder, String ambulanceId) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see system.Communicator#waitForAck(system.MobOrderImpl, int)
	 */
	public boolean waitForAck(MobilisationOrder mobOrder, String ambulanceId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see system.Communicator#send(system.DemobOrderImpl, int)
	 */
	public void send(DemobilisationOrder demobOrder, String ambulanceId) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see system.Communicator#waitForAck(system.DemobOrderImpl, int)
	 */
	public boolean waitForAck(DemobilisationOrder demobOrder, String ambulanceId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see system.Communicator#waitForEvent(int)
	 */
	public Event waitForEvent(String incidentInfoId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see system.Communicator#mainLoop()
	 */
	public void mainLoop() {
		
	}

	public void send(Event order, String ambulanceId) {
		// TODO Auto-generated method stub
		
	}

	public boolean waitForAck(Event order, String ambulanceId) {
		// TODO Auto-generated method stub
		return false;
	}

}
