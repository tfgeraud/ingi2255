package system;

import java.util.Hashtable;
import java.util.LinkedList;

import system.exception.AmbulanceAvailabilityUnknownException;
import system.exception.AmbulanceKindUnknownException;
import system.exception.AmbulanceStatusUnknwownException;
import system.exception.IllegalMobilizationException;

/**
 * This class represent the list of the ambulances known by the system.
 * 
 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
 */
public class AmbulanceImpl implements Ambulance {

	/**
	 * The list of all known ambulances
	 */
	private Hashtable<String, AmbulanceInfo> list;

	/**
	 * The ambulance info keep track of the information the system know about
	 * the ambulances
	 * 
	 * @author Antoine Cailliau <antoine.cailliau@student.uclouvain.be>
	 */
	private class AmbulanceInfo {

		/**
		 * The position of the ambulance
		 */
		private Coord pos;

		/**
		 * The status of ambulance, the status of an ambulance is either
		 * working, either broken
		 * 
		 * @see system.Ambulance#WORKING
		 * @see system.Ambulance#BROKEN
		 */
		private String status;

		/**
		 * The availability of an ambulance, the availability of an ambulance is
		 * either free, either chosen, either mobilized. The change of status is
		 * automatic with the update of the field incidentChosenFor and
		 * incidentMobilizedFor.
		 * 
		 * @see system.Ambulance#FREE
		 * @see system.Ambulance#MOBILIZED
		 * @see system.Ambulance#CHOSEN
		 */
		private String availability;

		/**
		 * The kind of the ambulance, either normal, either medicalized.
		 * 
		 * @see system.Ambulance#NORMAL
		 * @see system.Ambulance#MEDICALIZED
		 */
		private String kind;

		/**
		 * The unique identifier of the ambulance. The name should respect the
		 * global nomenclature.
		 */
		private String id;

		/**
		 * The incident which the ambulance is chosen for.
		 */
		private String incidentChosenFor;

		/**
		 * The incident which the ambulance is mobilized for.
		 */
		private String incidentMobilizedFor;

		/**
		 * Return the name of the incident
		 * 
		 * @return the incident chosen for the ambulance
		 */
		public String getIncidentChosenFor() {
			return incidentChosenFor;
		}

		/**
		 * Set the incident chosen for this ambulance and update the status of
		 * this ambulance
		 * 
		 * @param incidentChosenFor
		 *            the incident chosen for the incident.
		 */
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

		/**
		 * Return the incident this ambulance is mobilized for.
		 * 
		 * @return the incident id this ambulance is mobilized for.
		 */
		public String getIncidentMobilizedFor() {
			return incidentMobilizedFor;
		}

		/**
		 * Set the incident this ambulance is mobilized for The ambulance must
		 * be chosen for this incident before otherwise, an exception
		 * IllegalMobilizationException will be thrown. If incidentMobilizedFor
		 * is null, then the ambulance will be marked as Free and will be
		 * unchosen (not chosen)
		 * 
		 * @param incidentMobilizedFor
		 *            The incident this ambulance is mobilized for.
		 * @throws IllegalMobilizationException
		 *             if the ambulance was not chosen for this incident before.
		 */
		public void setIncidentMobilizedFor(String incidentMobilizedFor)
				throws IllegalMobilizationException {
			if (incidentMobilizedFor != null) {
				if (incidentMobilizedFor.equals(incidentChosenFor)) {
					this.availability = Ambulance.MOBILIZED;
					this.incidentMobilizedFor = incidentMobilizedFor;
				} else {
					throw new IllegalMobilizationException();
				}
			} else {
				this.availability = Ambulance.FREE;
				this.incidentChosenFor = null;
				this.incidentMobilizedFor = null;
			}
		}

		/**
		 * Create a new ambulance info using the given information. By default,
		 * the ambulance is free and working.
		 * 
		 * @param id
		 *            The unique identifier of the ambulance
		 * @param position
		 *            The position of the ambulance
		 */
		public AmbulanceInfo(String id, Coord position) {
			this.id = id;
			this.pos = position;
			this.status = Ambulance.FREE;
			this.availability = Ambulance.WORKING;
		}

		/**
		 * Get the unique identifier
		 * 
		 * @return the unique identifier of the ambulance
		 */
		public String getId() {
			return id;
		}

		/**
		 * Set the unique identifier
		 * 
		 * @param id
		 *            the new unique identifier of the ambulance
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * Get the kind of the ambulance, either {@link Ambulance#NORMAL} either
		 * {@link Ambulance#MEDICALIZED}.
		 * 
		 * @return The kind of the ambulance
		 */
		public String getKind() {
			return kind;
		}

		/**
		 * Set the kind of the ambulance, either {@link Ambulance#NORMAL} either
		 * {@link Ambulance#MEDICALIZED}.
		 * 
		 * @param kind
		 *            the kinf of the ambulance
		 * @throws AmbulanceKindUnknownException
		 *             the given kind of ambulance is not known by the system.
		 */
		public void setKind(String kind) throws AmbulanceKindUnknownException {
			if (kind.equals(Ambulance.NORMAL)
					| kind.equals(Ambulance.MEDICALIZED)) {
				this.kind = kind;
			} else {
				throw new AmbulanceKindUnknownException();
			}
		}

