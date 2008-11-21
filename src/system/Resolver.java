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
	 * Close the incident with id incidentInfoId.  The ambulance with ambulanceId
	 * id is mobilized on this incident.
	 *
	 * @pre		incidentInfoId a valid id
	 * 			ambulanceId a valid id
	 * @post	incident is closed and ambulance is demobilized
	 */
	public boolean closeIncident(int incidentInfoId, int ambulanceId);

}