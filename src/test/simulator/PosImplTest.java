package test.simulator;

import simulator.Pos;
import simulator.PosImpl;
import junit.framework.TestCase;

public class PosImplTest extends TestCase {
	private Pos pos = new PosImpl(0,0);

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testPosImplIntInt() {
		Pos p = new PosImpl(0,0);
		assertEquals(0,p.getX());
		assertEquals(0,p.getY());
	}

	public void testPosImplString() {
		Pos p = new PosImpl("(0,0)");
		assertEquals(0,p.getX());
		assertEquals(0,p.getY());
	}

	public void testToString() {
		assertEquals("(0,0)", pos.toString());	
	}

	public void testDist() {
		assertEquals(5, pos.dist(new PosImpl(3,4)));
	}

	public void testEqualsObject() {
		assertEquals(pos, new PosImpl(0,0));
	}

	public void testEqualsPos() {
		assertEquals(true, pos.equals(new PosImpl(0,0)));
	}

}
