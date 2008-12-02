package system;

import events.DemobilisationOrder;
import events.MobilisationOrder;
import system.exception.UnknownIncidentException;

/**
 * The Incident interface represent the collection of all the incidents stored
 * in the system.
 * 
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 * @author Simon Busard <simon.busard@student.uclouvain.be>
 */
public interface Incident {

	/**
	 * The known status of an incdent
	 */
	public static final String KNOWN = "known";

	/**
	 * The processed status of an incident
	 */
	public static final String PROCESSED = "processed";

	/**
	 * The chosen status of an incident
	 */
	public static final String CHOSEN = "chosen";

	/**
	 * The mobilized status of an incident
	 */
	public static final String MOBILIZED = "mobilized";

	/**
	 * The resolved status of an incident
	 */
	public static final String RESOLVED = "resolved";

	/**
	 * Return the ambulance kind that is needed by the incident incidentInfoId.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the kind of the ambulance needed by the incident
	 * 
	 * @param incidentInfoId
	 *            The unique identifier of the incident
	 * @throws UnknownIncidentException
	 *             The incident is not knwown by the system.
	 */
	public String getAmbulanceKindNeeded(String incidentInfoId)
			throws UnknownIncidentException;

	/**
	 * Return the mobilization order of the incident
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the order related to the incident
	 * 
	 * @param incidentInfoId
	 *            The unique identifier of the incident
	 * @throws UnknownIncidentException
	 *             The incident is not knwown by the system.
	 */
	public MobilisationOrder getMobOrder(String incidentInfoId)
			throws UnknownIncidentException;

	/**
	 * Return the demobilizationOrder for the incident incidentInfoId.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the order related to the incident
	 * 
	 * @param incidentInfoId
	 *            The unique identifier of the incident
	 * @throws UnknownIncidentException
	 *             The incident is not knwown by the system.
	 */
	public DemobilisationOrder getDemobOrder(String incidentInfoId)
			throws UnknownIncidentException;

	/**
	 * Return the age of the victim concerned by the incident
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the age of the victim
	 * 
	 * @param incidentInfoId
	 *            The unique identifier of the incident
	 * @throws UnknownIncidentException
	 *             The incident is not knwown by the system.
	 */
	public int getAge(String incidentInfoId) throws UnknownIncidentException;

	/**
	 * Return the pregnancy of the victim of the incident
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the pregrancy of the victim, true if pregrant, false
	 *       otherwise.
	 * 
	 * @param incidentInfoId
	 *            The unique identifier of the incident
	 * @throws UnknownIncidentException
	 *             The incident is not knwown by the system.
	 */
	public boolean getPregnant(String incidentInfoId)
			throws UnknownIncidentException;

	/**
	 * Return the localisation of the incident
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the localisation of the incident
	 * 
	 * @param incidentInfoId
	 *            The unique identifier of the incident
	 * @throws UnknownIncidentException
	 *             The incident is not knwown by the system.
	 */
	public String getLocalisation(String incidentInfoId)
			throws UnknownIncidentException;

	/**
	 * Return the position (in coordinate) of the incident
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the position (in coordinate) of the incident
	 * 
	 * @param incidentInfoId
	 *            The unique identifier of the incident
	 * @throws UnknownIncidentException
	 *             The incident is not knwown by the system.
	 */
	public Coord getPosition(String incidentInfoId)
			throws UnknownIncidentException;

	/**
	 * Get the description of the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the description of the incident
	 * 
	 * @param incidentInfoId
	 *            The unique identifier of the incident
	 * @throws UnknownIncidentException
	 *             The incident is not knwown by the system.
	 */
	public String getDescription(String incidentInfoId)
			throws UnknownIncidentException;

	/**
	 * Get the ambulanceId chosen for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the ambulance chosen for the incident
	 * 
	 * @param incidentInfoId
	 *            The unique identifier of the incident
	 * @throws UnknownIncidentException
	 *             The incident is not knwown by the system.
	 */
	public String getChosenAmbulance(String incidentInfoId)
			throws UnknownIncidentException;

	/**
	 * Get the ambulanceId mobilized for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post return the ambulance mobilized for the incident
	 * 
	 * @param incidentInfoId
	 *            The unique identifier of the incident
	 * @throws UnknownIncidentException
	 *             The incident is not knwown by the system.
	 */
	public String getMobilizedAmbulance(String incidentInfoId)
			throws UnknownIncidentException;

	/**
	 * Set the ambulanceId chosen for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post the ambulance ambulanceId is now chosen for the incident
	 *       incidentInfoId
	 * 
	 * @param incidentInfoId
	 *            The unique identifier of the incident
	 * @param ambulanceId
	 *            The unique identifier of the ambulance
	 * @throws UnknownIncidentException
	 *             The incident is not knwown by the system.
	 */
	public void setChosenAmbulance(String incidentInfoId, String ambulanceId)
			throws UnknownIncidentException;

	/**
	 * Set the ambulanceId mobilized for the incident.
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post the ambulance ambulanceId is now mobilized for the incident
	 *       incidentInfoId
	 * 
	 * @param incidentInfoId
	 *            The unique identifier of the incident
	 * @param ambulanceId
	 *            The unique identifier of the ambulance
	 * @throws UnknownIncidentException
	 *             The incident is not knwown by the system.
	 */
	public void setMobilizedAmbulance(String incidentInfoId, String ambulanceId)
			throws UnknownIncidentException;

	/**
	 * Set the incident as resolved
	 * 
	 * @pre incidentInfoId is the id of the incident
	 * @post the incident incidentInfoId is now considered as resolved.
	 * 
	 * @param incidentInfoId
	 *            The unique identifier of the incident
	 * @throws UnknownIncidentException
	 *             The incident is not knwown by the system.
	 */
	public void setAsResolved(String incidentInfoId)
			throws UnknownIncidentException;

	/**
	 * Add a new incident in the database and return its identifier
	 * 
	 * @pre age the age of the victim implied in the incident. age >= 0 pregnant ==
	 *      true iff the victim is pregnant localisation the localisation of the
	 *      incident description the description of the incident
	 * @post a new incident is stored in the database and its identifier is
	 * 		returned
	 * 
	 * @param age
	 *            The age of the victim
	 * @param pregnant
	 *            True if the victim is pregnant, false otherwise
	 * @param localisation
	 *            The localisation of the incident
	 * @param description
	 *            The description of the incident
	 */
	public String addIncident(int age, boolean pregnant, String localisation,
			String description);
}