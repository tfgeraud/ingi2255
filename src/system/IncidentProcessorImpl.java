package system;

import java.util.HashSet;
import java.util.Set;

import system.exception.UnknownIncidentException;

/**
 * This class implement the incident info processor. See
 * {@link IncidentProcessor} for more details. This class runs in its own thread
 * and call other system modules like ambulance chooser, mobilizer, resolver.
 * 
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 * @author Simon Busard <simon.busard@student.uclouvain.be>
 */
public class IncidentProcessorImpl implements IncidentProcessor {

	/**
	 * The ambulance chooser used for this incident
	 */
	private AmbulanceChooser chooser;

	/**
	 * The mobilizer used for this incident
	 */
	private Mobilizer mobilizer;

	/**
	 * The resolver used for this incident
	 */
	private Resolver resolver;

	/**
	 * The id of the incident processed by this processor
	 */
	private String incidentId;

	/**
	 * Create a new incident processor
	 * 
	 * @param chooser
	 *            The ambulance chooser of the system
	 * @param mobilizer
	 *            The mobilizer of the system
	 * @param resolver
	 *            The resolver of the system
	 * @param incidentId
	 *            The id of the incident to process
	 */
	public IncidentProcessorImpl(AmbulanceChooser chooser, Mobilizer mobilizer,
			Resolver resolver, String incidentId) {
		this.chooser = chooser;
		this.mobilizer = mobilizer;
		this.resolver = resolver;
		this.incidentId = incidentId;

		// Launch a thread
		Thread thread = new Thread(this);
		thread.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.IncidentProcessor#main(int)
	 */
	public void main(String incidentInfoId) {

		String ambulanceId = "";
		Set<String> exclusionSet = new HashSet<String>();
		boolean e = false;

		do {
			do {
				do {
					// Search for the best ambulance
					try {
						ambulanceId = this.chooser.chooseBestAmbulance(
								incidentInfoId, exclusionSet);
					} catch (UnknownIncidentException err) {
						// TODO Catching exception
					}

				} while (!ambulanceId.equals(""));

				// If we got the best ambulance, add it to the exclusion set. By
				// this way we ensure that this ambulance will not be selected
				// again on case of denial of mobilization
				exclusionSet.add(ambulanceId);

			} while (this.mobilizer.mobilize(incidentInfoId, ambulanceId) == false);

			// Close the incident
			e = this.resolver.closeIncident(incidentInfoId);

			// The ambulance was not able to close the incident. This ambulance
			// may be broken, etc. We need to exclude from next searches.
			exclusionSet = new HashSet<String>();
			exclusionSet.add(ambulanceId);

			// Demobilize the ambulance since the incident is either closed or
			// the ambulance is not available
			this.mobilizer.demobilize(incidentInfoId, ambulanceId);

		} while (e == false);

	}

	/**
	 * Run this processor
	 */
	public void run() {
		main(this.incidentId);
	}
}
