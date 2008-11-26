package simulator.gui;

import simulator.simobjects.SimObject;


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
	 * Show a graphical representation of the internal state of the objects
	 * and the events it has received so far
	 * 
	 * @pre object is a valid object
	 * @post A window with the representation of the object is shown to the user
	 */
	public void inspect(SimObject object);

}
