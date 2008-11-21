package simulator;


/**
 * Graphical User Interface used to control the simulation and inspect the simulated objects.
 * 
 * @author Erick Lavoie	
 *
 */
public interface GUI {
	/**
	 * Start the simulation
	 * 
	 * @Pre GUI is connected to a Simulator and Simulator is ready
	 * @Post Simulation is running
	 */
	public void start();
	
	/**
	 * Stop the simulation
	 * 
	 * @Pre Simulation is running
	 * @Post Simulation is stopped
	 */
	public void stop();
	
	/**
	 * Interrupt the simulation 
	 * 
	 * @Pre Simulation is running
	 * @Post Simulation is interrupted
	 */
	public void pause();
	
	/**
	 * Resume the simulation
	 * 
	 * @Pre Simulation is interrupted
	 * @Post Simulation is running
	 */
	public void resume();
	
	/**
	 * Execute one step of the simulation
	 * 
	 * @Pre Simulation is interrupted
	 * @Post Simulation is interrupted
	 */
	public void step();
	
	/**
	 * Show a graphical representation of the internal state of the objects
	 * and the events it has received so far
	 * 
	 * @Pre object is a valid object
	 * @Post A window with the representation of the object is shown to the user
	 */
	public void inspect(SimObject object);

}
