package simulator;

/**
 * Implementation of Pos.
 * @author fred
 *
 */
public class PosImpl implements Pos{
	private int x = 0;	//the x coordinate
	private int y = 0;	// the y cordinate
	
	/**
	 * Constructor of Pos.
	 * Creates a new coordinate with coordinates x,y
	 * @param x the new x coordinate
	 * @param y the new y coordinate 
	 */
	public PosImpl(int x,int y) {
		this.x = x;
		this.y = y;
	}
	/*
	 * (non-Javadoc)
	 * @see system.Coord#getX()
	 */
	
	public PosImpl(String incidentPos) {
		this.fromString(incidentPos);
	}

	public int getX() {
		return this.x;
	}
	/*
	 * (non-Javadoc)
	 * @see system.Coord#getY()
	 */

	public int getY() {
		return this.y;
	}
	/*
	 * (non-Javadoc)
	 * @see system.Coord#setX(int)
	 */

	public void setX(int x) {
		this.x = x;
	}
	/*
	 * (non-Javadoc)
	 * @see system.Coord#setY(int)
	 */
	public void setY(int y) {
		this.y = y;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
    /**
     * Sets the coordinate from a string representation
     * 
     * @param loc String that has for prefix (x,y) 
     * 		  where x,y are the coordinates as integers in meters.
     * @throws Exception if the format is invalid. in this case the object
     * 		   is unchanged.
     **/
    private void fromString(String s) throws NumberFormatException{
        s = s.trim();
        String Sx = s.substring(s.indexOf("(")+1,s.indexOf(","));
        String Sy = s.substring(s.indexOf(",")+1,s.indexOf(")"));
        this.x = Integer.parseInt(Sx);
        this.y = Integer.parseInt(Sy);
    }
    /*
     * (non-Javadoc)
     * @see system.Coord#dist(system.Coord)
     */
    public int dist(Pos b){
        return (int)Math.sqrt(   Math.pow( this.getX() - b.getX(), 2)
                           +Math.pow(this.getY() - b.getY(), 2));
    }
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    
    @Override
	public boolean equals(Object c){
    	/* http://www.javaspecialists.co.za/archive/Issue009.html
    	 * solves issues with assertEquals in testUnits
    	 */
    	if( c instanceof Pos ){
    		return this.equals((Pos)c);
    	}else{
    		return false;
    	}
    }
    /*
     * (non-Javadoc)
     * @see system.Coord#equals(system.Coord)
     */
    public boolean equals(Pos c){
        return (c.getX() == this.getX()) && (c.getY() == this.getY());
    }
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
	public int hashCode(){
    	return this.toString().hashCode();
    }

}
