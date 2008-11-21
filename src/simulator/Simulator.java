package simulator;


/**
 * Implement a simulator which drive the simulation by recuperating the events on the communication channels,
 * execute the step of the scenario and ask every SimObject to execute its simulation step.
 * 
 * @author Erick Lavoie
 *
 */
public interface Simulator {
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
	 * Setup the simulation
	 * 
	 * @Pre a scenario is know to the simulator
	 * @Post every ambulance and the map are connected to their communication channels
	 */
	public void init();
	

}
