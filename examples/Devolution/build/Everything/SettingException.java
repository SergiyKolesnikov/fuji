package Everything;

/**
 * Thrown exception for the setting module.
 * 
 * @author Marcel Jaeschke
 */
public class SettingException extends Exception {
	/**
	 * Prüfnummer zur Serialisierung.
	 */
	private static final long serialVersionUID = -745553382262243037L;

	/**
	 * Throwable exception without a default message.
	 */
	public SettingException () {
		this( "There was an unexpected and undescribed error." );
	}
	/**
	 * Throwable exception which print the message which decribed the error in
	 * natural speech.
	 * 
	 * @param message The message which decribed the error in natural speech.
	 */
	public SettingException ( String message ) {
		super( "Error in the setting: " + message );
	}
}