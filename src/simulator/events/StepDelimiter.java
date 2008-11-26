package simulator.events;

import events.Event;

/**
 * Delimits steps of simulation to synchronize all objects. Implements Singleton pattern
 * to avoid instanciation of many delimiters.  The object reference is directly used 
 * to check for a StepDelimiter in an object eventQueue.
 * 
 * @author Erick Lavoie
 *
 */
public class StepDelimiter extends Event {
	static private StepDelimiter instance = null;
	
	public StepDelimiter() {
		super("simulator");
	}
	
	public static StepDelimiter getInstance() {
		if(instance == null) {
			instance = new StepDelimiter();
		}
		return instance;	
	}
	
}
