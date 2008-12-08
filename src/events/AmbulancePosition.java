package events;

import system.Coord;
import system.CoordImpl;


/**
 * Sent by simulated ambulance to the LAS.
 * 
 * @author Simon Busard
 *
 */
public class AmbulancePosition extends Event {
	private String ambulanceID;
	private String ambulancePosition;

	public AmbulancePosition(String ambulanceID, String coord) {
		super(ambulanceID);
		this.ambulanceID = ambulanceID;
		this.ambulancePosition = coord;
	}
	
	public String getAmbulanceID() {
		return this.ambulanceID;
	}

	public Coord getPosition() {
		return new CoordImpl(this.ambulancePosition);
	}
}