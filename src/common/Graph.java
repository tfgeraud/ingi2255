package common;

import java.util.Hashtable;
import java.util.HashSet;
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
    private Set<Edge> street = new HashSet();
    private int blocSize = 10;
    
    public Graph(int cx,int cy){
    	this.setStreets(cx, cy);
    }
    public class Node {
        /* Nodes are crossroads in the map
         * Nodes are also point of interests : Distance can only be
         * computed from node to node !
         */
        private Point pos;
        private Set<Edge> street = new HashSet();
        private boolean obstacle;
        public Node(Point c){
            pos = c;
        }
        public boolean isOnNode(Point c){
            return pos.equals(c);
        }
        public Point getCoord(){
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
    private class Point{
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
    		return (int)Math.sqrt(	Math.pow(this.getX() + c.getX(),2) +
    								Math.pow(this.getX() + c.getX(),2) 	);
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
    }
    public class Edge{
        /* Edges are streets in the map*/
        private Node N,M;
        private Set<Point> obstacle = new HashSet();
        public Edge(Node N, Node M){
            this.N = N;
            this.M = M;
            obstacle = new HashSet();
            N.connect(this);
            M.connect(this);
        }
        public boolean isOnEdge(Point c){
            /* triangular inequality; could go wrong */
            return c.dist(N.getCoord())+c.dist(M.getCoord()) <= M.getCoord().dist(N.getCoord()) + 1;
        }
        public void addObstacle(Point c){
            obstacle.add(c);
        }
        public void remObstacle(Point c){
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
        public boolean obstructed(Point a, Point b){
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
            if(e.getEnd()!=N){
                e.getEnd().street.remove(e);
            }else{
                e.getStart().street.remove(e);
            }
        }
    }
    /*  returns Integer.MAX_VALUE if unreachable */
    public int distance(int startx, int starty, int endx, int endy){
    	return distance(new Point(startx,starty),new Point(endx,endy));
    }
	public int distance(Point impl, Point incidentCoord) {
        int distance = 0;
        Node startNode = findNode(impl);
        Node endNode = findNode(incidentCoord);
        boolean isStartNodeTemp = false;
        boolean isEndNodeTemp = false;
        Edge start = findEdge(impl);
        Edge end = findEdge(incidentCoord);
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
                return Integer.MAX_VALUE;
            }
            startNode = tempNode(start,impl);
            isStartNodeTemp = true;
        }
        if(endNode == null){    //pos not on a crossroad
            if(end == null){    //pos not on a street
                System.out.println("Error destination not on street");
                return Integer.MAX_VALUE;
            }
            endNode = tempNode(end,incidentCoord);
            isEndNodeTemp = true;
        }
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
            //System.out.println("Current Node : ("+CurrentNode.getCoord().getX()+","+CurrentNode.getCoord().getY()+") dist : "+Distance.get(CurrentNode));

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
                distance = Distance.get(closest);
                break;
            }
            CurrentNode = closest;
            if(CurrentNode == null){
                System.out.println("Destination unreachable");
                return Integer.MAX_VALUE;
            }
        }
        if(isEndNodeTemp){delTempNode(endNode);}   //disconnecting temporary nodes from the map
        if(isStartNodeTemp){delTempNode(startNode);}
        return distance;
	}
	public void addObstacle(int x,int y){
		
	}
	public void addObstacle(Point c) {
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
	
	public void removeObstacle(int x, int y){
		removeObstacle(new Point(x,y));
	}
	public void removeObstacle(Point c) {
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
}
