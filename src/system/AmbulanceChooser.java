package system;

import java.util.Set;

import system.exception.UnknownIncidentException;

/**
 * This interface represents ambulance chooser module.  It is able to
 * choose best ambulance able to resolve an incident.
 * 
 * @author Simon Busard
 */
public interface AmbulanceChooser {

	/**
	 * Return id of the best ambulance able to resolve incident designated by
	 * incidentInfoId id.  It do not take ambulances in exclusionSet into
	 * account.
	 * If no ambulance is found, null is returned.
	 *
	 * @pre		incidentInfoId id of the incident
	 * 			exclusionSet set of ids of ambulances not to take into account
	 * @post	return id of the best ambulance or null if no such ambulance
	 * 			is found
	 * @throws	UnknownIncidentException if no incident with id incidentInfoId
	 * 			is found
	 */
	public String chooseBestAmbulance(String incidentInfoId,Set<String> exclusionSet)
	throws UnknownIncidentException;

}