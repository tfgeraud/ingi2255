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
import junit.framework.Assert;
import junit.framework.TestCase;

public class BasicObjectTest extends TestCase {
	private BasicObject object;
	private BasicObject object2;
	private DummyObserver observer;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.object = new BasicObject("object");
		this.object2 = new BasicObject("object2");
		this.observer = new DummyObserver();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testStateChange() {
		
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State1"));
		
		this.object.accept(StepDelimiter.getInstance());
		this.object.step();
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State1"));
		
		this.object.accept(new BasicObject.PingPong("UnitTester"));
		this.object.accept(StepDelimiter.getInstance());
		this.object.step();
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State2"));
	}
	
	public void testNotificationStateChange() {
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State1"));
		
		/**
		 * Register an observer
		 */
		this.object.attach(this.observer);
		
		/**
		 * Send an event
		 */
		this.object.accept(new BasicObject.PingPong("UnitTester"));
		this.object.accept(StepDelimiter.getInstance());
		this.object.step();
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State2"));
		
		/**
		 * Verify that the event was correctly received
		 */
		Assert.assertNotNull(this.observer.receivedEvent);
		
	}
	
	public void testNoNotificationOnEventNotRegistered() {
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State1"));
		
		/**
		 * Register an observer
		 */
		this.object.attach(this.observer, EventNotUnderstood.class);
		
		/**
		 * Send an event
		 */
		this.object.accept(new BasicObject.PingPong("UnitTester"));
		this.object.accept(StepDelimiter.getInstance());
		this.object.step();
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State2"));
		
		/**
		 * Verify that the event was not received
		 */
		Assert.assertNull(this.observer.receivedEvent);
		
	}
	
	public void testNotificationOnSpecificEventRegistered() {
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State1"));
		
		/**
		 * Register an observer
		 */
		this.object.attach(this.observer, BasicObject.PingPong.class);
		
		/**
		 * Send an event
		 */
		this.object.accept(new BasicObject.PingPong("UnitTester"));
		this.object.accept(StepDelimiter.getInstance());
		this.object.step();
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State2"));
		
		/**
		 * Verify that the event was correctly received
		 */
		Assert.assertNotNull(this.observer.receivedEvent);
		
	}
	
	public void testEventNotUnderstood() {
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State1"));
		
		/**
		 * Register an observer
		 */
		this.object.attach(this.observer);
		
		/**
		 * Send an event
		 */
		this.object.accept(new Event("UnitTester"));
		this.object.accept(StepDelimiter.getInstance());
		this.object.step();
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State1"));
		
		/**
		 * Verify that the event was not understood
		 */
		Assert.assertNotNull(this.observer.receivedEvent);
		Assert.assertEquals(this.observer.receivedEvent.getClass(), EventNotUnderstood.class);
	}
	
	
	public void testCommunicationBetweenObjects() {
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State1"));
		Assert.assertEquals(true, this.object2.getCurrentStateNames().contains("State1"));
		
		/**
		 * Create a ping pong game ;-) from the changing state basic notification
		 */
		this.object.attach(this.object2, BasicObject.PingPong.class);
		this.object2.attach(this.object, BasicObject.PingPong.class);
		
		/**
		 * Start the game 
		 */
		this.object.accept(new BasicObject.PingPong("UnitTester"));
		
		/**
		 * Perform a computation step
		 */
		this.object.accept(StepDelimiter.getInstance());
		this.object2.accept(StepDelimiter.getInstance());
		this.object.step();
		this.object2.step();
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State2"));
		Assert.assertEquals(true, this.object2.getCurrentStateNames().contains("State1"));
		
		/**
		 * Perform a computation step
		 */
		this.object.accept(StepDelimiter.getInstance());
		this.object2.accept(StepDelimiter.getInstance());
		this.object.step();
		this.object2.step();
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State2"));
		Assert.assertEquals(true, this.object2.getCurrentStateNames().contains("State2"));
		
		/**
		 * Perform a computation step
		 */
		this.object.accept(StepDelimiter.getInstance());
		this.object2.accept(StepDelimiter.getInstance());
		this.object.step();
		this.object2.step();
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State1"));
		Assert.assertEquals(true, this.object2.getCurrentStateNames().contains("State2"));

		/**
		 * Perform a computation step
		 */
		this.object.accept(StepDelimiter.getInstance());
		this.object2.accept(StepDelimiter.getInstance());
		this.object.step();
		this.object2.step();
		Assert.assertEquals(true, this.object.getCurrentStateNames().contains("State1"));
		Assert.assertEquals(true, this.object2.getCurrentStateNames().contains("State1"));	
	}

	
	public void testEventClassesMap() {
		@SuppressWarnings("unchecked")
		Map<Class, List<Observer>> eventObserverMap = new Hashtable<Class, List<Observer>>();
		eventObserverMap.put(ChangingToState.class, new Vector<Observer>());
		eventObserverMap.get(ChangingToState.class).add(this.observer);
		Assert.assertEquals(1, eventObserverMap.get(ChangingToState.class).size());
	}
}
