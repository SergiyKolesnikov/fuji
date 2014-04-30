
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;



//import common.TextMessage;

/**
 * simple chat client
 */
abstract class Client$$Base implements Runnable {
	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");

		Client client = new Client(args[0], Integer.parseInt(args[1]));
		new Gui("Chat " + args[0] + ":" + args[1], client);
	}

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;
	protected FileOutputStream fileout;

	public Client$$Base(String host, int port) {
		try {
			System.out.println("Connecting to " + host + " (port " + port
					+ ")...");
			Socket s = new Socket(host, port);
			this.outputStream = new ObjectOutputStream((s.getOutputStream()));
			this.inputStream = new ObjectInputStream((s.getInputStream()));
			createFile();
			thread = new Thread(this);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createFile() {}
	public void closeFile() {}

	/**
	 * main method. waits for incoming messages.
	 */
	public void run() {
		try {
			while (true) {
				try {
					Object msg = inputStream.readObject();
					handleIncomingMessage(msg);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			thread = null;
			try {
				outputStream.close();
				closeFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	public String decrypt2(String txt) {
		return txt;
	} 
	
	public void writeFile(Object msg) {}
	 
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			writeFile(msg);
			fireAddLine(decrypt2(((TextMessage) msg).getContent()) + "\n");
		}
	}

	public void send(String line) {
		send(new TextMessage(encrypt1(line)));
	}
	
	public String encrypt1(String line) {
		return line;
	}

	public void send(TextMessage msg) {
		try {
			outputStream.writeObject(msg);
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			thread.stop();
		}
	}

	/**
	 * listener-list for the observer pattern
	 */
	private ArrayList listeners = new ArrayList();

	/**
	 * addListner method for the observer pattern
	 */
	public void addLineListener(ChatLineListener listener) {
		listeners.add(listener);
	}

	/**
	 * removeListner method for the observer pattern
	 */
	public void removeLineListener(ChatLineListener listner) {
		listeners.remove(listner);
	}

	/**
	 * fire Listner method for the observer pattern
	 */
	public void fireAddLine(String line) {
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
			ChatLineListener listener = (ChatLineListener) iterator.next();
			listener.newChatLine(line);
		}
	}

	public void stop() {
		thread.stop();
	}

}

abstract class Client$$Encryption extends  Client$$Base  {
	public String encrypt1(String line) {
		String line2 = "";
		for(int i=line.length()-1;i>=0;i--) {
			line2 = line2 + String.valueOf(line.charAt(i));
		}
		return line2;
	}
	
	public String decrypt2(String line) {
		return String.valueOf(line.charAt(1)) + String.valueOf(line.charAt(0)) + line.substring(2);
	}
      // inherited constructors



	public Client$$Encryption ( String host, int port ) { super(host, port); }
}

abstract class Client$$Color extends  Client$$Encryption  {
	public String workMsg(String txt) {
		return "bla";
	}
      // inherited constructors



	public Client$$Color ( String host, int port ) { super(host, port); }
}

public class Client extends  Client$$Color  {
	public void createFile() {
		try {
			super.fileout = new FileOutputStream("history_client.log",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void closeFile() {
		try {
			super.fileout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void writeFile(Object msg) {
		try {
				for (int i=0; i < (((TextMessage) msg).getContent()).length(); i++){
			      super.fileout.write((byte) (((TextMessage) msg).getContent()).charAt(i) );
				}
				super.fileout.write((byte)'\n');
			}
			catch (IOException e) {
					e.printStackTrace();
			}
			
	}
      // inherited constructors



	public Client ( String host, int port ) { super(host, port); }
}