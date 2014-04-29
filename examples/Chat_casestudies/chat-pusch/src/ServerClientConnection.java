
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.File;




abstract class ServerClientConnection$$messaging extends Thread {
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	protected List serverClientConnections;
	protected String username;
	
	private boolean stop=false;
	
	public void stopThread() {
		stop=true;
	}
	
	public ServerClientConnection$$messaging(List serverClientConnections, Socket socket) {
		this.serverClientConnections=serverClientConnections;
		System.out.println("connection engaged:" + socket);
		try {
			out=new ObjectOutputStream(socket.getOutputStream());
			in= new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			MessageObject msg;
			while (!stop && (msg = (MessageObject) in.readObject())!=null) {
				handleIncomingMessageObject(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			stopThread();
			serverClientConnections.remove(((ServerClientConnection) this));
		}
	}

	/**
		 * decides what to do with incoming messages
		 * @param msg
		 *            the message (Object) received from the sockets
		 * @throws ClassNotFoundException 
		 * @throws IOException 
		 */
	protected void handleIncomingMessageObject(MessageObject msgo) {
			System.out.println(msgo.getMessageType());
			String messageType = msgo.getMessageType();
			
			if (messageType.equals(MessageType.AUTH)) {
					boolean auth= authenticate(msgo);
//					System.out.println(auth);
					if (auth) {
						ArrayList clientUsernames = new ArrayList();
						for (int i=0; i<serverClientConnections.size(); i++) {
							ServerClientConnection serverClientCon = (ServerClientConnection) serverClientConnections.get(i);
							clientUsernames.add(serverClientCon.getUsername());
						}
						for (int i=0; i<serverClientConnections.size(); i++) {
							ServerClientConnection serverClientCon = (ServerClientConnection) serverClientConnections.get(i);
							// TODO: send only online messages to users who are in friendlist.
							ArrayList withoutOwn= new ArrayList(clientUsernames);
							withoutOwn.remove(serverClientCon.getUsername());
							serverClientCon.sendMessage(new MessageObject(MessageType.USER_ON, msgo.getSender(), (String[]) withoutOwn.toArray(new String[0]), null, null));
						}
					}
			}
			else if (messageType.equals(MessageType.TEXT)) {
					if (((ServerClientConnection) this).isAuthenticated()) {
						msgo.setSender(username);
						Date currentDate= new Date(System.currentTimeMillis());
						String timestamp= currentDate.toString().substring(11, 19);
						msgo.setMessage(username+"["+timestamp+"]: "+msgo.getMessage());
						for (int i=0; i<msgo.getRecipients().length; i++) {
							String rec = msgo.getRecipients()[i];
							for (int k=0; k<serverClientConnections.size(); k++) {
								ServerClientConnection serverClientCon = (ServerClientConnection) serverClientConnections.get(k);
								if (serverClientCon.getUsername().equals(rec))
									serverClientCon.sendMessage(msgo);
							}
						}
					}
			}
			//historyOut.println(msgo.toString());
	}
	
	/** 
		 * sends a MessageObject to the client.
		 * @param msg
		 */
	public void sendMessage(MessageObject msg) {
		try {
			out.writeObject(msg);
			out.flush();
			out.reset();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
		 * @param authMsg the authentication message.
		 * @return true, if authentication was successful
		 */
	public boolean authenticate(MessageObject authMsg) {
		// sender = username  /  message = password
		if (((ServerClientConnection) this).isAuthenticated()) {
			sendMessage( new MessageObject(MessageType.AUTH_OK, null, null, null, "you are already logged in.") );
			System.out.println("already logged in.");
			return true;
		}
		String username = authMsg.getSender();
		String password = authMsg.getMessage();
		
		
		if (!password.equals("spl11")) {
			sendMessage( new MessageObject(MessageType.AUTH_ERROR, null, null, null, "false password") );
			return false;	
		}
		
		for (int i=0; i<serverClientConnections.size(); i++) {
			ServerClientConnection serverClientCon = (ServerClientConnection) serverClientConnections.get(i);
			if (username.equals(serverClientCon.getUsername())) {
				sendMessage( new MessageObject(MessageType.AUTH_ERROR, null, null, null, "username already in use") );
				return false;
			}
		}
		sendMessage( new MessageObject(MessageType.AUTH_OK, null, null, null, "authentication successful") );
		serverClientConnections.add(((ServerClientConnection) this));
		((ServerClientConnection) this).username= username;
		return true;
	}
	
	/**
		 * @return true, if user is authenticated <br>
		 * 		   false, if user is not yet authenticated
		 */
	public boolean isAuthenticated() {
		return (((ServerClientConnection) this).username != null);
	}

	public String getUsername() {
		return ((ServerClientConnection) this).username;
	}
}

abstract class ServerClientConnection$$rot13 extends  ServerClientConnection$$messaging  {
	

	protected void handleIncomingMessageObject(MessageObject msgo) {
		ROT13Encryption.decrypt(msgo);
		super.handleIncomingMessageObject(msgo);
	}
	
	public void sendMessage(MessageObject msg) {
		ROT13Encryption.encrypt(msg);
		super.sendMessage(msg);
	}
      // inherited constructors


	
	public ServerClientConnection$$rot13 ( List serverClientConnections, Socket socket ) { super(serverClientConnections, socket); }
}

abstract class ServerClientConnection$$invert extends  ServerClientConnection$$rot13  {
	

	protected void handleIncomingMessageObject(MessageObject msgo) {
		InvertEncryption.decrypt(msgo);
		super.handleIncomingMessageObject(msgo);
	}
	
	public void sendMessage(MessageObject msg) {
		InvertEncryption.encrypt(msg);
		super.sendMessage(msg);
	}
      // inherited constructors


	
	public ServerClientConnection$$invert ( List serverClientConnections, Socket socket ) { super(serverClientConnections, socket); }
}



public class ServerClientConnection extends  ServerClientConnection$$invert  {
	
	private PrintWriter historyOut;
	
	public ServerClientConnection(List serverClientConnections, Socket socket) { super(serverClientConnections, socket); 

		try {
			File f= new File("serverHistory.txt");
			f.delete();
			historyOut = new PrintWriter(new FileOutputStream("serverHistory.txt", true),true);
		} catch (IOException e) {
			e.printStackTrace();
		} }

	
	protected void handleIncomingMessageObject(MessageObject msgo) {
		historyOut.println("in: "+msgo);
		super.handleIncomingMessageObject(msgo);
	}
	
	public void sendMessage(MessageObject msgo) {
		historyOut.println("out: "+msgo);
		super.sendMessage(msgo);
	}
	
	public void setHistoryOut(PrintWriter historyOut) {
		((ServerClientConnection) this).historyOut= historyOut;
	}
      // inherited constructors


}