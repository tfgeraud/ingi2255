package system;

import java.io.BufferedReader;

/**
 * This interface represents the map of the world known by the system.
 * It knows all paths in the world.  Obstacles that can occur are also
 * taken into account.
 *                
 *        Example of instanciation : 
 *        <pre>
 *        Map = new Map();
 *        Map.setStreets(5,6);
 *        Map.addAddress("toto", new Coord(10,15));
 *        </pre>
 * @author fred
 */
public interface Map {
        /**
         * Return the distance between two coordinates.
         * 
         * @param        coord1 valid coordinates
         * @param        coord2 valid coordinates
         * @return        return distance between coord1 and coord2.
         *                         Returns Integer.MAX_VALUE if there is no path
         *                         between coordinates.
         */
        public int distance(Coord coord1, Coord coord2);

        /**
         * Return coordinates of a localisation.
         * 
         * @param        address a known localisation
         * @param        return coordinates of the localisation, null
         *                         if the location doesn't exist.
         */
        public Coord addressToCoord(String localisation);
            /**
            * Returns the address closest to the localisation
            *
            * @param loc : the concerned localisation 
            * @post returns one of the closest addresses to loc, null if
             *                 there isn't any.
             */
        public String coordToAddress(Coord loc);

        /**
         * Add an obstacle at coordinate obstacleCoord
         * 
         * @param obstacleCoord coordinates of the new obstacle. Must be valid
         * 
         */

        public void addObstacle(Coord obstacleCoord);

        /**
         * Remove an obstacle at coordinates
         * 
         * @param                obstacleCoord must be coordinates of a recorded obstacle
         */
        public void removeObstacle(Coord obstacleCoord);
    /**
     * Adds a list of addresses->localisations in the map.
     *
     * @param address must be a list of lines with one address and one coordinate
     * on each, separated by an @. the coordinate must be on a street.
     * @return returns the list of invalid address.
     */
    public String addAddressList(BufferedReader address);
    /**
     * Adds an address in the Map. 
     * @param Address a string representing the address, can be anything.
     * @param coord the coord of the address, can be anywhere.
     */
    public void addAddress(String Address, Coord coord);
    /**
     * Sets the street on the map
     * 
     * the distance between roads is 10. the roads start at coordinate 0.
     * so numx = 5 -> roads at x = [0,10,20,30,40]
     * 
     * @param: numx : > 0 :the amount of North-South streets.
     * @param: numy : > 0 :the amount of West-East streets.
     *  
     */
    public void setStreets(int numx, int numy);
}
