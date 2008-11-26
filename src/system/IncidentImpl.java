package system;

import java.util.Hashtable;

import events.DemobilisationOrder;
import events.MobilisationOrder;

import system.exception.UnknownIncidentException;
import system.Ambulance;

public class IncidentImpl implements Incident {
	
	private class IncidentInfo {
		int age = 0;
		boolean pregnant = false;
		String localisation = null;
		String description = null;
		
		Coord position = null;
		String ambKindNeeded = null;
		
		String chosenAmb = null;
		String mobAmb = null;
		
		String state = null;
		
		public IncidentInfo(int age, boolean preg, String loc, String desc) {
			this.age = age;
			this.pregnant = preg;
			this.localisation = loc;
			this.description = desc;
		}
	}
	
	private Hashtable<String, IncidentInfo> incidents = null;
	private Map map = null;
	private int nextIncId = 0;
	
	// Constructors
	/**
	 * Create a new instance of Incident.  It represents an empty list of
	 * known incidents.
	 * 
	 * @param map : the map contained in the system
	 */
	public IncidentImpl(Map map) {
		this.incidents = new Hashtable<String, IncidentInfo>();
		this.map = map;
	}
		
	public void addIncident(int age, boolean pregnant, 
							String localisation, String description) {
		IncidentInfo inc = new IncidentInfo(age,pregnant,
											localisation,description);
		incidents.put("incident" + nextIncId,inc);
		
		// Process additional information
		// Position
		inc.position = map.addressToCoord(localisation);
		// Ambulance kind needed
		// This is very simple for the beginning.  If description is "grave"
		// then a medicalized ambulance is needed, else a normal ambulance
		// is needed.
		// TODO : implement more complex and usefull process
		if(description.equals("grave")) 
			inc.ambKindNeeded = Ambulance.MEDICALIZED;
		else
			inc.ambKindNeeded = Ambulance.NORMAL;
	}

	public int getAge(String incidentInfoId)
	throws UnknownIncidentException {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// Exception if not known
		if(inc == null) throw new UnknownIncidentException();
		return inc.age;
	}

	public String getAmbulanceKindNeeded(String incidentInfoId)
	throws UnknownIncidentException {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// Exception if not known
		if(inc == null) throw new UnknownIncidentException();
		return inc.ambKindNeeded;
	}

	public String getChosenAmbulance(String incidentInfoId)
	throws UnknownIncidentException {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// Exception if not known
		if(inc == null) throw new UnknownIncidentException();
		return inc.chosenAmb;
	}

	public DemobilisationOrder getDemobOrder(String incidentInfoId)
	throws UnknownIncidentException {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// Exception if not known
		if(inc == null) throw new UnknownIncidentException();
		return new DemobilisationOrder(	incidentInfoId,
										inc.position.toString(),inc.mobAmb);
	}

	public String getDescription(String incidentInfoId)
	throws UnknownIncidentException {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// Exception if not known
		if(inc == null) throw new UnknownIncidentException();
		return inc.description;
	}

	public String getLocalisation(String incidentInfoId)
	throws UnknownIncidentException {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// Exception if not known
		if(inc == null) throw new UnknownIncidentException();
		return inc.localisation;
	}

	public MobilisationOrder getMobOrder(String incidentInfoId)
	throws UnknownIncidentException {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// Exception if not known
		if(inc == null) throw new UnknownIncidentException();
		return new MobilisationOrder(incidentInfoId,
									 inc.position.toString(),inc.chosenAmb);
	}

	public String getMobilizedAmbulance(String incidentInfoId)
	throws UnknownIncidentException {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// Exception if not known
		if(inc == null) throw new UnknownIncidentException();
		return inc.mobAmb;
	}

	public Coord getPosition(String incidentInfoId)
	throws UnknownIncidentException {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// Exception if not known
		if(inc == null) throw new UnknownIncidentException();
		return inc.position;
	}

	public boolean getPregnant(String incidentInfoId)
	throws UnknownIncidentException {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// Exception if not known
		if(inc == null) throw new UnknownIncidentException();
		return inc.pregnant;
	}

	public void setAsResolved(String incidentInfoId)
	throws UnknownIncidentException {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// Exception if not known
		if(inc == null) throw new UnknownIncidentException();
		inc.state = RESOLVED;
	}

	public void setChosenAmbulance(String incidentInfoId, String ambulanceId)
	throws UnknownIncidentException {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// Exception if not known
		if(inc == null) throw new UnknownIncidentException();
		inc.chosenAmb = ambulanceId;
	}

	public void setMobilizedAmbulance(String incidentInfoId, String ambulanceId)
	throws UnknownIncidentException {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// Exception if not known
		if(inc == null) throw new UnknownIncidentException();
		inc.mobAmb = ambulanceId;
	}
}
