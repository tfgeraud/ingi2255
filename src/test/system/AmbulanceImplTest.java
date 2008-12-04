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
		  map = new MapImpl(5,5);
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
			
			result.add("Amb1");
			result.add("Amb2");
			result.add("Amb5");
			System.out.println(ambulance.getAllFree(Ambulance.NORMAL, exclusionSet));
			assertEquals(result, ambulance.getAllFree(Ambulance.NORMAL, exclusionSet));
			// Equals do not work
			
			result.remove("Amb1");
			result.remove("Amb2");
			result.remove("Amb5");
			result.add("Amb3");
			result.add("Amb4");
			System.out.println(ambulance.getAllFree(Ambulance.MEDICALIZED, exclusionSet));
			assertEquals(result, ambulance.getAllFree(Ambulance.MEDICALIZED, exclusionSet));
			// Equals do not work
		
			exclusionSet.add("Amb1");
			result.remove("Amb1");
			assertEquals(result, ambulance.getAllFree(Ambulance.NORMAL, exclusionSet));
			System.out.println(ambulance.getAllFree(Ambulance.NORMAL, exclusionSet));
			assertEquals(result, ambulance.getAllFree(Ambulance.MEDICALIZED, exclusionSet));
			System.out.println(ambulance.getAllFree(Ambulance.MEDICALIZED, exclusionSet));
			
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

	/**
	 * Test method for {@link system.AmbulanceImpl#getCoord(java.lang.String)}.
	 */
	public void testGetCoord() {
		try {
			ambulance.addAmbulance("Amb1", position, Ambulance.NORMAL, Ambulance.WORKING);
			assertEquals(position, ambulance.getCoord("Amb1"));
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

	/**
	 * Test method for {@link system.AmbulanceImpl#markAsBroken(java.lang.String)}.
	 */
	public void testMarkAsBroken() {
		try {
			ambulance.addAmbulance("Amb1", position, Ambulance.NORMAL, Ambulance.WORKING);
			assertEquals(ambulance.getStatus("Amb1"), ambulance.markAsBroken("Amb1"));
		} catch (AmbulanceStatusUnknwownException e) {
			// TODO Auto-generated catch block
			fail("An exception occured on addAmbulance because ambulance's status is unknown ");
			e.printStackTrace();
		} catch (AmbulanceKindUnknownException e) {
			// TODO Auto-generated catch block
			fail("An exception occured on addAmbulance because ambulance's kind is unknown");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#addAmbulance(java.lang.String, system.Coord, java.lang.String, java.lang.String)}.
	 */
	public void testAddAmbulance() {
		LinkedList<String> result = new LinkedList<String>();
		assertEquals(result,ambulance.addAmbulance("Amb1", position, kind, status))
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#getIncidentChosenFor(java.lang.String)}.
	 */
	public void testGetIncidentChosenFor() {
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#getIncidentMobilizedFor(java.lang.String)}.
	 */
	public void testGetIncidentMobilizedFor() {
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#getKind(java.lang.String)}.
	 */
	public void testGetKind() {
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#markAsRepaired(java.lang.String)}.
	 */
	public void testMarkAsRepaired() {
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#setIncidentChosenFor(java.lang.String, java.lang.String)}.
	 */
	public void testSetIncidentChosenFor() {
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#setIncidentMobilizedFor(java.lang.String, java.lang.String)}.
	 */
	public void testSetIncidentMobilizedFor() {
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#setPosition(java.lang.String, system.Coord)}.
	 */
	public void testSetPosition() {
		
	}

}
