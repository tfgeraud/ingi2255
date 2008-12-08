package test.simulator;

import simulator.simobjects.SimObjectImpl;
import events.Event;

/**
 * Basic object serving demonstration purposes
 * @author Erick Lavoie
 *
 */
public class BasicObject extends SimObjectImpl {

	public BasicObject(String name) {
		super(name);
		/**
		 * Initialisation of every states
		 */
		NextState state1 = new NextState("State1");
		NextState state2 = new NextState("State2");
		state1.setStates(state2);
		state2.setStates(state1);
		
		/**
		 * Initialisation of the state machine
		 */
		StateMachine sm = new StateMachine(state1);
		
		/**
		 * Only one state machine for this object
		 */
		this.stateMachineList.add(sm);
		
	}
	
	/**
	 * Definition of basic states for the object
	 */
	class NextState extends State {
		public NextState(String name) {
			super(name);
		}

		private State state2;
		
		
		public void setStates(State state2) {
			this.state2 = state2;
		}
		
		@Override
		public State execute(Event e) {
			if(e.getClass() == PingPong.class) {
				/**
				 * On receiving the "ball" send it back and change state
				 */
				BasicObject.this.notify(e);
				return this.state2;
			} else {
				/**
				 * Event not understood
				 */
				return null;
			}
		}	
	}
	
	/**
	 * Definition of custom event for some tests
	 */
	public static class PingPong extends Event {
		public PingPong(String sender) {
			super(sender);
		}
	}
}
