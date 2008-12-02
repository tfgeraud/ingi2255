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
	 * @post	return x coordinate in meters
	 */
	public int getX();
	
	/**
	 * Return y coordinate.
	 *
	 * @pre		-
	 * @post	return y coordinate in meters
	 */
	public int getY();
	
	/**
	 * Set x coordinate to x.
	 * 
	 * @pre		x a valid x coordinate in meters
	 * @post	x coordinate is set to x
	 */
	public void setX(int x);
	
	/**
	 * Set y coordinate to y.
	 * 
	 * @pre		y a valid y coordinate in meters
	 * @post	y coordinate is set to y
	 */
	public void setY(int y);

	/**
	 * Return a string representation of the coordinates.
	 * Format is the followed : (x,y)
	 * where x and y are corresponding coordinates in meters
	 * 
	 * @pre 	-
	 * @post	return a string representation of coordinates
	 */
	public String toString();
    /**
     * Sets the coordinate from astring representation
     * @pre loc has for prefix (x,y) where x,y are the coordinates as integers in meters.
     * @post the coord fits the data in loc if the format and localisation
     * are valid, else it is unchanged.
     **/
    public void fromString(String loc) throws Exception;
    /**
     * returns the straight line distance between this coordinate and b.
     * @param b
     * @return a positive integer equal to the dist from b.
     */
    public int dist(Coord b);
    public boolean equals(Coord c);
}