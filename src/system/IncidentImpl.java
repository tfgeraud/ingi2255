package system;

import java.util.Hashtable;

import events.DemobilisationOrder;
import events.MobilisationOrder;

import system.exception.UnknownIncidentException;
import system.Ambulance;

/**
 * This class represent the list of all incident
 * 
 * @author Simon Busard <simon.busard@student.uclouvain.be>
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public class IncidentImpl implements Incident {

	/**
	 * This class represent all the information stored by the system about the
	 * incidents
	 * 
	 * @author Simon Busard <simon.busard@student.uclouvain.be>
	 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
	 */
	private class IncidentInfo {
		/**
		 * The age of the victim. By default, 0.
		 */
		int age = 0;

		/**
		 * The pregnancy of the victim
		 */
		boolean pregnant = false;

		/**
		 * The localisation of the incident
		 */
		String localisation = null;

		/**
		 * The description of the incident
		 */
		String description = null;

		/**
		 * The position of the incident. Computed when we set the localisation
		 * of the incident
		 */
		Coord position = null;

		/**
		 * The kind of the ambulance needed for the incident. Computed when we
		 * set the description of the incident
		 */
		String ambKindNeeded = null;

		/**
		 * The unique identifier of the ambulance chosen for this incident
		 */
		String chosenAmb = null;

		/**
		 * The unique identifier of the ambulance mobilized for this incident
		 */
		String mobAmb = null;

		/**
		 * The state of the incident. Either {@link Incident#KNOWN}, either
		 * {@link Incident#PROCESSED}, either {@link Incident#CHOSEN}, either
		 * {@link Incident#MOBILIZED}, either {@link Incident#RESOLVED}.
		 */
		String state = null;

		/**
		 * Create a new incident
		 * 
		 * @param age
		 *            The age of the victim
		 * @param pregnant
		 *            True if the victim is pregrant, false otherwise
		 * @param localisation
		 *            The localisation of the incident
		 * @param description
		 *            The description of the incident
		 */
		public IncidentInfo(int age, boolean pregnant, String localisation,
				String description) {
			this.age = age;
			this.pregnant = pregnant;
			this.localisation = localisation;
			this.description = description;
		}
	}

	/**
	 * The list of all incident
	 */
	private Hashtable<String, IncidentInfo> incidents = null;

	/**
	 * The map of the system
	 */
	private Map map = null;

	/**
	 * The counter of incident, this number is the next number to use (so, not
	 * yet used)
	 */
	private int nextIncidentId = 0;

	/**
	 * Create a new instance of Incident. It represents an empty list of known
	 * incidents.
	 * 
	 * @param map
	 *            the map contained in the system
	 */
	public IncidentImpl(Map map) {
		this.incidents = new Hashtable<String, IncidentInfo>();
		this.map = map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Incident#addIncident(int, boolean, java.lang.String,
	 *      java.lang.String)
	 */
	synchronized public String addIncident(int age, boolean pregnant,
			String localisation, String description) {
		IncidentInfo inc = new IncidentInfo(age, pregnant, localisation,
				description);
		String incidentId = "incident" + this.nextIncidentId++;
		this.incidents.put(incidentId, inc);

		// Process additional information
		// Position
		// FIXME : can return null
		inc.position = this.map.addressToCoord(localisation);
		// Ambulance kind needed
		// This is very simple for the beginning. If description is "grave"
		// then a medicalized ambulance is needed, else a normal ambulance
		// is needed.
		// TODO : implement more complex and usefull process
		if (description.equals("grave")) {
			inc.ambKindNeeded = Ambulance.MEDICALIZED;
		} else {
			inc.ambKindNeeded = Ambulance.NORMAL;
		}

		return incidentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Incident#getAge(java.lang.String)
	 */
	public int getAge(String incidentInfoId) throws UnknownIncidentException {
		IncidentInfo inc = this.incidents.get(incidentInfoId);
		// Exception if not known
		if (inc == null) {
			throw new UnknownIncidentException();
		}
		return inc.age;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Incident#getAmbulanceKindNeeded(java.lang.String)
	 */
	public String getAmbulanceKindNeeded(String incidentInfoId)
			throws UnknownIncidentException {
		IncidentInfo inc = this.incidents.get(incidentInfoId);
		// Exception if not known
		if (inc == null) {
			throw new UnknownIncidentException();
		}
		return inc.ambKindNeeded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Incident#getChosenAmbulance(java.lang.String)
	 */
	public String getChosenAmbulance(String incidentInfoId)
			throws UnknownIncidentException {
		IncidentInfo inc = this.incidents.get(incidentInfoId);
		// Exception if not known
		if (inc == null) {
			throw new UnknownIncidentException();
		}
		return inc.chosenAmb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Incident#getDemobOrder(java.lang.String)
	 */
	public DemobilisationOrder getDemobOrder(String incidentInfoId)
			throws UnknownIncidentException {
		IncidentInfo inc = this.incidents.get(incidentInfoId);
		// Exception if not known
		if (inc == null) {
			throw new UnknownIncidentException();
		}
		return new DemobilisationOrder(incidentInfoId, inc.position.toString(),
				inc.mobAmb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Incident#getDescription(java.lang.String)
	 */
	public String getDescription(String incidentInfoId)
			throws UnknownIncidentException {
		IncidentInfo inc = this.incidents.get(incidentInfoId);
		// Exception if not known
		if (inc == null) {
			throw new UnknownIncidentException();
		}
		return inc.description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Incident#getLocalisation(java.lang.String)
	 */
	public String getLocalisation(String incidentInfoId)
			throws UnknownIncidentException {
		IncidentInfo inc = this.incidents.get(incidentInfoId);
		// Exception if not known
		if (inc == null) {
			throw new UnknownIncidentException();
		}
		return inc.localisation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Incident#getMobOrder(java.lang.String)
	 */
	public MobilisationOrder getMobOrder(String incidentInfoId)
			throws UnknownIncidentException {
		IncidentInfo inc = this.incidents.get(incidentInfoId);
		// Exception if not known
		if (inc == null) {
			throw new UnknownIncidentException();
		}
		return new MobilisationOrder(incidentInfoId, inc.position.toString(),
				inc.chosenAmb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Incident#getMobilizedAmbulance(java.lang.String)
	 */
	public String getMobilizedAmbulance(String incidentInfoId)
			throws UnknownIncidentException {
		IncidentInfo inc = this.incidents.get(incidentInfoId);
		// Exception if not known
		if (inc == null) {
			throw new UnknownIncidentException();
		}
		return inc.mobAmb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Incident#getPosition(java.lang.String)
	 */
	public Coord getPosition(String incidentInfoId)
			throws UnknownIncidentException {
		IncidentInfo inc = this.incidents.get(incidentInfoId);
		// Exception if not known
		if (inc == null) {
			throw new UnknownIncidentException();
		}
		return inc.position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Incident#getPregnant(java.lang.String)
	 */
	public boolean getPregnant(String incidentInfoId)
			throws UnknownIncidentException {
		IncidentInfo inc = this.incidents.get(incidentInfoId);
		// Exception if not known
		if (inc == null) {
			throw new UnknownIncidentException();
		}
		return inc.pregnant;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Incident#setAsResolved(java.lang.String)
	 */
	synchronized public void setAsResolved(String incidentInfoId)
			throws UnknownIncidentException {
		IncidentInfo inc = this.incidents.get(incidentInfoId);
		// Exception if not known
		if (inc == null) {
			throw new UnknownIncidentException();
		}
		inc.state = Incident.RESOLVED;
		// TODO Remove incident from list?
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Incident#setChosenAmbulance(java.lang.String,
	 *      java.lang.String)
	 */
	synchronized public void setChosenAmbulance(String incidentInfoId,
			String ambulanceId) throws UnknownIncidentException {
		IncidentInfo inc = this.incidents.get(incidentInfoId);
		// Exception if not known
		if (inc == null) {
			throw new UnknownIncidentException();
		}
		inc.chosenAmb = ambulanceId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Incident#setMobilizedAmbulance(java.lang.String,
	 *      java.lang.String)
	 */
	synchronized public void setMobilizedAmbulance(String incidentInfoId,
			String ambulanceId) throws UnknownIncidentException {
		IncidentInfo inc = this.incidents.get(incidentInfoId);
		// Exception if not known
		if (inc == null) {
			throw new UnknownIncidentException();
		}
		inc.mobAmb = ambulanceId;
		// TODO verify if chosen ambulance is correct
	}
}
