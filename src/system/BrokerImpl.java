package system;

/**
 * This class represents the broker. It stays at the interface between call
 * center (infoGUI or CallSimul).
 * 
 * @author Simon Busard <simon.busard@student.uclouvain.be>
 */
public class BrokerImpl implements Broker {

	/**
	 * The list of incidents known by the system
	 */
	private Incident incidentList = null;

	/**
	 * The ambulance chooser of the system
	 */
	private AmbulanceChooser chooser = null;

	/**
	 * The mobilizer of the system
	 */
	private Mobilizer mobilizer = null;

	/**
	 * The resolver of the system
	 */
	private Resolver resolver = null;

	/**
	 * Create a new broker
	 * 
	 * @param incidentList
	 *            The list of incidents known by the system
	 * @param chooser
	 *            The ambulance chooser of the system
	 * @param mobilizer
	 *            The mobilizeer of the system
	 * @param resolver
	 *            The resolver of the system
	 */
	public BrokerImpl(Incident incidentList, AmbulanceChooser chooser,
			Mobilizer mobilizer, Resolver resolver) {
		this.incidentList = incidentList;
		this.chooser = chooser;
		this.mobilizer = mobilizer;
		this.resolver = resolver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Broker#addIncidentInfo(java.lang.String, java.lang.String,
	 *      int, boolean)
	 */
	public void addIncidentInfo(String description, String localisation,
			int victimAge, boolean victimPregnant) {
		String incId = this.incidentList.addIncident(victimAge, victimPregnant,
				localisation, description);

		// Launch a thread
		new IncidentProcessorImpl(this.chooser, this.mobilizer, this.resolver,
				incId);
	}

}
