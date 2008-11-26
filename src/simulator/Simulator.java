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
	 * @pre GUI is connected to a Simulator and Simulator is ready
	 * @post Simulation is running
	 */
	public void start();
	
	/**
	 * Stop the simulation
	 * 
	 * @pre Simulation is running
	 * @post Simulation is stopped
	 */
	public void stop();
	
	/**
	 * Interrupt the simulation 
	 * 
	 * @pre Simulation is running
	 * @post Simulation is interrupted
	 */
	public void pause();
	
	/**
	 * Resume the simulation
	 * 
	 * @pre Simulation is interrupted
	 * @post Simulation is running
	 */
	public void resume();
	
	/**
	 * Execute one step of the simulation
	 * 
	 * @pre Simulation is interrupted
	 * @post Simulation is interrupted
	 */
	public void step();
	
	
	/**
	 * Setup the simulation
	 * 
	 * @pre a scenario is know to the simulator
	 * @post every ambulance and the map are connected to their communication channels
	 */
	public void init();
	

}
