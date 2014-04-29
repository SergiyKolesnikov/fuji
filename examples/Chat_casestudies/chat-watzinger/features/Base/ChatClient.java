import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;

public class ChatClient extends Observable implements Runnable {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Debug debug;

	public ChatClient(InetAddress serverAddress, int serverPort,
			Debug debug) throws IOException {
		debug.append("Initializing Socket...");
		initHistory();
		this.socket = new Socket(serverAddress, serverPort);
		this.out = new ObjectOutputStream(socket.getOutputStream());
		this.in = new ObjectInputStream(socket.getInputStream());
		this.debug = debug;

		debug.append("> Successfully established connection to "
				+ serverAddress + " on port " + serverPort);
	}
	
	private void initHistory() {
	}

	@Override
	public void run() {
		Object o = null;

		try {
			while ((o = in.readObject()) != null) {
				processMessage(o);
			}
		} catch (IOException e) {
			debug.appendError(e.toString());
		} catch (ClassNotFoundException e) {
			debug.appendError(e.toString());
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				debug.appendError(e.toString());
			}
		}
	}

	public void send(Message msg) {
			try {
				synchronized (out) {
					out.writeObject(msg);
				}
				out.flush();
			} catch (IOException e) {
				debug.appendError(e.toString());
			}
		}

	private void processMessage(Object o) {

		if (o instanceof Message) {
			setChanged();
			notifyObservers((Message) o);
		}
	}

}
