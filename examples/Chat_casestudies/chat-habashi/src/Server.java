
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;



public class Server{

	private HashSet connections = new HashSet();
	
	public static void main(String args[]) throws IOException {
		if (args.length != 1)
			throw new RuntimeException("Syntax: ChatServer <port>");
		new Server(Integer.parseInt(args[0]));
	}
	
	public Server(int port) throws IOException{
		ServerSocket loginSocket = new ServerSocket(port);
		while(true){
			System.out.println("Waiting for Clients");
			Socket socketClient = loginSocket.accept();
			System.out.println("Accepted Client: " + socketClient.getInetAddress());
			Connection c = connectTo(socketClient);
			c.start();
		}
	}
	
	private Connection connectTo(Socket socket){
		Connection connection = new Connection(socket, ((Server) this));
		connections.add(connection);
		return connection;
	}

	public void removeConnection(Connection connection) {
		connections.remove(connection);
	}

	public void broadcast(String text) {
		synchronized (connections) {
			for(Iterator iterator = connections.iterator(); iterator.hasNext();){
				Connection connection = (Connection) iterator.next();
				connection.send(text);
			}
		}
	}
	
}