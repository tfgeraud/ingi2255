package test.simulator;

import simulator.events.StepDelimiter;
import events.Event;
import junit.framework.TestCase;

public class EventTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testEventToString() {
		Event e1 = new Event("UnitTester");
		assertEquals("events.Event sentBy:UnitTester", e1.toString());
		
		Event e2 = StepDelimiter.getInstance();
		assertEquals("simulator.events.StepDelimiter sentBy:simulator", e2.toString());
		
		
	}

}
