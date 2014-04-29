package Everything;import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smack.filter.*;
import org.jivesoftware.smack.ChatManager.*;



abstract class IMView$$InstantBase extends ModelView implements MouseListener, KeyListener {
    protected final JMenuItem miPreferences = new JMenuItem( ProgramImages.PREFERENCES );
    protected DefaultMutableTreeNode rosterRoot;
    protected JTree contactTree;
    protected IMTabs tabManager;
    protected JTextField txtInputField = new JTextField();
    protected InstantMessenger modul;
    protected Devolution base;
    
    /**
     * Default constructor.
     * 
     * @param modul The modul which use the viewer.
     */
    public IMView$$InstantBase ( final Modul modul, final Devolution base ) {
        super( modul );
        this.base = base;
        this.modul = (InstantMessenger)modul;
        initView();
        this.lbStatusbar.setText( "Instant Messenger" );
    }

    public JComponent initMainContent () {
    	((IMView) this).tabManager = new IMTabs(((IMView) this));
        getMainContent().add( ((IMView) this).tabManager.getPane(), BorderLayout.CENTER );
        getMainContent().add(((IMView) this).txtInputField, BorderLayout.SOUTH);
        ((IMView) this).txtInputField.addKeyListener(((IMView) this));
        return ((IMView) this).coContent;
    }

    public JMenu initMenu () {
        ((IMView) this).mnMenu.addSeparator();
        ((IMView) this).miPreferences.addActionListener( ((IMView) this) );
        ((IMView) this).mnMenu.add( ((IMView) this).miPreferences );
        return ((IMView) this).mnMenu;
    }

    public PreferencesEntry initPreferences () {
        ((IMView) this).preferences = new IMPreferences( (InstantMessenger) ((IMView) this).modul );
        return ((IMView) this).preferences;
    }

    public JComponent initSidepanel() {
    	rosterRoot = new DefaultMutableTreeNode("Instant Messenging");
    	((IMView) this).contactTree = new JTree(createRosterTree(rosterRoot));
    	((IMView) this).contactTree.addMouseListener(((IMView) this)); // To detect double clicks
    	((IMView) this).contactTree.setRootVisible(false);
        ((IMView) this).coSidepanel = ((IMView) this).contactTree;        
        return ((IMView) this).coSidepanel;
    }
    
    protected DefaultMutableTreeNode createRosterTree(DefaultMutableTreeNode root) {
    	// Protocols refine me here
		return root;
    }

    public JComponent initToolbar () {
		// Protocols refine me here
		return ((IMView) this).tbToolbar;
    }
    
    protected void createNewTab(TreePath buddyPath) {
    	// Protocols refine me here
    }
    
    protected void sendMessage(String to, String msg) {
    	// Protocols refine me here
    }
    
    public void changeLanguage () {
        ((IMView) this).miPreferences.setText( "Preferences" );
        // TODO Hier müssen alle sprachabhänigen Strings gesetzt werden
    }
    
	public void expandAll(JTree tree) {
		int row = 0;
		while (row < tree.getRowCount()) {
			tree.expandRow(row);
			row++;
		}
	}

    public void actionPerformed ( final ActionEvent event ) {
        final Object source = event.getSource();
        if ( ((IMView) this).miPreferences.equals( source ) ) {
            new PreferencesDialog( ((IMView) this).modul.getBase(), ((IMView) this).modul.getBase().getView(), new TreePath( ((IMView) this).preferences ) );
        } else {
            super.actionPerformed( event );
        }
    }

	// Mouse listener for double clicks
	public void mousePressed(MouseEvent e) {
         if ( e != null && e.getClickCount() == 2) {
         	TreePath selPath = ((IMView) this).contactTree.getPathForLocation(e.getX(), e.getY());
         	if (selPath != null) {
				createNewTab(selPath);
			}
		}
    }
	public void	mouseEntered(MouseEvent e) {}
 	public void	mouseExited(MouseEvent e) {}
	public void	mouseClicked(MouseEvent e) {}
 	public void	mouseReleased(MouseEvent e) {}
 	
 	// Key listener for return (message sending)
	public void	keyPressed(KeyEvent e) {
		if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
			sendMessage(((IMView) this).tabManager.getActiveRoom(), ((IMView) this).txtInputField.getText());
			((IMView) this).txtInputField.setText("");
		}
	}
 	public void	keyReleased(KeyEvent e) {}
 	public void	keyTyped(KeyEvent e) {}
    
}



public class IMView extends  IMView$$InstantBase  implements JabberRosterListener {
	protected JButton btnJabberConnect;
    protected JMenuItem mnuJabberConnect;
    protected JMenuItem mnuJabberDisconnect;
    protected JabberConnection jabCon;

