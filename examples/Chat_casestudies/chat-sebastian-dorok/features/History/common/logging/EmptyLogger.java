package common.logging;

public class EmptyLogger implements ILogger {

	private static ILogger instance = null;

	private EmptyLogger() {
		// nothing to do here
	}

	public static ILogger getInstance() {
		if (EmptyLogger.instance == null) {
			EmptyLogger.instance = new EmptyLogger();
		}
		return EmptyLogger.instance;
	}

	@Override
	public void log(String msg) {
		// do nothing here
	}

}
