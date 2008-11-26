package system;

import system.exception.UnknownIncidentException;

/**
 * The Incident interface represent the collection
 * of all the incidents stored in the system.
 * 
 */
public interface Incident {
	
	public static final String KNOWN = "known";
	public static final String PROCESSED = "processed";
	public static final String CHOSEN = "chosen";
	public static final String MOBILIZED = "mobilized";
	public static final String RESOLVED = "resolved";

	/**
	 * Return the ambulance kind that is needed by the incident 
	 * incidentInfoId.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the kind of the ambulance needed by the incident
	 */
	public String getAmbulanceKindNeeded(String incidentInfoId)
	throws UnknownIncidentException ;

	/**
	 * Return the mobilization order of the incident
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the order related to the incident.
	 */
	public MobOrder getMobOrder(String incidentInfoId)
	throws UnknownIncidentException ;

	/**
	 * Return the demobilizationOrder for the incident incidentInfoId.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the order related to the incident
	 */
	public DemobOrder getDemobOrder(String incidentInfoId)
	throws UnknownIncidentException ;
	
	/**
	 * Return the age of the victim concerned by the incident
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the age of the victim
	 */
	public int getAge(String incidentInfoId)
	throws UnknownIncidentException ;
	
	/**
	 * Return the pregnancy of the victim of the incident
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the pregrancy of the victim, true if pregrant, false otherwise.
	 */
	public boolean getPregnant(String incidentInfoId)
	throws UnknownIncidentException ;
	
	/**
	 * Return the localisation of the incident 
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the localisation of the incident
	 */
	public String getLocalisation(String incidentInfoId)
	throws UnknownIncidentException ;
	
	/**
	 * Return the position (in coordinate) of the incident 
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the position (in coordinate) of the incident
	 */	
	public Coord getPosition(String incidentInfoId)
	throws UnknownIncidentException ;
	
	/**
	 * Get the description of the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the description of the incident
	 */
	public String getDescription(String incidentInfoId)
	throws UnknownIncidentException ;
	
	/**
	 * Get the ambulanceId chosen for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the ambulance chosen for the incident
	 */
	public String getChosenAmbulance(String incidentInfoId)
	throws UnknownIncidentException ;
	
	/**
	 * Get the ambulanceId mobilized for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the ambulance mobilized for the incident
	 */
	public String getMobilizedAmbulance(String incidentInfoId)
	throws UnknownIncidentException;
	
	/**
	 * Set the ambulanceId chosen for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post the ambulance ambulanceId is now chosen for the incident incidentInfoId
	 */
	public void setChosenAmbulance(String incidentInfoId, String ambulanceId)
	throws UnknownIncidentException;
	
	/**
	 * Set the ambulanceId mobilized for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post the ambulance ambulanceId is now mobilized for the incident incidentInfoId
	 */
	public void setMobilizedAmbulance(String incidentInfoId, String ambulanceId)
	throws UnknownIncidentException;
	
	/**
	 * Set the incident as resolved
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post the incident incidentInfoId is now considered as resolved.
	 */
	public void setAsResolved(String incidentInfoId)
	throws UnknownIncidentException;
	
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