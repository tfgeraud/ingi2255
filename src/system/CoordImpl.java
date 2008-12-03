package system;
import java.lang.Math.*;
public class CoordImpl implements Coord {
	private int x = 0;	//the x coordinate
	private int y = 0;	// the y cordinate
	
	public CoordImpl(int x,int y) {
		this.x = x;
		this.y = y;
	}
	/*
	 * (non-Javadoc)
	 * @see system.Coord#getX()
	 */
	
	public int getX() {
		return x;
	}
	/*
	 * (non-Javadoc)
	 * @see system.Coord#getY()
	 */

	public int getY() {
		return y;
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
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	/*
	 * (non-Javadoc)
	 * @see system.Coord#fromString(java.lang.String)
	 */
    public void fromString(String s) throws NumberFormatException{
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
    public int dist(Coord b){
        return (int)Math.sqrt(   Math.pow( this.getX() - b.getX(), 2)
                           +Math.pow(this.getY() - b.getY(), 2));
    }
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    
    public boolean equals(Object c){
    	/* http://www.javaspecialists.co.za/archive/Issue009.html
    	 * solves issues with assertEquals in testUnits
    	 */
    	if( c instanceof Coord ){
    		return this.equals((Coord)c);
    	}else{
    		return false;
    	}
    }
    /*
     * (non-Javadoc)
     * @see system.Coord#equals(system.Coord)
     */
    public boolean equals(Coord c){
        return (c.getX() == this.getX()) && (c.getY() == this.getY());
    }
}
