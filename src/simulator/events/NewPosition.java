/**
 * 
 */
package simulator.events;

import simulator.Pos;
import simulator.simobjects.SimObject;
import events.Event;

/**
 * 
 * Update message to notify everyone of a position change.
 * 
 * @author Erick Lavoie
 *
 */
public class NewPosition extends Event {
	private Pos position;
	private SimObject simObject;

	/**
	 * @param senderName
	 */
	public NewPosition(String senderName, Pos position, SimObject simObject) {
		super(senderName);
		this.position=position;
		this.simObject = simObject;
	}
	
	public Pos getPosition() {
		return this.position;
	}
	
	public SimObject getSimObject() {
		return this.simObject;
	}

}