    public JMenu initMenu () {
    	JMenu mnuJabber = new JMenu("Jabber");
    	((IMView) this).mnMenu.add(mnuJabber);
    	
    	mnuJabber.add(mnuJabberConnect = new JMenuItem("Connect"));
    	mnuJabberConnect.addActionListener(((IMView) this));
    	
        mnuJabber.add(mnuJabberDisconnect = new JMenuItem("Disconnect"));
        mnuJabberDisconnect.addActionListener(((IMView) this));
        mnuJabberDisconnect.setEnabled(false);
        
        return super.initMenu();
    }
    
    public JComponent initToolbar() {
        ((IMView) this).tbToolbar.add(btnJabberConnect = new JButton("Connect to Jabber"));
        btnJabberConnect.addActionListener(((IMView) this));
        return super.initToolbar();
    }
    
    public void actionPerformed(final ActionEvent event) {
    	// Refines the IMView Action Listener
    	final Object source = event.getSource();
    	if (source.equals(btnJabberConnect)) {
        	if (btnJabberConnect.getText().contains("Disconnect")) {
        		disconnect();
        	} else {
        		connect();
        	}
        } else if (source.equals(mnuJabberConnect)) {
        	connect();
        } else if (source.equals(mnuJabberDisconnect)) {
        	disconnect();
        }
    	super.actionPerformed(event);
    }

    public void connect() {
    	mnuJabberConnect.setEnabled(false);
        // Read the account/password from the setting here
		
		try {
			String username = ((IMView) this).modul.getSetting().getOptionAsString(IMPreferences.FEATURE_NAME, "JabberUsername");
			String password = TextCoding.rot48(((IMView) this).modul.getSetting().getOptionAsString(IMPreferences.FEATURE_NAME, "JabberPassword"));
			String domain = ((IMView) this).modul.getSetting().getOptionAsString(IMPreferences.FEATURE_NAME, "JabberDomain");
			String resource = ((IMView) this).modul.getSetting().getOptionAsString(IMPreferences.FEATURE_NAME, "JabberResource");
			((IMView) this).jabCon.connect(username, password, domain, resource);
			((IMView) this).jabCon.getConnection().addPacketListener(((IMView) this).tabManager, new PacketTypeFilter(Message.class));
		} catch (XMPPException e) {
			System.err.println("Jabber connection error:");
			System.err.println(e.getMessage());
			disconnect();
		} catch (SettingException e) {
			System.err.println("Error! Setting exception:");
			System.err.println(e.getMessage());
		}
		btnJabberConnect.setText("Disconnect from Jabber");
		mnuJabberDisconnect.setEnabled(true);
    }
    
    public void disconnect() {
    	mnuJabberDisconnect.setEnabled(false);
        // Read the account/password from the setting here
		
		try {
			((IMView) this).jabCon.disconnect();
		} catch (XMPPException e) {
			System.err.println("Jabber connection error:");
			System.err.println(e.getMessage());
		}
		
		btnJabberConnect.setText("Connect to Jabber");
		mnuJabberConnect.setEnabled(true);
    }
    
    protected DefaultMutableTreeNode createRosterTree(DefaultMutableTreeNode rosterRoot) {
     	// This is because somehow this method is called before this class is created...
		if (jabCon == null) {	
			jabCon = new JabberConnection(((IMView) this).base);
			jabCon.getJabberRoster().addLineListener(((IMView) this));
		}
		
    	rosterRoot.add(jabCon.getJabberRoster().getJabberRosterRoot());
    	return rosterRoot;    	
    }
    
    public void rosterChanged() {
    	try {
    		((IMView) this).contactTree.updateUI();
    	} catch (Exception e) {
    		System.err.println("Error in rosterChanged()");
    	}
    }
    
    protected void createNewTab(TreePath buddyPath) {
    	if (!buddyPath.getPath()[1].toString().equals("Jabber") || buddyPath.getPath().length <= 3) {
    		super.createNewTab(buddyPath);
    		return;
    	}
    	
		// Get the buddy
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)buddyPath.getPath()[buddyPath.getPath().length - 1];
    	IMBuddy buddy = (IMBuddy)node.getUserObject(); 
		createNewTab(buddy);
    }
    
    public void createNewTab(IMBuddy buddy) {
    	buddy.setChat(((IMView) this).jabCon.getConnection().getChatManager().createChat(buddy.getUID(), new JabberListener()));
		((IMView) this).tabManager.addRoom(buddy);
    }
    
    protected void sendMessage(String to, String msg) {
    	try {
	    	Chat chat = ((IMView) this).jabCon.getConnection().getChatManager().createChat(to, new JabberListener());
	    	chat.sendMessage(msg);
	    } catch (XMPPException e) {
	    	System.err.println("Error sending message to " + to);
	    	return;
	    }
	    ChatPanel panel = ((IMView) this).tabManager.getPanel(((IMView) this).tabManager.getActiveRoom());
	    String user = ((IMView) this).jabCon.getConnection().getUser();
    	panel.addMessage(user.substring(0, user.indexOf('/')), msg);
    }
      // inherited constructors


    
    /**
     * Default constructor.
     * 
     * @param modul The modul which use the viewer.
     */
    public IMView (  final Modul modul, final Devolution base ) { super(modul, base); }
}