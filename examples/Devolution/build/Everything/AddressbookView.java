package Everything;import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.TreePath;
import java.util.Iterator;



/**
 * @author Marcel Jaeschke
 * @since 1.6
 */
abstract class AddressbookView$$Addressbook extends ModelView implements MouseListener {
	// Menu
	protected final JMenuItem miPreferences = new JMenuItem( ProgramImages.PREFERENCES );
	protected final JMenuItem miNewContact = new JMenuItem();
	protected final JPopupMenu mPopup = new JPopupMenu();
	protected final JMenuItem miPopupEdit = new JMenuItem();
	//
	protected final DefaultListModel list = new DefaultListModel();
	protected Buddy popupBuddy = null;

	/**
	 * Default constructor.
	 * 
	 * @param addressbookModul The modul which use the viewer.
	 */
	public AddressbookView$$Addressbook ( final Addressbook addressbookModul ) {
		super( addressbookModul );
		initView();
		initPopup();
	}
	protected void initPopup () {
		((AddressbookView) this).miPopupEdit.addActionListener( ((AddressbookView) this) );
    ((AddressbookView) this).mPopup.add( ((AddressbookView) this).miPopupEdit );
	}
	/* === INITIALIZER === */
	/*
	 * (non-Javadoc)
	 * 
	 * @see Base.ModelView#initMainContent()
	 */
	public JComponent initMainContent () {
		getMainContent().add( new JLabel( "Not implemented." ), BorderLayout.CENTER );
		return ((AddressbookView) this).coContent;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see Base.ModelView#initMenu()
	 */
	public JMenu initMenu () {
		((AddressbookView) this).miNewContact.addActionListener( ((AddressbookView) this) );
		((AddressbookView) this).mnMenu.add( ((AddressbookView) this).miNewContact );
		((AddressbookView) this).mnMenu.addSeparator();
		((AddressbookView) this).miPreferences.addActionListener( ((AddressbookView) this) );
		((AddressbookView) this).mnMenu.add( ((AddressbookView) this).miPreferences );
		return ((AddressbookView) this).mnMenu;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see Base.ModelView#initPreferences()
	 */
	public PreferencesEntry initPreferences () {
		((AddressbookView) this).preferences = new AddressbookPreferences( (Addressbook) ((AddressbookView) this).modul );
		return ((AddressbookView) this).preferences;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see Base.ModelView#initSidepanel()
	 */
	public JComponent initSidepanel () {
		final Iterator iterator = ( (Addressbook) ((AddressbookView) this).modul).getAllBuddy().iterator();
		while ( iterator.hasNext() ) {
			((AddressbookView) this).list.addElement( iterator.next() );
		}
		((AddressbookView) this).coSidepanel = new JList( ((AddressbookView) this).list );
		((AddressbookView) this).coSidepanel.addMouseListener( ((AddressbookView) this) );
		return ((AddressbookView) this).coSidepanel;
	}
	public DefaultListModel getListModel () {
		return ((AddressbookView) this).list;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see Base.ModelView#initToolbar()
	 */
	public JComponent initToolbar () {
		return ((AddressbookView) this).tbToolbar;
	}
	/* === NONONONON === */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed ( final ActionEvent event ) {
		Object source = event.getSource();
		if ( ((AddressbookView) this).miNewContact.equals( source ) ) {
			new BuddyDialog( (Addressbook) ((AddressbookView) this).modul );
		} else if ( ((AddressbookView) this).miPopupEdit.equals( source ) ) {
			new BuddyDialog( ((AddressbookView) this).popupBuddy, (Addressbook) ((AddressbookView) this).modul );
			((AddressbookView) this).popupBuddy = null;
		} else if ( ((AddressbookView) this).miPreferences.equals( source ) ) {
			new PreferencesDialog( ((AddressbookView) this).modul.getBase(), ((AddressbookView) this).modul.getBase().getView(), new TreePath( ((AddressbookView) this).preferences ) );
		} else {
			super.actionPerformed( event );
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see miscellaneous.MultilingualListener#changeLanguage()
	 */
	public void changeLanguage () {
		((AddressbookView) this).miNewContact.setText( "New Contact" );
		((AddressbookView) this).miPreferences.setText( "Preferences" );
		((AddressbookView) this).miPopupEdit.setText( "Edit" );
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked ( MouseEvent event ) {
		if ( event.getButton() == MouseEvent.BUTTON3 ) {
			((AddressbookView) this).popupBuddy = (Buddy) ((AddressbookView) this).list.get( ( (JList) ((AddressbookView) this).coSidepanel ).locationToIndex( event.getPoint() ) );
			((AddressbookView) this).mPopup.show( event.getComponent(), event.getX(), event.getY() );
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered ( MouseEvent e ) {}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited ( MouseEvent e ) {}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed ( MouseEvent e ) {}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased ( MouseEvent e ) {}
}

public class AddressbookView extends  AddressbookView$$Addressbook  {
	protected JMenuItem miPopupChat;
	
	protected void initPopup () {
		if( ((AddressbookView) this).miPopupChat == null) {
			((AddressbookView) this).miPopupChat = new JMenuItem();
		}
		((AddressbookView) this).miPopupChat.addActionListener( ((AddressbookView) this) );
    	((AddressbookView) this).mPopup.add( ((AddressbookView) this).miPopupChat );
		super.initPopup();
	}
	
	public void changeLanguage () {
		super.changeLanguage();
		((AddressbookView) this).miPopupChat.setText( "Chat" );
	}
	
	public void actionPerformed ( final ActionEvent event ) {
		Object source = event.getSource();
		if ( ((AddressbookView) this).miPopupChat.equals( source ) ) {
			( (IMView) ((AddressbookView) this).modul.getBase().getModul( "Instant Messenger" ).getView() ).createNewTab( new IMBuddy( ((AddressbookView) this).popupBuddy.getInfo( Buddy.JABBER ), ((AddressbookView) this).popupBuddy.getInfo( Buddy.NICK ) ) );
			((AddressbookView) this).modul.getBase().getView().setActiveModul( "Instant Messenger" );
			((AddressbookView) this).popupBuddy = null;
		} else {
			super.actionPerformed( event );
		}
	}
      // inherited constructors



	/**
	 * Default constructor.
	 * 
	 * @param addressbookModul The modul which use the viewer.
	 */
	public AddressbookView (  final Addressbook addressbookModul ) { super(addressbookModul); }
}