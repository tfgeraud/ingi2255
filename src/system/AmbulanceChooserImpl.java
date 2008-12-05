package system;

import java.util.Set;

import system.exception.UnknownIncidentException;

/**
 * The ambulance choser is responsible of the choice of the best ambulance. See
 * {@link AmbulanceChooser} for more details.
 * 
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public class AmbulanceChooserImpl implements AmbulanceChooser {

	/**
	 * The incident we are interrested in
	 */
	private Incident incident;

	/**
	 * The set of all ambulance (including non-free)
	 */
	private Ambulance ambulance;

	/**
	 * The map we use to compute shortest path
	 */
	private Map map;

	/**
	 * Create a new incident chooser based on a incident, a set of ambulance and
	 * a map.
	 * 
	 * @param incident
	 *            The incident we want to find the nearest ambulance
	 * @param ambulance
	 *            The set of all ambulances
	 * @param map
	 *            The map used to compute shortest path
	 */
	public AmbulanceChooserImpl(Incident incident, Ambulance ambulance, Map map) {
		this.incident = incident;
		this.ambulance = ambulance;
		this.map = map;
	}

	/*
	 * (non-Javadoc)
	 * @see system.AmbulanceChooser#chooseBestAmbulance(java.lang.String, java.util.Set)
	 */
	public String chooseBestAmbulance(String incidentInfoId,
			Set<String> exclusionSet) throws UnknownIncidentException {
		
		String kind = this.incident.getAmbulanceKindNeeded(incidentInfoId);
		Coord coord = this.incident.getPosition(incidentInfoId);
		Set<String> ambulances = this.ambulance
				.getAllFree(kind, exclusionSet);

		return this.selectMinDist(ambulances, coord);
	}

	/**
	 * Return the nearest ambulance from the incident. If there is no ambulance
	 * provided, then the returned string is null.
	 */
	private String selectMinDist(Set<String> ambulances,
			Coord incidentCoord) {
		String ambulanceId = null;

		if (ambulances.size() <= 0) {
			return null;
		}

		for (String amb : ambulances) {
			int newDistance = this.map.distance(this.ambulance
					.getPosition(amb), incidentCoord);
			int oldDistance = this.map.distance(this.ambulance.getPosition(amb),
					incidentCoord);

			if (newDistance < oldDistance) {
				ambulanceId = amb;
			}
		}

		return ambulanceId;
	}

}
