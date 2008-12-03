package test.common;

import system.CoordImpl;
import common.Graph;

import junit.framework.TestCase;

public class GraphTest extends TestCase {
	Graph map;

	protected void setUp() throws Exception {
		super.setUp();
		map = new Graph(10,15);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testDistance(){
        
        assertEquals(0,map.distance(10,10, 10,10));  
        assertEquals(0,map.distance(10,15, 10,15));  
        assertEquals(40,map.distance(10,10,     
                                      50,10) );
        assertEquals(5,map.distance(10,12, 10,17) );
        assertEquals(110,map.distance(0,5, 50,65) );
        assertEquals(Integer.MAX_VALUE,map.distance(4,4, 20,20));
        assertEquals(20,map.distance(0,5, 10,5));
	}
	public void testDistanceObstacle(){
		map.addObstacle(20,25);
		assertEquals(34,map.distance(20,22, 20,28));
        map.addObstacle(20,20);
        assertEquals(Integer.MAX_VALUE,map.distance(20,22, 20,28));
        assertEquals(230,map.distance(0,0, 90,140));
	}
	public void testPath(){
		int[][] path;
		path = map.path(0,0, 50,40);
		for (int[] i:path){
			if(i!=null){
				System.out.println("("+i[0]+","+i[1]+")");
			}
		}
		assertEquals(10,path.length);
	}
}
