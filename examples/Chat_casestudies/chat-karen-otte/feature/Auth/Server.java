import java.net.Socket;
import java.io.*;

/**
 * TODO description
 */
public class Server {
	
	public boolean checkConnection(Socket client) throws IOException{
		
		DataInputStream input = new DataInputStream(client.getInputStream());
		String str = input.readUTF();
		if(str.equals("test")){
			return true;
		} else{
			System.out.println("Connection denied from " + client.getInetAddress());
			return false;
		}
	}

}