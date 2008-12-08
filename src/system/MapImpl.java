package system;

import java.io.BufferedReader;
import java.util.Hashtable;

import common.Graph;

/**
 * This class represents the map of the real world known by the system.
 * 
 * @author Frédéric van der Essn <frederic.vanderessen@student.uclouvain.be>
 * @author Simon Busard <simon.busard@student.uclouvain.be>
 */
public class MapImpl implements Map {
	Graph map; // implémentation de la map, see Map.

	// permet de récupérer la coordonnée d'une addresse.
	private Hashtable<String, Coord> addressToCoordMap = new Hashtable<String, Coord>();

	// permet de récupérer l'addrese d'une coordonnée.
	private Hashtable<Coord, String> coordToAddressMap = new Hashtable<Coord, String>();

	/**
	 * Renvoie une instance de Map
	 * 
	 * @pre : streetsx,streetsy > 0
	 * @post : renvoie une Map avec streetsx rues verticales, et streety rues
	 *       horizontales, toutes espacées de 10 unités. la position des rues
	 *       commence à 0 et termine a (streetsx|y-1)*10 Exemple d'instanciation :
	 *       MapImpl m = new MapImpl(5,6);
	 */

	public MapImpl(int streetsx, int streetsy) {
		this.map = new Graph(streetsx, streetsy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Map#addObstacle(system.Coord)
	 */
	synchronized public void addObstacle(Coord obstacleCoord) {
		this.map.addObstacle(obstacleCoord.getX(), obstacleCoord.getY());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Map#distance(system.Coord, system.Coord)
	 */
	public int distance(Coord coord1, Coord coord2) {
		return this.map.distance(coord1.getX(), coord1.getY(), coord2.getX(),
				coord2.getY());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Map#removeObstacle(system.Coord)
	 */
	synchronized public void removeObstacle(Coord obstacleCoord) {
		this.map.removeObstacle(obstacleCoord.getX(), obstacleCoord.getY());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Map#setStreets(int, int)
	 */
	synchronized public void setStreets(int numx, int numy) {
		this.map.setStreets(numx, numy);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Map#addressToCoord(java.lang.String)
	 */
	public Coord addressToCoord(String localisation) {
		return this.addressToCoordMap.get(localisation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Map#coordToAddress(system.Coord)
	 */
	public String coordToAddress(Coord loc) {
		int min_dist = Integer.MAX_VALUE;
		Coord min_coord = null;
		for (Coord c : this.coordToAddressMap.keySet()) {
			if (c.dist(loc) < min_dist) {
				min_dist = c.dist(loc);
				min_coord = c;
			}
		}
		if (min_coord == null) {
			return null;
		} else {
			return this.coordToAddressMap.get(min_coord);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Map#addAddressList(java.io.BufferedReader)
	 */
	synchronized public String addAddressList(BufferedReader addresslist) {
		String errors = "";
		while (true) {
			try {
				String line = addresslist.readLine();
				if (line != null) {
					String address = null;
					Coord c = new CoordImpl(0, 0);
					try {
						address = line.substring(0, line.indexOf("@")).trim();
						c.fromString(line.substring(line.indexOf("@") + 1));
					} catch (Exception e) {
						errors = errors + line;
						continue;
					}
					this.addressToCoordMap.put(address, c);
					this.coordToAddressMap.put(c, address);
				} else {
					break;
				}
			} catch (Exception e) {
				break;
			}
		}
		return errors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Map#addAddress(java.lang.String, system.Coord)
	 */
	synchronized public void addAddress(String address, Coord coord) {
		this.addressToCoordMap.put(address, coord);
		this.coordToAddressMap.put(coord, address);

	}

}
