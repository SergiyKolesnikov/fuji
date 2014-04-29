package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import server.Connection;
import common.*;
/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Server {


public String getClientName(Connection c){
	System.out.println("getting clientname for "+c);
	
	String s= nicknamesR.get(c);
	System.out.println(": "+s);
	return s;
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
		String nick =getFreeNick();
		nicknames.put(nick,connection);
		nicknamesR.put(connection, nick);
		return connection;
	}
	private String getFreeNick(){
		int i=1;
		while(nicknames.containsKey(new String("User"+i))){
			i++;
		}
		return "User"+i;
	}
	/**
	 * list of all known connections
	 */
	protected HashSet<Connection> connections = new HashSet<Connection>();


	/**
	 * remove a connection so that broadcasts are no longer sent there.
	 * 
	 * @param connection
	 *            connection to remove
	 */
	public void removeConnection(Connection connection) {
		connections.remove(connection);
		nicknames.remove(nicknamesR.get(connection));
		nicknamesR.remove(connection);
	
		
	}
	
	HashMap<String, Connection> nicknames= new HashMap<String,Connection>();
	HashMap<Connection, String> nicknamesR= new HashMap<Connection,String>();
	
	public void sendPrivateMessage(TextMessage msg){
	Connection con = nicknames.get(msg.getTargetUser());
	con.send(msg.getContent(),msg.isEncrypted());
	
	}
	public void changeUserName(String oldname,TextMessage msg){
		System.out.println("changeUsername");
		Connection con = nicknames.get(oldname);
		nicknames.remove(oldname);
		nicknames.put(msg.getNewName(), con);
		nicknamesR.remove(con);
		nicknamesR.put(con,msg.getNewName());
	}
	
}
