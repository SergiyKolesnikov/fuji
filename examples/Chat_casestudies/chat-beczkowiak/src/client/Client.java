package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import common.IMessage;
import common.TextMessage;
import common.LoginMessage;
import common.UserConfirmedMessage;

import ui.ClientGui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Clip;

import crypt.Crypt;



/**
 * simple chat client
 */
abstract class Client$$Base$client implements Runnable {
	
	protected static Client client;
	
	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");
	
		client = new Client(args[0], Integer.parseInt(args[1]));
	}

	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;
	protected Thread thread;
	
	private String host;
	private int port;
	protected boolean connected = false;
    
    private ArrayList listeners = new ArrayList();
	
	public Client$$Base$client(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void connect() {
	    if (connected) {
	        return;
	    }
	    try {
	        fireAddLine("Connecting to " + host + " (port " + port
                                + ")...");
                Socket s = new Socket(host, port);
                ((Client) this).outputStream = new ObjectOutputStream((s.getOutputStream()));
                ((Client) this).inputStream = new ObjectInputStream((s.getInputStream()));
                connected = true;
                thread = new Thread(((Client) this));
                thread.start();
            } catch (Exception e) {
                fireAddLine("Connection failed! "
                        + "Please contact your server admin!");
            }
	}

	/**
	 * main method. waits for incoming messages.
	 */
	public void run() {
	    try {
	        while (true) {
	            Object msg = inputStream.readObject();
	            handleIncomingMessage(msg);
	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
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
	
	public void send(IMessage msg) {
	    try {
	        if (outputStream == null) {
		            fireAddLine("Please connect to server!");
	        }
	        else {
	            beforeSending(msg);
                outputStream.writeObject(msg);
                outputStream.flush();
	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	        thread.stop();
	    }
	}
	
	protected void beforeSending(IMessage msg) {
	}
	
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



abstract class Client$$Login$client extends  Client$$Base$client  {
	protected boolean userConfirmed = false;
    protected String username;
    
    public void send(IMessage msg) {
    	if (!((Client) this).userConfirmed) {
            if (msg instanceof LoginMessage) {
                ((Client) this).username = ((LoginMessage) msg).getName();
                super.send(msg);
            } else {
                fireAddLine("Login needed!");
            }
        } else {
        	if (!(msg instanceof LoginMessage)) {
        		super.send(msg);
        	}
    	}
    }
    
    protected void handleIncomingMessage(Object msg) {
    	if (msg instanceof UserConfirmedMessage) {
            UserConfirmedMessage message = (UserConfirmedMessage) msg;
            ((Client) this).userConfirmed = message.isConfirmed();
            
            if (((Client) this).userConfirmed) {
                fireAddLine("Logged in as " + username + ".");
            } else {
                fireAddLine("Invalid username and/or password!");
            }
        }
        super.handleIncomingMessage(msg);
    }
    
    public boolean isConfirmedUser() {
    	return ((Client) this).userConfirmed;
    }
      // inherited constructors


	
	public Client$$Login$client ( String host, int port ) { super(host, port); }
}



abstract class Client$$GUI$client extends  Client$$Login$client  {
	public static void main(String args[]) throws IOException {
		Client$$Login$client.main(args);
		new ClientGui("Chat " + args[0] + ":" + args[1], client);
	}
      // inherited constructors


	
	public Client$$GUI$client ( String host, int port ) { super(host, port); }
}



abstract class Client$$History$client extends  Client$$GUI$client  {
	
	protected BufferedWriter bw;
	
	protected void handleIncomingMessage(Object msg) {
		super.handleIncomingMessage(msg);
		BufferedWriter bw = getBufferedWriter();
        if (msg instanceof TextMessage) {
            try {
                bw.write(((TextMessage) msg).getContent() + "\n");
                bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
            }
        }
	}
	
	protected BufferedWriter getBufferedWriter() {
        if (((Client) this).bw == null) {
            try {
                ((Client) this).bw = new BufferedWriter(new FileWriter(new File("Client.txt"), true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ((Client) this).bw;
    }
      // inherited constructors


	
	public Client$$History$client ( String host, int port ) { super(host, port); }
}



abstract class Client$$Audio$client extends  Client$$History$client  {
	protected Clip clip;
	
	public Client$$Audio$client(String host, int port) { super(host, port); 

		try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("affair.wav")));
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} }

	
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			clip.stop();
			clip.setFramePosition(0);
            clip.start();
		}
		super.handleIncomingMessage(msg);
	}
      // inherited constructors


}



public class Client extends  Client$$Audio$client  {
	
	protected Crypt crypt;
	
	public Client(String host, int port) { super(host, port); 

		((Client) this).crypt = new Crypt(); }

	
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof IMessage) {
	        ((IMessage) msg).decode(((Client) this).crypt);
	    }
	    super.handleIncomingMessage(msg);
	}
	
	protected void beforeSending(IMessage msg) {
		msg.encode(((Client) this).crypt);
		super.beforeSending(msg);
	}
      // inherited constructors


}