package system;

/**
 * This interface represents coordinates on the map.
 * 
 * @author fred
 */
public interface Coord {

	/**
	 * Return x coordinate.
	 *
	 * @return	return x coordinate
	 */
	public int getX();
	
	/**
	 * Return y coordinate.
	 *
	 * @return	return y coordinate
	 */
	public int getY();
	
	/**
	 * Set x coordinate to x.
	 * 
	 * @param	x a valid x coordinate 
	 */
	public void setX(int x);
	
	/**
	 * Set y coordinate to y.
	 * 
	 * @param		y a valid y coordinate 
	 */
	public void setY(int y);

	/**
	 * Return a string representation of the coordinates.
	 * Format is the followed : (x,y)
	 * where x and y are corresponding coordinates in meters
	 * 
	 * @return	return a string representation of coordinates
	 */
	public String toString();
    /**
     * Sets the coordinate from astring representation
     * 
     * @param loc String that has for prefix (x,y) 
     * 		  where x,y are the coordinates as integers in meters.
     * @throws Exception if the format is invalid. in this case the object
     * 		   is unchanged.
     **/
    public void fromString(String loc) throws Exception;
    /**
     * returns the straight line distance between this coordinate and b.
     * @param b
     * @return a positive integer equal to the dist from b.
     */
    public int dist(Coord b);
    /**
     * returns true if the coordinates are equal.
     * @param c	the tested non-null coordinate.
     * @return true if the coordinates are equal.
     */
    public boolean equals(Coord c);
    /**
     * returns the hash of the coordinate, for use in mapImpl
     * @return the hashcode of the coordinate.
     */
    public int hashCode();
}