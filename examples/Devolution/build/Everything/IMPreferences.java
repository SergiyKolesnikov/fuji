package Everything;
import java.awt.event.ActionEvent;
import javax.swing.*;



abstract class IMPreferences$$InstantBase extends PreferencesEntry {
    private static final long serialVersionUID = -3143066956838455738L;
    protected final InstantMessenger modul;
    protected static final String FEATURE_NAME = "Devolution";

    public IMPreferences$$InstantBase ( final InstantMessenger modul ) {
        super( modul.getName() );
        this.modul = modul;
    }

    public boolean checkInput () {
        return true;
    }

    protected void initContent () {
        addSeperator();
    }

    public void actionPerformed ( ActionEvent e ) {
    }

    public void changeLanguage () {
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see PreferencesEntry#ok()
	 */
	protected void ok() {
		apply();
		//close
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see PreferencesEntry#apply()
	 */
	protected void apply() {
		// Protocols refine me here
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see PreferencesEntry#cancel()
	 */
	protected void cancel(){}
}



public class IMPreferences extends  IMPreferences$$InstantBase  {
	private JLabel lblUsername = new JLabel();
	private JLabel lblDomain = new JLabel();
	private JLabel lblResource = new JLabel();
	private JLabel lblPassword = new JLabel();
	private JTextField txtUsername = new JTextField();
	private JTextField txtDomain = new JTextField();
	private JTextField txtResource = new JTextField();
	private JPasswordField txtPassword = new JPasswordField();

	protected void initContent () {
		// Headline
		JLabel lblJabber = new JLabel();
		PreferencesEntry.setHeadlineText( "Jabber", lblJabber );
		addHeadline( lblJabber );
		
		// Read the settings
		try {
			((IMPreferences) this).txtUsername.setText(((IMPreferences) this).modul.getSetting().getOptionAsString(IMPreferences.FEATURE_NAME, "JabberUsername"));
			((IMPreferences) this).txtDomain.setText(((IMPreferences) this).modul.getSetting().getOptionAsString(IMPreferences.FEATURE_NAME, "JabberDomain"));
			((IMPreferences) this).txtResource.setText(((IMPreferences) this).modul.getSetting().getOptionAsString(IMPreferences.FEATURE_NAME, "JabberResource"));
			((IMPreferences) this).txtPassword.setText(TextCoding.rot48(((IMPreferences) this).modul.getSetting().getOptionAsString(IMPreferences.FEATURE_NAME, "JabberPassword")));
		} catch (SettingException e) {
			System.err.println("Error! Setting exception:");
			System.err.println(e.getMessage());
		}
		
		// Entries
		addElement(lblUsername, txtUsername);
		addElement(lblDomain, txtDomain);
		addElement(lblResource, txtResource);
		addElement(lblPassword, txtPassword);
	}
	
	public void changeLanguage () {
		((IMPreferences) this).lblUsername.setText("Username: ");
		((IMPreferences) this).lblDomain.setText("Domain: ");
		((IMPreferences) this).lblResource.setText("Resource: ");
		((IMPreferences) this).lblPassword.setText("Password: ");
	}
	
	public void apply() {
		try {
			((IMPreferences) this).modul.getSetting().setOption( IMPreferences.FEATURE_NAME, "JabberUsername", ((IMPreferences) this).txtUsername.getText() );
			((IMPreferences) this).modul.getSetting().setOption( IMPreferences.FEATURE_NAME, "JabberDomain", ((IMPreferences) this).txtDomain.getText() );
			((IMPreferences) this).modul.getSetting().setOption( IMPreferences.FEATURE_NAME, "JabberResource", ((IMPreferences) this).txtResource.getText() );
			((IMPreferences) this).modul.getSetting().setOption( IMPreferences.FEATURE_NAME, "JabberPassword", TextCoding.rot48(((IMPreferences) this).txtPassword.getText()));
		} catch (SettingException e) {
			System.err.println("Error! Setting exception:");
			System.err.println(e.getMessage());
		}
	}
      // inherited constructors



    public IMPreferences (  final InstantMessenger modul ) { super(modul); }
}