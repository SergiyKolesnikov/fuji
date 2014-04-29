import java.io.IOException; 
import java.net.ServerSocket; 
import java.net.Socket; 
import java.util.HashSet; import java.util.Iterator; 

public   class  Server {
	

    public static void main(String args[]) throws IOException {
	if (args.length != 1)
	    throw new RuntimeException("Syntax: ChatServer <port>");
	new Server(Integer.parseInt(args[0]));
    }

	

    /**
     * awaits incoming connections and creates Connection objects accordingly.
     * 
     * @param port
     *            port to listen on
     */
    public Server(int port) throws IOException {
	ServerSocket server = new ServerSocket(port);
	while (true) {
	    System.out.println("Waiting for Connections...");
	    Socket client = server.accept();
	    System.out.println("Accepted from " + client.getInetAddress());
	    Connection c = connectTo(client);
	    c.start();
	}
    }

	

    /**
     * creates a new connection for a socket
     * 
     * @param socket
     *            socket
     * @return the Connection object that handles all further communication with
     *         this socket
     */
    public Connection connectTo(Socket socket) {
	Connection connection = new Connection(socket, this);
	connections.add(connection);
	return connection;
    }

	

    /**
     * list of all known connections
     */
    protected HashSet connections = new HashSet();

	

    /**
     * send a message to all known connections
     * 
     * @param text
     *            content of the message
     */
     private void  broadcast__wrappee__Color  (String text) {
	synchronized (connections) {
	    for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
		Connection connection = (Connection) iterator.next();
		connection.send(text);
	    }
	}
    }

	

    public void broadcast(String text) {
	String msgCmd = "/msg";

	if (text.matches(".*" + msgCmd + " [^\\s]+ .+")) {
	    String usrNameExt = text.substring(text.indexOf(msgCmd)
		    + msgCmd.length() + 1);
	    String userName = usrNameExt.substring(0, usrNameExt.indexOf(' '));
	    String message = text.replaceFirst(msgCmd + " [^\\s]+", "");
	    this.unicast(message, userName);
	} else {
	    broadcast__wrappee__Color(text);
	}
    }

	

    /**
     * remove a connection so that broadcasts are no longer sent there.
     * 
     * @param connection
     *            connection to remove
     */
    public void removeConnection(Connection connection) {
	connections.remove(connection);
    }

	

    public void unicast(String text, String userName) {

	synchronized (connections) {

	    for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
		Connection connection = (Connection) iterator.next();

		if ((connection.getUserName() != null)
			&& (connection.getUserName().equals(userName))) {
		    connection.send(text);
		}
	    }
	}
    }


}
