package test.system;

import system.*;
import system.MapImpl.Edge;
import system.MapImpl.Node;

import junit.framework.TestCase;

public class MapImplTest extends TestCase {
	private MapImpl map;
	protected void setUp() throws Exception {
		super.setUp();
		map = new MapImpl(10,10);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
	}
	public void testIntern(){
		Node Crossroad = map.findNode(new CoordImpl(20,20));
        Node Crossroad2 = map.findNode(new CoordImpl(42,56));
        Edge Street = map.findEdge(new CoordImpl(10,15));
        Edge Street2 = map.findEdge(new CoordImpl(25,35));
        assertNotNull(Crossroad);
        assertNull(Crossroad2);
        assertNotNull(Street);
        assertNull(Street2);    
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
	}
	public void testDistanceObstacle(){
		map.addObstacle(new CoordImpl(20,25));
        assertEquals(34,map.distance(new CoordImpl(20,22),new CoordImpl(20,28)));
        map.addObstacle(new CoordImpl(20,20));
        assertEquals(Integer.MAX_VALUE,map.distance(new CoordImpl(20,22),new CoordImpl(20,28)));
	}

}
