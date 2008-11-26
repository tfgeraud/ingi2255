package system;

import java.util.LinkedList;

/**
 * The Ambulance interface represents the set of all ambulances known by the
 * system.
 * 
 * @author Simon Busard
 */
public interface Ambulance {

	public static final String FREE = "free";
	public static final String CHOSEN = "chosen";
	public static final String MOBILIZED = "mobilized";

	public static final String BROKEN = "broken";
	public static final String WORKING = "working";

	public static final String MEDICALIZED = "medicalized";
	public static final String NORMAL = "normal";

	/**
	 * Add an ambulance in the system.
	 * 
	 * @pre ambulanceId the id of the new ambulance coord the new ambulance
	 *      coordinates kind the kind of the new ambulance (normal or
	 *      medicalized) status the ambulance status (working, broken)
	 * @post a new ambulance is stored in the system
	 */
	public void addAmbulance(String ambulanceId, Coord coord, String kind,
			String status);

	/**
	 * Return all free ambulance of kind that are not listed in exclusionSet
	 * 
	 * @pre kind the ambulance kind needed (normal or medicalized) exclusionSet
	 *      a list of ambulance ids that dont have to be taken in account
	 * @post return a list of ids of free ambulances
	 */
	public LinkedList<String> getAllFree(String kind,
			LinkedList<String> exclusionSet);

	/**
	 * Return coordinates of ambulance identified by ambulanceId.
	 * 
	 * @pre ambulanceId a valid id
	 * @post return coordinates of concerned ambulance
	 */
	public Coord getCoord(String ambulanceId);

	/**
	 * Mark ambulance as broken.
	 * 
	 * @pre ambulanceInfoId a valid id
	 * @post ambulance with ambulanceInfoId id is set as broken
	 */
	public void markAsBroken(String ambulanceInfoId);

	/**
	 * Mark ambulance as repaired.
	 * 
	 * @pre ambulanceInfoId a valid id
	 * @post ambulance with ambulanceInfoId id is set as repaired
	 */
	public void markAsRepaired(String ambulanceInfoId);

	/**
	 * Return kind of an ambulance.
	 * 
	 * @pre ambulanceInfoId id of the ambulance
	 * @post return kind of the ambulance
	 */
	public String getKind(String ambulanceInfoId);

	/**
	 * Return id of the incident for which the ambulance has been chosen.
	 * 
	 * @pre ambulanceInfoId id of the ambulance
	 * @post return incident id
	 */
	public String getIncidentChosenFor(String ambulanceInfoId);

	/**
	 * Return id of the incident for which the ambulance has been mobilized.
	 * 
	 * @pre ambulanceInfoId id of the ambulance
	 * @post return incident id
	 */
	public String getIncidentMobilizedFor(String ambulanceInfoId);

	/**
	 * Set ambulance position to coord.
	 * 
	 * @pre ambulanceInfoId id of the ambulance coord new coordinates
	 * @post coordinates of the ambulance are updated
	 */
	public void setPosition(String ambulanceInfoId, Coord coord);

	/**
	 * Set incident for which ambulance is chosen to incidentInfoId.
	 * If the ambulance is no longer chosen, then incidentInfoId 
	 * should be null.
	 * 
	 * @pre ambulanceInfoId id of the ambulance incidentInfoId id of the
	 *      incident
	 * @post choice for the ambulance is updated, ambulance is
	 * chosen for the incident incidentInfoId if incidentInfoId is not
	 * null and the ambulance is free if incidentInfoId is null.
	 */
	public void setIncidentChosenFor(String ambulanceInfoId,
			String incidentInfoId);

	/**
	 * Set incident for which ambulance is mobilized to incidentInfoId.
	 * If the ambulance is no longer mobilized, then incidentInfoId 
	 * should be null.
	 * 
	 * @pre ambulanceInfoId id of the ambulance incidentInfoId id of the
	 *      incident
	 * @post mobilization for the ambulance is updated, ambulance is
	 * mobilized for the incident incidentInfoId if incidentInfoId is not
	 * null and the ambulance is free if incidentInfoId is null.
	 */
	public void setIncidentMobilizedFor(String ambulanceInfoId,
			String incidentInfoId);

}