package system;

import java.io.BufferedReader;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.Set;

import common.Graph;
public class MapImpl implements Map{
	Graph map;	// implémentation de la map, see Map.
	// permet de récupérer la coordonnée d'une addresse.
	private Hashtable<String,Coord> addressToCoordMap = new Hashtable();
	// permet de récupérer l'addrese d'une coordonnée.
    private Hashtable<Coord,String> coordToAddressMap= new Hashtable();
    /**
     * Renvoie une instance de Map
     * @pre : 	streetsx,streetsy > 0
     * @post : 	renvoie une Map avec streetsx rues verticales, et
     * 		 	streety rues horizontales, toutes espacées de 10 unités. 
     * 			la position des rues commence à 0 et termine a (streetsx|y-1)*10
     * Exemple d'instanciation :
     * MapImpl m = new MapImpl(5,6);
     */
    
    public MapImpl(int streetsx, int streetsy){
    	map = new Graph(streetsx,streetsy);
    }
    /*
     * (non-Javadoc)
     * @see system.Map#addObstacle(system.Coord)
     */
	public void addObstacle(Coord obstacleCoord) {
		map.addObstacle(obstacleCoord.getX(), obstacleCoord.getY());
	}
	/*
	 * (non-Javadoc)
	 * @see system.Map#distance(system.Coord, system.Coord)
	 */
	public int distance(Coord coord1, Coord coord2) {
		return map.distance(coord1.getX(), coord1.getY(), coord2.getX(), coord2.getY());
	}
	/*
	 * (non-Javadoc)
	 * @see system.Map#removeObstacle(system.Coord)
	 */
	public void removeObstacle(Coord obstacleCoord) {
		map.removeObstacle(obstacleCoord.getX(),obstacleCoord.getY());
	}
	/*
	 * (non-Javadoc)
	 * @see system.Map#setStreets(int, int)
	 */
	public void setStreets(int numx, int numy) {
		map.setStreets(numx, numy);
		
	}
	/*
	 * (non-Javadoc)
	 * @see system.Map#addressToCoord(java.lang.String)
	 */
	public Coord addressToCoord(String localisation) {
		return addressToCoordMap.get(localisation);
	}
	/*
	 * (non-Javadoc)
	 * @see system.Map#coordToAddress(system.Coord)
	 */
    public String coordToAddress(Coord loc) {
    	int min_dist = Integer.MAX_VALUE;
    	Coord min_coord = null;
    	for(Coord c: coordToAddressMap.keySet()){
    		if(c.dist(loc)<min_dist){
    			min_dist = c.dist(loc);
    			min_coord = c;
    		}
    	}
    	if(min_coord == null){
    		return null;
    	}else{
    		return coordToAddressMap.get(min_coord);
    	}
    }
    /*
     * (non-Javadoc)
     * @see system.Map#addAddressList(java.io.BufferedReader)
     */
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
    /*
     * (non-Javadoc)
     * @see system.Map#addAddress(java.lang.String, system.Coord)
     */
	public void addAddress(String address, Coord coord) {
		addressToCoordMap.put(address, coord);
		coordToAddressMap.put(coord, address);
		
	}

}
