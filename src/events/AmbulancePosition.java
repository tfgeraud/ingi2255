package events;

import system.Coord;

/**
 * Sent by simulated ambulance to the LAS.
 * 
 * @author Simon Busard
 *
 */
public class AmbulancePosition extends Event {
	private String ambulanceID;
	private Coord ambulancePosition;
	// FIXME : Coord in a common package OR
	// mapping between real world coords and system coords ???

	public AmbulancePosition(String ambulanceID, Coord coord) {
		super(ambulanceID);
		this.ambulanceID = ambulanceID;
		this.ambulancePosition = coord;
	}
	
	public String getAmbulanceID() {
		return this.ambulanceID;
	}

	public Coord getPosition() {
		return this.ambulancePosition;
	}
}