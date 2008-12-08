package test.simulator;

import events.IncidentResolved;
import simulator.PosImpl;
import simulator.events.AmbulanceOnScene;
import simulator.events.NewPosition;
import simulator.simobjects.Incident;
import junit.framework.TestCase;

public class IncidentTest extends TestCase {
	private DummySimObject simObject;
	private Incident incident;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		simObject = new DummySimObject();
		incident = new Incident("incident1", new PosImpl(10,10));
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAmbulanceAtIncidentPosition () {
		assert(incident.getCurrentStateNames().contains("Occured"));
		
		// Object move but not at incident position
		incident.accept(new NewPosition(simObject.getName(), new PosImpl(5,0), simObject));
		incident.step();
		
		// Make sure that the incident didn't change its state
		assert(incident.getCurrentStateNames().contains("Occured"));
		// Make sure that the incident is not listening to the object
		assertEquals(0, simObject.observerMap.size());
		assertEquals(0, simObject.observerSet.size());
		
		// Object move but not at incident position
		incident.accept(new NewPosition(simObject.getName(), new PosImpl(10,10), simObject));
		incident.step();
		
		// Make sure that the incident didn't change its state
		assert(incident.getCurrentStateNames().contains("Occured"));
		// Make sure that the incident now listens to events of type IncidentResolve
		assertNotNull(simObject.observerMap.get(IncidentResolved.class));
		//  ... but no other events
		assertEquals(1, simObject.observerMap.size());
		assertEquals(0, simObject.observerSet.size());
		// Make sure that the object received the event AmbulanceOnScene
		assertEquals(AmbulanceOnScene.class, simObject.receivedEvent.getClass());
		
		// Fake that the object is an ambulance an resolve the incident
		incident.accept(new IncidentResolved(incident.getName(), "fake_ambulance"));
		incident.step();
		
		// Make sure that the incident is now resolved
		assert(incident.getCurrentStateNames().contains("Resolved"));
		// Make sure that the incident listens to nothing anymore
		assertEquals(0, simObject.observerMap.size());
		assertEquals(0, simObject.observerSet.size());
	}

}
