package Everything;

/**
 * Only a placeholder for shortcuts
 * 
 * @author Marcel Jaeschke
 * @since 1.6
 */
public class Resources {
	/**
	 * Returns the path to the resouce.
	 * 
	 * @return the path to the resouce.
	 */
	public static final String getPath () {
		//return '/' + Resources.class.getPackage().getName().replace( '.', '/' );
		return "/resources"; // The parent folder of resources has to be in the classpath (lib)
	}
}