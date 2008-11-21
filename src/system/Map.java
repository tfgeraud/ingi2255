package system;

public interface Map {

	public int distance(Coord impl, Coord incidentCoord);
	
	public Coord addressToCoord(String localisation);
	
	public void addObstacle(Coord obstacleCoord);
	
	public void removeObstacle(Coord obstacleCoord);
}