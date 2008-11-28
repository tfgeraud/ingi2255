package system;

import java.io.BufferedReader;
import java.util.Hashtable;
import java.lang.Math.*;

public class MapImpl implements Map {

	/**
     * @author fred
     * CURRENT LIMITATIONS
     * 
     * This partial implementation of Map doesn't deal with obstacles to compute
     * the distance yet.
     * Streets are restricted to a grid of squares.
     * The obstacles can be added on the streets or the crossings. Currently
     * an obstacle on a street blocks the whole street. an obstacle on a crossing
     * obviously blocks the whole crossing.
     * Localisation of streets numbers must be entered individually.
     * the street address must be an exact match of the recorded one.
     *
     * thread synchronization unexisting.
     *
     * @TODO :
     * the street should be represented as edges and nodes internally to
     * compute the minimum distance and take obstacles into account. at
     * this point more complex maps come for free. the only thing becoming
     * harder is mapping coord to streets.
     *
     * @BRAINFUCK :
     * localisations are currently meant to be exact. this will be hard to
     * maintain, even internally. Some sort of tolerance should be built in.
     * currently you can use the streetWidth.
     *
	 */
    Hashtable<String,Coord> addressToCoordMap;
    Hashtable<Coord,String> coordToAddressMap;
    Hashtable<Coord,Integer>obstacle;   //amount of obstacles at coord
    int streetCountx = 10;
    int streetCounty = 10;
    int blocSize = 42;
    int streetWidth = 2;    /*radius of position error treshold*/
    public MapImpl(){
        addressToCoordMap = new Hashtable();
        coordToAddressMap = new Hashtable();
        obstacle = new Hashtable();
    }
    private Coord getStreetCoord(Coord c){
        /*if the c is at a crossroad, returns c.
         else returns the center of the closest "street edge"*/
        int distx = c.getX()%blocSize;
        int disty = c.getY()%blocSize;
        int x = c.getX() - distx;
        int y = c.getY() - disty;
        if(        Math.abs(blocSize - distx) < streetWidth
                && Math.abs(blocSize - disty) < streetWidth ){ /* on crossroad */
            if(blocSize - distx <= streetWidth){x += blocSize;}
            if(blocSize - disty <= streetWidth){y += blocSize;}
            return new CoordImpl(x,y);
        }else{  /*not on crossroad*/
            if( Math.abs(blocSize - distx) < streetWidth){
                if(blocSize - distx <= streetWidth){ x+= blocSize;}
                y += blocSize/2;
                return new CoordImpl(x,y);
            }else{
                if(blocSize - disty <= streetWidth){ y += blocSize;}
                x += blocSize/2;
                return new CoordImpl(x,y);
            }
        }
    }
	public int distance(Coord impl, Coord incidentCoord) {
		// TODO FIXME Implement obstacle detection;

		return Math.abs(impl.getX() - incidentCoord.getX()) +
                Math.abs(impl.getY() - incidentCoord.getY());
	}

	public void addObstacle(Coord obstacleCoord) {
        Coord c = getStreetCoord(obstacleCoord);
        if (obstacle.containsKey(c)){
            int cCount = obstacle.remove(c);
            obstacle.put(c, cCount+1);
        }else{
            obstacle.put(c, 1);
        }
	}
	public Coord addressToCoord(String localisation) {
		return addressToCoordMap.get(localisation);
	}

	public void removeObstacle(Coord obstacleCoord) {
		Coord c = getStreetCoord(obstacleCoord);
        if(obstacle.containsKey(c)){
            int cCount = obstacle.remove(c);
            obstacle.put(c, Math.max(cCount-1,0));
        }
	}

    public String coordToAddress(Coord loc) {
        return coordToAddressMap.get(loc);
    }

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

    public void setStreets(int numx, int numy, int blocSize,int streetWidth) {
        this.streetCountx = numx;
        this.streetCounty = numy;
        this.streetWidth = streetWidth/2;
        this.blocSize = blocSize;
    }
}
