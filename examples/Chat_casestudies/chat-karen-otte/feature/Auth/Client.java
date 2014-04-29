import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * TODO description
 */
public class Client {
	
	public void streaming(Socket s) throws IOException{
		
		DataOutputStream output = new DataOutputStream(s.getOutputStream());
		output.writeUTF("test");
		original(s);
		
	}

}