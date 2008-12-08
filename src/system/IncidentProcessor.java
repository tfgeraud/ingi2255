package system;

/**
 * This interface represents incident processor module. It is the core of the
 * incident resolution system. It calls all other modules to achieve its goal.
 * 
 * @author Simon Busard <simon.busard@student.uclouvain.be>
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public interface IncidentProcessor extends Runnable {

	/**
	 * Resolve an incident.
	 * 
	 * @pre incidentInfoId is the unique identifier of the incident to resolve
	 * @post the incident is resolved
	 * 
	 * @param incidentInfoId
	 *            The incident unique identifier to resolve
	 */
	public void main(String incidentInfoId);

}