package system;

/**
 * The broker interface present an API to add incident information
 * to the database
 */
public interface Broker {

	/**
	 * Add incidentInformation in the system
	 * 
	 * @pre	description is the description of the incident
	 * 		localisation is the location of the incident, textually represented
	 * 		victimAge is the age of the victim (victimAge > 0)
	 * 		victimPregnant is the pregnancy of the victim
	 * @post a new incident information is now stored in the database
	 */
	public void addIncidentInfo(String description, String localisation, int victimAge, boolean victimPregnant);
}
