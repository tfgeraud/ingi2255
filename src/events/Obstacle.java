package events;

/**
 * Sent by map to LAS
 * 
 * @author Erick Lavoie
 *
 */
public class Obstacle extends Event {
	private String obstaclePos;

	public Obstacle(String obstaclePos) {
		super("simulator");
		this.obstaclePos = obstaclePos;
	}
	
	public String getPos() {
		return this.obstaclePos;
	}
	
	public String toString() {
		return super.toString() + " pos: " + this.obstaclePos;
	}

}
