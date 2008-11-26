package system;

/**
 * This interface represents the mobilizer module.  It is able to send an order
 * of mobilization or demobilization to an ambulance.
 * 
 * @author Simon Busard
 */
public interface Mobilizer {

	/**
	 * Mobilize ambulance with ambulanceId id on incident with incidentInfoId id
	 * 
	 * @pre		incidentInfoId a valid id
	 * 			ambulanceId a valid id
	 * @post	ambulance is mobilized on incident 
	 */
	public boolean mobilize(int incidentInfoId, int ambulanceId);

	/**
	 * Dembilize ambulance with ambulanceId id 
	 * from incident with incidentInfoId id
	 * 
	 * @pre		incidentInfoId a valid id
	 * 			ambulanceId a valid id
	 * @post	ambulance is demobilized from incident 
	 */
	public boolean demobilize(int incidentInfoId, int ambulanceId);

}