package system;

import events.DemobilisationOrder;
import events.Event;
import events.MobilisationOrder;


public class CommunicatorImpl implements Communicator {
	
	/* (non-Javadoc)
	 * @see system.Communicator#send(system.MobOrderImpl, int)
	 */
	public void send(MobilisationOrder mobOrder, int ambulanceId) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see system.Communicator#waitForAck(system.MobOrderImpl, int)
	 */
	public boolean waitForAck(MobilisationOrder mobOrder, int ambulanceId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see system.Communicator#send(system.DemobOrderImpl, int)
	 */
	public void send(DemobilisationOrder demobOrder, int ambulanceId) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see system.Communicator#waitForAck(system.DemobOrderImpl, int)
	 */
	public boolean waitForAck(DemobilisationOrder demobOrder, int ambulanceId) {
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

	public void send(Event order, int ambulanceId) {
		// TODO Auto-generated method stub
		
	}

	public boolean waitForAck(Event order, int ambulanceId) {
		// TODO Auto-generated method stub
		return false;
	}

}
