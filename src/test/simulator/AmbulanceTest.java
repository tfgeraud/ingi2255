package test.simulator;

import events.AmbulanceBroken;
import events.Event;
import events.MobilisationOrder;
import simulator.Observer;
import simulator.events.DestinationOrder;
import simulator.events.StepDelimiter;
import simulator.simobjects.Ambulance;
import junit.framework.TestCase;

public class AmbulanceTest extends TestCase {
	private Ambulance ambulance; 
	private DummyObserver observer;

	protected void setUp() throws Exception {
		super.setUp();
		ambulance = new Ambulance("alpha1");
		observer = new DummyObserver();
		ambulance.attach(observer);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testBasic() {
		assertEquals(true, ambulance.getCurrentStateNames().contains("Free"));
		assertEquals(true, ambulance.getCurrentStateNames().contains("NotMoving"));
		
		/**
		 * Ensure that without events, nothing changes
		 */
		ambulance.accept(StepDelimiter.getInstance());
		ambulance.step();
		assertEquals(true, ambulance.getCurrentStateNames().contains("Free"));
		assertEquals(true, ambulance.getCurrentStateNames().contains("NotMoving"));	
	}
	
	public void testGoodMobilisation() {
		assertEquals(true, ambulance.getCurrentStateNames().contains("Free"));
		assertEquals(true, ambulance.getCurrentStateNames().contains("NotMoving"));
		
		/**
		 * On mobilization, ambulance get mobilized
		 */
		ambulance.accept(new MobilisationOrder("incident1", "pos1", "alpha1"));
		ambulance.accept(StepDelimiter.getInstance());
		ambulance.step();
		assertEquals(true, ambulance.getCurrentStateNames().contains("Mobilized"));
		assertEquals(true, ambulance.getCurrentStateNames().contains("NotMoving"));	
	}
	
	public void testMobilisationToWrongAmbulance() {
		assertEquals(true, ambulance.getCurrentStateNames().contains("Free"));
		assertEquals(true, ambulance.getCurrentStateNames().contains("NotMoving"));
		
		/**
		 * On mobilization, ambulance get mobilized
		 */
		ambulance.accept(new MobilisationOrder("incident1", "pos1", "alpha2"));
		ambulance.accept(StepDelimiter.getInstance());
		ambulance.step();
		assertEquals(true, ambulance.getCurrentStateNames().contains("Free"));
		assertEquals(true, ambulance.getCurrentStateNames().contains("NotMoving"));	
	}
	
	public void testMoving() {
		assertEquals(true, ambulance.getCurrentStateNames().contains("Free"));
		assertEquals(true, ambulance.getCurrentStateNames().contains("NotMoving"));
		
		ambulance.accept(new DestinationOrder("unittester"));
		ambulance.step();
		assertEquals(true, ambulance.getCurrentStateNames().contains("Free"));
		assertEquals(true, ambulance.getCurrentStateNames().contains("Moving"));		
	}
	
	
	public void testBroken() {
		assertEquals(true, ambulance.getCurrentStateNames().contains("Free"));
		assertEquals(true, ambulance.getCurrentStateNames().contains("NotMoving"));
		
		ambulance.accept(new AmbulanceBroken("alpha1"));
		ambulance.step();
		assertEquals(true, ambulance.getCurrentStateNames().contains("Broken"));
		assertEquals(true, ambulance.getCurrentStateNames().contains("NotMoving"));
		
	}
	
	public void testDestinationOrderWhileBroken() {
		assertEquals(true, ambulance.getCurrentStateNames().contains("Free"));
		assertEquals(true, ambulance.getCurrentStateNames().contains("NotMoving"));
		
		ambulance.accept(new AmbulanceBroken("alpha1"));
		ambulance.step();
		assertEquals(true, ambulance.getCurrentStateNames().contains("Broken"));
		assertEquals(true, ambulance.getCurrentStateNames().contains("NotMoving"));
		
		/**
		 * On destination order, ambulance doesn't move
		 */
		ambulance.accept(new DestinationOrder("unittester"));
		ambulance.accept(StepDelimiter.getInstance());
		ambulance.step();
		assertEquals(true, ambulance.getCurrentStateNames().contains("Broken"));
		assertEquals(true, ambulance.getCurrentStateNames().contains("NotMoving"));	
	}
	
	class DummyObserver implements Observer {
		public Event receivedEvent = null;

		public void accept(Event event) {
			receivedEvent = event;
		}
	}

}
