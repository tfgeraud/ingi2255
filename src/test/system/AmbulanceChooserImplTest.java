package test.system;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import system.Ambulance;
import system.AmbulanceChooser;
import system.AmbulanceChooserImpl;
import system.AmbulanceImpl;
import system.CoordImpl;
import system.Incident;
import system.IncidentImpl;
import system.Map;
import system.MapImpl;
import system.exception.AmbulanceKindUnknownException;
import system.exception.AmbulanceStatusUnknwownException;
import system.exception.UnknownIncidentException;

public class AmbulanceChooserImplTest {

	private AmbulanceChooser chooser;

	private Incident incidents;

	private Ambulance ambulances;

	private Map map;

	@Before
	public void setUp() throws Exception {
		map = new MapImpl(10, 10);
		incidents = new IncidentImpl(map);
		ambulances = new AmbulanceImpl();
		chooser = new AmbulanceChooserImpl(incidents, ambulances, map);

		map.addAddress("Rue Fleurie no4", new CoordImpl(4, 4));
		map.addAddress("Avenue Gentil premier, no10", new CoordImpl(10, 3));
		map.addAddress("Grand Place", new CoordImpl(20, 30));

		map.addObstacle(new CoordImpl(20, 25));
		map.addObstacle(new CoordImpl(20, 20));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testAmbulanceChooserImpl() {
		// Nothing to test here
	}

	@Test
	public final void testChooseBestAmbulance() {
		// Create incident
		String incId = incidents.addIncident(25, true, "Grand Place", "grave");

		// Create ambulances:
		// mike1 : free
		// mike2 : free
		// alpha1 : free
		// alpha2 : free
		try {
			ambulances.addAmbulance("mike1", new CoordImpl(10, 10),
					Ambulance.MEDICALIZED, AmbulanceImpl.WORKING);
			ambulances.addAmbulance("mike2", new CoordImpl(30, 30),
					Ambulance.MEDICALIZED, AmbulanceImpl.WORKING);
			ambulances.addAmbulance("alpha1", new CoordImpl(10, 3),
					Ambulance.NORMAL, AmbulanceImpl.WORKING);
			ambulances.addAmbulance("alpha2", new CoordImpl(20, 23),
					Ambulance.NORMAL, AmbulanceImpl.WORKING);
		} catch (AmbulanceStatusUnknwownException e) {
			System.err.println("Ambulance status unknown");
		} catch (AmbulanceKindUnknownException e) {
			System.err.println("Ambulance kind unknown");
		}

		Set<String> exclSet = new HashSet<String>();

		try {
			assertTrue("mike2".equals(chooser.chooseBestAmbulance(incId,
					exclSet)));
			// Break mike2
			ambulances.markAsBroken("mike2");
			assertTrue("mike1".equals(chooser.chooseBestAmbulance(incId,
					exclSet)));
			// Repair mike2
			ambulances.markAsRepaired("mike2");
			assertTrue("mike2".equals(chooser.chooseBestAmbulance(incId,
					exclSet)));
			// Break mike1 and exclude mike2
			ambulances.markAsBroken("mike1");
			exclSet.add("mike2");
			assertNull(chooser.chooseBestAmbulance(incId, exclSet));
		} catch (UnknownIncidentException e) {
			System.err.println("Cannot find incident " + incId);
		}

		// Create incident
		String incId2 = incidents.addIncident(13, false, "Grand Place",
				"pas grave");

		Set<String> exclSet2 = new HashSet<String>();

		try {
			assertTrue("alpha1".equals(chooser.chooseBestAmbulance(incId2,
					exclSet2)));
			// Break alpha1
			ambulances.markAsBroken("alpha1");
			assertTrue("alpha2".equals(chooser.chooseBestAmbulance(incId2,
					exclSet2)));
			// Repair alpha1
			ambulances.markAsRepaired("alpha1");
			assertTrue("alpha1".equals(chooser.chooseBestAmbulance(incId2,
					exclSet2)));
			// Break alpha2 and exclude alpha1
			ambulances.markAsBroken("alpha2");
			exclSet2.add("alpha1");
			assertNull(chooser.chooseBestAmbulance(incId2, exclSet2));
		} catch (UnknownIncidentException e) {
			System.err.println("Cannot find incident " + incId2);
		}
	}

}
