package system;

/**
 * This interface represents resolver module that is able to close an incident.
 * Closing an incident means waiting its resolution (sent by ambulance crew),
 * demobilize ambulance and modify appropriate informations about the incident.
 * 
 * @author Simon Busard
 */
public interface Resolver {

	/**
	 * Close the incident with id incidentInfoId. It wait for incident resolved
	 * event or problem event
	 * 
	 * @pre incidentInfoId a valid id
	 * @post return true if incident is announced as resolved by the ambulance.
	 *       false otherwise
	 */
	public boolean closeIncident(String incidentInfoId);

}