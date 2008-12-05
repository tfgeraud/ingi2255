package test.simulator;

import simulator.events.StepDelimiter;
import events.Event;
import junit.framework.Assert;
import junit.framework.TestCase;

public class EventTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testEventToString() {
		Event e1 = new Event("UnitTester");
		Assert.assertEquals("events.Event sentBy:UnitTester", e1.toString());
		
		Event e2 = StepDelimiter.getInstance();
		Assert.assertEquals("simulator.events.StepDelimiter sentBy:simulator", e2.toString());
		
		
	}

}
