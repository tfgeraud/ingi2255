/**
 * 
 */
package test.system;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import system.Ambulance;
import system.Incident;
import system.IncidentImpl;
import system.Map;
import system.MapImpl;
import system.exception.UnknownIncidentException;

/**
 * @author Simon Busard <simon.busard@student.uclouvain.be>
 *
 */
public class IncidentImplTest {
	
	private Incident incidents;
	private Map map;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		map = new MapImpl();
		incidents = new IncidentImpl(map);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link system.IncidentImpl#IncidentImpl(system.Map)}.
	 */
	@Test
	public final void testIncidentImpl() {
	}

	/**
	 * Test method for {@link system.IncidentImpl#addIncident(int, boolean, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testAddIncident() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Broadway";
		String description = "grave";
		
		String incId = incidents.addIncident(age, pregnant, localisation, description);
		
		try {assertEquals(incidents.getAge(incId),age);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		try {assertEquals(incidents.getPregnant(incId),pregnant);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		try {assertEquals(incidents.getDescription(incId),description);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		try {assertEquals(incidents.getLocalisation(incId),localisation);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
	}

	/**
	 * Test method for {@link system.IncidentImpl#getAge(java.lang.String)}.
	 */
	@Test
	public final void testGetAge() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Broadway";
		String description = "grave";
		
		String incId = incidents.addIncident(age, pregnant, localisation, description);
		
		try {assertEquals(incidents.getAge(incId),age);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
	}

	/**
	 * Test method for {@link system.IncidentImpl#getAmbulanceKindNeeded(java.lang.String)}.
	 */
	@Test
	public final void testGetAmbulanceKindNeeded() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Broadway";
		String description = "grave";
		
		String incId = incidents.addIncident(age, pregnant, localisation, description);
		
		try {assertEquals(incidents.getAmbulanceKindNeeded(incId),Ambulance.MEDICALIZED);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		String incId2 = incidents.addIncident(age, pregnant, localisation, "pas grave");
		
		try {assertEquals(incidents.getAmbulanceKindNeeded(incId2),Ambulance.NORMAL);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
	}

	/**
	 * Test method for {@link system.IncidentImpl#getChosenAmbulance(java.lang.String)}.
	 */
	@Test
	public final void testGetChosenAmbulance() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link system.IncidentImpl#getDemobOrder(java.lang.String)}.
	 */
	@Test
	public final void testGetDemobOrder() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link system.IncidentImpl#getDescription(java.lang.String)}.
	 */
	@Test
	public final void testGetDescription() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Broadway";
		String description = "grave";
		
		String incId = incidents.addIncident(age, pregnant, localisation, description);
		
		try {assertEquals(incidents.getDescription(incId),description);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
	}

	/**
	 * Test method for {@link system.IncidentImpl#getLocalisation(java.lang.String)}.
	 */
	@Test
	public final void testGetLocalisation() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Broadway";
		String description = "grave";
		
		String incId = incidents.addIncident(age, pregnant, localisation, description);
		
		try {assertEquals(incidents.getLocalisation(incId),localisation);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
	}

	/**
	 * Test method for {@link system.IncidentImpl#getMobOrder(java.lang.String)}.
	 */
	@Test
	public final void testGetMobOrder() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link system.IncidentImpl#getMobilizedAmbulance(java.lang.String)}.
	 */
	@Test
	public final void testGetMobilizedAmbulance() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link system.IncidentImpl#getPosition(java.lang.String)}.
	 */
	@Test
	public final void testGetPosition() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link system.IncidentImpl#getPregnant(java.lang.String)}.
	 */
	@Test
	public final void testGetPregnant() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Broadway";
		String description = "grave";
		
		String incId = incidents.addIncident(age, pregnant, localisation, description);
		
		try {assertEquals(incidents.getPregnant(incId),pregnant);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
	}

	/**
	 * Test method for {@link system.IncidentImpl#setAsResolved(java.lang.String)}.
	 */
	@Test
	public final void testSetAsResolved() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link system.IncidentImpl#setChosenAmbulance(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testSetChosenAmbulance() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link system.IncidentImpl#setMobilizedAmbulance(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testSetMobilizedAmbulance() {
		fail("Not yet implemented"); // TODO
	}

}
