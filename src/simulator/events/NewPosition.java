/**
 * 
 */
package simulator.events;

import simulator.Pos;
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

	/**
	 * @param senderName
	 */
	public NewPosition(String senderName, Pos position) {
		super(senderName);
		this.position=position;
	}
	
	public Pos getPosition() {
		return this.position;
	}

}
