package test;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

import test.core.ParallelParameterized;
import test.core.TestProperties;
import test.core.Util;

/**
 * A parameterized Junit test to test JastAdd2
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
@RunWith(ParallelParameterized.class)
public class TestAll {

	private static final String PROPERTIES_FILE = "jjcfg.test";
	private static final TestProperties properties =
			Util.getProperties(new File(PROPERTIES_FILE));

	private final String testDir;

	/**
	 * Construct a new JastAdd test
	 * @param testDir The base directory for the test
	 */
	public TestAll(String testDir) {
		this.testDir = testDir;
	}

	/**
	 * Run the JastAdd test
	 */
	@Test
	public void runTest() {
		TestRunner.runTest(testDir, properties);
	}

	@SuppressWarnings("javadoc")
	@Parameters(name = "{0}")
	public static Iterable<Object[]> getTests() {
		return Util.getTests(properties);
	}
}
