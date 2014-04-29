package Everything;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.*;



abstract class IMTabs$$InstantBase implements ActionListener {
	protected JTabbedPane tabPane = new JTabbedPane();
	protected ArrayList activeBuddyList = new ArrayList();
	protected final IMView imview;
	
	public IMTabs$$InstantBase(IMView imview) {
		this.imview = imview;
	}
	
	public JTabbedPane getPane () {
		return ((IMTabs) this).tabPane;
	}
	
	public JScrollPane addRoom ( IMBuddy buddy ) {
		((IMTabs) this).activeBuddyList.add(buddy);
		String room = buddy.getUID();
		
		JPanel panel = new JPanel( new FlowLayout( FlowLayout.LEFT, 0, 0 ) );
		JLabel label = new JLabel( room );
		JButton tabButton = new JButton( ProgramImages.CLOSE );
		ChatPanel tab = new ChatPanel();
		panel.setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0 ) );
		panel.setOpaque( false );
		//
		tabButton.setContentAreaFilled( false );
		tabButton.setBorder( BorderFactory.createEmptyBorder( 0, 5, 0, 0 ) );
		tabButton.setActionCommand( room );
		tabButton.setToolTipText( "Close" );
		tabButton.addActionListener( ((IMTabs) this) );

		panel.add( label, BorderLayout.WEST );
		panel.add( tabButton, BorderLayout.EAST );
		
		JScrollPane scrollTab = new JScrollPane(tab);
		scrollTab.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		((IMTabs) this).tabPane.addTab( room, scrollTab );
		((IMTabs) this).tabPane.setTabComponentAt( ((IMTabs) this).tabPane.getTabCount() - 1, panel );
		
		return scrollTab;
	}
	
	public void closeAll () {
		for ( int i = ((IMTabs) this).tabPane.getTabCount() - 1; i >= 0; i-- ) {
			close( ((IMTabs) this).tabPane.getTitleAt( i ) );
		}
		((IMTabs) this).activeBuddyList.clear();
	}
	
	protected ChatPanel getPanel ( String room ) {
		int index = ((IMTabs) this).tabPane.indexOfTab( room );
		if ( index < 0 ) { return null; }
		JScrollPane sp = (JScrollPane)((IMTabs) this).tabPane.getComponentAt(index);
		ChatPanel cp = (ChatPanel)sp.findComponentAt(10, 10);
		if (cp == null) {
			sp.updateUI();
			cp = (ChatPanel)sp.findComponentAt(10, 10);
		}
		return cp;
	}
	
	public void close ( String room ) {
		int idx = ((IMTabs) this).tabPane.indexOfTab( room );
		((IMTabs) this).tabPane.remove( idx );
		((IMTabs) this).activeBuddyList.remove( idx );
	}
	
	public String getActiveRoom () {
		if ( isEmpty() ) { return null; }
		return ((IMTabs) this).tabPane.getTitleAt( ((IMTabs) this).tabPane.getSelectedIndex() );
	}

	public void actionPerformed ( ActionEvent e ) {
		close( e.getActionCommand() );
	}

	public boolean isEmpty () {
		return ((IMTabs) this).tabPane.getSelectedIndex() < 0;
	}
	
	public void addMessage(String sender, String msg) {
		ChatPanel tab;
		if ( ((IMTabs) this).tabPane.indexOfTab( sender ) < 0 ) {
			//tab = addRoom( sender );
			System.out.println("DEBUG: Room doesn't exist. Please create it before calling this function");
			return;
		} else {
			tab = getPanel( sender );
		}
		
		try {
			tab.addMessage( sender, msg );
			tab.setCaretPosition( tab.getDocument().getLength() );
		} catch (Exception e) {
			System.err.println("Error in InstantBase.IMTabs.addMessage()");
		}
	}
}



public class IMTabs extends  IMTabs$$InstantBase  implements PacketListener {	
	public void processPacket(Packet packet)  {
        if (packet instanceof Message) {
        	Message msg = (Message)packet;
        	if (msg.getBody() == null) return;
        	String sender = msg.getFrom().substring(0,msg.getFrom().indexOf('/'));
        	if (getPanel(sender) == null) { // Kein Raum
				IMBuddy buddy = new IMBuddy(sender, sender);
				imview.createNewTab(buddy);
        	}
        	addMessage(sender, msg.getBody());
        }
    }
      // inherited constructors


	
	public IMTabs ( IMView imview ) { super(imview); }
}