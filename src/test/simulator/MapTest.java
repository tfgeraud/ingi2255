package test.simulator;


import junit.framework.Assert;
import junit.framework.TestCase;
import simulator.*;
import simulator.simobjects.*;

public class MapTest extends TestCase {
	private Map map = new Map("square city",9,13);
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testNextPos() {
		Pos start = new PosImpl(10,10);
		Pos end = new PosImpl(10,20);
		Pos next = map.nextPos(start, end, 5);
		assertEquals(new PosImpl(10,15),next);
		next = map.nextPos(start, end, 0);
		assertEquals(start,next);
		next = map.nextPos(start, end, 10);
		assertEquals(end,next);
		next = map.nextPos(start,end, 15);
		assertEquals(end,next);
	}
	
	public void testMoveOnePositionInX() {
		Pos start = new PosImpl(0,0);
		Pos dest = new PosImpl(10,0);
		Pos next = map.nextPos(start, dest, 1);
		assertEquals(new PosImpl(1,0), next);
	}
	
	public void testEdge() {
		Pos start = new PosImpl(0,0);
		Pos end = new PosImpl(0,10);
		Pos next = map.nextPos(start, end, 10);
		assertEquals(new PosImpl(0,10), next);
	}

	public void testAddObstacle() {
		map.addObstacle(new PosImpl(10,10));
	}

	public void testRemoveObstacle() {
		Assert.fail("Not yet implemented");
	}

}
