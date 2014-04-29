package Base;

/**
 * @author Marcel Jaeschke
 * @since 1.6
 */
public interface SettableProgram {
	/**
	 * Returns The path to the default settings.
	 * 
	 * @return the path to the default settings.
	 */
	public abstract String getRessourcePath ();
	/**
	 * Returns the name of the program.
	 * 
	 * @return The name of the program.
	 */
	public abstract String getProgramName ();
	/**
	 * Returns the name of the programs main feature.
	 * 
	 * @return The name of the programs main feature.
	 */
	public abstract String getSettingMainFeature ();
	/**
	 * Returns the version number of the program.
	 * 
	 * @return The version number of the program.
	 */
	public abstract double getProgramVersion ();
	/**
	 * Returns the subtitle of the program.
	 * 
	 * @return The subtitle of the program.
	 */
	public String getSubtitle ();
}