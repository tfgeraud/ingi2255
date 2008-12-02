package system;

import java.util.LinkedList;

import system.exception.UnknownIncidentException;

public class AmbulanceChooserImpl implements AmbulanceChooser {

	private Incident incident;
	private Ambulance ambulance;
	private Map map;
	
	public AmbulanceChooserImpl(Incident incident, Ambulance ambulance, Map map) {
		this.incident = incident;
		this.ambulance = ambulance;
		this.map = map;
	}
	
	/* (non-Javadoc)
	 * @see system.AmbulanceChooser#chooseBestAmbulance(int, java.util.LinkedList)
	 */

	public String chooseBestAmbulance(String incidentInfoId, LinkedList<String> exclusionSet)
	throws UnknownIncidentException
	{		
		String kind = incident.getAmbulanceKindNeeded(incidentInfoId);
		Coord coord = incident.getPosition(incidentInfoId);
		LinkedList<String> ambulances = ambulance.getAllFree(kind, exclusionSet);
		
		return selectMinDist(ambulances,coord);
	}
	
	private String selectMinDist(LinkedList<String> ambulances, Coord incidentCoord) {
		String ambulanceId = ambulances.get(0);
		
		for(int i = 0;i < ambulances.size();i++) {
			if(	map.distance(	ambulance.getCoord(ambulances.get(i)),
								incidentCoord)
				<
				map.distance(	ambulance.getCoord(ambulanceId),
								incidentCoord))
				ambulanceId = ambulances.get(i);
		}
		
		return ambulanceId;
	}

}
