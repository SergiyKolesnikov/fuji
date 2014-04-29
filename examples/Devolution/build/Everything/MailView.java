package Everything;import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;




/**
 * @author Marcel Jaeschke
 * @since 1.6
 */
abstract class MailView$$MailBase extends ModelView implements TreeSelectionListener {
	protected final JMenuItem miPreferences = new JMenuItem( ProgramImages.PREFERENCES );
	protected JButton refreshButton, sendButton;

	/**
	 * Default constructor.
	 * 
	 * @param modul The modul which use the viewer.
	 */
	public MailView$$MailBase ( final Modul modul ) {
		super( modul );
		initView();
		this.lbStatusbar.setText( "Click on an Account to show the E-Mails" );
	}
	/* === INITIALIZER === */
	/*
	 * (non-Javadoc)
	 * 
	 * @see Base.ModelView#initMainContent()
	 */
	public JComponent initMainContent () {
		getMainContent().add( new MailContentPane(null) );
		return ((MailView) this).coContent;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see Base.ModelView#initMenu()
	 */
	public JMenu initMenu () {
		((MailView) this).mnMenu.add( new JMenuItem( "check accounts" ) );
		((MailView) this).mnMenu.addSeparator();
		((MailView) this).miPreferences.addActionListener( ((MailView) this) );
		((MailView) this).mnMenu.add( ((MailView) this).miPreferences );
		return ((MailView) this).mnMenu;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see Base.ModelView#initPreferences()
	 */
	public PreferencesEntry initPreferences () {
		((MailView) this).preferences = new MailPreferences( (Mail) ((MailView) this).modul );
		return ((MailView) this).preferences;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see Base.ModelView#initSidepanel()
	 */
	public JComponent initSidepanel () {
		// TODO: How to Use Trees
		// http://java.sun.com/docs/books/tutorial/uiswing/components/tree.html
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Accounts");
		Account[] accounts = Account.getAccounts();
		if ( accounts != null )
			for (int i=0; i<accounts.length; i++){
			    String accName = accounts[i].getName();
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(accName);
				top.add(node);
			}
	
		if(((MailView) this).coSidepanel == null){
			((MailView) this).coSidepanel = new JTree(top);
			((JTree)((MailView) this).coSidepanel).addTreeSelectionListener(((MailView) this));
		}
		else{
			((DefaultTreeModel)((JTree)((MailView) this).coSidepanel).getModel()).setRoot(top);
		}
		return ((MailView) this).coSidepanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Base.ModelView#initToolbar()
	 */
	public JComponent initToolbar () {
		((MailView) this).tbToolbar.setLayout( new FlowLayout(FlowLayout.LEFT,5,0) );
		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener( ((MailView) this) );
		((MailView) this).tbToolbar.add(refreshButton);
		sendButton = new JButton("Send");
		sendButton.addActionListener( ((MailView) this) );
		((MailView) this).tbToolbar.add(sendButton);
		return ((MailView) this).tbToolbar;
	}
	/* === NONONONON === */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed ( final ActionEvent event ) {
		final Object source = event.getSource();

		if (source == sendButton){
			JTree tree = (JTree)((MailView) this).coSidepanel;
			if (tree.getSelectionPath() != null ){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
				if (!node.isRoot()){
					new MailWritePane(node.toString());
				}
				else {
					new MailWritePane();
				}
			}
			else {
				new MailWritePane();
			}
			return;
		}

		if (source == refreshButton){
			JTree tree = (JTree)((MailView) this).coSidepanel;
			if (tree.getSelectionPath() != null ){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
				if (!node.isRoot()){
					((MailView) this).lbStatusbar.setText( "New E-Mails loading..." );
					((MailView) this).lbStatusbar.paintImmediately(((MailView) this).lbStatusbar.getVisibleRect());
					MailContentPane mcp = (MailContentPane) ((MailView) this).coContent.getComponent(0);
					refreshButton.setEnabled( false );
					mcp.refresh();
					refreshButton.setEnabled( true );
					((MailView) this).lbStatusbar.setText( "Click on an Account to show the E-Mails" );
					((MailView) this).pbStatusbar.setValue(0);
				}
			}
			return;
		}

		if (source == miPreferences) {
			new PreferencesDialog( ((MailView) this).modul.getBase(), ((MailView) this).modul.getBase().getView(), new TreePath( ((MailView) this).preferences ) );
			return;
		} 

		super.actionPerformed( event );
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see miscellaneous.MultilingualListener#changeLanguage()
	 */
	public void changeLanguage () {
		((MailView) this).miPreferences.setText( "Preferences" );
		// TODO Hier müssen alle sprachabhänigen Strings gesetzt werden die im
		// gesammten Mail-Modul verwendet werden!
	}

	public void valueChanged(TreeSelectionEvent e){
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) ((JTree)e.getSource()).getSelectionPath().getLastPathComponent();
		if (node!=null && !node.isRoot()){
			try{
				((MailView) this).lbStatusbar.setText( "E-Mails loading..." );
				((MailView) this).lbStatusbar.paintImmediately(((MailView) this).lbStatusbar.getVisibleRect());
				((MailView) this).coSidepanel.setEnabled( false );
				String name = node.toString();
				((MailView) this).coContent.remove(0);
				final MailContentPane pane = new MailContentPane(name);
				pane.setProgressBar( ((MailView) this).pbStatusbar );
				pane.setVisible( true );
				((MailView) this).coContent.add( pane );
				//this.coContent.revalidate();
				//this.coContent.repaint();
				//while ( !this.coContent.isValid() ){};
			    pane.insertMessages();
				((MailView) this).coSidepanel.setEnabled( true );
				((MailView) this).pbStatusbar.setValue(0);
				((MailView) this).lbStatusbar.setText( "Click on an Account to show the E-Mails" );
			} catch (Exception ex) {
					((MailView) this).coSidepanel.setEnabled( true );
					((MailView) this).lbStatusbar.setText( "Click on an Account to show the E-Mails" );
					System.out.println(ex.toString());
			}
		}
	}
}




public class MailView extends  MailView$$MailBase  {

	public void actionPerformed ( final ActionEvent event ) {
		final Object source = event.getSource();

		if (source == sendButton){
			JTree tree = (JTree)((MailView) this).coSidepanel;
			if (tree.getSelectionPath() != null ){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
				if (!node.isRoot()){
					MailWritePane mwp = new MailWritePane(node.toString());
					mwp.registerModul(((MailView) this).modul);
				}
				else{
					MailWritePane mwp = new MailWritePane();
					mwp.registerModul(((MailView) this).modul);
				}
			}
			else{
				MailWritePane mwp = new MailWritePane();
				mwp.registerModul(((MailView) this).modul);
			}
			return;
		}

		if (source == refreshButton){
			JTree tree = (JTree)((MailView) this).coSidepanel;
			if (tree.getSelectionPath() != null ){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
				if (!node.isRoot()){
					((MailView) this).lbStatusbar.setText( "New E-Mails loading..." );
					((MailView) this).lbStatusbar.paintImmediately(((MailView) this).lbStatusbar.getVisibleRect());
					MailContentPane mcp = (MailContentPane) ((MailView) this).coContent.getComponent(0);
					refreshButton.setEnabled( false );
					mcp.refresh();
					refreshButton.setEnabled( true );
					((MailView) this).lbStatusbar.setText( "Click on an Account to show the E-Mails" );
					((MailView) this).pbStatusbar.setValue(0);
				}
			}
			return;
		}

		if (source == miPreferences) {
			new PreferencesDialog( ((MailView) this).modul.getBase(), ((MailView) this).modul.getBase().getView(), new TreePath( ((MailView) this).preferences ) );
			return;
		} 

		super.actionPerformed( event );
	}
      // inherited constructors



	/**
	 * Default constructor.
	 * 
	 * @param modul The modul which use the viewer.
	 */
	public MailView (  final Modul modul ) { super(modul); }
}