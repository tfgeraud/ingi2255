package test.simulator;

import events.AmbulanceBroken;
import events.Event;
import events.MobilisationOrder;
import simulator.Observable;
import simulator.Observer;
import simulator.Pos;
import simulator.PosImpl;
import simulator.events.DestinationOrder;
import simulator.events.StepDelimiter;
import simulator.simobjects.Ambulance;
import simulator.simobjects.Map;
import junit.framework.TestCase;

public class AmbulanceTest extends TestCase {
	private Ambulance ambulance; 
	private DummyObserver observer;
	private Map map;

	protected void setUp() throws Exception {
		super.setUp();
		map = new Map("simulatorMap", 10, 10);
		ambulance = new Ambulance("alpha1", map, new PosImpl(0,0));
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
		ambulance.accept(new MobilisationOrder("incident1", "(0,0)", "alpha1"));
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
		
		ambulance.accept(new DestinationOrder("unittester", new PosImpl(0,0)));
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
		ambulance.accept(new DestinationOrder("unittester", new PosImpl(0,0)));
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

		public void disconnect(Observable observable) {
			// Not used
			
		}

		public void disconnect() {
			// Not used
			
		}

		public void observing(Observable observable) {
			// Not used
			
		}
	}

}
