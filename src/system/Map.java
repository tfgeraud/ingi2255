package system;

/**
 * This interface represents the map of the world known by the system.
 * It knows all paths in the world.  Obstacles that can occur are also
 * taken into account.
 *
 * @author Simon Busard
 */
public interface Map {

	/**
	 * Return the distance between two coordinates.
	 * 
	 * @pre		coord1 valid coordinates
	 * 			coord2 valid coordinates
	 * @post	return distance between coord1 and coord2
	 */
	public int distance(Coord coord1, Coord coord2);
	
	/**
	 * Return coordinates of a localisation.
	 * 
	 * @pre		address a known localisation
	 * @post	return coordinates of the localisation
	 */
	public Coord addressToCoord(String localisation);
	
	/**
	 * Add an obstacle at coordinates
	 *
	 * @pre		obstacleCoord coordinates of the new obstacle.  Must be valid
	 * @post	a new obstacle is added at obstacleCoord
	 */
	public void addObstacle(Coord obstacleCoord);
	
	/**
	 * Remove an obstacle at coordinates
	 * 
	 * @pre		obstacleCoord must be coordinates of a recorded obstacle
	 * @post	obstacle at obstacleCoord is removed from the system
	 */
	public void removeObstacle(Coord obstacleCoord);
}