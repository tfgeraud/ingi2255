package test.simulator;

import simulator.Pos;
import simulator.PosImpl;
import junit.framework.Assert;
import junit.framework.TestCase;

public class PosImplTest extends TestCase {
	private Pos pos = new PosImpl(0,0);

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testPosImplIntInt() {
		Pos p = new PosImpl(0,0);
		Assert.assertEquals(0,p.getX());
		Assert.assertEquals(0,p.getY());
	}

	public void testPosImplString() {
		Pos p = new PosImpl("(0,0)");
		Assert.assertEquals(0,p.getX());
		Assert.assertEquals(0,p.getY());
	}

	public void testToString() {
		Assert.assertEquals("(0,0)", this.pos.toString());	
	}

	public void testDist() {
		Assert.assertEquals(5, this.pos.dist(new PosImpl(3,4)));
	}

	public void testEqualsObject() {
		Assert.assertEquals(this.pos, new PosImpl(0,0));
	}

	public void testEqualsPos() {
		Assert.assertEquals(true, this.pos.equals(new PosImpl(0,0)));
	}

}
