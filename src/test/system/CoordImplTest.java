package test.system;

import system.*;
import junit.framework.Assert;
import junit.framework.TestCase;

public class CoordImplTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testToString() {
		Coord c = new CoordImpl(5,5);
		Assert.assertEquals("(5,5)",c.toString());
		c = new CoordImpl(-1,32);
		Assert.assertEquals("(-1,32)",c.toString());
	}

	public void testFromString() {
		String s = "(12,45)";
		Coord c1 = new CoordImpl(0,0);
		Coord c2 = new CoordImpl(12,45);
		try{
			c1.fromString(s);
		}catch(Exception e){e.printStackTrace();}
		Assert.assertEquals(c1,c2);
	}

	public void testDist() {
		Coord c1 = new CoordImpl(0,0);
		Coord c2 = new CoordImpl(10,10);
		Coord c3 = new CoordImpl(10,20);
		Assert.assertEquals(14,c1.dist(c2));
		Assert.assertEquals(10,c2.dist(c3));
	}

	public void testEqualsCoord() {
		Assert.assertEquals(new CoordImpl(13,13),(new CoordImpl(13,13)));
	}

}
