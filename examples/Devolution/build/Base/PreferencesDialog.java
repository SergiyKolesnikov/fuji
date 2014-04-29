package Base;import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;



/**
 * @author Marcel Jaeschke
 * @since 1.6
 */
public class PreferencesDialog extends JDialog implements ActionListener, MultilingualListener, TreeSelectionListener {
	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 7033517833385609168L;
	private final DefaultMutableTreeNode content = new DefaultMutableTreeNode( "Preferences" );
	private final Devolution base;
	// GUI
	private final JTree trMenu;
	private final JSplitPane spMenu = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, true );
	private final JPanel pnButtons = new JPanel( new GridLayout( 1, 0 ) );
	protected final JButton btApply = new JButton( ProgramImages.SAVE_AS );
	protected final JButton btCancel = new JButton( ProgramImages.CANCEL );
	protected final JButton btOk = new JButton();

	/**
	 * The default constructor.
	 * 
	 * @param base The base which use this GUI and provide the settings.
	 * @param owner The frame which was calling this preferences.
	 * @param activePath The path which will be active and expanded.
	 */
	public PreferencesDialog ( final Devolution base, final JFrame owner, final TreePath activePath ) {
		super( owner, "Preferences", true );
		this.base = base;
		setSize( 640, 480 );
		setResizable( false );
		setLocationRelativeTo( owner );
		setDefaultCloseOperation( JDialog.DO_NOTHING_ON_CLOSE );
		//
		this.trMenu = new JTree( initContent( base ) );
		//this.trMenu.setRootVisible( false );
		this.trMenu.getSelectionModel().setSelectionMode( TreeSelectionModel.SINGLE_TREE_SELECTION );
		this.trMenu.addTreeSelectionListener( this );
		//this.trMenu.expandPath( activePath );
		this.trMenu.setSelectionPath( activePath );
		this.trMenu.scrollPathToVisible( activePath );
		final JScrollPane scMenu = new JScrollPane( this.trMenu );
		scMenu.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
		this.spMenu.setLeftComponent( scMenu );
		//
		this.pnButtons.add( Box.createHorizontalGlue() );
		this.pnButtons.add( Box.createHorizontalGlue() );
		this.pnButtons.add( Box.createHorizontalGlue() );
		this.btApply.addActionListener( this );
		this.pnButtons.add( this.btApply );
		this.btCancel.addActionListener( this );
		this.pnButtons.add( this.btCancel );
		this.btOk.addActionListener( this );
		this.pnButtons.add( this.btOk );
		//
		add( this.spMenu, BorderLayout.CENTER );
		add( this.pnButtons, BorderLayout.SOUTH );
		changeLanguage();
		//
		setVisible( true );
	}
	/**
	 * Create/collect the content of the tree.
	 * 
	 * @param base The base which is used to collect all entries
	 * @return The root node of the tree.
	 */
	private DefaultMutableTreeNode initContent ( final Devolution base ) {
		final PreferencesEntry root = base.getView().getPreferences();
		final Iterator iterator = base.getModules().iterator();
		while ( iterator.hasNext() ) {
			Modul modul = (Modul) iterator.next();
			((PreferencesDialog) this).content.add( modul.getView().getPreferences() );
			modul.getView().getPreferences().setParent( ((PreferencesDialog) this).content );
		}
		((PreferencesDialog) this).content.add( root );
		root.setParent( ((PreferencesDialog) this).content );
		return ((PreferencesDialog) this).content;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged ( final TreeSelectionEvent event ) {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) ((PreferencesDialog) this).trMenu.getLastSelectedPathComponent();
		if ( !node.isRoot() ) {
			((PreferencesDialog) this).spMenu.setRightComponent( ( (PreferencesEntry) node ).getPanel() );
			((PreferencesDialog) this).spMenu.setDividerLocation( Math.max( 152, ((PreferencesDialog) this).spMenu.getLastDividerLocation() ) );
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see miscellaneous.MultilingualListener#changeLanguage()
	 */
	public void changeLanguage () {
		((PreferencesDialog) this).btApply.setText( "Apply" );
		((PreferencesDialog) this).btCancel.setText( "Cancel" );
		((PreferencesDialog) this).btOk.setText( "OK" );
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed ( final ActionEvent event ) {
		final Object source = event.getSource();
		if ( ((PreferencesDialog) this).btApply.equals( source ) || ((PreferencesDialog) this).btOk.equals( source ) ) {
			try {
				for (int i=0; i<content.getChildCount(); i++)
					((PreferencesEntry) content.getChildAt(i)).apply();
				if ( !((PreferencesDialog) this).base.getSetting().isSaved() ) {
					((PreferencesDialog) this).base.getSetting().save( true );
				}
				if ( ((PreferencesDialog) this).btOk.equals( source ) ) {
					for (int i=0; i<content.getChildCount(); i++)
						((PreferencesEntry) content.getChildAt(i)).ok();
					dispose();
				}
			} catch ( SettingException exception ) {
				JOptionPane.showMessageDialog( ((PreferencesDialog) this), exception.getMessage(), "Coudn't save the changes of the profile.", JOptionPane.ERROR_MESSAGE );
			}
		} else if ( ((PreferencesDialog) this).btCancel.equals( source ) ) {
			for (int i=0; i<content.getChildCount(); i++)
				((PreferencesEntry) content.getChildAt(i)).cancel();
			dispose();
		}
	}
}