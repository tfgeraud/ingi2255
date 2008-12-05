package simulator.simobjects;

import common.Graph;

import simulator.Pos;
import simulator.PosImpl;

/**
 * Provide a way to store the obstacles in the town and encapsulate the path finding every ambulance
 * use will moving
 * 
 * @author fred
 *
 */
public class Map extends SimObjectImpl {
	private Graph map;
	protected Map(String name) {
		super(name);
		
	}
	/**
	 * Creates a Map with name name, and streetx NS streets, streety WE
	 * streets. the distance between streets is 10 units, and the first
	 * streets are at coordinates 0. 
	 * @param name the name of the map.
	 * @param streetx	the count of NS streets
	 * @param streety	the count of WE streets
	 */
	public Map(String name, int streetx, int streety){
		super(name);
		this.map = new Graph(streetx,streety);
	}

	/**
	 * Return the next position on the shortest path between currentPos and nextPos
	 * @param currentPos	the current position , must lie on a street or crossroad.
	 * @param nextPos 		the destination, must lie on a street or crossroad.
	 * @param dist			the distance the ambulance will ride from currentPos to the returned one. 
	 * @post return null if no path exist between the positions, the next position otherwise
	 
	 */
	public Pos nextPos(Pos currentPos, Pos nextPos, int dist) {
		int[][] path = this.map.path(currentPos.getX(),currentPos.getY(),
								nextPos.getX(), nextPos.getY());
		if(path == null){
			return null;
		}else{	//we will iterate trough the path 
			int i = 0;
			int tmp_dist = 0;
			Pos current = currentPos;	//current at each iteration.
			while(i < path.length){
				if(i > 0){
					current = new PosImpl(path[i][0],path[i][1]);
					tmp_dist += current.dist(new PosImpl(path[i-1][0],path[i-1][1]));
				}
				if(tmp_dist == dist){
					return current;
				}else if (i <path.length -1 && tmp_dist > dist){	//we interpolate between previous and next crossroad pos.
					tmp_dist -= dist;
					Pos next = new PosImpl(path[i+1][0],path[i+1][1]);
					if(next.equals(current)){
						return current;	//avoid division by zero.
					}
					double dx = (next.getX() - current.getX())/(double)next.dist(current);
					double dy = (next.getY() - current.getY())/(double)next.dist(current);
					return new PosImpl(	(int)(current.getX() + dx*tmp_dist),
										(int)(current.getY() + dy*tmp_dist)		);
				}
				i++;
			}
			return current;	//will be the last pos of the path.
		}
	}
	/**
	 * Adds an obstacle to the map.
	 * @param obstacle the position where the new obstacle will be added.
	 */
	public void addObstacle(Pos obstacle){
		this.map.addObstacle(obstacle.getX(), obstacle.getY());
	}
	/**
	 * Removes an obstacle from the map.
	 * @param obstacle 	the position of an(or multiple) existing obstace that will 
	 * 					be removed
	 */
	public void removeObstacle(Pos obstacle){
		this.map.removeObstacle(obstacle.getX(), obstacle.getY());
	}
}
