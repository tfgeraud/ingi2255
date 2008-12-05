package simulator;


/**
 * Provide a position in cartesian coordinates corresponding to a position on the map.
 * The position is considered immutable so no setters are provided.
 * 
 * @author Frederic Van der Essen
 *
 */

public interface Pos {
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
	 * Return a string representation of the coordinates.
	 * Format is the followed : (x,y)
	 * where x and y are corresponding coordinates in meters
	 * 
	 * @return	return a string representation of coordinates
	 */
	public String toString();

    /**
     * returns the straight line distance between this coordinate and b.
     * @param b
     * @return a positive integer equal to the dist from b.
     */
    public int dist(Pos b);
    /**
     * returns true if the coordinates are equal.
     * @param c	the tested non-null coordinate.
     * @return true if the coordinates are equal.
     */
    public boolean equals(Pos c);
    /**
     * returns the hash of the coordinate, for use in mapImpl
     * @return the hashcode of the coordinate.
     */
    public int hashCode();

}
