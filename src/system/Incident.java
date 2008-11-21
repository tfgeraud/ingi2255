package system;

/**
 * The Incident interface represent the collection
 * of all the incidents stored in the system.
 * 
 */
public interface Incident {

	/**
	 * Return the ambulance kind that is needed by the incident 
	 * incidentInfoId.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the kind of the ambulance needed by the incident
	 */
	public int getAmbulanceKindNeeded(int incidentInfoId);

	/**
	 * Return the coordinate of the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the coordinates of the incident
	 */
	public Coord getCoord(int incidentInfoId);

	/**
	 * Return the mobilization order of the incident
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the order related to the incident.
	 */
	public MobOrder getMobOrder(int incidentInfoId);

	/**
	 * Return the demobilizationOrder for the incident incidentInfoId.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the order related to the incident
	 */
	public DemobOrder getDemobOrder(int incidentInfoId);
	
	/**
	 * Return the age of the victim concerned by the incident
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the age of the victim
	 */
	public int getAge(int incidentInfoId);
	
	/**
	 * Return the pregnancy of the victim of the incident
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the pregrancy of the victim, true if pregrant, false otherwise.
	 */
	public boolean getPregnant(int incidentInfoId);
	
	/**
	 * Return the localisation of the incident 
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the localisation of the incident
	 */
	public String getLocalisation(int incidentInfoId);
	
	/**
	 * Return the position (in coordinate) of the incident 
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the position (in coordinate) of the incident
	 */	
	public Coord getPosition(int incidentInfoId);
	
	/**
	 * Get the description of the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the description of the incident
	 */
	public String getDescription(int incidentInfoId);
	
	/**
	 * Get the ambulanceId chosen for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the ambulance chosen for the incident
	 */
	public int getChosenAmbulance(int incidentInfoId);
	
	/**
	 * Get the ambulanceId mobilized for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the ambulance mobilized for the incident
	 */
	public int getMobilizedAmbulance(int incidentInfoId);
	
	/**
	 * Set the ambulanceId chosen for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post the ambulance ambulanceId is now chosen for the incident incidentInfoId
	 */
	public void setChosenAmbulance(int incidentInfoId, int ambulanceId);
	
	/**
	 * Set the ambulanceId mobilized for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post the ambulance ambulanceId is now mobilized for the incident incidentInfoId
	 */
	public void setMobilizedAmbulance(int incidentInfoId, int ambulanceId);
	
	/**
	 * Set the incident as resolved
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post the incident incidentInfoId is now considered as resolved.
	 */
	public void setAsResolved(int incidentInfoId);
}