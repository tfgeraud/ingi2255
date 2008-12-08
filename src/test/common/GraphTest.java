package test.common;

import junit.framework.Assert;
import junit.framework.TestCase;

import common.Graph;

public class GraphTest extends TestCase {
	Graph map;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.map = new Graph(10,15);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testDistance(){
        
        Assert.assertEquals(0,this.map.distance(10,10, 10,10));  
        Assert.assertEquals(0,this.map.distance(10,15, 10,15));  
        Assert.assertEquals(40,this.map.distance(10,10,     
                                      50,10) );
        Assert.assertEquals(5,this.map.distance(10,12, 10,17) );
        Assert.assertEquals(110,this.map.distance(0,5, 50,65) );
        Assert.assertEquals(Integer.MAX_VALUE,this.map.distance(4,4, 20,20));
        Assert.assertEquals(20,this.map.distance(0,5, 10,5));
	}
	public void testDistanceObstacle(){
		this.map.addObstacle(20,25);
		Assert.assertEquals(34,this.map.distance(20,22, 20,28));
        this.map.addObstacle(20,20);
        Assert.assertEquals(Integer.MAX_VALUE,this.map.distance(20,22, 20,28));
        Assert.assertEquals(230,this.map.distance(0,0, 90,140));
	}
	public void testPath(){
		int[][] path;
		path = this.map.path(0,0, 50,40);
		for (int[] i:path){
			if(i!=null){
				System.out.println("("+i[0]+","+i[1]+")");
			}
		}
		Assert.assertEquals(10,path.length);
	}
}
