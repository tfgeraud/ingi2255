package simulator;

/**
 * Provide a way to store the obstacles in the town and encapsulate the path finding every ambulance
 * use will moving
 * 
 * @author Erick Lavoie
 *
 */
public interface Map {
	/**
	 * Return the next position on the shortest path between currentPos and nextPos
	 * @Pre currentPos and nextPos are valid
	 * @Post return null if no path exist between the positions, the next position otherwise
	 */
	public Pos nextPos(Pos currentPos, Pos nextPos);
}
