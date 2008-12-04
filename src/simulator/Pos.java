package simulator;

/**
 * Provide a position in cartesian coordinates corresponding to a position on the map.
 * The position is considered immutable so no setters are provided.
 * 
 * @author Erick Lavoie
 *
 */
public class Pos {
	private int x;
	private int y;
	
	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 *  Getters and setters 
	 */
	int getX() {
		return this.x;
	}
	int getY() {
		return this.y;
	}
}