		/**
		 * Return the position of the ambulance
		 * 
		 * @return The position of the ambulance
		 */
		public Coord getPos() {
			return pos;
		}

		/**
		 * Set the position of the ambulance
		 * 
		 * @param pos
		 *            the position of the ambulance
		 */
		public void setPos(Coord pos) {
			this.pos = pos;
		}

		/**
		 * Return the status of the ambulance: either {@link Ambulance#WORKING}
		 * either {@link Ambulance#BROKEN}.
		 * 
		 * @return the status of the ambulance
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * Set the status of the ambulance: either {@link Ambulance#WORKING}
		 * either {@link Ambulance#BROKEN}. Otherwise, the function will throw
		 * an AmbulanceStatusUnknwownException
		 * 
		 * @param status
		 *            the status of the ambulance
		 */
		public void setStatus(String status)
				throws AmbulanceStatusUnknwownException {
			if (status.equals(Ambulance.BROKEN)
					| status.equals(Ambulance.WORKING)) {
				this.status = status;
			} else {
				throw new AmbulanceStatusUnknwownException();
			}
		}

		/**
		 * Return the availability of the ambulance. Either
		 * {@link Ambulance#FREE}, either {@link Ambulance#CHOSEN}, either
		 * {@link Ambulance#MOBILIZED}.
		 * 
		 * @return the availability of the ambulance
		 */
		public String getAvailability() {
			return availability;
		}

		/**
		 * Set the availability of the ambulance. Either {@link Ambulance#FREE}
		 * either {@link Ambulance#CHOSEN}, either {@link Ambulance#MOBILIZED}.
		 * 
		 * @param availability
		 *            the availability of the ambulance
		 * @throws AmbulanceAvailabilityUnknownException
		 *             the availability of the ambulance is not known
		 */
		public void setAvailability(String availability)
				throws AmbulanceAvailabilityUnknownException {
			if (availability.equals(Ambulance.FREE)
					| availability.equals(Ambulance.MOBILIZED)
					| availability.equals(Ambulance.CHOSEN)) {
				this.availability = availability;
			} else {
				throw new AmbulanceAvailabilityUnknownException();
			}
		}
	}

	/**
	 * Create a new empty list of ambulances
	 */
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
		try {
			this.list.get(ambulanceId).setStatus(Ambulance.BROKEN);
		} catch (AmbulanceStatusUnknwownException e) {
			System.err.println("The ambulance " + ambulanceId
					+ " cannot be set as broken");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Ambulance#addAmbulance(java.lang.String, system.Coord,
	 *      java.lang.String, java.lang.String)
	 */
	public void addAmbulance(String ambulanceId, Coord coord, String kind,
			String status) throws AmbulanceStatusUnknwownException,
			AmbulanceKindUnknownException {
		this.list.put(ambulanceId, new AmbulanceInfo(ambulanceId, coord));
		this.list.get(ambulanceId).setKind(kind);
		this.list.get(ambulanceId).setStatus(status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Ambulance#getIncidentChosenFor(java.lang.String)
	 */
	public String getIncidentChosenFor(String ambulanceId) {
		return this.list.get(ambulanceId).getIncidentChosenFor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Ambulance#getIncidentMobilizedFor(java.lang.String)
	 */
	public String getIncidentMobilizedFor(String ambulanceId) {
		return this.list.get(ambulanceId).getIncidentMobilizedFor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Ambulance#getKind(java.lang.String)
	 */
	public String getKind(String ambulanceId) {
		return this.list.get(ambulanceId).getKind();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Ambulance#markAsRepaired(java.lang.String)
	 */
	public void markAsRepaired(String ambulanceId) {
		try {
			this.list.get(ambulanceId).setStatus(Ambulance.WORKING);
		} catch (AmbulanceStatusUnknwownException e) {
			System.err.println("The ambulance " + ambulanceId
					+ " cannot be marked as repaired");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Ambulance#setIncidentChosenFor(java.lang.String,
	 *      java.lang.String)
	 */
	public void setIncidentChosenFor(String ambulanceId, String incidentInfoId) {
		this.list.get(ambulanceId).setIncidentChosenFor(incidentInfoId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Ambulance#setIncidentMobilizedFor(java.lang.String,
	 *      java.lang.String)
	 */
	public void setIncidentMobilizedFor(String ambulanceId,
			String incidentInfoId) throws IllegalMobilizationException {
		this.list.get(ambulanceId).setIncidentMobilizedFor(incidentInfoId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see system.Ambulance#setPosition(java.lang.String, system.Coord)
	 */
	public void setPosition(String ambulanceId, Coord coord) {
		this.list.get(ambulanceId).setPos(coord);
	}
}
