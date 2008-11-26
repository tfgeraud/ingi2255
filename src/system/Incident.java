package system;

/**
 * The Incident interface represent the collection
 * of all the incidents stored in the system.
 * 
 */
public interface Incident {
	
	public final String KNOWN = "known";
	public final String PROCESSED = "processed";
	public final String CHOSEN = "chosen";
	public final String MOBILIZED = "mobilized";
	public final String RESOLVED = "resolved";

	/**
	 * Return the ambulance kind that is needed by the incident 
	 * incidentInfoId.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the kind of the ambulance needed by the incident
	 */
	public String getAmbulanceKindNeeded(String incidentInfoId);

	/**
	 * Return the mobilization order of the incident
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the order related to the incident.
	 */
	public MobOrder getMobOrder(String incidentInfoId);

	/**
	 * Return the demobilizationOrder for the incident incidentInfoId.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the order related to the incident
	 */
	public DemobOrder getDemobOrder(String incidentInfoId);
	
	/**
	 * Return the age of the victim concerned by the incident
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the age of the victim
	 */
	public int getAge(String incidentInfoId);
	
	/**
	 * Return the pregnancy of the victim of the incident
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the pregrancy of the victim, true if pregrant, false otherwise.
	 */
	public boolean getPregnant(String incidentInfoId);
	
	/**
	 * Return the localisation of the incident 
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the localisation of the incident
	 */
	public String getLocalisation(String incidentInfoId);
	
	/**
	 * Return the position (in coordinate) of the incident 
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the position (in coordinate) of the incident
	 */	
	public Coord getPosition(String incidentInfoId);
	
	/**
	 * Get the description of the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the description of the incident
	 */
	public String getDescription(String incidentInfoId);
	
	/**
	 * Get the ambulanceId chosen for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the ambulance chosen for the incident
	 */
	public String getChosenAmbulance(String incidentInfoId);
	
	/**
	 * Get the ambulanceId mobilized for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the ambulance mobilized for the incident
	 */
	public String getMobilizedAmbulance(String incidentInfoId);
	
	/**
	 * Set the ambulanceId chosen for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post the ambulance ambulanceId is now chosen for the incident incidentInfoId
	 */
	public void setChosenAmbulance(String incidentInfoId, String ambulanceId);
	
	/**
	 * Set the ambulanceId mobilized for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post the ambulance ambulanceId is now mobilized for the incident incidentInfoId
	 */
	public void setMobilizedAmbulance(String incidentInfoId, String ambulanceId);
	
	/**
	 * Set the incident as resolved
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post the incident incidentInfoId is now considered as resolved.
	 */
	public void setAsResolved(String incidentInfoId);
	
	/**
	 * Add a new incident in the database
	 * 
	 * @pre	age the age of the victim implied in the incident. age >= 0
	 * 		pregnant == true iff the victim is pregnant
	 * 		localisation the localisation of the incident
	 * 		description the description of the incident
	 * @post a new incident is stored in the database
	 */
	public void addIncident(int age, boolean pregnant, String localisation,String description);
}