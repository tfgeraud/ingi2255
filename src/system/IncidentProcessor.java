package system;

/**
 * This interface represents incident processor module. It is the core of
 * the incident resolution system.  It calls all other modules to achieve its
 * goal.
 * 
 * @author Simon Busard
 */
public interface IncidentProcessor {

	/**
	 * Resolve an incident.
	 * 
	 * @pre		incidentInfoId id of the incident to resolve
	 * @post	the incident is resolved
	 */
	public void main(int incidentInfoId);

}