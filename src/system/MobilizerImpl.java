package system;

import system.exception.UnknownIncidentException;
import events.DemobilisationOrder;
import events.MobilisationOrder;

/**
 * This class represents the mobilizer. This module mobilizes and demobilizes
 * ambulances for incidents.
 * 
 * @author Simon Busard <simon.busard@student.uclouvain.be>
 */
public class MobilizerImpl implements Mobilizer {

	/**
	 * The list of incidents known by the system
	 */
	private Incident incidents;

	/**
	 * The communicator module allowing communication with ambulances MDT
	 */
	private Communicator communicator;

	/**
	 * Create a new Mobilizer
	 * 
	 * @param communicator the communicator of the system
	 * @param incidents the set of incidents known by the system
	 */
	public MobilizerImpl(Communicator communicator, Incident incidents) {
		this.communicator = communicator;
		this.incidents = incidents;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Mobilizer#mobilize(java.lang.String, java.lang.String)
	 */
	public boolean mobilize(String incidentInfoId, String ambulanceId) {
		try {
			// Create and send mobilize order
			MobilisationOrder mobOrder = this.incidents.getMobOrder(incidentInfoId);
			this.communicator.send(mobOrder);
			// Wait for acknoledgment
			return this.communicator.waitForAck(mobOrder, ambulanceId);
		} catch (UnknownIncidentException e) {
			// TODO
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Mobilizer#demobilize(java.lang.String, java.lang.String)
	 */
	public boolean demobilize(String incidentInfoId, String ambulanceId) {
		try {
			// Create an send demobilization order
			DemobilisationOrder demobOrder = this.incidents
					.getDemobOrder(incidentInfoId);
			this.communicator.send(demobOrder);
			// Wait for acknoledgment
			return this.communicator.waitForAck(demobOrder, ambulanceId);
		} catch (UnknownIncidentException e) {
			// TODO
			return false;
		}
	}

}
