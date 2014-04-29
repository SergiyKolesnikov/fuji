
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;



abstract class Client$$Base implements Runnable {

	private ObjectInputStream inputStream;
	ObjectOutputStream outputStream;
	public Color defaultColor = Color.BLACK;

	private Thread thread;

	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("wrong parameter!");
		
		Client client = new Client(args[0], Integer.parseInt(args[1]));
		
		new Console(client);		
	}

	
	public Client$$Base(String host, int port) {
		try {
			Socket s = new Socket(host, port);
			this.outputStream = new ObjectOutputStream(s.getOutputStream());
			this.inputStream = new ObjectInputStream(s.getInputStream());

			thread = new Thread(this);
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	public void run() {
		try {
			while (true) {
				Object msg = inputStream.readObject();
				handleIncomingMessage(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			thread = null;
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {
		thread.stop();
	}

	void send(String text) {
		send(new OutgoingTextMessage(text, defaultColor));
	}

	void send(String text, Color color) {
		send(new OutgoingTextMessage(text, color));
	}

	protected void send(OutgoingTextMessage textMessage) {
		
		try {
			outputStream.writeObject(textMessage);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
			thread.stop();
		}
	}

	public void handleIncomingMessage(Object msg) throws IOException {
		if (msg instanceof TextMessage) {
			TextMessage tempTextMessage = (TextMessage) msg;
			fireAddLine(tempTextMessage.getText() + "\n");
		}	
	}

	void fireAddLine(String line) {
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
			ChatLineListener listener = (ChatLineListener) iterator.next();
			listener.newChatLine(line);
		}
	}

	private ArrayList listeners = new ArrayList();

	public void addListener(ChatLineListener listener) {
		listeners.add(listener);
	}

	public void removeLineListener(ChatLineListener listener) {
		listeners.remove(listener);
	}

	public void setDefaultColor(Color color) {
		((Client) this).defaultColor = color;
	}
}

abstract class Client$$GUI extends  Client$$Base  {
	
	public static void main(String args[]) throws IOException{
		if (args.length != 2)
			throw new RuntimeException("wrong parameter!");
		
		Client client = new Client(args[0], Integer.parseInt(args[1]));		
		new GUI(client);	
	}
      // inherited constructors



	
	public Client$$GUI ( String host, int port ) { super(host, port); }

}

abstract class Client$$Authentifizierung extends  Client$$GUI  {
	
	private boolean ALLOW_INTERACTION;
	public final String CODE = getCode();

	public void send(OutgoingTextMessage textMessage) {
		if(!ALLOW_INTERACTION){
			if(textMessage.getText().equals(CODE)) ALLOW_INTERACTION=true;
			return;
		}
		super.send(textMessage);
	}

	public void handleIncomingMessage(Object msg) throws IOException {
		if(!ALLOW_INTERACTION)
			return;
		super.handleIncomingMessage(msg);			
	}
	
	public String getCode(){
		return "code";
	}
      // inherited constructors



	
	public Client$$Authentifizierung ( String host, int port ) { super(host, port); }
	
}

abstract class Client$$Verschluesseln extends  Client$$Authentifizierung  {

	public void send(OutgoingTextMessage textMessage) {
		textMessage.encodeText();
		super.send(textMessage);
	}

	public void handleIncomingMessage(Object msg) throws IOException {
		if(msg instanceof TextMessage){
			TextMessage tempTextMessage = (TextMessage) msg;
			IncomingTextMessage textMessage = new IncomingTextMessage(tempTextMessage.getText(),tempTextMessage.getColor());
			fireAddLine(textMessage.getText() + "\n");	
		}
	}
	
	public String getCode(){
		return "edoc";	
	}
      // inherited constructors



	
	public Client$$Verschluesseln ( String host, int port ) { super(host, port); }

}



abstract class Client$$History extends  Client$$Verschluesseln  {

	private File file = new File("History" + getClass().toString() + ".txt");
	
	public void handleIncomingMessage(Object msg) throws IOException {
		super.handleIncomingMessage(msg);
		writeHistory((TextMessage) msg);
	}
	
	void writeHistory(TextMessage msg) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		IncomingTextMessage text = new IncomingTextMessage(msg.getText());
		bw.write(text.getText());
		bw.newLine();
		bw.close();
	}
      // inherited constructors



	
	public Client$$History ( String host, int port ) { super(host, port); }

}

abstract class Client$$SpamFilter extends  Client$$History  {

	protected String[] spamList = {"lila","blau","rot","gelb","justin bieber"};
	
	protected boolean contains(String text) {
		for(int i=0;i<spamList.length;i++){
			if(spamList[i].equals(text))
				return true;
		}
		return false;
	}
	
	public void send(OutgoingTextMessage textMessage) {
		if(contains(textMessage.getText()))
			return;
		super.send(textMessage);
	}
      // inherited constructors



	
	public Client$$SpamFilter ( String host, int port ) { super(host, port); }

}

public class Client extends  Client$$SpamFilter  {

	public void handleIncomingMessage(Object msg) throws IOException {
		super.handleIncomingMessage(msg);
		Toolkit.getDefaultToolkit().beep();
		System.out.println("beep");	
	}
      // inherited constructors



	
	public Client ( String host, int port ) { super(host, port); }

}