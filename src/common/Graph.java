package common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
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
	 */
	
    private Node[][] crossroad;	// the set of nodes at the crossroads.
    private Set<Node> tempNodes = new HashSet<Node>();	// temporary nodes used for distance computation.
    private Set<Edge> street = new HashSet<Edge>();	//the set of streets 
    private int blocSize = 10;	//distance between roads.
    /**
     * Construct of graph :
     * a new Graph is returned with the number of streets separated by 10 units
     * each, from (0,0) to ((cx-1)*10,(cy-1)*10)
     * 
     * Example : 
     * <pre>
     * 	Graph g = new Graph(5,6);
     * </pre>
     * @param cx > 0 the number of streets NS
     * @param cy > 0 the number of streets WE 
     * 
     */
    public Graph(int cx,int cy){
    	this.setStreets(cx, cy);
    }
    private class Node {
        /*
         * Nodes are crossroads in the map
         * Nodes are also point of interests : Distance can only be
         * computed from node to node !
         */
        private Point pos;	//the pos of the node
        private Set<Edge> street = new HashSet<Edge>();	//the streets connected to this node
        private boolean obstacle; //true if there is an obstacle on the node
        public Node(Point c){	//creates a new node at point c.
            this.pos = c;
        }
        public boolean isOnNode(Point c){	//return true if c is on this node
            return this.pos.equals(c);
        }
        public Point getCoord(){	//returns the coordinates of this node
            return this.pos;
        }
        public void addObstacle(){	//adds an obstacle to the map
            this.obstacle = true;
        }
        public void remObstacle(){	//removes an obstacle from the map
            this.obstacle = false;
        }
        public void connect(Edge e){	//connects the node to the street.
            this.street.add(e);
        }
        public void disconnect(Edge e){	//removes an edge from the street
        	this.street.remove(e);
        }
        public void delete(){	//deletes the nodes and all the adj. edges
        	for (Edge e:this.street){
        		Node ns = e.getStart();
        		Node ne = e.getEnd();
        		if(ns != this){
        			ns.disconnect(e);
        		}else{
        			ne.disconnect(e);
        		}
        	}
        	this.street.clear();
        }
        /* returns the list of nodes reachable from this one, and
         * the distance to each.
         * obstacled nodes are also excluded
         */
        public Hashtable<Node,Integer> neighbours(){
            Hashtable<Node,Integer> t = new Hashtable<Node,Integer>();
            for (Edge e: this.street){
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
    private class Point{	//code copied from Coord. see doc there.
    		private int x,y;
    	public Point(int x, int y){
    		this.x = x;
    		this.y = y;
    	}
    	public int getX(){
    		return this.x;
    	}
    	public int getY(){
    		return this.y;
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
    	@Override
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
    private class Edge{
        /* Edges are streets in the map*/
        private Node N,M;	//start and end node of the edte
        private Set<Point> obstacle = new HashSet<Point>();	//sets of obstacles in the street.
        public Edge(Node N, Node M){	//create a new edge from N to M
            this.N = N;
            this.M = M;
            this.obstacle = new HashSet<Point>();
            N.connect(this);
            M.connect(this);
        }
        public boolean isOnEdge(Point c){	//returns true if point is on this street
            /* triangular inequality; could go wrong */
            return c.dist(this.N.getCoord())+c.dist(this.M.getCoord()) <= this.M.getCoord().dist(this.N.getCoord()) + 1;
        }
        public void addObstacle(Point c){	//adds an obstacle to the street at coords c
            this.obstacle.add(c);
        }
        public void remObstacle(Point c){	//removes the obstacle at coordinates c
            this.obstacle.remove(c);
        }
        public int getLength(){	//returns the length of the street.
            return this.N.getCoord().dist(this.M.getCoord());
        }
        public boolean obstructed(){	//return true if there is an obstacle on the road.
            return !this.obstacle.isEmpty();
        }
        public Node getStart(){	//get the starting node of the edge
            return this.N;
        }
        public Node getEnd(){	//get the end node of the edge
            return this.M;
        }
        public void disconnect(){
        	this.N.disconnect(this);
        	this.M.disconnect(this);
        }
        public boolean obstructed(Point a, Point b){
        	/* returns true if there is an obstacle on the street between the
        	 * point a and b
        	 */
            if (!this.obstructed()){
                return false;
            }else{
                for (Point c: this.obstacle){
                    if(c.dist(a) + c.dist(b) <= a.dist(b)){
                        return true;
                    }
                }
                return false;
            }
        }
    }
    /**
     * @param c a Point not null
     * @return returns a edge containing the point c, 
     * null if there isn't any.
     */
    private Edge findEdge(Point c){
        /*returns a street located at c, null if no street at c*/
        for (Edge e: this.street){
            if (e.isOnEdge(c)){
                return e;
            }
        }
        return null;    
    }
    /**
     * @param c a Point not null
     * @return returns the point located at c, null if there isn't any
     * */
    private Node findNode(Point c){
        for (Node[] N:this.crossroad){
            for (Node n:N){
                if (n.isOnNode(c)){
                    return n;
                }
            }
        }
        return null;
    }
    /**
     * connects a temporary node with the position on
     * the street connected to the nodes of the original
     * street.
     * Used to compute Dijkstra (only from node to node)
     * @param street : the street where you want to add a temporary node
     * @param c 	 : the coordinate of the temp. node
     * @return 	 a node at c connected to the ends of the streets
     * if there isn't any obstacles. 
     */
    private Node tempNode(Edge street, Point c){
        Node N = new Node(c);
        if (!street.obstructed(c, street.getStart().getCoord())){
            new Edge(N,street.getStart());
        }
        if (!street.obstructed(c,street.getEnd().getCoord())){
            new Edge(N,street.getEnd());
        }
        this.tempNodes.add(N);
        return N;
    }
    /**
     * removes all temporary nodes from the map. 
     *
     */
    private void delTempNodes(){
        /*remove a node from the map and all the streets connected to it*/
        for (Node n:this.tempNodes){
        	n.delete();
        }
        this.tempNodes.clear();
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
    	return this.distance(new Point(startx,starty),new Point(endx,endy),null);
    }
    /**
     * Computes the shortest path from 'start' to 'end'
     * 
     * @param startx : coordinate x of the starting point
     * @param starty : coordinate y of the starting point 
     * @param endx	 : coordinate x of the ending point
     * @param endy 	 : coordinate y of the ending point
     * @return returns the path from startxy to endxy as an array of {x,y} coordinates.
     * the first element is startxy the last is endxy.
     * if the destination is not reachable, it returns null.
     */
    public int[][] path(int startx,int starty,int endx,int endy){
    	List<Point> P = new ArrayList<Point>();
    	int[][] path;
    	this.distance(new Point(startx,starty),new Point(endx,endy),P);
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
     * Computes the distance from impl to incidentCoord
     * 
     * @param impl starting point must be on a street or a node.
     * @param incidentCoord end point must be on streets or on nodes.
     * @return returns the distance from impl to incidentcoord.
     * returns Integer.MAX_VALUE if unreachable 
     * if path is not null, it will contain the path as a list of points
     * from finish to start
     */
	private int distance(Point impl, Point incidentCoord, List<Point> path) {
        int distance = 0;
        Node startNode = this.findNode(impl);
        Node endNode = this.findNode(incidentCoord);
        Edge start = this.findEdge(impl);
        Edge end = this.findEdge(incidentCoord);
        this.delTempNodes();
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
            startNode = this.tempNode(start,impl);
        }
        if(endNode == null){    //pos not on a crossroad
            if(end == null){    //pos not on a street
                System.out.println("Error destination not on street");
                return Integer.MAX_VALUE;
            }
            endNode = this.tempNode(end,incidentCoord);
        }
        Set<Node> Visited = new HashSet<Node>();  //Node where we know the smallest dist
        Set<Node> Unvisited = new HashSet<Node>();//Initially All the Nodes
        Hashtable<Node,Integer> Distance = new Hashtable<Node,Integer>();
        Hashtable<Node,Node> Previous = new Hashtable<Node,Node>();
        for(Node[] N:this.crossroad){
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
	 * an obstacle is added on the point. 
	 * @param x	the x coordinate of a point on the map
	 * @param y the y coordinate of a point on the map
	 *   
	 */
	public void addObstacle(int x,int y){
		this.addObstacle(new Point(x,y));
	}
	/**
	 * an obstacle is added on the point
	 * @param c : the coordinate of the point
	 */
	private void addObstacle(Point c) {
        for (Node[] m : this.crossroad){
            for (Node n : m){
                if (n.isOnNode(c)){
                    n.addObstacle();
                    return;
                }
            }
        }
        for (Edge e : this.street){
            if (e.isOnEdge(c)){
                e.addObstacle(c);
                return;
            }
        }
	}
	/**
	 * removes all obstacle on a point. 
	 * There must be at least
	 * an existing obstacle on the point. 
	 * @param x the coordinate of a point 
	 * @param y the coordinate of a point
	 * 
	 */
	public void removeObstacle(int x, int y){
		this.removeObstacle(new Point(x,y));
	}
	/**
	 * removes the obstacle on the point
	 * @param c : the point where obstacle are removed.
	 * 			there must be at least one obstacle on the point
	 */
	private void removeObstacle(Point c) {
        for (Node[] m : this.crossroad){
            for (Node n : m){
                if(n.isOnNode(c)){
                    n.remObstacle();
                    return;
                }
            }
        }
        for (Edge e : this.street){
            if(e.isOnEdge(c)){
                e.remObstacle(c);
                return;
            }
        }
	}
	/**
	 * sets the number of streets on the map
	 * 
	 * the map is initialized with those number of streets. 
	 * distance between streets is 10. the position of the streets
	 * start at 0, and go to (numx-1)*10
	 * 
	 * @pre the map must be uninitialized.
	 * @param numx : > 0, the number of NS streets
	 * @param numy : > 0, the nomber of WE streets
	 * 
	 */
    public void setStreets(int numx, int numy) {
        int i = numx;
        int j = numy;
        this.crossroad = new Node[numx][numy];
        while(i-- > 0){
            j = numy;
            while(j-- > 0){
                this.crossroad[i][j] = new Node(new Point(i*this.blocSize,j*this.blocSize));
            }
        }
        i = numx;
        j = numy;
        while(i-- > 0){
            j = numy;
            while(j-- > 0){
                if(i + 1 < numx){
                    Edge e = new Edge(this.crossroad[i][j],this.crossroad[i+1][j]);
                    this.street.add(e);
                }
                if(j + 1 < numy){
                    Edge e = new Edge(this.crossroad[i][j],this.crossroad[i][j+1]);
                    this.street.add(e);
                }
            }
        }
    }
}
