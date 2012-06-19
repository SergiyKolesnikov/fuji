package test;

import static org.junit.Assert.*;

import org.junit.Test;

import fuji.TestSuite;


public class Test00 {

	@Test
	public void testMain() {
		String[] args = new String[1];
		try {
			TestSuite.main(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(TestSuite.errors.isEmpty());
		assertTrue(TestSuite.warnings.isEmpty());
	}

}
