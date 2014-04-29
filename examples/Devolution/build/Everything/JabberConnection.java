package Everything;
import org.jivesoftware.smack.*;
import java.util.*;
import javax.swing.tree.*;



public class JabberConnection {
	protected XMPPConnection connection;
	protected JabberRoster jabRoster;
	protected Devolution base;

	public JabberConnection(Devolution base) {
		this.base = base;
		this.jabRoster = new JabberRoster(this);
	}

	// Verbindet mit dem angegebenen Jabber Server
	public void connect(String username, String password, String resource, String servername, String host, int port) throws XMPPException {
		connection = new XMPPConnection(new ConnectionConfiguration(host, port, servername));
		login(username, password, resource);
	}
	
	public void disconnect() throws XMPPException {
		connection.disconnect();
		//connection = null;
	}
	
	// Verbindet mit einem Jabber Server, Serveradresse wird aus DNS-Record gelesen
	public void connect(String username, String password, String servername, String resource) throws XMPPException {
		connection = new XMPPConnection(servername);
		login(username, password, resource);
	}
	
	private void login(String username, String password, String resource) throws XMPPException {
		((JabberConnection) this).connection.connect();
		((JabberConnection) this).connection.login(username, password, resource);
		
		// Update roster and add roster listener
		((JabberConnection) this).jabRoster.rebuildRoster();
		((JabberConnection) this).connection.getRoster().addRosterListener(((JabberConnection) this).jabRoster);
	}
	
	public JabberRoster getJabberRoster() {
 		return ((JabberConnection) this).jabRoster;
 	}
 	
 	public Devolution getBase() {
 		return ((JabberConnection) this).base;
 	}
 	
 	public XMPPConnection getConnection() {
 		return ((JabberConnection) this).connection;
 	}
}