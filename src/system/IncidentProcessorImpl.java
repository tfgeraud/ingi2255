package system;

import java.util.LinkedList;

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
		LinkedList<String> exclusionSet = new LinkedList<String>();
		boolean e = false;

		do {
			do {
				do {
					// Search for the best ambulance
					ambulanceId = chooser.chooseBestAmbulance(incidentInfoId,
							exclusionSet);

				} while (!ambulanceId.equals(""));

				// If we got the best ambulance, add it to the exclusion set. By
				// this way we ensure that this ambulance will not be selected
				// again on case of denial of mobilization
				exclusionSet.add(ambulanceId);

			} while (mobilizer.mobilize(incidentInfoId, ambulanceId) == false);

			// Close the incident
			e = resolver.closeIncident(incidentInfoId, ambulanceId);

			// The ambulance was not able to close the incident. This ambulance
			// may be broken, etc. We need to exclude from next searches.
			exclusionSet = new LinkedList<String>();
			exclusionSet.add(ambulanceId);

			// Demobilize the ambulance since the incident is either closed or
			// the ambulance is not available
			mobilizer.demobilize(incidentInfoId, ambulanceId);

		} while (e == false);

	}
}
