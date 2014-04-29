package Everything;import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URI;
import java.util.Iterator;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;



/**
 * The GUI of "Devolution".
 * 
 * @author Marcel Jaeschke
 * @since 1.6
 */
public class View extends JFrame implements ActionListener, MultilingualListener, WindowListener {
	/**
	 * The serial number.
	 */
	private static final long serialVersionUID = -3291107162533654557L;
	/**
	 * The session of devolution which use the GUI.
	 */
	private final Devolution base;
	/**
	 * The modul which is currently active.
	 */
	private Modul activeModul;
	private final PreferencesEntry preferences = new Preferences();
	// File menu
	private final JMenu mnFile = new JMenu();
	private final JMenuItem miFileClose = new JMenuItem( ProgramImages.EXIT );
	// Edit menu
	private final JMenu mnEdit = new JMenu();
	private final JMenuItem miEditPreferences = new JMenuItem( ProgramImages.PREFERENCES );
	// Help menu
	private final JMenu mnHelp = new JMenu();
	private final JMenuItem miHelpManual = new JMenuItem( ProgramImages.MANUEL );
	private final JMenuItem miHelpWebsite = new JMenuItem();
	private final JMenuItem miHelpAbout = new JMenuItem( ProgramImages.ABOUT );
	// Contents
	private final JSplitPane spContent = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, true );
	private final JPanel pnToolbar = new JPanel( new BorderLayout() );
	private final JPanel pnSidepanel = new JPanel( new BorderLayout() );
	private final Statusbar statusbar = new Statusbar();
	private final JLabel lbLoadedModulSidepanel = new JLabel( "modul", JLabel.CENTER );

	/**
	 * The default constructor.
	 * 
	 * @param base The session of devolution which use the GUI.
	 */
	public View ( final Devolution base ) {
		// START: initialisation of the Frame
		super( base.getProgramName() );
		// Maybe used while the initialisation of the Frame.
		this.base = base;
		addWindowListener( this );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setIconImage( ProgramImages.ICON.getImage() );
		// init content
		setJMenuBar( initMenubar() );
		add( this.pnToolbar, BorderLayout.NORTH );
		this.spContent.setLeftComponent( initSidepanel() );
		this.spContent.setRightComponent( Box.createGlue() );
		this.spContent.setDividerLocation( 152 );
		add( this.spContent, BorderLayout.CENTER );
		add( this.statusbar.getComponent(), BorderLayout.SOUTH );
		// schow frame
		setSize( 640, 480 );
		initPosition();
		setVisible( true );
		// END initialisation of the Frame
	}
	/* +++ INITIALIZER +++ */
	/**
	 * Initialize the menubar of the GUI.
	 * 
	 * @return The menubar of the GUI.
	 */
	private JMenuBar initMenubar () {
		final JMenuBar menubar = new JMenuBar();
		// File menus
		((View) this).mnFile.addSeparator();
		((View) this).miFileClose.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_F4, ActionEvent.ALT_MASK ) );
		((View) this).miFileClose.addActionListener( ((View) this) );
		((View) this).mnFile.add( ((View) this).miFileClose );
		menubar.add( ((View) this).mnFile );
		// Edit menus
		((View) this).mnEdit.addSeparator();
		((View) this).miEditPreferences.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_P, ActionEvent.CTRL_MASK ) );
		((View) this).miEditPreferences.addActionListener( ((View) this) );
		((View) this).mnEdit.add( ((View) this).miEditPreferences );
		menubar.add( ((View) this).mnEdit );
		// Modul menus
		final Iterator iterator = ((View) this).base.getModules().iterator();
		while ( iterator.hasNext() ) {
			Modul modul = (Modul) iterator.next();
			menubar.add( modul.getView().getMenu() );
		}
		// Help
		((View) this).miHelpManual.addActionListener( ((View) this) );
		((View) this).mnHelp.add( ((View) this).miHelpManual );
		((View) this).miHelpWebsite.addActionListener( ((View) this) );
		((View) this).mnHelp.add( ((View) this).miHelpWebsite );
		((View) this).mnHelp.addSeparator();
		((View) this).miHelpAbout.addActionListener( ((View) this) );
		((View) this).mnHelp.add( ((View) this).miHelpAbout );
		menubar.add( ((View) this).mnHelp );
		// END
		return menubar;
	}
	/**
	 * Initialize the sidepanel.
	 * 
	 * @return The sidepanel.
	 */
	private JComponent initSidepanel () {
		final JPanel sidebar = new JPanel( new GridBagLayout() );
		final GridBagConstraints constraints = new GridBagConstraints( GridBagConstraints.RELATIVE, 0, 1, 1, 1D, 0D, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 1, 1 );
		sidebar.add( ((View) this).lbLoadedModulSidepanel, constraints );
		constraints.gridy++;
		constraints.weighty = 1D;
		sidebar.add( new JScrollPane( ((View) this).pnSidepanel ), constraints );
		constraints.weighty = 0D;
		final Iterator iterator = ((View) this).base.getModules().iterator();
		while ( iterator.hasNext() ) {
			Modul modul = (Modul) iterator.next();
			constraints.gridy++;
			sidebar.add( modul.getView().getSidepanelButton(), constraints );
		}
		return sidebar;
	}
	/* +++ REST +++ */
	/**
	 * Returns the main preference-entry.
	 * 
	 * @return The main preference-entry.
	 */
	public PreferencesEntry getPreferences () {
		return ((View) this).preferences;
	}
	/**
	 * Set an modul on focus.
	 * 
	 * @param modulName The modul which will be active.
	 */
	public void setActiveModul ( final String modulName ) {
		final Modul modul = ((View) this).base.getModul( modulName );
		if ( modul != null && ( ((View) this).activeModul == null || !((View) this).activeModul.equals( modul ) ) ) {
			// Toolbar
			if ( ((View) this).activeModul != null ) {
				((View) this).pnToolbar.remove( ((View) this).activeModul.getView().getToolbar() );
			}
			((View) this).pnToolbar.add( modul.getView().getToolbar(), BorderLayout.NORTH );
			((View) this).pnToolbar.repaint();
			// Sidebar
			if ( ((View) this).activeModul != null ) {
				((View) this).pnSidepanel.remove( ((View) this).activeModul.getView().getSidepanel() );
			}
			((View) this).pnSidepanel.add( modul.getView().getSidepanel(), BorderLayout.CENTER );
			((View) this).lbLoadedModulSidepanel.setText( modul.getName() );
			// Main-Panel
			((View) this).spContent.setRightComponent( modul.getView().getMainContent() );
			// Statusbar
			((View) this).statusbar.modulToStatusbar( modul );
			//
			((View) this).spContent.setDividerLocation( Math.max( 152, ((View) this).spContent.getLastDividerLocation() ) );
			((View) this).activeModul = modul;
		}
	}
	/**
	 * Centering the frame.
	 */
	private void initPosition () {
		final Rectangle bounds = getGraphicsConfiguration().getBounds();
		setLocation( bounds.width / 2 - getWidth() / 2, bounds.height / 2 - getHeight() / 2 );
	}
	/**
	 * Check for unsaved settings (confirm dialog) and close the gui.
	 */
	public void close () {
		final Iterator iterator = ((View) this).base.getModules().iterator();
		while ( iterator.hasNext() ) {
			Modul modul = (Modul) iterator.next();
			modul.close();
		}
		if ( !((View) this).base.getSetting().isSaved() && JOptionPane.showConfirmDialog( ((View) this), "The settings are changed and unsaved. Save it now?", "Save settings?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE ) == JOptionPane.OK_OPTION ) {
			try {
				((View) this).base.getSetting().save( true );
			} catch ( SettingException exception ) {
				JOptionPane.showMessageDialog( ((View) this), exception.getMessage(), "Coudn't save the changes of the profile.", JOptionPane.ERROR_MESSAGE );
			}
		}
		// frame
		setVisible( false );
		dispose();
	}
	/* === GETTERS === */
	/**
   * Returns the modul which has currently on focus.
   * @return The modul which has currently on focus.
   */
  public Modul getActiveModul () {
  	return ((View) this).activeModul;
  }
	/*
	 * (non-Javadoc)
	 * 
	 * @see miscellaneous.MultilingualListener#changeLanguage()
	 */
	public void changeLanguage () {
		// File menu
		((View) this).mnFile.setText( "File" );
		((View) this).miFileClose.setText( "Exit" );
		// Edit menu
		((View) this).mnEdit.setText( "Edit" );
		((View) this).miEditPreferences.setText( "Preferences" );
		// Help menu
		((View) this).mnHelp.setText( "Help" );
		((View) this).miHelpManual.setText( "Manual" );
		((View) this).miHelpWebsite.setText( "Website" );
		((View) this).miHelpAbout.setText( "About" );
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed ( ActionEvent event ) {
		final Object source = event.getSource();
		if ( ((View) this).miFileClose.equals( source ) ) {
			close();
		} else if ( ((View) this).miEditPreferences.equals( source ) ) {
			new PreferencesDialog( ((View) this).base, ((View) this), new TreePath( ((View) this).preferences ) );
		} else if ( ((View) this).miHelpManual.equals( source ) ) {
			// TODO: add JavaHelp
			JOptionPane.showConfirmDialog( ((View) this), "Isn't implanted yet.", "Isn't implanted yet", JOptionPane.WARNING_MESSAGE );
		} else if ( ((View) this).miHelpWebsite.equals( source ) ) {
			try {
				Desktop.getDesktop().browse( new URI( "http", "rephstone.de", "/projects/devolution/", null ) );
			} catch ( Exception exception ) {
				System.err.println( "Coudn't follow the link." );
			}
		} else if ( ((View) this).miHelpAbout.equals( source ) ) {
			new AboutDialog( ((View) this).base, ((View) this) );
		}
	}
	/* +++ WindowListener +++ */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	public void windowActivated ( WindowEvent e ) {}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	public void windowClosed ( WindowEvent e ) {}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing ( WindowEvent e ) {
		close();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	public void windowDeactivated ( WindowEvent e ) {}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	public void windowDeiconified ( WindowEvent e ) {}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	public void windowIconified ( WindowEvent e ) {}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	public void windowOpened ( WindowEvent e ) {}
}