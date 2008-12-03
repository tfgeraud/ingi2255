package test.system;

import system.*;

import junit.framework.TestCase;

public class MapImplTest extends TestCase {
	private MapImpl map;
	protected void setUp() throws Exception {
		super.setUp();
		map = new MapImpl(10,15);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
	}
	public void testDistance(){
        
        assertEquals(0,map.distance(new CoordImpl(10,10),new CoordImpl(10,10)));  
        assertEquals(0,map.distance(new CoordImpl(10,15),new CoordImpl(10,15)));  
        assertEquals(40,map.distance(new CoordImpl(10,10),     
                                      new CoordImpl(50,10)) );
        assertEquals(5,map.distance(new CoordImpl(10,12),     
                                      new CoordImpl(10,17)) );
        assertEquals(110,map.distance(new CoordImpl(0,5),       
                                      new CoordImpl(50,65)) );
        assertEquals(Integer.MAX_VALUE,map.distance(new CoordImpl(4,4),new CoordImpl(20,20)));
        assertEquals(20,map.distance(new CoordImpl(0,5),new CoordImpl(10,5)));
        assertEquals(230,map.distance(new CoordImpl(0,0), new CoordImpl(90,140)));
	}
	public void testDistanceObstacle(){
		map.addObstacle(new CoordImpl(20,25));
		assertEquals(34,map.distance(new CoordImpl(20,22),new CoordImpl(20,28)));
        map.addObstacle(new CoordImpl(20,20));
        assertEquals(Integer.MAX_VALUE,map.distance(new CoordImpl(20,22),new CoordImpl(20,28)));
	}
	public void testAddresses(){
		map.addAddress("Rue Fleurie no4", new CoordImpl(4,4));
		map.addAddress("Avenue Gentil premier, no10", new CoordImpl(10,3));
		map.addAddress("Grand Place", new CoordImpl(2,2));
		assertEquals(new CoordImpl(2,2),map.addressToCoord("Grand Place"));
		assertEquals("Rue Fleurie no4",map.coordToAddress(new CoordImpl(4,4)));
	}

}
