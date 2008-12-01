package system;

import java.io.BufferedReader;
import java.util.Hashtable;
import java.lang.Math.*;
import java.util.HashSet;
import java.util.Set;

public class MapImpl implements Map {

	/**
     * @author fred
     * CURRENT LIMITATIONS
     * 
     * Streets are restricted to a grid of squares.
     * The obstacles can be added on the streets or the crossings. Currently
     * an obstacle on a street blocks the whole street. an obstacle on a crossing
     * obviously blocks the whole crossing.
     * Localisation of streets numbers must be entered individually.
     * the street address must be an exact match of the recorded one.
     *
     * thread synchronization unexisting.
     *
     *
     * @TODO:
     * localisations are currently meant to be exact. this will be hard to
     * maintain, even internally. Some sort of tolerance should be built in.
     * Practical example : diagonal street coordinates are seldom integers.
     *
     * Currently dijkstra is computed each time a distance is needed.
     * Could it be cheaper to compute dijkstra for every node in the graph
     * and cache the results ?
     *
     * then update when obstacles are added ?
     *
     *
	 */
    private Hashtable<String,Coord> addressToCoordMap = new Hashtable();
    private Hashtable<Coord,String> coordToAddressMap= new Hashtable();
    private Hashtable<Coord,Integer>obstacle = new Hashtable();   //amount of obstacles at coord
    private Node[][] crossroad;
    private Set<Edge> street = new HashSet();
    private int streetCountx = 10;  //count of NS streets
    private int streetCounty = 10;  //count of WE sreets
    private int blocSize = 10;
    private class Node {
        /* Nodes are crossroads in the map
         * Nodes are also point of interests : Distance can only be
         * computed from node to node !
         */
        private Coord pos;
        private Set<Edge> street;
        private boolean obstacle;
        public Node(Coord c){
            pos = c;
        }
        public boolean isOnNode(Coord c){
            return pos.equals(c);
        }
        public Coord getCoord(){
            return pos;
        }
        public void addObstacle(){
            obstacle = true;
        }
        public void remObstacle(){
            obstacle = false;
        }
        public void connect(Edge e){
            street.add(e);
        }
        /* returns the list of nodes reachable from this one, and
         * the distance to each.
         * obstacled nodes are also excluded
         */
        public Hashtable<Node,Integer> neighbours(){
            Hashtable<Node,Integer> t = new Hashtable();
            for (Edge e: street){
                if(!e.obstructed()){    //exclude unreachable nodes
                    if (e.getStart() != this){      //don't add yourself.
                        if(!e.getStart().obstacle){ //remove obstacled nodes
                            t.put(e.getStart(),e.getLength());
                        }
                    }
                    else{
                        if(!e.getEnd().obstacle){
                            t.put(e.getEnd(),e.getLength());
                        }
                    }
                }
            }
            return t;
        }
    }
    private class Edge{
        /* Edges are streets in the map*/
        private Node N,M;
        private Set<Coord> obstacle;
        public Edge(Node N, Node M){
            this.N = N;
            this.M = M;
            obstacle = new HashSet();
            N.connect(this);
            M.connect(this);
        }
        public boolean isOnEdge(Coord c){
            /* triangular inequality; could go wrong */
            return c.dist(N.getCoord())+c.dist(M.getCoord()) <= M.getCoord().dist(N.getCoord()) + 1;
        }
        public void addObstacle(Coord c){
            obstacle.add(c);
        }
        public void remObstacle(Coord c){
            obstacle.remove(c);
        }
        public int getLength(){
            return N.getCoord().dist(M.getCoord());
        }
        public boolean obstructed(){
            return !obstacle.isEmpty();
        }
        public Node getStart(){
            return N;
        }
        public Node getEnd(){
            return M;
        }
        public boolean obstructed(Coord a, Coord b){
            if (!obstructed()){
                return false;
            }else{
                for (Coord c: obstacle){
                    if(c.dist(a) + c.dist(b) <= a.dist(b)){
                        return true;
                    }
                }
                return false;
            }
        }
    }
    private Edge findEdge(Coord c){
        /*returns a street located at c*/
        for (Edge e: street){
            if (e.isOnEdge(c)){
                return e;
            }
        }
        return null;    
    }
    private Node tempNode(Edge street, Coord c){
        /*connects a temporary node with the position on
         * the street connected to the nodes of the original
         * street.
         * Used to compute Dijkstra (only from node to node)
         */
        Node N = new Node(c);
        boolean connected = false;
        if (!street.obstructed(c, street.getStart().getCoord())){
            Edge e = new Edge(N,street.getStart());
        }
        if (!street.obstructed(c,street.getEnd().getCoord())){
            Edge e = new Edge(N,street.getEnd());
        }
        return N;
    }
    private void delTempNode(Node N){
        /*remove a node from the map and all the streets connected to it*/
        for(Edge e:N.street){
            e.getEnd().street.remove(e);
            e.getStart().street.remove(e);
        }
    }
    /*  returns Integer.MAX_VALUE if unreachable */
	public int distance(Coord impl, Coord incidentCoord) {
        int distance = 0;
        Edge start = findEdge(impl);
        Edge end = findEdge(incidentCoord);
        if (start == end){  /*if ambulance is on same street as incident */
            if(!start.obstructed(impl, incidentCoord)){ /*check no obstacle in between*/
                return impl.dist(incidentCoord);
            }
        }
        /* creating a temporary node in the graph corresponding to the localisation
         * of the ambulance and of the destination
         */
        Node startNode = tempNode(start,impl);
        Node endNode = tempNode(start,incidentCoord);

        Set<Node> Visited = new HashSet();  //Node where we know the smallest dist
        Set<Node> Unvisited = new HashSet();//Initially All the Nodes
        Hashtable<Node,Integer> Distance = new Hashtable();

        for(Node[] N:crossroad){
            for(Node n:N){
                Unvisited.add(n);
            }
        }
        Unvisited.add(startNode);
        Unvisited.add(endNode);

        for(Node n:Unvisited){  // All nodes are infinitely distant for now
             Distance.put(n, Integer.MAX_VALUE);
        }
        Distance.put(startNode, 0);     //startnode is the where we are

        Node CurrentNode = startNode;

        while(!Unvisited.isEmpty()){    //Dijkstra
            Visited.add(CurrentNode);
            Unvisited.remove(CurrentNode);

            Hashtable<Node,Integer> NeighbourDist = CurrentNode.neighbours();
            for(Node n: NeighbourDist.keySet() ){
                int dist = Distance.get(n);
                int newdist = Distance.get(CurrentNode)+NeighbourDist.get(n);
                if (newdist < dist){
                    Distance.put(n,newdist);
                }
            }
            
            int minDist = Integer.MAX_VALUE;
            Node closest = null;    //we are looking for the closest note to current

            for(Node n:Unvisited){  //TODO use a sorted queue
                if(Distance.get(n) < minDist){
                    minDist = Distance.get(n);
                    closest = n;
                }
            }
            if(closest == endNode){
                break;
            }
            CurrentNode = closest;
        }
        delTempNode(endNode);   //disconnecting temporary nodes from the map
        delTempNode(startNode);
        return distance;
	}

