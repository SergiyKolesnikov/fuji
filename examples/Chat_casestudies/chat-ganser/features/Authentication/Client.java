import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {

    private void setup(String host, int port) {
	try {
	    System.out.println("Connecting to " + host + " (port " + port
		    + ")...");
	    Socket s = new Socket(host, port);
	    this.outputStream = new ObjectOutputStream((s.getOutputStream()));
	    this.inputStream = new ObjectInputStream((s.getInputStream()));

	    LoginMessage loginMsg = new LoginMessage(Constants.CLIENT_PASSWORD);
	    outputStream.writeObject(loginMsg);

	    Object loginReply = inputStream.readObject();

	    if (loginReply instanceof LoginReplyMessage) {

		if (!((LoginReplyMessage) loginReply).wasLoginSuccessful()) {
		    inputStream.close();
		    outputStream.close();
		    s.close();
		    throw new Error("Login was rejected by the server.");
		}
	    } else {
		inputStream.close();
		outputStream.close();
		s.close();
		throw new Error("Server sent unexpected response on login.");
	    }

	    thread = new Thread(this);
	    thread.start();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
