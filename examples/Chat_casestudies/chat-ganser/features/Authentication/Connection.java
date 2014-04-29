public class Connection {

    private boolean connectionAccepted = false;;
    
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
	original();
    }
    
    public void send(TextMessage msg) {
	
	if (connectionAccepted) {
	    original(msg);
	}
    }
}
