package simulator.simobjects;

import simulator.Pos;
import simulator.events.AmbulanceOnScene;
import simulator.events.NewPosition;
import events.Event;
import events.IncidentResolved;

/**
 * Simulation object encapsulating the state of the incident
 * @author Erick Lavoie
 *
 */
public class Incident extends SimObjectImpl {
	private Pos pos;

	public Incident(String name, Pos pos) {
		super(name);
		this.pos = pos;
		

		/**
		 * Initialisation of every states
		 */
		Occured occured = new Occured("Occured");
		Resolved resolved = new Resolved("Resolved");
		occured.setStates(resolved);
		
		/**
		 * Initialisation of the state machine
		 */
		StateMachine sm = new StateMachine(occured);
		
		/**
		 * Only one state machine for this object
		 */
		this.stateMachineList.add(sm);
		
	}
	
	/**
	 * Initial state of an incident
	 * 
	 * @author Erick Lavoie
	 *
	 */
	class Occured extends State {
		private Resolved resolved;
		
		public Occured(String name) {
			super(name);
		}
		
		public void setStates(Resolved resolved) {
			this.resolved = resolved;
		}
		
		@Override
		public State execute(Event e) {
			if(e.getClass() == NewPosition.class) {
				/**
				 * For each position received, verify that someone arrived on scene.
				 * If it is the case, listen for that object to resolve the incident.
				 */
				NewPosition newpos = (NewPosition)e;
				if(newpos.getPosition().equals(Incident.this.pos)) {
					newpos.getSimObject().accept(new AmbulanceOnScene(Incident.this.getName()));
					newpos.getSimObject().attach(Incident.this, IncidentResolved.class);
				}
				return this;
			} else if(e.getClass() == IncidentResolved.class) {
				/**
				 * Disconnect from everything to stop receiving events
				 */
				Incident.this.disconnect();
				return this.resolved;
			} else {
				/**
				 * Event not understood
				 */
				return null;
			}
		}	
	}
	
	/**
	 * No more work needs to be done, kept for status interrogation in the simulation
	 * 
	 * @author Erick Lavoie
	 *
	 */
	class Resolved extends State {

		public Resolved(String name) {
			super(name);
		}
	
	}

}
