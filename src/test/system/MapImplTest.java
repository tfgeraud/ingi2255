package test.system;

import system.*;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MapImplTest extends TestCase {
	private MapImpl map;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.map = new MapImpl(10,15);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
	}
	public void testDistance(){
        
        Assert.assertEquals(0,this.map.distance(new CoordImpl(10,10),new CoordImpl(10,10)));  
        Assert.assertEquals(0,this.map.distance(new CoordImpl(10,15),new CoordImpl(10,15)));  
        Assert.assertEquals(40,this.map.distance(new CoordImpl(10,10),     
                                      new CoordImpl(50,10)) );
        Assert.assertEquals(5,this.map.distance(new CoordImpl(10,12),     
                                      new CoordImpl(10,17)) );
        Assert.assertEquals(110,this.map.distance(new CoordImpl(0,5),       
                                      new CoordImpl(50,65)) );
        Assert.assertEquals(Integer.MAX_VALUE,this.map.distance(new CoordImpl(4,4),new CoordImpl(20,20)));
        Assert.assertEquals(20,this.map.distance(new CoordImpl(0,5),new CoordImpl(10,5)));
        Assert.assertEquals(230,this.map.distance(new CoordImpl(0,0), new CoordImpl(90,140)));
	}
	public void testDistanceObstacle(){
		this.map.addObstacle(new CoordImpl(20,25));
		Assert.assertEquals(34,this.map.distance(new CoordImpl(20,22),new CoordImpl(20,28)));
        this.map.addObstacle(new CoordImpl(20,20));
        Assert.assertEquals(Integer.MAX_VALUE,this.map.distance(new CoordImpl(20,22),new CoordImpl(20,28)));
	}
	public void testAddresses(){
		this.map.addAddress("Rue Fleurie no4", new CoordImpl(4,4));
		this.map.addAddress("Avenue Gentil premier, no10", new CoordImpl(10,3));
		this.map.addAddress("Grand Place", new CoordImpl(2,2));
		Assert.assertEquals(new CoordImpl(2,2),this.map.addressToCoord("Grand Place"));
		Assert.assertEquals("Rue Fleurie no4",this.map.coordToAddress(new CoordImpl(4,4)));
	}

}
