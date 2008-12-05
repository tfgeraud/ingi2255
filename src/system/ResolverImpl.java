package system;

import system.exception.UnknownIncidentException;
import events.AmbulanceBroken;
import events.DestinationUnreachable;
import events.Event;
import events.IncidentResolved;

/**
 * This class implements the Resolver interface. It represents the resolver
 * module that set incidents as resolved
 * 
 * @author Simon Busard <simon.busard@student.uclouvain.be>
 */
public class ResolverImpl implements Resolver {

	/**
	 * The communicator module allowing communication with ambulances MDT
	 */
	private Communicator communicator;

	/**
	 * The set of ambulances known by the system
	 */
	private Ambulance ambulances;

	/**
	 * The set of incidents known by the system
	 */
	private Incident incidents;

	/**
	 * Create a new Resolver
	 * 
	 * @param communicator
	 *            the communicator module of the system
	 * @param incidents
	 *            the set of incidents known by the system
	 * @param ambulances
	 *            the set of ambulances known by the system
	 */
	public ResolverImpl(Communicator communicator, Incident incidents,
			Ambulance ambulances) {
		this.communicator = communicator;
		this.incidents = incidents;
		this.ambulances = ambulances;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Resolver#closeIncident(java.lang.String)
	 */
	public boolean closeIncident(String incidentInfoId) {
		// Wait for interesting event
		Event e = this.communicator.waitForEvent(incidentInfoId);
		try {
			// Get corresponding ambulance
			String ambulanceId = this.incidents
					.getMobilizedAmbulance(incidentInfoId);

			// Manage event
			// Ambulance broken
			if (e.getClass().equals(AmbulanceBroken.class)) {
				this.ambulances.markAsBroken(ambulanceId);
				return false;
			}
			// Destination unreachable
			else if (e.getClass().equals(DestinationUnreachable.class)) {
				return false;
			}
			// Incident resolved
			else if (e.getClass().equals(IncidentResolved.class)) {
				return true;
			} else {
				return false;
			}
		} catch (UnknownIncidentException err) {
			// if the incident is unknown, return false
			return false;
		}
	}

}
