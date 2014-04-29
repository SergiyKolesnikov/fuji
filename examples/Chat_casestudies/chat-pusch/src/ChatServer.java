
import java.util.List;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;




abstract class ChatServer$$messaging {

	public static final String SEP= "_/_";
	
	private static final int PORT= 1120;
	
	private List serverClientConnections;


	
	public ChatServer$$messaging() {
		serverClientConnections= new ArrayList();
		try {
			ServerSocket s= new ServerSocket(PORT);
			while (true) {
				ServerClientConnection serverClientCon= new ServerClientConnection(serverClientConnections, s.accept());
				serverClientCon.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * creates a ChatServer.
	 * @param args
	 */
	public static void main(String[] args) {
		ChatServer s= new ChatServer();
	}
	
	public List getServerClientConnections() {
		return serverClientConnections;
	}

}

public class ChatServer extends  ChatServer$$messaging  {
	
		private PrintWriter historyOut;


	
	public ChatServer() { super(); 

			try {
				historyOut = new PrintWriter(new FileOutputStream("serverHistory.txt"),true);
			} catch (IOException e) {
				e.printStackTrace();
			} }

      // inherited constructors


}