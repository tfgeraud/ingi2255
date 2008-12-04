package test.system;

import system.*;
import junit.framework.TestCase;

public class CoordImplTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testToString() {
		Coord c = new CoordImpl(5,5);
		assertEquals("(5,5)",c.toString());
		c = new CoordImpl(-1,32);
		assertEquals("(-1,32)",c.toString());
	}

	public void testFromString() {
		String s = "(12,45)";
		Coord c1 = new CoordImpl(0,0);
		Coord c2 = new CoordImpl(12,45);
		try{
			c1.fromString(s);
		}catch(Exception e){e.printStackTrace();}
		assertEquals(c1,c2);
	}

	public void testDist() {
		Coord c1 = new CoordImpl(0,0);
		Coord c2 = new CoordImpl(10,10);
		Coord c3 = new CoordImpl(10,20);
		assertEquals(14,c1.dist(c2));
		assertEquals(10,c2.dist(c3));
	}

	public void testEqualsCoord() {
		assertEquals(new CoordImpl(13,13),(new CoordImpl(13,13)));
	}

}
