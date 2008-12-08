package test.simulator;

import simulator.simobjects.SimObject;
import junit.framework.TestCase;

public class SimObjectImplTest extends TestCase {
	private SimObject simObject; 
	private DummyObservable dummyObservable; 

	protected void setUp() throws Exception {
		super.setUp();
		simObject = new BasicObject("unittest");
		dummyObservable = new DummyObservable();
		dummyObservable.attach(simObject);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testObserving() {
		assertEquals(1, dummyObservable.observerSet.size());
	}
	
	public void testDisconnect() {
		
		simObject.disconnect();
		assertEquals(0, dummyObservable.observerSet.size());
	}



}
