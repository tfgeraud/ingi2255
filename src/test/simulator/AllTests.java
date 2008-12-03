package test.simulator;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for test.simulator");
		//$JUnit-BEGIN$
		suite.addTestSuite(EventTest.class);
		suite.addTestSuite(BasicObjectTest.class);
		suite.addTestSuite(AmbulanceTest.class);
		//$JUnit-END$
		return suite;
	}

}
