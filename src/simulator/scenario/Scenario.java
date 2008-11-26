package simulator.scenario;

import java.util.Set;

import simulator.simobjects.Ambulance;

import events.Event;

/**
 * Provide a list of events for each step of simulation.
 * @author Erick Lavoie
 *
 */
public interface Scenario {
	/**
	 * Provide the initial list of ambulances
	 * @pre Scenario has correctly been initialized
	 * @post Every ambulance of the simulation are in the set returned
	 */
	public Set<Ambulance> getAmb();
	
	/**
	 * Provide the events corresponding to the current step of the simulation
	 * @pre Initial list of ambulances has been retrieved
	 * @post Every event for the current step have been returned in the step
	 * @return
	 */
	public Set<Event> nextStep();
}
