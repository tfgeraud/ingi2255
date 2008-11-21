package system;

public class MobilizerImpl implements Mobilizer {

	private Incident incident;
	private Communicator communicator;
	
	/* (non-Javadoc)
	 * @see system.Mobilizer#mobilize(int, int)
	 */
	public boolean mobilize(int incidentInfoId, int ambulanceId) {
		
		MobOrder mobOrder = incident.getMobOrder(incidentInfoId);	
		communicator.send(mobOrder, ambulanceId);
		
		return communicator.waitForAck(mobOrder, ambulanceId);
	}

	/* (non-Javadoc)
	 * @see system.Mobilizer#demobilize(int, int)
	 */
	public boolean demobilize(int incidentInfoId, int ambulanceId) {
		
		DemobOrder demobOrder = incident.getDemobOrder(incidentInfoId);	
		communicator.send(demobOrder, ambulanceId);
		
		return communicator.waitForAck(demobOrder, ambulanceId);
	}

}
