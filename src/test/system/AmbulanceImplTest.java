/**
 * 
 */
package test.system;

import java.util.LinkedList;

import system.Ambulance;
import system.AmbulanceImpl;
import system.Coord;
import system.CoordImpl;
import system.Incident;
import system.IncidentImpl;
import system.Map;
import system.MapImpl;
import system.exception.AmbulanceKindUnknownException;
import system.exception.AmbulanceStatusUnknwownException;
import junit.framework.TestCase;

/**
 * @author Quentin Pirmez
 *
 */
public class AmbulanceImplTest extends TestCase {
	
	private Ambulance ambulance;
	private Incident incident;
	private Map map;
	private Coord position;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		  ambulance = new AmbulanceImpl();
		  position = new CoordImpl(1,1);
		  map = new MapImpl();
		  incident = new IncidentImpl(map);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#getAllFree(java.lang.String, java.util.LinkedList)}.
	 */
	public void testGetAllFree() {
		LinkedList<String> exclusionSet = new LinkedList<String>();
		LinkedList<String> result = new LinkedList<String>();
		
		try {
			incident.addIncident(20, false, "1 1", "no description");
			
			assertEquals(result, ambulance.getAllFree(Ambulance.NORMAL, exclusionSet));
			assertEquals(result, ambulance.getAllFree(Ambulance.MEDICALIZED, exclusionSet));
			
			ambulance.addAmbulance("Amb1", position, Ambulance.NORMAL, Ambulance.WORKING);
			ambulance.addAmbulance("Amb2", position, Ambulance.NORMAL, Ambulance.WORKING);
			ambulance.addAmbulance("Amb3", position, Ambulance.MEDICALIZED, Ambulance.WORKING);
			ambulance.addAmbulance("Amb4", position, Ambulance.MEDICALIZED, Ambulance.WORKING);
			ambulance.addAmbulance("Amb5", position, Ambulance.NORMAL, Ambulance.BROKEN);
			
			exclusionSet.add("Amb1");
			assertEquals(result, ambulance.getAllFree(Ambulance.NORMAL, exclusionSet));
			assertEquals(result, ambulance.getAllFree(Ambulance.MEDICALIZED, exclusionSet));
		
			assertEquals(result, ambulance.getAllFree(Ambulance.NORMAL, exclusionSet));
			
		} catch (AmbulanceStatusUnknwownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("An exception occured on addAmbulance because ambulance's status is unknown ");
		} catch (AmbulanceKindUnknownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("An exception occured on addAmbulance because ambulance's kind is unknown");
			
		}
	}



}
