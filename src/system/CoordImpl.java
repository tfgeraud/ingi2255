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
}
