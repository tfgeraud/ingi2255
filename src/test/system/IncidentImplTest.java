/**
 * 
 */
package test.system;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import system.Ambulance;
import system.Incident;
import system.IncidentImpl;
import system.Map;
import system.MapImpl;
import system.exception.UnknownIncidentException;

/**
 * This class make tests on IncidentImpl
 * 
 * @author Simon Busard <simon.busard@student.uclouvain.be>
 */
public class IncidentImplTest {
	
	private Incident incidents;
	private Map map;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.map = new MapImpl(3,4);
		this.incidents = new IncidentImpl(this.map);
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
		
		String incId = this.incidents.addIncident(age, pregnant, localisation, description);
		
		try {Assert.assertEquals(this.incidents.getAge(incId),age);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		try {Assert.assertEquals(this.incidents.getPregnant(incId),pregnant);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		try {Assert.assertEquals(this.incidents.getDescription(incId),description);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		try {Assert.assertEquals(this.incidents.getLocalisation(incId),localisation);}
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
		
		String incId = this.incidents.addIncident(age, pregnant, localisation, description);
		
		try {Assert.assertEquals(this.incidents.getAge(incId),age);}
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
		
		String incId = this.incidents.addIncident(age, pregnant, localisation, description);
		
		try {Assert.assertEquals(this.incidents.getAmbulanceKindNeeded(incId),Ambulance.MEDICALIZED);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		String incId2 = this.incidents.addIncident(age, pregnant, localisation, "pas grave");
		
		try {Assert.assertEquals(this.incidents.getAmbulanceKindNeeded(incId2),Ambulance.NORMAL);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
	}

	/**
	 * Test method for {@link system.IncidentImpl#getChosenAmbulance(java.lang.String)}.
	 */
	@Test
	public final void testGetChosenAmbulance() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Broadway";
		String description = "grave";
		
		String incId = this.incidents.addIncident(age, pregnant, localisation, description);
		
		// Assert null
		try {Assert.assertEquals(this.incidents.getChosenAmbulance(incId), null);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		// Set chosen ambulance
		try {this.incidents.setChosenAmbulance(incId, "mike3");}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		// Assert good ambulance
		try {Assert.assertEquals(this.incidents.getChosenAmbulance(incId), "mike3");}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}

		// Change chosen ambulance
		try {this.incidents.setChosenAmbulance(incId, "mike5");}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		// Assert good ambulance
		try {Assert.assertEquals(this.incidents.getChosenAmbulance(incId), "mike5");}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
	}

	/**
	 * Test method for {@link system.IncidentImpl#getDemobOrder(java.lang.String)}.
	 */
	@Test
	public final void testGetDemobOrder() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Broadway";
		String description = "grave";
		
		String ambId = "mike3";
		
		// Add incident
		String incId = this.incidents.addIncident(age, pregnant, localisation, description);
		
		// Chose ambulance
		try {this.incidents.setChosenAmbulance(incId, ambId);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		// Assert good demob order
		// FIXME : cannot tested now because no valid address in map
		//try {assertTrue(incidents.getDemobOrder(incId).equals(new DemobilisationOrder(incId,incidents.getPosition(incId).toString(),ambId)));}
		//catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		Assert.fail("Not yet implemented");
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
		
		String incId = this.incidents.addIncident(age, pregnant, localisation, description);
		
		try {Assert.assertEquals(this.incidents.getDescription(incId),description);}
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
		
		String incId = this.incidents.addIncident(age, pregnant, localisation, description);
		
		try {Assert.assertEquals(this.incidents.getLocalisation(incId),localisation);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
	}

	/**
	 * Test method for {@link system.IncidentImpl#getMobOrder(java.lang.String)}.
	 */
	@Test
	public final void testGetMobOrder() {
		Assert.fail("Not yet implemented"); // TODO not testable without good map (there is now not good address)
	}

	/**
	 * Test method for {@link system.IncidentImpl#getMobilizedAmbulance(java.lang.String)}.
	 */
	@Test
	public final void testGetMobilizedAmbulance() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Broadway";
		String description = "grave";
		
		String incId = this.incidents.addIncident(age, pregnant, localisation, description);
		
		// Assert null
		try {Assert.assertEquals(this.incidents.getMobilizedAmbulance(incId), null);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		// Set chosen ambulance
		try {this.incidents.setMobilizedAmbulance(incId, "mike3");}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		// Assert good ambulance
		try {Assert.assertEquals(this.incidents.getMobilizedAmbulance(incId), "mike3");}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}

		// Change chosen ambulance
		try {this.incidents.setMobilizedAmbulance(incId, "mike5");}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
		
		// Assert good ambulance
		try {Assert.assertEquals(this.incidents.getMobilizedAmbulance(incId), "mike5");}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
	}

	/**
	 * Test method for {@link system.IncidentImpl#getPosition(java.lang.String)}.
	 */
	@Test
	public final void testGetPosition() {
		Assert.fail("Not yet implemented"); // TODO not testable without good map (there is now not good address)
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
		
		String incId = this.incidents.addIncident(age, pregnant, localisation, description);
		
		try {Assert.assertEquals(this.incidents.getPregnant(incId),pregnant);}
		catch(UnknownIncidentException e) {System.err.println("Unknown incident");}
	}

	/**
	 * Test method for {@link system.IncidentImpl#setAsResolved(java.lang.String)}.
	 */
	@Test
	public final void testSetAsResolved() {
		Assert.fail("Not yet implemented"); // TODO Cannot verify an incident is set as resolved
	}

	/**
	 * Test method for {@link system.IncidentImpl#setChosenAmbulance(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testSetChosenAmbulance() {
		// Done in testGetChosenAmbulance
	}

	/**
	 * Test method for {@link system.IncidentImpl#setMobilizedAmbulance(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testSetMobilizedAmbulance() {
		// Done in testGetMobilizedAmbulance
	}

}
