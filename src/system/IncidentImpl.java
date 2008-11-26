package system;

import java.util.Hashtable;

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
	private int nextIncId = 0;
	
	// Constructors
	
	public IncidentImpl() {
		this.incidents = new Hashtable<String, IncidentInfo>();
	}
		
	public void addIncident(int age, boolean pregnant, String localisation, String description) {
		incidents.put(	"incident" + nextIncId,
						new IncidentInfo(age,pregnant,localisation,description)
						);
		// TODO : process additional information
	}

	public int getAge(String incidentInfoId) {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// TODO : exception if not known
		return inc.age;
	}

	public String getAmbulanceKindNeeded(String incidentInfoId) {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// TODO : exception if not known
		return inc.ambKindNeeded;
	}

	public String getChosenAmbulance(String incidentInfoId) {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// TODO : exception if not known
		return inc.chosenAmb;
	}

	public DemobOrder getDemobOrder(String incidentInfoId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription(String incidentInfoId) {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// TODO : exception if not known
		return inc.description;
	}

	public String getLocalisation(String incidentInfoId) {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// TODO : exception if not known
		return inc.localisation;
	}

	public MobOrder getMobOrder(String incidentInfoId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMobilizedAmbulance(String incidentInfoId) {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// TODO : exception if not known
		return inc.mobAmb;
	}

	public Coord getPosition(String incidentInfoId) {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// TODO : exception if not known
		return inc.position;
	}

	public boolean getPregnant(String incidentInfoId) {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// TODO : exception if not known
		return inc.pregnant;
	}

	public void setAsResolved(String incidentInfoId) {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// TODO : exception if not known
		inc.state = RESOLVED;
	}

	public void setChosenAmbulance(String incidentInfoId, String ambulanceId) {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// TODO : exception if not known
		inc.chosenAmb = ambulanceId;
	}

	public void setMobilizedAmbulance(String incidentInfoId, String ambulanceId) {
		IncidentInfo inc = incidents.get(incidentInfoId);
		// TODO : exception if not known
		inc.mobAmb = ambulanceId;
	}
}
