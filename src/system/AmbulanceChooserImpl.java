package system;

import java.util.LinkedList;

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
	 * 
	 * @see system.AmbulanceChooser#chooseBestAmbulance(int,
	 *      java.util.LinkedList)
	 */
	public String chooseBestAmbulance(String incidentInfoId,
			LinkedList<String> exclusionSet) throws UnknownIncidentException {
		String kind = incident.getAmbulanceKindNeeded(incidentInfoId);
		Coord coord = incident.getPosition(incidentInfoId);
		LinkedList<String> ambulances = ambulance
				.getAllFree(kind, exclusionSet);

		return selectMinDist(ambulances, coord);
	}

	/**
	 * Return the nearest ambulance from the incident. If there is no ambulance
	 * provided, then the returned string is null.
	 */
	private String selectMinDist(LinkedList<String> ambulances,
			Coord incidentCoord) {
		String ambulanceId;

		if (ambulances.size() > 0) {
			ambulanceId = ambulances.get(0);
		} else {
			return null;
		}

		for (int i = 0; i < ambulances.size(); i++) {
			int newDistance = map.distance(ambulance
					.getCoord(ambulances.get(i)), incidentCoord);
			int oldDistance = map.distance(ambulance.getCoord(ambulanceId),
					incidentCoord);

			if (newDistance < oldDistance)
				ambulanceId = ambulances.get(i);
		}

		return ambulanceId;
	}

}
