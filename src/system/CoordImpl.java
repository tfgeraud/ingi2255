package system;
public class CoordImpl implements Coord {

	private int x = 0;
	private int y = 0;
	
	public CoordImpl(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}
    public void fromString(String s) throws NumberFormatException{
        s = s.trim();
        String Sx = s.substring(s.indexOf("(")+1,s.indexOf(","));
        String Sy = s.substring(s.indexOf(",")+1,s.indexOf(")"));
        this.x = Integer.parseInt(Sx);
        this.y = Integer.parseInt(Sy);
    }
    public int dist(Coord b){
        return (int)Math.sqrt(   Math.pow( this.getX() - b.getX(), 2)
                           +Math.pow(this.getY() - b.getY(), 2));
    }
    public boolean equals(Object c){
    	/* JAVA IS A PILE OF STINKING CRAP !!!
    	 * http://www.javaspecialists.co.za/archive/Issue009.html
    	 * solves issues with assertEquals in testUnits
    	 */
    	if( c instanceof Coord ){
    		return this.equals((Coord)c);
    	}else{
    		return false;
    	}
    }
    public boolean equals(Coord c){
        return (c.getX() == this.getX()) && (c.getY() == this.getY());
    }
}
