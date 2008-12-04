package test.simulator;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import events.Event;
import simulator.Observer;
import simulator.events.ChangingToState;
import simulator.events.EventNotUnderstood;
import simulator.events.StepDelimiter;
import simulator.simobjects.BasicObject;
import junit.framework.TestCase;

public class BasicObjectTest extends TestCase {
	private BasicObject object;
	private BasicObject object2;
	private DummyObserver observer;

	protected void setUp() throws Exception {
		super.setUp();
		object = new BasicObject("object");
		object2 = new BasicObject("object2");
		observer = new DummyObserver();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testStateChange() {
		
		assertEquals(true, object.getCurrentStateNames().contains("State1"));
		
		object.accept(StepDelimiter.getInstance());
		object.step();
		assertEquals(true, object.getCurrentStateNames().contains("State1"));
		
		object.accept(new BasicObject.PingPong("UnitTester"));
		object.accept(StepDelimiter.getInstance());
		object.step();
		assertEquals(true, object.getCurrentStateNames().contains("State2"));
	}
	
	public void testNotificationStateChange() {
		assertEquals(true, object.getCurrentStateNames().contains("State1"));
		
		/**
		 * Register an observer
		 */
		object.attach(observer);
		
		/**
		 * Send an event
		 */
		object.accept(new BasicObject.PingPong("UnitTester"));
		object.accept(StepDelimiter.getInstance());
		object.step();
		assertEquals(true, object.getCurrentStateNames().contains("State2"));
		
		/**
		 * Verify that the event was correctly received
		 */
		assertNotNull(observer.receivedEvent);
		
	}
	
	public void testNoNotificationOnEventNotRegistered() {
		assertEquals(true, object.getCurrentStateNames().contains("State1"));
		
		/**
		 * Register an observer
		 */
		object.attach(observer, EventNotUnderstood.class);
		
		/**
		 * Send an event
		 */
		object.accept(new BasicObject.PingPong("UnitTester"));
		object.accept(StepDelimiter.getInstance());
		object.step();
		assertEquals(true, object.getCurrentStateNames().contains("State2"));
		
		/**
		 * Verify that the event was not received
		 */
		assertNull(observer.receivedEvent);
		
	}
	
	public void testNotificationOnSpecificEventRegistered() {
		assertEquals(true, object.getCurrentStateNames().contains("State1"));
		
		/**
		 * Register an observer
		 */
		object.attach(observer, BasicObject.PingPong.class);
		
		/**
		 * Send an event
		 */
		object.accept(new BasicObject.PingPong("UnitTester"));
		object.accept(StepDelimiter.getInstance());
		object.step();
		assertEquals(true, object.getCurrentStateNames().contains("State2"));
		
		/**
		 * Verify that the event was correctly received
		 */
		assertNotNull(observer.receivedEvent);
		
	}
	
	public void testEventNotUnderstood() {
		assertEquals(true, object.getCurrentStateNames().contains("State1"));
		
		/**
		 * Register an observer
		 */
		object.attach(observer);
		
		/**
		 * Send an event
		 */
		object.accept(new Event("UnitTester"));
		object.accept(StepDelimiter.getInstance());
		object.step();
		assertEquals(true, object.getCurrentStateNames().contains("State1"));
		
		/**
		 * Verify that the event was not understood
		 */
		assertNotNull(observer.receivedEvent);
		assertEquals(observer.receivedEvent.getClass(), EventNotUnderstood.class);
	}
	
	
	public void testCommunicationBetweenObjects() {
		assertEquals(true, object.getCurrentStateNames().contains("State1"));
		assertEquals(true, object2.getCurrentStateNames().contains("State1"));
		
		/**
		 * Create a ping pong game ;-) from the changing state basic notification
		 */
		object.attach(object2, BasicObject.PingPong.class);
		object2.attach(object, BasicObject.PingPong.class);
		
		/**
		 * Start the game 
		 */
		object.accept(new BasicObject.PingPong("UnitTester"));
		
		/**
		 * Perform a computation step
		 */
		object.accept(StepDelimiter.getInstance());
		object2.accept(StepDelimiter.getInstance());
		object.step();
		object2.step();
		assertEquals(true, object.getCurrentStateNames().contains("State2"));
		assertEquals(true, object2.getCurrentStateNames().contains("State1"));
		
		/**
		 * Perform a computation step
		 */
		object.accept(StepDelimiter.getInstance());
		object2.accept(StepDelimiter.getInstance());
		object.step();
		object2.step();
		assertEquals(true, object.getCurrentStateNames().contains("State2"));
		assertEquals(true, object2.getCurrentStateNames().contains("State2"));
		
		/**
		 * Perform a computation step
		 */
		object.accept(StepDelimiter.getInstance());
		object2.accept(StepDelimiter.getInstance());
		object.step();
		object2.step();
		assertEquals(true, object.getCurrentStateNames().contains("State1"));
		assertEquals(true, object2.getCurrentStateNames().contains("State2"));

		/**
		 * Perform a computation step
		 */
		object.accept(StepDelimiter.getInstance());
		object2.accept(StepDelimiter.getInstance());
		object.step();
		object2.step();
		assertEquals(true, object.getCurrentStateNames().contains("State1"));
		assertEquals(true, object2.getCurrentStateNames().contains("State1"));	
	}

	
	public void testEventClassesMap() {
		@SuppressWarnings("unchecked")
		Map<Class, List<Observer>> eventObserverMap = new Hashtable<Class, List<Observer>>();
		eventObserverMap.put(ChangingToState.class, new Vector<Observer>());
		eventObserverMap.get(ChangingToState.class).add(new Observer(){

			public void accept(Event event) {
				// TODO Auto-generated method stub
				
			}});
		assertEquals(1, eventObserverMap.get(ChangingToState.class).size());
	}
	
	private class DummyObserver implements Observer {
		public Event receivedEvent = null;

		public void accept(Event event) {
			receivedEvent = event;
		}
	}

}
