package Color;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JOptionPane;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.sql.*;



/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
abstract class Server$$server {
	int serverPort;
	
	public static void main(String args[]) throws IOException {
		new Server();
	}

	/**
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 */
	public Server$$server() throws IOException {
		getConfiguration();
		ServerSocket server = new ServerSocket(serverPort);
		while (true) {
			System.out.println("Waiting for Connections...");
			Socket client = server.accept();
			System.out.println("Accepted from " + client.getInetAddress());
			Connection c = connectTo(client);
			c.start();
		}
	}
	public Server$$server(boolean validate){}
		
	
	protected synchronized void getConfiguration()
	{
		try
		{
			char[] buffer = new char[128];

			FileReader configFile = new FileReader("serverConfig.cfg");
			
			configFile.read( buffer );
			
			String value = String.copyValueOf( buffer );
			String[] temp = value.split(";");
			
			serverPort = Integer.parseInt( temp[0] );
			configFile.close();
		}
		catch( FileNotFoundException fnf_e )
		{
			JOptionPane.showMessageDialog( null,"Configuration File Not Found, Using Defaults","Configuration File Missing",JOptionPane.ERROR_MESSAGE );
			serverPort = 1665;
	
		}
		catch( IOException io_e )
		{
			JOptionPane.showMessageDialog( null,"Error Reading Configuration File, Using Defaults","Configuration Error",JOptionPane.ERROR_MESSAGE );
			serverPort = 1665;
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
	public void broadcast(TextMessage msg) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				connection.send(msg);
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



public class Server extends  Server$$server  {
	Connection conn = null;
	Hashtable userData = null;
	public static void main(String args[]) throws IOException {
		boolean authentication = true;
		new Server(authentication);
	}
	protected void getDB(){
		userData = new Hashtable();
		try{
			FileInputStream fstream = new FileInputStream("user.db");
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    while ((strLine = br.readLine()) != null)   {
		    	String[] temp = strLine.split(";");
		    	userData.put(temp[0],temp[1]);
		    }
		    in.close();
		    /*Enumeration keys =  userData.keys();
		    while (keys.hasMoreElements()){
		    	System.out.println((String)(keys.nextElement()));
		    }*/
		} catch (Exception e){//Catch exception if any
		    	System.err.println("Error: " + e.getMessage());
		}
	}
	public Server(boolean validate) { super(validate); 

		getConfiguration();
		getDB();
		try{
			ServerSocket server = new ServerSocket(serverPort);
			while (true) {
				System.out.println("Waiting for Connections...");
				Socket client = server.accept();
				System.out.println("Accepted from " + client.getInetAddress());
				Connection c = connectTo(client);
				c.start();
			}
		} catch (Exception e){
			e.printStackTrace();
		} }

      // inherited constructors



	/**
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 */
	public Server (  )  throws IOException{ super(); }
}