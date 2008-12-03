package system;

import java.io.BufferedReader;
import java.util.Hashtable;

import common.Graph;



public class MapImpl implements Map{
	Graph map;	// implémentation de la map, see Map.
	// permet de récupérer la coordonnée d'une addresse.
	private Hashtable<String,Coord> addressToCoordMap = new Hashtable();
	// permet de récupérer l'addrese d'une coordonnée.
    private Hashtable<Coord,String> coordToAddressMap= new Hashtable();
    /**
     * Renvoie une instance de Map
     * @pre : streetsx,streetsy > 0
     * @post : renvoie une Map avec streetsx rues verticales, et
     * 			streety rues horizontales, toutes espacées de 10 unités. 
     */
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
		map.setStreets(numx, numy);
		
	}
	public Coord addressToCoord(String localisation) {
		return addressToCoordMap.get(localisation);
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

}
