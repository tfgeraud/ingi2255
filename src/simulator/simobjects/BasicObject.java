package simulator.simobjects;

import events.Event;

/**
 * Basic object serving demonstration purposes
 * @author Erick Lavoie
 *
 */
public class BasicObject extends SimObjectImpl {

	public BasicObject() {
		/**
		 * Initialisation of every states
		 */
		NextState state1 = new NextState();
		NextState state2 = new NextState();
		state1.setStates(state2);
		state1.setName("State1");
		state2.setStates(state1);
		state2.setName("State2");
		
		/**
		 * Initialisation of the state machine
		 */
		StateMachine sm = new StateMachine(state1);
		
		/**
		 * Only one state machine for this object
		 */
		stateMachineList.add(sm);
		
	}
	
	/**
	 * Definition of basic states for the object
	 */
	class NextState extends State {
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
				return state2;
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
