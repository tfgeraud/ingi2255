package simulator.simobjects;

import simulator.events.AmbulanceOnScene;
import simulator.events.DestinationOrder;
import events.*;

/**
 * Simulation object encapsulating the state of an ambulance.  Use a Map simulation object to compute its position while moving.
 * @author Erick Lavoie
 *
 */
public class Ambulance extends SimObjectImpl {

	public Ambulance(String name) {
		super(name);
		
		/**
		 * Initialization of every states
		 */
		Moving moving = new Moving("Moving");
		NotMoving notMoving = new NotMoving("NotMoving");
		
		moving.setStates(notMoving);
		notMoving.setStates(moving);
		
		
		Free free = new Free("Free");
		Mobilized mobilized = new Mobilized("Mobilized");
		Broken broken = new Broken("Broken");
		
		free.setStates(broken, mobilized);
		mobilized.setStates(free, broken);
		broken.setStates(free);
		
		
		/**
		 * Initialization of state machines
		 */
		StateMachine sm1 = new StateMachine(notMoving);
		StateMachine sm2 = new StateMachine(free);
		
		stateMachineList.add(sm1);
		stateMachineList.add(sm2);
		
	}
	
	
	/**
	 * Definition of states for the ambulance
	 */
	
	/**
	 * Moving state machine
	 */
	class Moving extends State {
		private NotMoving notMoving;

		public Moving(String name) {
			super(name);
		}
		
		public void setStates(NotMoving notmoving) {
			this.notMoving = notmoving;
		}
		
		@Override
		public State execute(Event e) {
			if(e.getClass() == AmbulanceOnScene.class) {
				return notMoving;
			} else if(e.getClass() == AmbulanceBroken.class ){
				return notMoving;
			} else if(e.getClass() == DestinationUnreachable.class) {
				return notMoving;
			} else if(e.getClass() == DestinationOrder.class) {
				return this;
			} else {
			/**
			 * Event not understood
			 */
			return null;
			}
		}
	}
	
	class NotMoving extends State {
		private Moving moving;

		public NotMoving(String name) {
			super(name);
		}
		
		public void setStates(Moving moving) {
			this.moving = moving;
		}
		
		@Override
		public State execute(Event e) {
			if(e.getClass() == DestinationOrder.class) {
				if(Ambulance.this.getCurrentStateNames().contains("Broken")) {
					return this;
				} else {
					return moving;
				}
			}  else {
			/**
			 * Event not understood
			 */
			return null;
			}
		}
		
	}
	
	/**
	 * Status State Machine
	 */
	private class Free extends State {
		private Broken broken;
		private Mobilized mobilized;

		public Free(String name) {
			super(name);
		}
		
		public void setStates(Broken broken, Mobilized mobilized) {
			this.broken = broken;
			this.mobilized = mobilized;
		}
		
		@Override
		public State execute(Event e) {
			if(e.getClass() == MobilisationOrder.class) {
				MobilisationOrder mobOrder = (MobilisationOrder)e;
				if (mobOrder.getAmbulanceID() == Ambulance.this.getName()) {
					return this.mobilized; 
				} else {
					return this;
				}
			} else if (e.getClass() == DestinationOrder.class ){
				return this;
			} else if (e.getClass() == AmbulanceOnScene.class) {
				return this;
			} else if (e.getClass() == AmbulanceBroken.class) {
				return broken;
			} else {
		
			/**
			 * Event not understood
			 */
			return null;
			}	
		}
		
	}
	
	private class Broken extends State {
		private Free free;

		public Broken(String name) {
			super(name);
		}
		
		public void setStates(Free free) {
			this.free = free;
		}
		
		@Override
		public State execute(Event e) {
			if(e.getClass() == AmbulanceRepaired.class) {
				return free;
			} else {
		
			/**
			 * Event not understood
			 */
			return null;
			}
		}
	}
	
	private class Mobilized extends State {
		private Free free;
		private Broken broken;

		public Mobilized(String name) {
			super(name);
		}
		
		public void setStates(Free free, Broken broken) {
			this.free = free;
			this.broken = broken;
		}
		
		@Override
		public State execute(Event e) {
			if(e.getClass() == AmbulanceOnScene.class) {
				return this;
			} else if(e.getClass() == DemobilisationOrder.class){
				return this.free;
			} else if(e.getClass() == IncidentResolved.class) {
				return this.free;
			} else if(e.getClass() == AmbulanceBroken.class) {
				return this.broken;
			}else {
				/**
				 * Event not understood
				 */
				return null;
			}
		}
		
	}

}
