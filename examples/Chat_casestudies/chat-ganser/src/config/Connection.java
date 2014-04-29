import java.io.EOFException; 
import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.net.SocketException; public   class  Connection  extends Thread {
	
    protected Socket socket;

	
    protected ObjectInputStream inputStream;

	
    protected ObjectOutputStream outputStream;

	
    private Server server;

	

    public Connection(Socket s, Server server) {
	this.socket = s;
	try {
	    inputStream = new ObjectInputStream((s.getInputStream()));
	    outputStream = new ObjectOutputStream((s.getOutputStream()));
	} catch (IOException e) {
	    e.printStackTrace();
	}

	this.server = server;
    }

	

    /**
     * waits for incoming messages from the socket
     */
     private void  run__wrappee__UserNames  () {
	String clientName = socket.getInetAddress().toString();
	try {
	    server.broadcast(clientName + " has joined.");
	    Object msg = null;
	    while ((msg = inputStream.readObject()) != null) {
		handleIncomingMessage(clientName, msg);
	    }
	} catch (SocketException e) {
	} catch (EOFException e) {
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    server.removeConnection(this);
	    server.broadcast(clientName + " has left.");
	    try {
		socket.close();
	    } catch (IOException ex) {
		ex.printStackTrace();
	    }
	}
    }

	
    
    public void run() {
	
	try {
	    Object loginMessage = inputStream.readObject();

	    connectionAccepted = (loginMessage instanceof LoginMessage)
		    && (((LoginMessage) loginMessage).getPassword()
			    .equals(Constants.CLIENT_PASSWORD));
	    outputStream.writeObject(new LoginReplyMessage(connectionAccepted));
	    String clientName = socket.getInetAddress().getHostAddress();
	    
	    if (connectionAccepted) {
		System.out.println("Client " + clientName + " accepted.");
	    } else {
		System.out.println("Client " + clientName + " rejected.");
		server.removeConnection(this);
		return;
	    }
	} catch (IOException e) {
	    server.removeConnection(this);
	} catch (ClassNotFoundException e) {
	    server.removeConnection(this);
	}
	run__wrappee__UserNames();
    }

	

    /**
     * decides what to do with incoming messages
     * 
     * @param name
     *            name of the client
     * @param msg
     *            received message
     */
     private void  handleIncomingMessage__wrappee__Base  (String name, Object msg) {
	if (msg instanceof TextMessage)
	    server.broadcast(name + " - " + ((TextMessage) msg).getContent());
    }

	

     private void  handleIncomingMessage__wrappee__Color  (String name, Object msg) {
	
	if (msg instanceof TextMessage) {
	    	TextMessage message = (TextMessage) msg;
	    	String color = message.getColor();
	    	String text = message.getContent();
	    	
	    	if (color != null) {
	    	    text = "<" + color + ">" + text + "</" + color + ">";
	    	    msg = new TextMessage(text);
	    	}
	}
	handleIncomingMessage__wrappee__Base(name, msg);
    }

	

    private void handleIncomingMessage(String name, Object msg) {

	if (msg instanceof TextMessage) {
	    String text = ((TextMessage) msg).getContent().trim();
	    String setUserCmd = "/username";

	    if (text.matches(".*" + setUserCmd + " .+")) {
		text = text.substring(text.indexOf(setUserCmd)
			+ setUserCmd.length() + 1);
		int spaceIndex = text.indexOf(' ');
		
		if (spaceIndex > 0) {
		    this.userName = text.substring(0, spaceIndex);
		} else {
		    this.userName = text;
		}
		this.send("OK. User name is " + this.userName);
		return;
	    }
	}
	handleIncomingMessage__wrappee__Color(name, msg);
    }

	

    /**
     * sends a message to the client
     * 
     * @param line
     *            text of the message
     */
    public void send(String line) {
	send(new TextMessage(line));
    }

	

     private void  send__wrappee__UserNames  (TextMessage msg) {

	try {
	    synchronized (outputStream) {
		outputStream.writeObject(msg);
	    }
	    outputStream.flush();
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }

	
    
    public void send(TextMessage msg) {
	
	if (connectionAccepted) {
	    send__wrappee__UserNames(msg);
	}
    }

	

    private String userName;

	

    public String getUserName() {
	return this.userName;
    }

	

    private boolean connectionAccepted = false;

	;


}
