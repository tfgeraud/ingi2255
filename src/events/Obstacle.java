package events;

/**
 * This class represent an obstacle discovered event. This event is sent by map
 * to LAS.
 * 
 * @author Erick Lavoie
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public class Obstacle extends Event {

	/**
	 * The position of the obstacle
	 */
	private String obstaclePos;

	/**
	 * Create a new obstacle event at the given position
	 * 
	 * @param obstaclePos
	 *            The position of the obstacle were discovered
	 */
	public Obstacle(String obstaclePos) {
		super("simulator");
		this.obstaclePos = obstaclePos;
	}

	/**
	 * Return the position of the obstacle
	 * 
	 * @return the position of the obstacle
	 */
	public String getPos() {
		return this.obstaclePos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see events.Event#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " pos: " + this.obstaclePos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see events.Event#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof Obstacle && arg0.getClass().equals(this.getClass())) {
			Obstacle mo = ((Obstacle) arg0);
			return this.obstaclePos.equals(mo.obstaclePos);
		} else {
			return super.equals(arg0);
		}
	}
}
