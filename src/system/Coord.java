package system;

/**
 * This interface represents coordinates on the map.
 * 
 * @author Simon Busard
 */
public interface Coord {

	/**
	 * Return x coordinate.
	 *
	 * @pre		-
	 * @post	return x coordinate
	 */
	public int getX();
	
	/**
	 * Return y coordinate.
	 *
	 * @pre		-
	 * @post	return y coordinate
	 */
	public int getY();
	
	/**
	 * Set x coordinate to x.
	 * 
	 * @pre		x a valid x coordinate
	 * @post	x coordinate is set to x
	 */
	public void setX(int x);
	
	/**
	 * Set y coordinate to y.
	 * 
	 * @pre		y a valid y coordinate
	 * @post	y coordinate is set to y
	 */
	public void setY(int y);

	/**
	 * Return a string representation of the coordinates.
	 * Format is the followed : (x,y)
	 * where x and y are corresponding coordinates
	 * 
	 * @pre 	-
	 * @post	return a string representation of coordinates
	 */
	public String toString();
}