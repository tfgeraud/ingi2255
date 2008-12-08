package test.simulator;

import junit.framework.Assert;
import junit.framework.TestCase;
import simulator.PosImpl;
import simulator.events.DestinationOrder;
import simulator.events.StepDelimiter;
import simulator.simobjects.Ambulance;
import simulator.simobjects.Map;
import events.AmbulanceBroken;
import events.MobilisationOrder;

public class AmbulanceTest extends TestCase {
	private Ambulance ambulance; 
	private DummyObserver observer;
	private Map map;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.map = new Map("simulatorMap", 10, 10);
		this.ambulance = new Ambulance("alpha1", this.map, new PosImpl(0,0));
		this.observer = new DummyObserver();
		this.ambulance.attach(this.observer);
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
		
		this.ambulance.accept(new DestinationOrder("unittester", new PosImpl(0,0)));
		this.ambulance.step();
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Free"));
		Assert.assertEquals(true, this.ambulance.getCurrentStateNames().contains("Moving"));		
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
	


}
