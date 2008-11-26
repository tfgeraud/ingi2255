package system;

public class MobilizerImpl implements Mobilizer {

	private Incident incident;
	private Communicator communicator;
	
	/* (non-Javadoc)
	 * @see system.Mobilizer#mobilize(int, int)
	 */
	public boolean mobilize(int incidentInfoId, int ambulanceId) {
		// FIXME
		/*
		MobilisationOrder MobilisationOrder = incident.getMobOrder(incidentInfoId);	
		communicator.send(MobilisationOrder, ambulanceId);
		
		return communicator.waitForAck(MobilisationOrder, ambulanceId);
		*/
		return false;
	}

	/* (non-Javadoc)
	 * @see system.Mobilizer#demobilize(int, int)
	 */
	public boolean demobilize(int incidentInfoId, int ambulanceId) {
		// FIXME
		/*
		DemobOrder demobOrder = incident.getDemobOrder(incidentInfoId);	
		communicator.send(demobOrder, ambulanceId);
		
		return communicator.waitForAck(demobOrder, ambulanceId);
		*/
		return false;
	}

}
