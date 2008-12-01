package system;

public class ResolverImpl implements Resolver {

	private Communicator communicator;
	private Ambulance ambulance;
	
	/* (non-Javadoc)
	 * @see system.Resolver#closeIncident(int, int)
	 */
	public boolean closeIncident(int incidentInfoId, int ambulanceId) {
		// FIXME
		/*
		Event e = communicator.waitForEvent(incidentInfoId);
		switch(e.getType()) {
			case Communicator.AMBULANCE_ACCIDENT:
				ambulance.markAsBroken(ambulanceId);
				return false;
			case Communicator.INCIDENT_OBSTACLE:
				return false;
			default:
				return true;
		}
		*/
		return false;
	}

}
