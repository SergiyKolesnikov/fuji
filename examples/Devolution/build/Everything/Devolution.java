package Everything;import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.UIManager;



/**
 * @author Marcel Jaeschke
 * @since 1.6
 */
abstract class Devolution$$Base implements Multilingual, SettableProgram {
	
	/**
	 * The list of the modules.
	 */
	protected final HashMap modules = new HashMap();
	/**
	 * The setting of "Devolution".
	 */
	protected Setting setting = null;
	/**
	 * The GUI of "Devolution".
	 */
	protected View view = null;

	/**
	 * Default constructor.
	 */
	public Devolution$$Base () {
		String language = Translator.DEFAULT_LANGUAGE;
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch ( Exception exception ) {
			System.err.println( "Can't use the native system look." );
		}
		try {
			this.setting = new Setting( this );
		} catch ( SettingException exception ) {
			System.err.println( "Error during the initialisation of a new session." );
		}
		try {
			language = this.setting.getOptionAsString( getSettingMainFeature(), "lang" );
		} catch ( Exception exception ) {
			System.err.println( "Coudn't read the default language given by setting." );
		}
		initModules();
		this.view = new View( (Devolution) this );
		changeLanguage( language );
	}

	/**
	 * Load the standard modules
	 */
	protected void initModules () {
		((Devolution) this).modules.clear();
	}

	/**
	 * Returns the list of current loaded modules as a collection.
	 * @return The list of current loaded modules as a collection.
	 */
	public Collection getModules () {
		return ((Devolution) this).modules.values();
	}

	/**
	 * Returns the the requested Modul, addressed by name.
	 * @param modulName The name of the requested modul.
	 * @return The the requested modul or null if the modul isn't loaded.
	 */
	public Modul getModul ( final String modulName ) {
		return (Modul)((Devolution) this).modules.get( modulName );
	}

	/**
	 * Returns the GUI of "Devolution".
	 * @return The GUI of "Devolution".
	 */
	public View getView () {
		return ((Devolution) this).view;
	}

	/**
	 * Returns the setting of "Devolution".
	 * @return The setting of "Devolution".
	 */
	public Setting getSetting () {
		return ((Devolution) this).setting;
	}
	/**
	 * Check if the modul is aleady loaded.
	 * 
	 * @param modulName The Module adressed by the name, which will be checked
	 * @return TRUE if the Modul is already loaded, else FALSE.
	 */
	public boolean isLoaded ( final String modulName ) {
		return ((Devolution) this).modules.containsKey( modulName );
	}
	/**
	 * Add a modul to the base.
	 * If the Modul already loaded it will be replaced.
	 * 
	 * @param modul The Module which will be added to the base.
	 * @return TRUE if the Modul was already loaded, else FALSE.
	 */
	public boolean addModul ( final Modul modul ) {
		final boolean wasLoaded = isLoaded( modul.getName() );
		((Devolution) this).modules.put( modul.getName(), modul );
		return wasLoaded;
	}
	// START: Multilingual
	/*
	 * (non-Javadoc)
	 * requested
	 * @see miscellaneous.Multilingual#changeLanguage(java.lang.String)
	 */
	public boolean changeLanguage ( final String language ) {
		Translator.setLanguage( language );
		((Devolution) this).view.changeLanguage();
		final Iterator iterator = getModules().iterator();
		while ( iterator.hasNext() ) {
			Modul modul = (Modul) iterator.next();
			modul.changeLanguage();
		}
		return Translator.isSupported( language );
	}
	// END: Multilingual
	// START: SettableProgram
	/* (non-Javadoc)
	 * @see miscellaneous.setting.SettableProgram#getProgramName()
	 */
	public String getProgramName () {
		return "Devolution";
	}

	/* (non-Javadoc)
	 * @see miscellaneous.setting.SettableProgram#getProgramVersion()
	 */
	public double getProgramVersion () {
		return 0.3D;
	}

	/* (non-Javadoc)
	 * @see miscellaneous.setting.SettableProgram#getRessourcePath()
	 */
	public String getRessourcePath () {
		return Resources.getPath();
	}

	/* (non-Javadoc)
	 * @see miscellaneous.setting.SettableProgram#getSettingMainFeature()
	 */
	public String getSettingMainFeature () {
		return getProgramName();
	}

	/* (non-Javadoc)
	 * @see miscellaneous.setting.SettableProgram#getSubtitle()
	 */
	public String getSubtitle () {
		return "The Featured Communication Center";
	}
	// END: SettableProgram
	/**
	 * Start method of Devolution.
	 * @param args
	 */
	public static void main (String[] args ) {
		final Devolution session = new Devolution();
	}
}

abstract class Devolution$$Addressbook extends  Devolution$$Base  {
	public void initModules(){
		super.initModules();
		addModul( new Addressbook( ((Devolution) this) ) );
	}
      // inherited constructors



	/**
	 * Default constructor.
	 */
	public Devolution$$Addressbook (  ) { super(); }
}

abstract class Devolution$$MailBase extends  Devolution$$Addressbook {

	public void initModules(){
		super.initModules();
		addModul(new Mail(((Devolution) this)));
	}
      // inherited constructors



	/**
	 * Default constructor.
	 */
	public Devolution$$MailBase (  ) { super(); }
}


public class Devolution extends  Devolution$$MailBase  {
    
    public void initModules() {
        super.initModules();
        addModul(new InstantMessenger(((Devolution) this)));
    }
      // inherited constructors



	/**
	 * Default constructor.
	 */
	public Devolution (  ) { super(); }
}