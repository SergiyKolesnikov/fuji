package test.core;

/**
 * Possible results for a unit test.
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public enum Result {
	/**
	 * The compilation failed
	 */
	COMPILE_FAILED,
	
	/**
	 * The compilation passed
	 */
	COMPILE_PASSED,
	
	/**
	 * Execution of the compiled code failed (exit status != 0)
	 */
	EXEC_FAILED,
	
	/**
	 * Execution succeeded (exit status == 0)
	 */
	EXEC_PASSED,
	
	/**
	 * Execution succeeded and the output was identical to the expected output
	 */
	OUTPUT_PASSED
}