	public void addObstacle(Coord c) {
        for (Node[] m : crossroad){
            for (Node n : m){
                if (n.isOnNode(c)){
                    n.addObstacle();
                    return;
                }
            }
        }
        for (Edge e : street){
            if (e.isOnEdge(c)){
                e.addObstacle(c);
                return;
            }
        }
	}

	public void removeObstacle(Coord c) {
        for (Node[] m : crossroad){
            for (Node n : m){
                if(n.isOnNode(c)){
                    n.remObstacle();
                    return;
                }
            }
        }
        for (Edge e : street){
            if(e.isOnEdge(c)){
                e.remObstacle(c);
                return;
            }
        }
	}
	public Coord addressToCoord(String localisation) {
		return addressToCoordMap.get(localisation);
	}
    public String coordToAddress(Coord loc) {
        return coordToAddressMap.get(loc);
    }
    public Coord closestCoord(Coord loc){
        /* FIXME NOT IN INTERFACE BUT COULD BE USEFULL
         * returns the closest registered coordinate */
        double dist = Double.MAX_VALUE;
        Coord closest = null;
        for (Coord c : coordToAddressMap.keySet()){
            double tmp = Math.sqrt(   Math.pow(loc.getX()-c.getX(),2)
                                    + Math.pow(loc.getY()-c.getY(),2) );
            if(tmp < dist){
                dist = tmp;
                closest = c;
            }
        }
        return closest;
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

    public void setStreets(int numx, int numy) {
        this.streetCountx = numx;
        this.streetCounty = numy;
        int i = numx;
        int j = numy;
        crossroad = new Node[numx][numy];
        while(i-- >= 0){
            while(j-- >= 0){
                crossroad[i][j] = new Node(new CoordImpl(i*blocSize,j*blocSize));
            }
        }
        i = numx;
        j = numy;
        while(i-- >= 0){
            while(j-- >= 0){
                if(i + 1 < numx){
                    Edge e = new Edge(crossroad[i][j],crossroad[i+1][j]);
                    street.add(e);
                }
                if(j + 1 < numy){
                    Edge e = new Edge(crossroad[i][j],crossroad[i+1][j]);
                    street.add(e);
                }
            }
        }
    }
}
