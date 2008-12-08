package simulator.simobjects;

import simulator.Pos;
import simulator.PosImpl;
import simulator.events.AmbulanceOnScene;
import simulator.events.DestinationOrder;
import simulator.events.NewPosition;
import events.*;

/**
 * Simulation object encapsulating the state of an ambulance.  Use a Map simulation object to compute its position while moving.
 * @author Erick Lavoie
 *
 */
public class Ambulance extends SimObjectImpl {
	private Map map;
	private Pos pos;
	private String incidentID;
	private final int speed = 1;
	
	public Ambulance(String name, Map map, Pos initialPos) {
		super(name);
		this.map = map;
		this.pos = initialPos;
		
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
		
		this.stateMachineList.add(sm1);
		this.stateMachineList.add(sm2);
		
		/**
		 * Connect to itself for moving
		 */
		this.attach(this, DestinationOrder.class);
		
	}
	
	/**
	 * Getters and setters
	 */
	public Pos getCurrentPos() {
		return this.pos;
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
			if(e.getClass() == AmbulanceBroken.class ){
				/**
				 * Stop moving when broken
				 */
				return this.notMoving;
			} else if(e.getClass() == DestinationUnreachable.class) {
				/**
				 * Stop when unable to reach the destination, further movements will need
				 * to be reinitiated.
				 */
				return this.notMoving;
			} else if(e.getClass() == DestinationOrder.class) {
				/**
				 * Move one step further until destination reached.  Resend the event to self
				 * until done.  Notify for new position when position changed.
				 */
				DestinationOrder dest = (DestinationOrder)e;
				Pos temp = Ambulance.this.map.nextPos(Ambulance.this.pos, dest.getDestination(), Ambulance.this.speed);
				if(temp == null) {
					Ambulance.this.notify(new DestinationUnreachable(Ambulance.this.incidentID, Ambulance.this.getName()));
				} else {
					if(!Ambulance.this.pos.equals(temp)) {
						Ambulance.this.pos = temp;
						/**
						 *  Both events have the same semantic but NewPosition is conserved to pass
						 *  the reference of the ambulance to the incident.
						 */
						Ambulance.this.notify(new NewPosition(Ambulance.this.getName(), Ambulance.this.pos, Ambulance.this));
						Ambulance.this.notify(new AmbulancePosition(Ambulance.this.getName(), Ambulance.this.pos.toString()));
						Ambulance.this.notify(e);
						
						return this;
					} else {
						return notMoving;
					}
				}
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
				/**
				 * Start moving only on DestinationOrder and not broken.
				 */
				if(Ambulance.this.getCurrentStateNames().contains("Broken")) {
					return this;
				} else {
					/**
					 * Start moving at once
					 */
					return this.moving.execute(e);
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
				/**
				 * On mobilization, confirm reception and start moving toward
				 * destination.  Currently, always accept mobilization.
				 */
				MobilisationOrder mobOrder = (MobilisationOrder)e;
				if (mobOrder.getAmbulanceID() == Ambulance.this.getName()) {
					Ambulance.this.incidentID=mobOrder.getIncidentID();
					Ambulance.this.notify(new MobilisationConfirmation(mobOrder.getIncidentID(), 
							mobOrder.getAmbulanceID(), true));
					Ambulance.this.notify(new DestinationOrder(Ambulance.this.getName(), 
							new PosImpl(mobOrder.getIncidentPos())));
					return this.mobilized; 
				} else {
					return this;
				}
			} else if (e.getClass() == DestinationOrder.class ){
				/**
				 * On Destination Order stay free.
				 * Allows moving to somewhere will not being mobilized
				 * to optimize placement of the ambulances.
				 */
				return this;
			} else if (e.getClass() == AmbulanceOnScene.class) {
				/**
				 * FIXME: This is where we can handle the case where an 
				 *        ambulance arrives on an incident scene it was
				 *        not mobilized on.  For the moment, the 
				 *        ambulance will ignore it.
				 */
				return this;
			} else if (e.getClass() == AmbulanceBroken.class) {
				/**
				 * Propagate the event and change the sender to this ambulance
				 */
				Ambulance.this.notify(new AmbulanceBroken(Ambulance.this.getName()));
				return this.broken;
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
				/**
				 * The only thing that can move an ambulance out of 
				 * a broken state, is a repaired event.  Notify
				 * everyone after that the ambulance is repaired and
				 * change the sender to this ambulance.
				 */
				Ambulance.this.notify(new AmbulanceRepaired(Ambulance.this.getName()));
				return this.free;
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
				/**
				 * When arriving on scene, stay mobilized until IncidentResolved
				 * and resolve event
				 */
				Ambulance.this.notify(new IncidentResolved(Ambulance.this.incidentID, Ambulance.this.getName()));
				return this;
			} else if(e.getClass() == DemobilisationOrder.class){
				/**
				 * Become free again if requested, even if incident is not resolved
				 */
				Ambulance.this.incidentID=null;
				return this.free;
			} else if(e.getClass() == IncidentResolved.class) {
				/**
				 * Become free again when incident is resolved.
				 * FIXME: Maybe should wait for LAS to become free again.
				 */
				return this.free;
			} else if(e.getClass() == AmbulanceBroken.class) {
				/**
				 * 
				 */
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
