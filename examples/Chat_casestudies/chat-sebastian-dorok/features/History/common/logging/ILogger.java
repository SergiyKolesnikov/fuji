package common.logging;

public interface ILogger {

	/**
	 * Writes the given message to the loggers log file.
	 * 
	 * @param msg
	 */
	public abstract void log(String msg);

}