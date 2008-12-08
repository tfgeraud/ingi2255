package test.simulator;

import simulator.*;
import simulator.simobjects.*;
import junit.framework.TestCase;

public class MapTest extends TestCase {
	Map map = new Map("square city",9,13);
	protected void setUp() throws Exception {
		super.setUp();
	}

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

	public void testAddObstacle() {
		Pos start = new PosImpl(10,10);
		Pos end = new PosImpl(10,20);
		Pos next = null;
		map.addObstacle(new PosImpl(0,10));
		map.addObstacle(new PosImpl(10,15));
		next = map.nextPos(start, end, 5);
		assertEquals(new PosImpl(15,10),next);
		next = map.nextPos(start, end, 15);
		assertEquals(new PosImpl(20,15),next);
		map.addObstacle(new PosImpl(10,0));
		map.addObstacle(new PosImpl(15,10));
		next = map.nextPos(start, end, 10);
		assertNull(next);
		
		
	}

	public void testRemoveObstacle() {
		Pos start = new PosImpl(10,10);
		Pos end = new PosImpl(10,20);
		Pos next = null;
		map.addObstacle(new PosImpl(0,10));
		map.addObstacle(new PosImpl(10,15));
		map.addObstacle(new PosImpl(10,0));
		map.addObstacle(new PosImpl(15,10));
		next = map.nextPos(start, end, 10);
		assertNull(next);
		map.removeObstacle(new PosImpl(10,0));
		next = map.nextPos(start, end, 10);
		assertNotNull(next);
		
	}

}
