package test.simulator;

import junit.framework.Assert;
import junit.framework.TestCase;

import simulator.PosImpl;
import simulator.events.DestinationOrder;
import simulator.events.NewPosition;
import simulator.events.StepDelimiter;
import simulator.simobjects.Ambulance;
import simulator.simobjects.Incident;
import simulator.simobjects.Map;
import events.AmbulanceBroken;
import events.MobilisationOrder;

public class AmbulanceTest extends TestCase {
	private Ambulance ambulance; 
	private DummyObserver observer;
	private Map map;
	private Incident incident;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.map = new Map("simulatorMap", 10, 10);
		this.ambulance = new Ambulance("alpha1", this.map, new PosImpl(0,0));
		this.observer = new DummyObserver();
		this.ambulance.attach(this.observer);
		
		this.incident = new Incident("incident1", new PosImpl(3,0));
		this.ambulance.attach(this.incident, NewPosition.class);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testBasic() {
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Free"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));
		
		/**
		 * Ensure that without events, nothing changes
		 */
		this.ambulance.accept(StepDelimiter.getInstance());
		this.ambulance.step();
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Free"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));	
	}
	
	public void testGoodMobilisation() {
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Free"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));
		
		/**
		 * On mobilization, ambulance get mobilized
		 */
		this.ambulance.accept(new MobilisationOrder("incident1", "(0,0)", "alpha1"));
		this.ambulance.accept(StepDelimiter.getInstance());
		this.ambulance.step();
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Mobilized"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));	
	}
	
	public void testMobilisationToWrongAmbulance() {
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Free"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));
		
		/**
		 * On mobilization, ambulance get mobilized
		 */
		this.ambulance.accept(new MobilisationOrder("incident1", "pos1", "alpha2"));
		this.ambulance.accept(StepDelimiter.getInstance());
		this.ambulance.step();
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Free"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));	
	}
	
	public void testMoving() {
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Free"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));
		
		/**
		 * Receive destination and move one position at once
		 */
		this.ambulance.accept(new DestinationOrder("unittester", new PosImpl(1,0)));
		this.ambulance.accept(StepDelimiter.getInstance());
		this.ambulance.step();
		Assert.assertEquals(new PosImpl(1,0), this.ambulance.getCurrentPos());
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Free"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Moving"));
		
		/**
		 * Ambulance arrived at destination
		 */
		this.ambulance.accept(StepDelimiter.getInstance());
		this.ambulance.step();
		Assert.assertEquals(new PosImpl(1,0), this.ambulance.getCurrentPos());
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Free"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));
	}
	
	
	public void testBroken() {
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Free"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));
		
		this.ambulance.accept(new AmbulanceBroken("alpha1"));
		this.ambulance.step();
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Broken"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));
		
	}
	
	public void testDestinationOrderWhileBroken() {
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Free"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));
		
		this.ambulance.accept(new AmbulanceBroken("alpha1"));
		this.ambulance.step();
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Broken"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));
		
		/**
		 * On destination order, ambulance doesn't move
		 */
		this.ambulance.accept(new DestinationOrder("unittester", new PosImpl(0,0)));
		this.ambulance.accept(StepDelimiter.getInstance());
		this.ambulance.step();
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Broken"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));	
	}
	/**
	 * Integration test, ambulance receives mobilisation order, move to position of incident
	 * then resolve the incident
	 */
	public void testResolvingMobilisationOrder() {
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Free"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));
		
		/**
		 * On mobilization, ambulance get mobilized
		 */
		this.ambulance.accept(new MobilisationOrder("incident1", "(3,0)", "alpha1"));
		this.ambulance.accept(StepDelimiter.getInstance());
		this.ambulance.step();
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Mobilized"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("NotMoving"));
		
		/**
		 * Start moving at once
		 * 
		 */
		this.incident.accept(StepDelimiter.getInstance());
		this.ambulance.accept(StepDelimiter.getInstance());
		this.incident.step();
		this.ambulance.step();
		
		assertEquals(new PosImpl(1,0), this.ambulance.getCurrentPos());
		assertTrue(this.ambulance.getCurrentStateNames().contains("Mobilized"));
		assertTrue(this.ambulance.getCurrentStateNames().contains("Moving"));
		assertTrue(this.incident.getCurrentStateNames().contains("Occured"));
		
		/**
		 * Move toward the incident
		 */
		this.incident.accept(StepDelimiter.getInstance());
		this.ambulance.accept(StepDelimiter.getInstance());
		this.incident.step();
		this.ambulance.step();
		assertEquals(new PosImpl(2,0), this.ambulance.getCurrentPos());
		assertTrue(this.ambulance.getCurrentStateNames().contains("Mobilized"));
		assertTrue(this.ambulance.getCurrentStateNames().contains("Moving"));
		assertTrue(this.incident.getCurrentStateNames().contains("Occured"));
		
		/**
		 * Arrive on scene
		 */
		this.incident.accept(StepDelimiter.getInstance());
		this.ambulance.accept(StepDelimiter.getInstance());
		this.incident.step();
		this.ambulance.step();
		assertEquals(new PosImpl(3,0), this.ambulance.getCurrentPos());
		assertTrue(this.ambulance.getCurrentStateNames().contains("Mobilized"));
		assertTrue(this.ambulance.getCurrentStateNames().contains("Moving"));
		assertTrue(this.incident.getCurrentStateNames().contains("Occured"));
		
		/**
		 * Stop moving and resolve incident
		 */
		this.incident.accept(StepDelimiter.getInstance());
		this.ambulance.accept(StepDelimiter.getInstance());
		this.incident.step();
		this.ambulance.step();
		assertEquals(new PosImpl(3,0), this.ambulance.getCurrentPos());
		assertTrue(this.ambulance.getCurrentStateNames().contains("Mobilized"));
		assertTrue(this.ambulance.getCurrentStateNames().contains("NotMoving"));
		assertTrue(this.incident.getCurrentStateNames().contains("Occured"));
		
		/**
		 * Incident is being resolved
		 */
		this.incident.accept(StepDelimiter.getInstance());
		this.ambulance.accept(StepDelimiter.getInstance());
		this.incident.step();
		this.ambulance.step();
		assertEquals(new PosImpl(3,0), this.ambulance.getCurrentPos());
		assertTrue(this.ambulance.getCurrentStateNames().contains("Mobilized"));
		assertTrue(this.ambulance.getCurrentStateNames().contains("NotMoving"));
		assertTrue(this.incident.getCurrentStateNames().contains("Occured"));
		
		/**
		 * Incident is resolved
		 */
		this.incident.accept(StepDelimiter.getInstance());
		this.ambulance.accept(StepDelimiter.getInstance());
		this.incident.step();
		this.ambulance.step();
		assertEquals(new PosImpl(3,0), this.ambulance.getCurrentPos());
		assertTrue(this.ambulance.getCurrentStateNames().contains("Mobilized"));
		assertTrue(this.ambulance.getCurrentStateNames().contains("NotMoving"));
		assertTrue(this.incident.getCurrentStateNames().contains("Resolved"));
	}
	


}
