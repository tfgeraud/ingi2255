package system;

import java.io.BufferedReader;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.Set;

import common.Graph;
public class MapImpl implements Map{
	Graph map;
	private Hashtable<String,Coord> addressToCoordMap = new Hashtable();
    private Hashtable<Coord,String> coordToAddressMap= new Hashtable();
  
    public MapImpl(int streetsx, int streetsy){
    	map = new Graph(streetsx,streetsy);
    }
	public void addObstacle(Coord obstacleCoord) {
		map.addObstacle(obstacleCoord.getX(), obstacleCoord.getY());
	}

	public int distance(Coord coord1, Coord coord2) {
		return map.distance(coord1.getX(), coord1.getY(), coord2.getX(), coord2.getY());
	}

	public void removeObstacle(Coord obstacleCoord) {
		map.removeObstacle(obstacleCoord.getX(),obstacleCoord.getY());
	}

	public void setStreets(int numx, int numy) {
		// TODO Auto-generated method stub
		
	}
	public Coord addressToCoord(String localisation) {
		return addressToCoordMap.get(localisation);
	}
    public String coordToAddress(Coord loc) {
        return coordToAddressMap.get(loc);
    }
    /*public Point closestCoord(Point loc){
        // FIXME NOT IN INTERFACE BUT COULD BE USEFULL
        // returns the closest registered coordinate 
        double dist = Double.MAX_VALUE;
        Point closest = null;
        for (Point c : coordToAddressMap.keySet()){
            double tmp = Math.sqrt(   Math.pow(loc.getX()-c.getX(),2)
                                    + Math.pow(loc.getY()-c.getY(),2) );
            if(tmp < dist){
                dist = tmp;
                closest = c;
            }
        }
        return closest;
    }*/

    public String addAddressList(BufferedReader addresslist) {
        String errors = "";
        while(true){
            try{
                String line = addresslist.readLine();
                if(line != null){
                    String address = null;
                    Coord c = new CoordImpl(0,0);
                    try{
                        address = line.substring(0,line.indexOf("@") ).trim();
                        c.fromString(line.substring(line.indexOf("@")+1));
                    }catch(Exception e){
                        errors = errors + line;
                        continue;
                    }
                    addressToCoordMap.put(address, c);
                    coordToAddressMap.put(c, address);
                }else{
                    break;
                }
            }catch(Exception e){break;}
        }
        return errors;
    }

}
