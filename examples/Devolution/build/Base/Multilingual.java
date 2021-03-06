package Base;

/**
 * Support multilingual Programs.
 * 
 * @author Marcel Jaeschke
 * @since 1.6
 */
public interface Multilingual {
	/**
	 * Change the language of the enviroment.
	 * 
	 * @param language The name of language according ISO-3166, e.g. 'en' or 'de'.
	 * @return TRUE if the modification was accepted, else FALSE;
	 */
	public boolean changeLanguage ( String language );
}