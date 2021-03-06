package system;

import java.util.HashSet;
import java.util.Set;

import system.exception.UnknownIncidentException;

/**
 * This class implement the incident info processor. See
 * {@link IncidentProcessor} for more details.
 * 
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
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
					ambulanceId = this.chooser.chooseBestAmbulance(incidentInfoId,
							exclusionSet);
					} catch (UnknownIncidentException err) {
						// TODO
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
}
