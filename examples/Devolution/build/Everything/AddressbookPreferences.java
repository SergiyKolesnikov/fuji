package Everything;import java.awt.event.ActionEvent;



/**
 * @author Marcel Jaeschke
 * @since 1.6
 */
public class AddressbookPreferences extends PreferencesEntry {
	/**
	 * The serial number.
	 */
	private static final long serialVersionUID = 3790841817759146662L;

	/**
	 * @param modul
	 */
	public AddressbookPreferences ( final Addressbook modul ) {
		super( modul.getName() );
		// TODO Auto-generated constructor stub
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see miscellaneous.PreferencesEntry#checkInput()
	 */
	public boolean checkInput () {
		// TODO Auto-generated method stub
		return false;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see miscellaneous.PreferencesEntry#initContent()
	 */
	protected void initContent () {
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed ( ActionEvent e ) {
		// TODO Auto-generated method stub
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see miscellaneous.MultilingualListener#changeLanguage()
	 */
	public void changeLanguage () {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see PreferencesEntry#ok()
	 */
	protected void ok(){}

	/*
	 * (non-Javadoc)
	 * 
	 * @see PreferencesEntry#apply()
	 */
	protected void apply(){}

	/*
	 * (non-Javadoc)
	 * 
	 * @see PreferencesEntry#cancel()
	 */
	protected void cancel(){}
}