package system;

import java.util.LinkedList;

/**
 * The Ambulance interface represents the set of all ambulances known
 * by the system.
 * 
 * @author Simon Busard
 */
public interface Ambulance {
	
	/**
	 * Add an ambulance in the system.
	 * 
	 * @pre		ambulanceId the id of the new ambulance
	 * 			coord the new ambulance coordinates
	 * 			kind the kind of the new ambulance (normal or medicalized)
	 * 			status the ambulance status (working, broken)
	 * @post	a new ambulance is stored in the system
	 */
	public void addAmbulance(int ambulanceId, Coord coord, int kind, int status);

	/**
	 * Return all free ambulance of kind that are not listed in exclusionSet
	 *
	 * @pre		kind the ambulance kind needed (normal or medicalized)
	 * 			exclusionSet a list of ambulance ids that dont have to be
	 * 				taken in account
	 * @post	return a list of ids of free ambulances
	 */
	public LinkedList getAllFree(int kind, LinkedList exclusionSet);

	/**
	 * Return coordinates of ambulance identified by ambulanceId.
	 * 
	 * @pre		ambulanceId a valid id
	 * @post	return coordinates of concerned ambulance
	 */
	public Coord getCoord(int ambulanceId);

	/**
	 * Mark ambulance as broken.
	 * 
	 * @pre		ambulanceInfoId a valid id
	 * @post	ambulance with ambulanceInfoId id is set as broken
	 */
	public void markAsBroken(int ambulanceInfoId);
	
	/**
	 * Mark ambulance as repaired.
	 * 
	 * @pre		ambulanceInfoId a valid id
	 * @post	ambulance with ambulanceInfoId id is set as repaired
	 */
	public void markAsRepaired(int ambulanceInfoId);
	
	/**
	 * Return coordinates of an ambulance.
	 * 
	 * @pre		ambulanceInfoId id of the ambulance
	 * @post	return coordinates of the ambulance
	 */
	public Coord getPosition(int ambulanceInfoId);
	
	/**
	 * Return kind of an ambulance.
	 * 
	 * @pre		ambulanceInfoId id of the ambulance
	 * @post	return kind of the ambulance
	 */
	public int getKind(int ambulanceInfoId);
	
	/**
	 * Return id of the incident for which the ambulance has been chosen.
	 * 
	 * @pre		ambulanceInfoId id of the ambulance
	 * @post	return coordinates of the ambulance
	 */
	public int getIncidentChosenFor(int ambulanceInfoId);
	
	public int getIncidentMobilizedFor(int ambulanceInfoId);
	
	public void setPosition(int ambulanceInfoId, Coord coord);
	
	public void setIncidentChosenFor(int ambulanceInfoId, int incidentInfoId);
	
	public void setIncidentMobilizedFor(int ambulanceInfoId, int incidentInfoId);
}