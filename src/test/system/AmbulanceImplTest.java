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
import junit.framework.Assert;
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
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		  this.ambulance = new AmbulanceImpl();
		  this.position = new CoordImpl(1,1);
		  this.map = new MapImpl(5,5);
		  this.incident = new IncidentImpl(this.map);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
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
			Assert.assertEquals(result, this.ambulance.getAllFree(Ambulance.NORMAL, exclusionSet));
			Assert.assertEquals(result, this.ambulance.getAllFree(Ambulance.MEDICALIZED, exclusionSet));
			
			this.ambulance.addAmbulance("amb1", this.position, Ambulance.NORMAL, Ambulance.WORKING);
			this.ambulance.addAmbulance("amb2", this.position, Ambulance.NORMAL, Ambulance.WORKING);
			this.ambulance.addAmbulance("amb3", this.position, Ambulance.MEDICALIZED, Ambulance.WORKING);
			this.ambulance.addAmbulance("amb4", this.position, Ambulance.MEDICALIZED, Ambulance.WORKING);
			this.ambulance.addAmbulance("amb5", this.position, Ambulance.NORMAL, Ambulance.BROKEN);
			
			result.add("amb1");
			result.add("amb2");
			Assert.assertEquals(result, this.ambulance.getAllFree(Ambulance.NORMAL, exclusionSet));
			
			result.remove("amb1");
			result.remove("amb2");
			result.remove("amb5");
			result.add("amb3");
			result.add("amb4");
			Assert.assertEquals(result, this.ambulance.getAllFree(Ambulance.MEDICALIZED, exclusionSet));
		
			exclusionSet.add("amb1");
			result.remove("amb1");
			Assert.assertEquals(result, this.ambulance.getAllFree(Ambulance.MEDICALIZED, exclusionSet));
			
			result.add("amb2");
			result.remove("amb3");
			result.remove("amb4");
			Assert.assertEquals(result, this.ambulance.getAllFree(Ambulance.NORMAL, exclusionSet));
			
			
		} catch (AmbulanceStatusUnknwownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail("An exception occured on addAmbulance because ambulance's status is unknown ");
		} catch (AmbulanceKindUnknownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail("An exception occured on addAmbulance because ambulance's kind is unknown");
			
		}
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#getPosition(java.lang.String)}.
	 */
	public void testGetCoord() {
		
		try {
			this.ambulance.addAmbulance("amb1", this.position, Ambulance.NORMAL, Ambulance.WORKING);
			Assert.assertEquals(this.position, this.ambulance.getPosition("amb1"));
			
		} catch (AmbulanceStatusUnknwownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail("An exception occured on addAmbulance because ambulance's status is unknown ");
			
		} catch (AmbulanceKindUnknownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail("An exception occured on addAmbulance because ambulance's kind is unknown");
		}
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#markAsBroken(java.lang.String)}.
	 */
	public void testMarkAsBroken() {
		
		try {
			this.ambulance.addAmbulance("amb1", this.position, Ambulance.NORMAL, Ambulance.WORKING);
			this.ambulance.markAsBroken("amb1");
			
			Assert.assertEquals(((AmbulanceImpl) this.ambulance).getStatus("amb1"), Ambulance.BROKEN );
			
		} catch (AmbulanceStatusUnknwownException e) {
			// TODO Auto-generated catch block
			Assert.fail("An exception occured on addAmbulance because ambulance's status is unknown ");
			e.printStackTrace();
			
		} catch (AmbulanceKindUnknownException e) {
			// TODO Auto-generated catch block
			Assert.fail("An exception occured on addAmbulance because ambulance's kind is unknown");
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
			this.ambulance.addAmbulance("amb1", this.position, Ambulance.NORMAL, Ambulance.WORKING);
			//FIXME Equals do not work (add getAmbulance, getId, dans ambulanceImpl)
			//assertEquals(result,ambulance);
			
			
		} catch (AmbulanceStatusUnknwownException e) {
			// TODO Auto-generated catch block
			Assert.fail("An exception occured on addAmbulance because ambulance's status is unknown ");
			e.printStackTrace();
			
		} catch (AmbulanceKindUnknownException e) {
			// TODO Auto-generated catch block
			Assert.fail("An exception occured on addAmbulance because ambulance's kind is unknown");
			e.printStackTrace();
		}
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#getIncidentChosenFor(java.lang.String)}.
	 */
	public void testGetIncidentChosenFor() {	
		try {
			String incId;
			
			this.ambulance.addAmbulance("amb1", this.position, Ambulance.NORMAL, Ambulance.WORKING);
			this.ambulance.addAmbulance("amb2", this.position, Ambulance.MEDICALIZED, Ambulance.WORKING);
			
			incId = this.incident.addIncident(20, false, "1 3", "Chute de poney, douleur violente au coxis");
			this.ambulance.setIncidentChosenFor("amb1", incId);
			this.incident.setChosenAmbulance(incId, "amb1");
			
			incId = this.incident.addIncident(35, false, "2 2", "Contorsion du nombryl avec production d'�tincelle");
			this.ambulance.setIncidentChosenFor("amb2", incId);
			this.incident.setChosenAmbulance(incId, "amb2");

			Assert.assertEquals("incident0", this.ambulance.getIncidentChosenFor("amb1"));
			Assert.assertEquals("incident1", this.ambulance.getIncidentChosenFor("amb2"));
		
		} catch (AmbulanceStatusUnknwownException e) {
			// TODO Auto-generated catch block
			Assert.fail("An exception occured on addAmbulance because ambulance's status is unknown ");
			e.printStackTrace();
			
		} catch (AmbulanceKindUnknownException e) {
			// TODO Auto-generated catch block
			Assert.fail("An exception occured on addAmbulance because ambulance's kind is unknown");
			e.printStackTrace();
			
		} catch (UnknownIncidentException e) {
			// TODO Auto-generated catch block
			Assert.fail("An exception occured on getIncidentChosenFor because incident is unknown");
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Test method for {@link system.AmbulanceImpl#getIncidentMobilizedFor(java.lang.String)}.
	 */
	public void testGetIncidentMobilizedFor() {
		
		try {
			String incId;
			
			this.ambulance.addAmbulance("amb1", this.position, Ambulance.NORMAL, Ambulance.WORKING);
			this.ambulance.addAmbulance("amb2", this.position, Ambulance.MEDICALIZED, Ambulance.WORKING);
			
			incId = this.incident.addIncident(20, false, "1 3", "Chute de poney, douleur violente au coxis");
			this.ambulance.setIncidentChosenFor("amb1", incId);
			this.ambulance.setIncidentMobilizedFor("amb1", incId);
			this.incident.setChosenAmbulance(incId, "amb1");
			this.incident.setMobilizedAmbulance(incId, "amb1");
			
			incId = this.incident.addIncident(35, false, "2 2", "Contorsion du nombryl avec production d'�tincelle");
			this.ambulance.setIncidentChosenFor("amb2", incId);
			this.ambulance.setIncidentMobilizedFor("amb2", incId);
			this.incident.setChosenAmbulance(incId, "amb2");
			this.incident.setMobilizedAmbulance(incId, "amb2");

			Assert.assertEquals("incident0", this.ambulance.getIncidentMobilizedFor("amb1"));
			Assert.assertEquals("incident1", this.ambulance.getIncidentMobilizedFor("amb2"));
		
		} catch (AmbulanceStatusUnknwownException e) {
			// TODO Auto-generated catch block
			Assert.fail("An exception occured on addAmbulance because ambulance's status is unknown ");
			e.printStackTrace();
			
		} catch (AmbulanceKindUnknownException e) {
			// TODO Auto-generated catch block
			Assert.fail("An exception occured on addAmbulance because ambulance's kind is unknown");
			e.printStackTrace();
			
		} catch (UnknownIncidentException e) {
			// TODO Auto-generated catch block
			Assert.fail("An exception occured on getIncidentChosenFor because incident is unknown");
			e.printStackTrace();
			
		} catch (IllegalMobilizationException e) {
			// TODO Auto-generated catch block
			Assert.fail("IllegalMobilizationException occured: ambulance cannot be mobilizied before chosen");
			e.printStackTrace();
		}
		
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
