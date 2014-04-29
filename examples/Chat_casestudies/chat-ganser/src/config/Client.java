import java.io.IOException; 
import java.io.EOFException; import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.util.ArrayList; 
import java.util.Iterator; 

public   class  Client  implements Runnable {
	
    public static void main(String args[]) throws IOException {
	if (args.length != 2)
	    throw new RuntimeException("Syntax: ChatClient <host> <port>");

	Client client = new Client(args[0], Integer.parseInt(args[1]));
	new Gui("Chat " + args[0] + ":" + args[1], client);
    }

	

    protected ObjectInputStream inputStream;

	

    protected ObjectOutputStream outputStream;

	

    protected Thread thread;

	

    public Client(String host, int port) {
	this.setup(host, port);
    }

	

    private void setup  (String host, int port) {
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

	

    /**
     * main method. waits for incoming messages.
     */
    public void run() {
	try {
	    Thread thisthread = Thread.currentThread();
	    while (thread == thisthread) {
		try {
		    Object msg = inputStream.readObject();
		    handleIncomingMessage(msg);
		} catch (EOFException e) {
		    e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
     private void  handleIncomingMessage__wrappee__Authentication  (Object msg) {
	if (msg instanceof TextMessage) {
	    fireAddLine(((TextMessage) msg).getContent() + "\n");
	}
    }

	

    protected void handleIncomingMessage(Object msg) {

	if (msg instanceof TextMessage) {
	    TextMessage message = (TextMessage) msg;

	    try {
		History.log(message);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	handleIncomingMessage__wrappee__Authentication(msg);
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
	    this.stop();
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
	thread = null;
    }

	

    public void send(String line, String color) {
	TextMessage message = new TextMessage(line);
	message.setColor(color);
	this.send(message);
    }


}
