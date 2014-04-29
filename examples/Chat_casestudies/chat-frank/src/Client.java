
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import javax.sound.sampled.*;



/** 
 * simple chat client
 */
abstract class Client$$Base implements Runnable {
	
	public static void main(String args[]) throws IOException {


		Client client = new Client("Localhost", 8081);
		new StartFrame("Config Chat ", "Localhost: 8081" ,client);
		//new Gui("Config Chat " , "Localhost" + ":" + "8081", client);
	}

	void startConsole(String args, Client client) {
		new Console(args,client);
	}

	void startGui(String args, Client client) {
		new Gui(args ,client);
	}

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;

	public Client$$Base(String host, int port) {
		try {
			System.out.println("Connecting to " + host + " (port " + port
					+ ")...");
			Socket s = new Socket(host, port);
			this.outputStream = new ObjectOutputStream((s.getOutputStream()));
			this.inputStream = new ObjectInputStream((s.getInputStream()));
			thread = new Thread(this);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			
			
			fireAddLine(((TextMessage) msg).getContent() + "\n");
		}
	}

	public void send(String line) {
		send(new TextMessage(line));
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
	protected ArrayList listeners = new ArrayList();

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
			addToHistory(line);
			playSound();
		}
	}

	public void stop() {
		thread.stop();
	}
	
	//==============================================================
	public void addToHistory(String msg){}
	public void playSound(){}

}



abstract class Client$$History extends  Client$$Base  {

    public void addToHistory(String msg){

        RandomAccessFile raf;
		try {
			raf = new RandomAccessFile("/home/rene/"+((Client) this).toString()+".log", "rws");
			raf.skipBytes( (int)raf.length() );
			raf.writeBytes(msg);
		} 
		catch (IOException e){}
    }
    
    public void fireAddLine(String line) {

		super.fireAddLine(line);
	}
      // inherited constructors



	public Client$$History ( String host, int port ) { super(host, port); }
}


//Toolkit.getDefaultToolkit().beep(); hat unter Linux nicht so richtig funktioniert

public class Client extends  Client$$History  {

	private void sound(int hz,int msecs) throws LineUnavailableException {

		byte[] buf = new byte[msecs*8];

		for (int i=0; i<buf.length; i++) {
			double angle = i / (8000.0 / hz) * 2.0 * Math.PI;
			buf[i] = (byte)(Math.sin(angle) * 80.0);
		}

		AudioFormat af = new AudioFormat(8000f,8,1,true,false);
		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
		sdl.open(af);
		sdl.start();
		sdl.write(buf,0,buf.length);
		sdl.drain();
		sdl.close();
	}

	public void playSound(){
		
		try {sound(2000, 150);} 
		catch (LineUnavailableException e) {}
	}
	
	public void fireAddLine(String line) {
		
		super.fireAddLine(line);
	}
      // inherited constructors



	public Client ( String host, int port ) { super(host, port); }
}