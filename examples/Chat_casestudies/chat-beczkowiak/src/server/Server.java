package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;



/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
abstract class Server$$Base$server {
    
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
    public Server$$Base$server(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        
        beforeStarting();
        while (true) {
            System.out.println("Waiting for Connections...");
            Socket client = server.accept();
            System.out.println("Accepted from " + client.getInetAddress());
            Connection c = connectTo(client);
            c.start();
		}
    }
    
    protected void beforeStarting() throws IOException {	
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
        Connection connection = new Connection(socket, ((Server) this));
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
    public void broadcast(String text) {
        synchronized (connections) {
            for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
                Connection connection = (Connection) iterator.next();
                connection.send(text);
            }
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
}



abstract class Server$$History$server extends  Server$$Base$server  {
	
	protected BufferedWriter bw;
	
	protected void beforeStarting() throws IOException {
		super.beforeStarting();
		((Server) this).bw = new BufferedWriter(new FileWriter(new File("Server.txt"), true));
	}
	
	public void broadcast(String text) {
		super.broadcast(text);
        try {
            ((Server) this).bw.write(text + "\n");
            ((Server) this).bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
      // inherited constructors



    /**
     * awaits incoming connections and creates Connection objects accordingly.
     * 
     * @param port
     *            port to listen on
     */
    public Server$$History$server ( int port )  throws IOException{ super(port); }
}

public class Server extends  Server$$History$server  {
	
	protected String[] blocklist;
	
	protected void beforeStarting() throws IOException {
		super.beforeStarting();
		blocklist = new String[] {"Idiot", "Bombe", "Terrorist"};
    }
	
	public void broadcast(String text) {
        for (int i = 0; i < ((Server) this).blocklist.length; i++) {
            if (text.contains(((Server) this).blocklist[i])) {
                return;
            }
        }
        super.broadcast(text);
	}
      // inherited constructors



    /**
     * awaits incoming connections and creates Connection objects accordingly.
     * 
     * @param port
     *            port to listen on
     */
    public Server ( int port )  throws IOException{ super(port); }
}