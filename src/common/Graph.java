package common;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {

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
    private Node[][] crossroad;
    private Set<Node> tempNodes = new HashSet();
    private Set<Edge> street = new HashSet();
    private int blocSize = 10;
    /**
     * @pre cx,cy are > 0. they are the number of streets NS,WE respectively.
     * @post a new Graph is returned with the number of streets separated by 10 units
     * each, from (0,0) to ((cx-1)*10,(cy-1)*10)
     * Example of Instanciation : 
     * Graph g = new Graph(5,6);
     */
    public Graph(int cx,int cy){
    	this.setStreets(cx, cy);
    }
    public class Node {
        /* Nodes are crossroads in the map
         * Nodes are also point of interests : Distance can only be
         * computed from node to node !
         */
        private Point pos;	//the pos of the node
        private Set<Edge> street = new HashSet();	//the streets connected to this node
        private boolean obstacle; //true if there is an obstacle on the node
        public Node(Point c){	//creates a new node at point c.
            pos = c;
        }
        public boolean isOnNode(Point c){	//return true if c is on this node
            return pos.equals(c);
        }
        public Point getCoord(){	//returns the coordinates of this node
            return pos;
        }
        public void addObstacle(){	//adds an obstacle to the map
            obstacle = true;
        }
        public void remObstacle(){	//removes an obstacle from the map
            obstacle = false;
        }
        public void connect(Edge e){	//connects the node to the street.
            street.add(e);
        }
        public void disconnect(Edge e){	//removes an edge from the street
        	street.remove(e);
        }
        public void delete(){	//deletes the nodes and all the adj. edges
        	for (Edge e:street){
        		Node ns = e.getStart();
        		Node ne = e.getEnd();
        		if(ns != this){
        			ns.disconnect(e);
        		}else{
        			ne.disconnect(e);
        		}
        	}
        	street.clear();
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
    public class Point{	//code copied from Coord. see doc there.
    		private int x,y;
    	public Point(int x, int y){
    		this.x = x;
    		this.y = y;
    	}
    	public int getX(){
    		return x;
    	}
    	public int getY(){
    		return y;
    	}
    	public void setX(int x){
    		this.x = x;
    	}
    	public void setY(int y){
    		this.y = y;
    	}
    	public int dist(Point c){
    		return (int)Math.sqrt(	Math.pow(this.getX() - c.getX(),2) +
    								Math.pow(this.getY() - c.getY(),2) 	);
    	}
    	public boolean equals(Object c){
    		if (c instanceof Point){
    			return this.equals((Point)c);
    		}else{
    			return false;
    		}
    	}
    	public boolean equals(Point c){
    		return (this.getX() == c.getX()) && (this.getY() == c.getY());
    	}
    	public String tostring(){
    		return "("+this.x+","+this.y+")";
    	}
    }
    public class Edge{
        /* Edges are streets in the map*/
        private Node N,M;	//start and end node of the edte
        private Set<Point> obstacle = new HashSet();	//sets of obstacles in the street.
        public Edge(Node N, Node M){	//create a new edge from N to M
            this.N = N;
            this.M = M;
            obstacle = new HashSet();
            N.connect(this);
            M.connect(this);
        }
        public boolean isOnEdge(Point c){	//returns true if point is on this street
            /* triangular inequality; could go wrong */
            return c.dist(N.getCoord())+c.dist(M.getCoord()) <= M.getCoord().dist(N.getCoord()) + 1;
        }
        public void addObstacle(Point c){	//adds an obstacle to the street at coords c
            obstacle.add(c);
        }
        public void remObstacle(Point c){	//removes the obstacle at coordinates c
            obstacle.remove(c);
        }
        public int getLength(){	//returns the length of the street.
            return N.getCoord().dist(M.getCoord());
        }
        public boolean obstructed(){	//return true if there is an obstacle on the road.
            return !obstacle.isEmpty();
        }
        public Node getStart(){	//get the starting node of the edge
            return N;
        }
        public Node getEnd(){	//get the end node of the edge
            return M;
        }
        public void disconnect(){
        	N.disconnect(this);
        	M.disconnect(this);
        }
        public boolean obstructed(Point a, Point b){
        	/* returns true if there is an obstacle on the street between the
        	 * point a and b
        	 */
            if (!obstructed()){
                return false;
            }else{
                for (Point c: obstacle){
                    if(c.dist(a) + c.dist(b) <= a.dist(b)){
                        return true;
                    }
                }
                return false;
            }
        }
    }
    public Edge findEdge(Point c){
        /*returns a street located at c*/
        for (Edge e: street){
            if (e.isOnEdge(c)){
                return e;
            }
        }
        return null;    
    }
    /*returns the point located at c*/
    public Node findNode(Point c){
        for (Node[] N:crossroad){
            for (Node n:N){
                if (n.isOnNode(c)){
                    return n;
                }
            }
        }
        return null;
    }
    private Node tempNode(Edge street, Point c){
        /*connects a temporary node with the position on
         * the street connected to the nodes of the original
         * street.
         * Used to compute Dijkstra (only from node to node)
         */
        Node N = new Node(c);
       // boolean connected = false;
        if (!street.obstructed(c, street.getStart().getCoord())){
            Edge e = new Edge(N,street.getStart());
        }
        if (!street.obstructed(c,street.getEnd().getCoord())){
            Edge e = new Edge(N,street.getEnd());
        }
        tempNodes.add(N);
        return N;
    }
    private void delTempNodes(){
        /*remove a node from the map and all the streets connected to it*/
        for (Node n:tempNodes){
        	n.delete();
        }
        tempNodes.clear();
    }
    /**
     * 
     * @param startx	x coordinate of the start point
     * @param starty	y coordinate of the start point
     * @param endx		x coordinate of the end point
     * @param endy		y coordinate of the end point
     * @return	returns the distance from start to end, according
     * to the map. Returns Integer.MAX_VALUE if unreachable.
     * 
     */
    
    public int distance(int startx, int starty, int endx, int endy){
    	return distance(new Point(startx,starty),new Point(endx,endy),null);
    }
    /**
     * @pre : startx, starty and endx,endy represents coordinates of the starting
     * point and ending point on the map.
     * @post: returns the path from startxy to endxy as an array of {x,y} coordinates.
     * the first element is startxy the last is endxy.
     * if the destination is not reachable, it returns null.
     */
    public int[][] path(int startx,int starty,int endx,int endy){
    	List<Point> P = new ArrayList();
    	int[][] path;
    	distance(new Point(startx,starty),new Point(endx,endy),P);
    	if(P.isEmpty()){
    		return null;
    	}else{
    		path = new int[P.size()][2];
    		int i = 0;
    		while(i < P.size()){
    			int I = P.size()-1-i;
    			path[i][0] = P.get(I).getX();
    			path[i][1] = P.get(I).getY();
    			i++;
    		}
    		return path;
    	}
    }
    /**
     * @pre impl and incidentCoord must be on streets or on nodes.
     * @post returns the distance from impl to incidentcoord.
     * returns Integer.MAX_VALUE if unreachable 
     * if path is not null, it will contain the path as a list of points
     * from finish to start
     */
	private int distance(Point impl, Point incidentCoord, List<Point> path) {
        int distance = 0;
        Node startNode = findNode(impl);
        Node endNode = findNode(incidentCoord);
        Edge start = findEdge(impl);
        Edge end = findEdge(incidentCoord);
        delTempNodes();
        if (impl.equals(incidentCoord)){
            return 0;
        }
        if (startNode != null && endNode != null){
            start = end = null;
        }
        if (start != null && start == end){  /*if ambulance is on same street as incident */
            if(!start.obstructed(impl, incidentCoord)){ /*check no obstacle in between*/
                return impl.dist(incidentCoord);
            }
        }

        /* creating a temporary node in the graph corresponding to the localisation
         * of the ambulance and of the destination, if necessary
         */
        if(startNode == null){  //pos not on a crossroad
            if(start == null){      //pos not on a street
                System.out.println("Error ambulance not on street");
                return Integer.MAX_VALUE;
            }
            startNode = tempNode(start,impl);
        }
        if(endNode == null){    //pos not on a crossroad
            if(end == null){    //pos not on a street
                System.out.println("Error destination not on street");
                return Integer.MAX_VALUE;
            }
            endNode = tempNode(end,incidentCoord);
        }
        Set<Node> Visited = new HashSet();  //Node where we know the smallest dist
        Set<Node> Unvisited = new HashSet();//Initially All the Nodes
        Hashtable<Node,Integer> Distance = new Hashtable();
        Hashtable<Node,Node> Previous = new Hashtable();

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
            //System.out.println("Current Node : ("+CurrentNode.getCoord().getX()+","+CurrentNode.getCoord().getY()+") dist : "+Distance.get(CurrentNode));

            Hashtable<Node,Integer> NeighbourDist = CurrentNode.neighbours();
            for(Node n: NeighbourDist.keySet() ){
            	if(n!=null){
	            	if(Distance.get(n) == null){ //prevents crash when graph gets corrupted
	            		continue;
            		}
            		int dist = Distance.get(n);
                	int newdist = Distance.get(CurrentNode)+NeighbourDist.get(n);
                	if (newdist < dist){
                    	Distance.put(n,newdist);
                    	Previous.put(n, CurrentNode);
                	}
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
                distance = Distance.get(closest);
                break;
            }
            CurrentNode = closest;
            if(CurrentNode == null){	//destination unreachable
                return Integer.MAX_VALUE;
            }
        }
        
        /*creating path */
        if(path != null){
        	path.add(endNode.getCoord());
        	Node n = endNode;
        	while(Previous.get(n)!=null){
        		n = Previous.get(n);
        		path.add(n.getCoord());
        	}
        }
        return distance;
	}
	/**
	 * @pre x,y are the coordinates of a point on the map
	 * @post an obstacle is added on that point.  
	 */
	public void addObstacle(int x,int y){
		this.addObstacle(new Point(x,y));
	}
	private void addObstacle(Point c) {
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
	/**
	 * @pre x,y are the coordinates of a point on the map where there
	 * is at least an obstacle.
	 * @post removes all the obstacles on that point.
	 */
	public void removeObstacle(int x, int y){
		removeObstacle(new Point(x,y));
	}
	private void removeObstacle(Point c) {
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
	/**
	 * @pre the map must be uninitialized.
	 * @param numx : the number of NS streets
	 * @param numy : the nomber of WE streets
	 * @post the map is initialized with those number of streets. 
	 */
    public void setStreets(int numx, int numy) {
        //this.streetCountx = numx;
        //this.streetCounty = numy;
        int i = numx;
        int j = numy;
        crossroad = new Node[numx][numy];
        while(i-- > 0){
            j = numy;
            while(j-- > 0){
                crossroad[i][j] = new Node(new Point(i*blocSize,j*blocSize));
            }
        }
        i = numx;
        j = numy;
        while(i-- > 0){
            j = numy;
            while(j-- > 0){
                if(i + 1 < numx){
                    Edge e = new Edge(crossroad[i][j],crossroad[i+1][j]);
                    street.add(e);
                }
                if(j + 1 < numy){
                    Edge e = new Edge(crossroad[i][j],crossroad[i][j+1]);
                    street.add(e);
                }
            }
        }
    }
    public static void main(String[] args){
    	//Graph map = new Graph(10,10);
    	//Point p = new map.Point(10,10);
    }
}
