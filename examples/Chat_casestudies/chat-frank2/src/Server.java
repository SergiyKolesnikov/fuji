
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;



/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
abstract class Server$$Base {

	public static void main(String args[]) throws IOException {
		
		new Server(8081);
	}

	final String psswd = "halloWelt";

	/**
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 */
	public Server$$Base(int port) throws IOException {
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
	public void broadcast(String name, String txt) throws  IOException {
		synchronized (connections) {
			
		 	String text = filter(decrypt(txt));
			
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				connection.send(name, encrypt(text));
			}
			history(name+text);
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
	
	//==============================================================
	public void history(String msg){}
	public String filter(String msg){return msg;}
	public String decrypt(String msg){return msg;}
	public String encrypt(String msg){return msg;}
}



public class Server extends  Server$$Base  {

	public void history(String msg){

		try {
        	RandomAccessFile raf = new RandomAccessFile("/home/rene/serverHistory.log", "rws");
        	raf.skipBytes( (int)raf.length() );
        	raf.writeBytes(msg+"\n");
		}
		catch (IOException e){}
    }
    
    public void broadcast(String name, String text) throws FileNotFoundException, IOException {
		
		super.broadcast(name, text);
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