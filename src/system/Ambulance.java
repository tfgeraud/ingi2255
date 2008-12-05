package system;

import java.util.Set;

import system.exception.AmbulanceKindUnknownException;
import system.exception.AmbulanceStatusUnknwownException;
import system.exception.IllegalMobilizationException;

/**
 * The Ambulance interface represents the set of all ambulances known by the
 * system. An ambulance is either normal, either medicalized depending on its
 * equipement and the skills of its staff.
 * 
 * The status of an ambulance is its ability to perform some actions, so an
 * ambulance is either working, either broken.
 * 
 * The availability of an ambulance is its ability to perform some actions for a
 * while. So the availability of an ambulance is either free, either chosen,
 * either mobilized.
 * 
 * @author Simon Busard <simon.busard@student.uclouvain.be>
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public interface Ambulance {

	/**
	 * The free status of an ambulance
	 */
	public static final String FREE = "free";

	/**
	 * The chosen status of an ambulance
	 */
	public static final String CHOSEN = "chosen";

	/**
	 * The mobilized status of an ambulance
	 */
	public static final String MOBILIZED = "mobilized";

	/**
	 * The broken status of the ambulance
	 */
	public static final String BROKEN = "broken";

	/**
	 * The working status of an ambulance
	 */
	public static final String WORKING = "working";

	/**
	 * The medicalized kind of an ambulance
	 */
	public static final String MEDICALIZED = "medicalized";

	/**
	 * The normal kind of an ambulance
	 */
	public static final String NORMAL = "normal";

	/**
	 * Add an ambulance in the system.
	 * 
	 * @pre ambulanceId the id of the new ambulance coord the new ambulance
	 *      coordinates kind the kind of the new ambulance (normal or
	 *      medicalized) status the ambulance status (working, broken)
	 * @post a new ambulance is stored in the system
	 * 
	 * @param ambulanceId
	 *            The unique identifier of the ambulance
	 * @param position
	 *            The position of the ambulance
	 * @param kind
	 *            The kind of an ambulance
	 * @param status
	 *            The status of an ambulance
	 * 
	 * @throws AmbulanceStatusUnknwownException
	 *             The status of the ambulance is not known.
	 * @throws AmbulanceKindUnknownException
	 *             The kind of the ambulance is not known
	 */
	public void addAmbulance(String ambulanceId, Coord position, String kind,
			String status) throws AmbulanceStatusUnknwownException,
			AmbulanceKindUnknownException;

	/**
	 * Return all free ambulance of kind that are not listed in exclusionSet
	 * matching the kind and that are not marked as broken. All those ambulances
	 * returned by the method may be used for an incident.
	 * 
	 * @pre kind the ambulance kind needed (normal or medicalized) exclusionSet
	 *      a list of ambulance ids that dont have to be taken in account
	 * @post return a list of ids of free ambulances
	 * 
	 * @param kind
	 *            The needed kind of the ambulance
	 * @param exclusionSet
	 *            The set of ambulance that cannot be chosen for this incident.
	 */
	public Set<String> getAllFree(String kind,
			Set<String> exclusionSet);

	/**
	 * Return coordinates of ambulance identified by ambulanceId.
	 * 
	 * @pre ambulanceId a valid id
	 * @post return coordinates of concerned ambulance
	 * 
	 * @param ambulanceId
	 *            The unique identifier of the ambulance
	 * @return the coordinate of the ambulance
	 */
	public Coord getPosition(String ambulanceId);

	/**
	 * Mark ambulance as broken.
	 * 
	 * @pre ambulanceId a valid id
	 * @post ambulance with ambulanceId id is set as broken
	 * 
	 * @param ambulanceId
	 *            The unique identifier of the ambulance
	 */
	public void markAsBroken(String ambulanceId);

	/**
	 * Mark ambulance as repaired.
	 * 
	 * @pre ambulanceId a valid id
	 * @post ambulance with ambulanceId id is set as repaired
	 * 
	 * @param ambulanceId
	 *            The unique identifier of the ambulance
	 */
	public void markAsRepaired(String ambulanceId);

	/**
	 * Return kind of an ambulance.
	 * 
	 * @pre ambulanceId id of the ambulance
	 * @post return kind of the ambulance
	 * 
	 * @param ambulanceId
	 *            The unique identifier of the ambulance
	 */
	public String getKind(String ambulanceId);

	/**
	 * Return id of the incident for which the ambulance has been chosen.
	 * 
	 * @pre ambulanceId id of the ambulance
	 * @post return incident id
	 * 
	 * @param ambulanceId
	 *            The unique identifier of the ambulance
	 */
	public String getIncidentChosenFor(String ambulanceId);

	/**
	 * Return unique identifier of the incident the ambulance has been mobilized
	 * for.
	 * 
	 * @pre ambulanceId id of the ambulance
	 * @post return incident id
	 * 
	 * @param ambulanceId
	 *            The unique identifier of the ambulance
	 * @return the unique identifier of the incident the ambulance is mobilized
	 *         for
	 */
	public String getIncidentMobilizedFor(String ambulanceId);

	/**
	 * Set ambulance position to coord.
	 * 
	 * @pre ambulanceId id of the ambulance coord new coordinates
	 * @post coordinates of the ambulance are updated
	 * 
	 * @param ambulanceId
	 *            The unique identifier of the ambulance
	 * @param coord
	 *            The coordinate of the ambulance
	 */
	public void setPosition(String ambulanceId, Coord coord);

	/**
	 * Set incident for which ambulance is chosen to incidentInfoId. If the
	 * ambulance is no longer chosen, then incidentInfoId should be null.
	 * 
	 * @pre ambulanceId id of the ambulance incidentInfoId id of the incident
	 * @post choice for the ambulance is updated, ambulance is chosen for the
	 *       incident incidentInfoId if incidentInfoId is not null and the
	 *       ambulance is free if incidentInfoId is null.
	 * 
	 * @param ambulanceId
	 *            The unique identifier of the ambulance
	 * @param incidentInfoId
	 *            The unique indentifier of the incident
	 */
	public void setIncidentChosenFor(String ambulanceId, String incidentInfoId);

	/**
	 * Set incident for which ambulance is mobilized to incidentInfoId. If the
	 * ambulance is no longer mobilized, then incidentInfoId should be null.
	 * 
	 * @pre ambulanceId id of the ambulance incidentInfoId id of the incident
	 * @post mobilization for the ambulance is updated, ambulance is mobilized
	 *       for the incident incidentInfoId if incidentInfoId is not null and
	 *       the ambulance is free if incidentInfoId is null.
	 * 
	 * @param ambulanceId
	 *            The unique identifier of the ambulance
	 * @param incidentInfoId
	 *            The unique indentifier of the incident
	 * 
	 * @throws IllegalMobilizationException
	 *             The mobilization is illegal since the ambulance was not
	 *             chosen before.
	 */
	public void setIncidentMobilizedFor(String ambulanceId,
			String incidentInfoId) throws IllegalMobilizationException;

	/**
	 * Return the status of the ambulance corresponding to the unique identifier
	 * given as parameter The status of the ambulance is either
	 * {@link Ambulance#WORKING} or {@link Ambulance#BROKEN}.
	 * 
	 * @param ambulanceId
	 *            The unique identifier of the ambulance
	 * @return The status of the ambulance identified by ambulanceId
	 */
	public String getStatus(String ambulanceId);

}