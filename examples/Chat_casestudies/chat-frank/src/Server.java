
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



abstract class Server$$History extends  Server$$Base  {

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
	public Server$$History ( int port )  throws IOException{ super(port); }	
}



abstract class Server$$Spamfilter extends  Server$$History  {

	public void broadcast(String name, String text) throws  IOException {
	
		super.broadcast(name, text);
	}
				
	public String filter(String txt){
				
		String [] badWords = {"doof", "bloed", "bla"};		
		String [] words = txt.split(" |\\.|,");
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		
		//for(String s : words){
		String word = "";
		String badword = "";
		for(int i=0; i<words.length; i++){	
			
			word=words[i];
			//for(String t : badWords) {
			for(int u=0; u<badWords.length; u++){
	
				badword=badWords[u];
				if(word.equals(badword)) {sb.append(changeBadWord(word)); if(i!=words.length-1) sb.append(" "); flag = true; break;} 
			}

			if(!flag) {sb.append(word); if(i!=words.length-1) sb.append(" ");}
			else flag = false;
		}
		
		return sb.toString();
	}
	
	private String changeBadWord(String txt){
		
		int len = txt.length();
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<len; i++){
			sb.append('*');
		}
		return sb.toString();
	}
      // inherited constructors



	/**
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 */
	public Server$$Spamfilter ( int port )  throws IOException{ super(port); }
	
	
}

public class Server extends  Server$$Spamfilter  {
	
	public void broadcast(String name, String txt) throws  IOException {
			
		 super.broadcast(name, txt);
		
	}
	
			private String encryption (String arg){

        	return new StringBuffer(arg).reverse().toString();
    	}
    
		private String encryption2 (String arg){
		  	
		  	StringBuffer sb = new StringBuffer(arg);
		  	char a = sb.charAt(0); sb.deleteCharAt(0);
		  	char b = sb.charAt(sb.length()-1); sb.deleteCharAt(sb.length()-1);
		  	sb.append(a);
		  	sb.insert(0,b);
		  	return sb.toString();
		}

	
		public String encrypt (String args){
		
			return encryption(encryption2(args));
		}

		public String decrypt (String args){

			return encryption2(encryption(args));
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