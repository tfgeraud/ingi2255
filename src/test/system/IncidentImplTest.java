/**
 * 
 */
package test.system;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import events.DemobilisationOrder;
import events.MobilisationOrder;

import system.Ambulance;
import system.CoordImpl;
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
		map = new MapImpl(10, 15);
		incidents = new IncidentImpl(map);

		map.addAddress("Rue Fleurie no4", new CoordImpl(4, 4));
		map.addAddress("Avenue Gentil premier, no10", new CoordImpl(10, 3));
		map.addAddress("Grand Place", new CoordImpl(2, 2));

		map.addObstacle(new CoordImpl(20, 25));
		map.addObstacle(new CoordImpl(20, 20));
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
	 * Test method for
	 * {@link system.IncidentImpl#addIncident(int, boolean, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testAddIncident() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Rue Fleurie no4";
		String description = "grave";

		String incId = incidents.addIncident(age, pregnant, localisation,
				description);

		try {
			assertEquals(incidents.getAge(incId), age);
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}

		try {
			assertEquals(incidents.getPregnant(incId), pregnant);
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}

		try {
			assertEquals(incidents.getDescription(incId), description);
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}

		try {
			assertEquals(incidents.getLocalisation(incId), localisation);
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}
	}

	/**
	 * Test method for {@link system.IncidentImpl#getAge(java.lang.String)}.
	 */
	@Test
	public final void testGetAge() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Rue Fleurie no4";
		String description = "grave";

		String incId = incidents.addIncident(age, pregnant, localisation,
				description);

		try {
			assertEquals(incidents.getAge(incId), age);
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}
	}

	/**
	 * Test method for
	 * {@link system.IncidentImpl#getAmbulanceKindNeeded(java.lang.String)}.
	 */
	@Test
	public final void testGetAmbulanceKindNeeded() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Rue Fleurie no4";
		String description = "grave";

		String incId = incidents.addIncident(age, pregnant, localisation,
				description);

		try {
			assertEquals(incidents.getAmbulanceKindNeeded(incId),
					Ambulance.MEDICALIZED);
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}

		String incId2 = incidents.addIncident(age, pregnant, localisation,
				"pas grave");

		try {
			assertEquals(incidents.getAmbulanceKindNeeded(incId2),
					Ambulance.NORMAL);
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}
	}

	/**
	 * Test method for
	 * {@link system.IncidentImpl#getChosenAmbulance(java.lang.String)}.
	 */
	@Test
	public final void testGetChosenAmbulance() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Rue Fleurie no4";
		String description = "grave";

		String incId = incidents.addIncident(age, pregnant, localisation,
				description);

		// Assert null
		try {
			assertEquals(incidents.getChosenAmbulance(incId), null);
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}

		// Set chosen ambulance
		try {
			incidents.setChosenAmbulance(incId, "mike3");
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}

		// Assert good ambulance
		try {
			assertEquals(incidents.getChosenAmbulance(incId), "mike3");
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}

		// Change chosen ambulance
		try {
			incidents.setChosenAmbulance(incId, "mike5");
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}

		// Assert good ambulance
		try {
			assertEquals(incidents.getChosenAmbulance(incId), "mike5");
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}
	}

	/**
	 * Test method for
	 * {@link system.IncidentImpl#getDemobOrder(java.lang.String)}.
	 */
	@Test
	public final void testGetDemobOrder() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Rue Fleurie no4";
		String description = "grave";

		String ambId = "mike3";

		// Add incident
		String incId = incidents.addIncident(age, pregnant, localisation,
				description);

		// Chose ambulance
		try {
			incidents.setChosenAmbulance(incId, ambId);
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}

		// Assert good demob order
		try {
			assertTrue(incidents.getDemobOrder(incId).equals(
					new DemobilisationOrder(incId, incidents.getPosition(incId)
							.toString(), ambId)));
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}
	}

	/**
	 * Test method for
	 * {@link system.IncidentImpl#getDescription(java.lang.String)}.
	 */
	@Test
	public final void testGetDescription() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Rue Fleurie no4";
		String description = "grave";

		String incId = incidents.addIncident(age, pregnant, localisation,
				description);

		try {
			assertEquals(incidents.getDescription(incId), description);
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}
	}

	/**
	 * Test method for
	 * {@link system.IncidentImpl#getLocalisation(java.lang.String)}.
	 */
	@Test
	public final void testGetLocalisation() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Rue Fleurie no4";
		String description = "grave";

		String incId = incidents.addIncident(age, pregnant, localisation,
				description);

		try {
			assertEquals(incidents.getLocalisation(incId), localisation);
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}
	}

	/**
	 * Test method for {@link system.IncidentImpl#getMobOrder(java.lang.String)}.
	 */
	@Test
	public final void testGetMobOrder() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Rue Fleurie no4";
		String description = "grave";

		String ambId = "mike3";

		// Add incident
		String incId = incidents.addIncident(age, pregnant, localisation,
				description);

		// Chose ambulance
		try {
			incidents.setChosenAmbulance(incId, ambId);
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}

		// Assert good demob order
		try {
			assertTrue(incidents.getMobOrder(incId).equals(
					new MobilisationOrder(incId, incidents.getPosition(incId)
							.toString(), ambId)));
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}
	}

	/**
	 * Test method for
	 * {@link system.IncidentImpl#getMobilizedAmbulance(java.lang.String)}.
	 */
	@Test
	public final void testGetMobilizedAmbulance() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Rue Fleurie no4";
		String description = "grave";

		String incId = incidents.addIncident(age, pregnant, localisation,
				description);

		// Assert null
		try {
			assertEquals(incidents.getMobilizedAmbulance(incId), null);
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}

		// Set chosen ambulance
		try {
			incidents.setMobilizedAmbulance(incId, "mike3");
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}

		// Assert good ambulance
		try {
			assertEquals(incidents.getMobilizedAmbulance(incId), "mike3");
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}

		// Change chosen ambulance
		try {
			incidents.setMobilizedAmbulance(incId, "mike5");
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}

		// Assert good ambulance
		try {
			assertEquals(incidents.getMobilizedAmbulance(incId), "mike5");
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}
	}

	/**
	 * Test method for {@link system.IncidentImpl#getPosition(java.lang.String)}.
	 */
	@Test
	public final void testGetPosition() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Rue Fleurie no4";
		String description = "grave";

		// Add incident
		String incId = incidents.addIncident(age, pregnant, localisation,
				description);

		// Assert good demob order
		try {
			assertTrue(incidents.getPosition(incId).equals(
					map.addressToCoord(localisation)));
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}
	}

	/**
	 * Test method for {@link system.IncidentImpl#getPregnant(java.lang.String)}.
	 */
	@Test
	public final void testGetPregnant() {
		int age = 15;
		boolean pregnant = false;
		String localisation = "Rue Fleurie no4";
		String description = "grave";

		String incId = incidents.addIncident(age, pregnant, localisation,
				description);

		try {
			assertEquals(incidents.getPregnant(incId), pregnant);
		} catch (UnknownIncidentException e) {
			System.err.println("Unknown incident");
		}
	}

	/**
	 * Test method for
	 * {@link system.IncidentImpl#setAsResolved(java.lang.String)}.
	 */
	@Test
	public final void testSetAsResolved() {
		fail("Not yet implemented"); // FIXME Cannot verify an incident is set
		// as resolved
	}

	/**
	 * Test method for
	 * {@link system.IncidentImpl#setChosenAmbulance(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testSetChosenAmbulance() {
		// Done in testGetChosenAmbulance
	}

	/**
	 * Test method for
	 * {@link system.IncidentImpl#setMobilizedAmbulance(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testSetMobilizedAmbulance() {
		// Done in testGetMobilizedAmbulance
	}

}
