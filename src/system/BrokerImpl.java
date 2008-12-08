package system;

/**
 * This class represents the broker.  It stays at the interface between 
 * call center (infoGUI or CallSimul).
 * 
 * @author Simon Busard <simon.busard@student.uclouvain.be>
 */
public class BrokerImpl implements Broker {
	
	/**
	 * The list of incidents
	 */
	private Incident incidentList = null;
	
	public BrokerImpl(Incident incidentList) {
		this.incidentList = incidentList;
	}

	/*
	 * (non-Javadoc)
	 * @see system.Broker#addIncidentInfo(java.lang.String, java.lang.String, int, boolean)
	 */
	public void addIncidentInfo(String description, String localisation,
			int victimAge, boolean victimPregnant) {
		this.incidentList.addIncident(victimAge, victimPregnant, localisation, description);
		
	}

}
