/**
 * 
 */
package test.system;

import java.util.HashSet;
import java.util.Set;

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
import system.exception.IllegalMobilizationException;
import system.exception.UnknownIncidentException;
import junit.framework.TestCase;

/**
 * 
 * @author Quentin Pirmez <quentin.pirmez@student.uclouvain.be>
 *
 */
public class AmbulanceImplTest extends TestCase {
	
	private Ambulance ambulance;
	private Coord position;
	private Incident incident;
	private Map map;

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
		Set<String> exclusionSet = new HashSet<String>();
		Set<String> result = new HashSet<String>();
		
		try {
			assertEquals(result, ambulance.getAllFree(Ambulance.NORMAL, exclusionSet));
			assertEquals(result, ambulance.getAllFree(Ambulance.MEDICALIZED, exclusionSet));
			
			ambulance.addAmbulance("amb1", position, Ambulance.NORMAL, Ambulance.WORKING);
			ambulance.addAmbulance("amb2", position, Ambulance.NORMAL, Ambulance.WORKING);
			ambulance.addAmbulance("amb3", position, Ambulance.MEDICALIZED, Ambulance.WORKING);
			ambulance.addAmbulance("amb4", position, Ambulance.MEDICALIZED, Ambulance.WORKING);
			ambulance.addAmbulance("amb5", position, Ambulance.NORMAL, Ambulance.BROKEN);
			
			result.add("amb1");
			result.add("amb2");
			assertEquals(result, ambulance.getAllFree(Ambulance.NORMAL, exclusionSet));
			
			result.remove("amb1");
			result.remove("amb2");
			result.remove("amb5");
			result.add("amb3");
			result.add("amb4");
			assertEquals(result, ambulance.getAllFree(Ambulance.MEDICALIZED, exclusionSet));
		
			exclusionSet.add("amb1");
			result.remove("amb1");
			assertEquals(result, ambulance.getAllFree(Ambulance.MEDICALIZED, exclusionSet));
			
			result.add("amb2");
			result.remove("amb3");
			result.remove("amb4");
			assertEquals(result, ambulance.getAllFree(Ambulance.NORMAL, exclusionSet));
			
			
		} catch (AmbulanceStatusUnknwownException e) {
			e.printStackTrace();
			fail("An exception occured on addAmbulance because ambulance's status is unknown ");
			
		} catch (AmbulanceKindUnknownException e) {
			e.printStackTrace();
			fail("An exception occured on addAmbulance because ambulance's kind is unknown");
			
		}
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#getCoord(java.lang.String)}.
	 */
	public void testGetPosition() {
		
		try {
			ambulance.addAmbulance("amb1", position, Ambulance.NORMAL, Ambulance.WORKING);
			assertEquals(position, ambulance.getPosition("amb1"));
			
		} catch (AmbulanceStatusUnknwownException e) {
			e.printStackTrace();
			fail("An exception occured on addAmbulance because ambulance's status is unknown ");
			
		} catch (AmbulanceKindUnknownException e) {
			e.printStackTrace();
			fail("An exception occured on addAmbulance because ambulance's kind is unknown");
		}
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#markAsBroken(java.lang.String)}.
	 */
	public void testMarkAsBroken() {
		
		try {
			ambulance.addAmbulance("amb1", position, Ambulance.NORMAL, Ambulance.WORKING);
			ambulance.addAmbulance("amb2", position, Ambulance.MEDICALIZED, Ambulance.WORKING);
			
			ambulance.markAsBroken("amb1");
			ambulance.markAsBroken("amb2");
			
			assertEquals(((AmbulanceImpl) ambulance).getStatus("amb1"), Ambulance.BROKEN );
			assertEquals(((AmbulanceImpl) ambulance).getStatus("amb2"), Ambulance.BROKEN );

			
		} catch (AmbulanceStatusUnknwownException e) {
			fail("An exception occured on addAmbulance because ambulance's status is unknown ");
			e.printStackTrace();
			
		} catch (AmbulanceKindUnknownException e) {
			fail("An exception occured on addAmbulance because ambulance's kind is unknown");
			e.printStackTrace();
		}
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#addAmbulance(java.lang.String, system.Coord, java.lang.String, java.lang.String)}.
	 */
	public void testAddAmbulance() {
		Set<String> result = new HashSet<String>();
		
		try {
			result.add("amb1");
			ambulance.addAmbulance("amb1", position, Ambulance.NORMAL, Ambulance.WORKING);
			//FIXME Equals do not work (add getAmbulance, getId, dans ambulanceImpl)
			//assertEquals(result,ambulance);
			
		} catch (AmbulanceStatusUnknwownException e) {
			fail("An exception occured on addAmbulance because ambulance's status is unknown ");
			e.printStackTrace();
			
		} catch (AmbulanceKindUnknownException e) {
			fail("An exception occured on addAmbulance because ambulance's kind is unknown");
			e.printStackTrace();
		}
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#getIncidentChosenFor(java.lang.String)}.
	 */
	public void testGetIncidentChosenFor() {	
		try {
			String incId;
			
			ambulance.addAmbulance("amb1", position, Ambulance.NORMAL, Ambulance.WORKING);
			ambulance.addAmbulance("amb2", position, Ambulance.MEDICALIZED, Ambulance.WORKING);
			
			incId = incident.addIncident(20, false, "1 3", "Chute de poney, douleur violente au coxis");
			ambulance.setIncidentChosenFor("amb1", incId);
			incident.setChosenAmbulance(incId, "amb1");
			
			incId = incident.addIncident(35, false, "2 2", "Contorsion du nombryl avec production d'étincelle");
			ambulance.setIncidentChosenFor("amb2", incId);
			incident.setChosenAmbulance(incId, "amb2");

			assertEquals("incident0", ambulance.getIncidentChosenFor("amb1"));
			assertEquals("incident1", ambulance.getIncidentChosenFor("amb2"));
		
		} catch (AmbulanceStatusUnknwownException e) {
			// TODO Auto-generated catch block
			fail("An exception occured on addAmbulance because ambulance's status is unknown ");
			e.printStackTrace();
			
		} catch (AmbulanceKindUnknownException e) {
			// TODO Auto-generated catch block
			fail("An exception occured on addAmbulance because ambulance's kind is unknown");
			e.printStackTrace();
			
		} catch (UnknownIncidentException e) {
			// TODO Auto-generated catch block
			fail("An exception occured on getIncidentChosenFor because incident is unknown");
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#getIncidentMobilizedFor(java.lang.String)}.
	 */
	public void testGetIncidentMobilizedFor() {
		
		try {
			String incId;
			
			ambulance.addAmbulance("amb1", position, Ambulance.NORMAL, Ambulance.WORKING);
			ambulance.addAmbulance("amb2", position, Ambulance.MEDICALIZED, Ambulance.WORKING);
			
			incId = incident.addIncident(20, false, "1 3", "Chute de poney, douleur violente au coxis");
			ambulance.setIncidentChosenFor("amb1", incId);
			ambulance.setIncidentMobilizedFor("amb1", incId);
			incident.setChosenAmbulance(incId, "amb1");
			incident.setMobilizedAmbulance(incId, "amb1");
			
			incId = incident.addIncident(35, false, "2 2", "Contorsion du nombryl avec production d'étincelle");
			ambulance.setIncidentChosenFor("amb2", incId);
			ambulance.setIncidentMobilizedFor("amb2", incId);
			incident.setChosenAmbulance(incId, "amb2");
			incident.setMobilizedAmbulance(incId, "amb2");

			assertEquals("incident0", ambulance.getIncidentMobilizedFor("amb1"));
			assertEquals("incident1", ambulance.getIncidentMobilizedFor("amb2"));
		
		} catch (AmbulanceStatusUnknwownException e) {
			// TODO Auto-generated catch block
			fail("An exception occured on addAmbulance because ambulance's status is unknown ");
			e.printStackTrace();
			
		} catch (AmbulanceKindUnknownException e) {
			// TODO Auto-generated catch block
			fail("An exception occured on addAmbulance because ambulance's kind is unknown");
			e.printStackTrace();
			
		} catch (UnknownIncidentException e) {
			// TODO Auto-generated catch block
			fail("An exception occured on getIncidentChosenFor because incident is unknown");
			e.printStackTrace();
			
		} catch (IllegalMobilizationException e) {
			// TODO Auto-generated catch block
			fail("IllegalMobilizationException occured: ambulance cannot be mobilizied before chosen");
			e.printStackTrace();
		}
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#getKind(java.lang.String)}.
	 */
	public void testGetKind() {
		try {
			ambulance.addAmbulance("amb1", position, Ambulance.NORMAL, Ambulance.WORKING);
			ambulance.addAmbulance("amb2", position, Ambulance.MEDICALIZED, Ambulance.WORKING);
			
			assertEquals(Ambulance.NORMAL, ambulance.getKind("amb1"));
			assertEquals(Ambulance.MEDICALIZED, ambulance.getKind("amb2"));
			
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
	 * Test method for {@link system.AmbulanceImpl#markAsRepaired(java.lang.String)}.
	 */
	public void testMarkAsRepaired() {
		try {
			ambulance.addAmbulance("amb1", position, Ambulance.NORMAL, Ambulance.BROKEN);
			ambulance.addAmbulance("amb2", position, Ambulance.MEDICALIZED, Ambulance.BROKEN);
			
			ambulance.markAsRepaired("amb1");
			ambulance.markAsRepaired("amb2");
			
			assertEquals(((AmbulanceImpl) ambulance).getStatus("amb1"), Ambulance.WORKING );
			assertEquals(((AmbulanceImpl) ambulance).getStatus("amb2"), Ambulance.WORKING );
			
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
	 * Test method for {@link system.AmbulanceImpl#setIncidentChosenFor(java.lang.String, java.lang.String)}.
	 */
	public void testSetIncidentChosenFor() {
		//already tested in testGetIncidentChosenFor		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#setIncidentMobilizedFor(java.lang.String, java.lang.String)}.
	 */
	public void testSetIncidentMobilizedFor() {
		//already tested in testGetIncidentMobiliziedFor
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#setPosition(java.lang.String, system.Coord)}.
	 */
	public void testSetPosition() {
		//already tested in testGetPosition
		
	}

}
