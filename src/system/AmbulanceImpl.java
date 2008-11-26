package system;

import java.util.Hashtable;
import java.util.LinkedList;

/**
 * This class represent the list of the ambulances known
 * by the system.
 * 
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public class AmbulanceImpl implements Ambulance {

	private Hashtable<String, AmbulanceInfo> list;

	private class AmbulanceInfo {

		private Coord pos;

		private String status;

		private String availability;

		private String kind;

		private String id;

		private String incidentChosenFor;

		private String incidentMobilizedFor;

		public String getIncidentChosenFor() {
			return incidentChosenFor;
		}

		public void setIncidentChosenFor(String incidentChosenFor) {
			if (incidentChosenFor != null) {
				this.availability = Ambulance.CHOSEN;
				this.incidentChosenFor = incidentChosenFor;
			} else {
				this.availability = Ambulance.FREE;
				this.incidentChosenFor = null;
				this.incidentMobilizedFor = null;
			}
		}

		public String getIncidentMobilizedFor() {
			return incidentMobilizedFor;
		}

		public void setIncidentMobilizedFor(String incidentMobilizedFor) {
			if (incidentMobilizedFor != null) {
				this.availability = Ambulance.MOBILIZED;
				this.incidentMobilizedFor = incidentMobilizedFor;
			} else {
				this.availability = Ambulance.FREE;
				this.incidentChosenFor = null;
				this.incidentMobilizedFor = null;
			}
		}

		public AmbulanceInfo(String id, Coord coord, String kind, String status) {
			this.id = id;
			this.pos = coord;
			this.kind = kind;
			this.status = status;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getKind() {
			return kind;
		}

		public void setKind(String kind) {
			this.kind = kind;
		}

		public Coord getPos() {
			return pos;
		}

		public void setPos(Coord pos) {
			this.pos = pos;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getAvailability() {
			return availability;
		}

		public void setAvailability(String availability) {
			this.availability = availability;
		}
	}

	public AmbulanceImpl() {
		this.list = new Hashtable<String, AmbulanceInfo>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Ambulance#getAllFree(int, java.util.LinkedList)
	 */
	public LinkedList<String> getAllFree(String kind,
			LinkedList<String> exclusionSet) {
		
		LinkedList<String> result = new LinkedList<String>();
		
		for (String key : this.list.keySet()) {
			AmbulanceInfo a = this.list.get(key);
			if (a.getAvailability().equals(Ambulance.FREE)
					&& exclusionSet.contains(a.getId())) {
				result.add(a.getId());
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Ambulance#getCoord(int)
	 */
	public Coord getCoord(String ambulanceId) {
		return this.list.get(ambulanceId).getPos();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Ambulance#markAsBroken(int)
	 */
	public void markAsBroken(String ambulanceId) {
		this.list.get(ambulanceId).setStatus(Ambulance.BROKEN);
	}

	public void addAmbulance(String ambulanceId, Coord coord, String kind,
			String status) {
		this.list.put(ambulanceId, new AmbulanceInfo(ambulanceId, coord, kind, status));
	}

	public String getIncidentChosenFor(String ambulanceId) {
		return this.list.get(ambulanceId).getIncidentChosenFor();
	}

	public String getIncidentMobilizedFor(String ambulanceId) {
		return this.list.get(ambulanceId).getIncidentMobilizedFor();
	}

	public String getKind(String ambulanceId) {
		return this.list.get(ambulanceId).getKind();
	}

	public void markAsRepaired(String ambulanceId) {
		this.list.get(ambulanceId).setStatus(Ambulance.WORKING);
	}

	public void setIncidentChosenFor(String ambulanceId, String incidentInfoId) {
		this.list.get(ambulanceId).setIncidentChosenFor(incidentInfoId);
	}

	public void setIncidentMobilizedFor(String ambulanceId, String incidentInfoId) {
		this.list.get(ambulanceId).setIncidentMobilizedFor(incidentInfoId);
	}

	public void setPosition(String ambulanceId, Coord coord) {
		this.list.get(ambulanceId).setPos(coord);
	}
}
