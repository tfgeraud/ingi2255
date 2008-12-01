package system;

import java.util.LinkedList;

public class AmbulanceChooserImpl implements AmbulanceChooser {

	private Incident incident;
	private Ambulance ambulance;
	private Map map;
	
	/* (non-Javadoc)
	 * @see system.AmbulanceChooser#chooseBestAmbulance(int, java.util.LinkedList)
	 */
	public int chooseBestAmbulance(int incidentInfoId, LinkedList<Integer> exclusionSet) {
		
        String kind = incident.getAmbulanceKindNeeded(incidentInfoId);
        Coord coord = incident.getPosition(incidentInfoId);
        LinkedList ambulances = ambulance.getAllFree(kind, exclusionSet);
        
		Coord[] ambulancesCoord = getAmbulancesCoord(ambulances);
		return selectMinDist(ambulancesCoord,coord);
	}
	
	private Coord[] getAmbulancesCoord(LinkedList ambulances) {
		Coord[] coords = new Coord[ambulances.size()];
		
		for(int i = 0;i < coords.length;i++) {
			coords[i] = ambulance.getCoord(((Integer)ambulances.get(i)).intValue());
		}
		
		return coords;
	}
	
	private int selectMinDist(Coord[] ambulancesCoord, Coord incidentCoord) {
		
		int ambulanceId = 0;
		
		for(int i = 0;i < ambulancesCoord.length;i++) {
			if(	map.distance(	ambulancesCoord[i],
								incidentCoord)
				<
				map.distance(	ambulancesCoord[ambulanceId],
								incidentCoord))
				ambulanceId = i;
		}
		
		return ambulanceId;
	}

}
