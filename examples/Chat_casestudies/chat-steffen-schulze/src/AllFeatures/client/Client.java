package client; 

import java.io.*; 
import java.net.Socket; 
import java.util.ArrayList; 
import java.util.Iterator; 
import java.util.Random; 

import server.Server; 

import common.*; 

import common.TextMessage; 

import encryption.ROT13; 
import java.util.Calendar; 

import java.util.List; 
import common.Users; 

/**
 * simple chat client
 */
public   class  Client  implements Runnable {
	
	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");

		new Client(args[0], Integer.parseInt(args[1]));
	}

	

	protected ObjectInputStream inputStream;

	

	protected ObjectOutputStream outputStream;

	

	protected Thread thread;

	
	
	protected String host;

	
	protected int port;

	
	protected User user;

	

	public Client(String host, int port) {
		try {
			this.host = host;
			this.port = port;
			
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
	 private void  run__wrappee__Authentication  () {
		try {
			Thread thisthread = Thread.currentThread();
			this.user = getRandomUser();
			authenticate();
			
			while (thread == thisthread) {
				try {
					Object msg = inputStream.readObject();
					
					if (msg.toString().equals(Constants.LOGIN_FAILED)) 
					{
						return;
					} 
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

	
	
	public void run() {
		new Gui("Chat " + host + ":" + port, this);
		run__wrappee__Authentication();
	}

	

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			TextMessage textMsg = (TextMessage) msg;
			String content = textMsg.getContent();
			String sender = textMsg.getSender();
			
			content = encodeMessage(sender, content);
			String line = sender + " - " + getColoredMessage(sender, content);
			fireAddLine(line);
			writeToFile(line);
		}
	}

	
	
	 private void  authenticate__wrappee__Color  () throws IOException
	{
		this.outputStream.writeObject(user.getUsername());
		this.outputStream.flush();
	}

	
	
	protected void authenticate() throws IOException
	{
		authenticate__wrappee__Color();
		this.outputStream.writeObject(user.getPassword());
		this.outputStream.flush();
	}

	
	
	private void writeToFile  (String line)
	{
		try {
			if (outputFile == null) 
			{
				openFile();
				writeToFile(Calendar.getInstance().getTime().toString());
			}

			outputFile.write(line);
			outputFile.newLine();
			outputFile.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	 private String  encodeMessage__wrappee__Base  (String sender, String content)
	{
		return content;
	}

	
	
	protected String encodeMessage(String sender, String content)
	{
		if (!sender.equals(Server.NAME)) content = (new ROT13()).encode(content);
		return encodeMessage__wrappee__Base(sender, content);
	}

	
	
	 private String  getColoredMessage__wrappee__Logging  (String sender, String content)
	{
		return content;
	}

	
	
	private String getColoredMessage(String sender, String msg)
	{
		String color = getUserColor(sender);
		msg = "<" + color +">" + msg + "</" + color +">";
		return getColoredMessage__wrappee__Logging(sender, msg);
	}

	

	 private void  send__wrappee__Messaging  (String line) {
		send(getMessage(line));
	}

	
    	
	public void send(String line) 
	{
		if (!isSpam(line)) send__wrappee__Messaging(line);
	}

	
	
	 private TextMessage  getMessage__wrappee__Base  (String line)
	{
		return new TextMessage(user.getUsername(), line);
	}

	
	
	 private TextMessage  getMessage__wrappee__Gui  (String line)
	{
		line = (new ROT13()).decode(line);
		return getMessage__wrappee__Base(line);
	}

	

	
	protected TextMessage getMessage(String line)
	{
		TextMessage msg;
		if (line.contains("/msg ")) 
		{
			int index = line.indexOf(' ', 5);
			if (index < 0) return getMessage__wrappee__Gui(line);
			
			String receiver = line.substring(5, index);
			line = line.substring(index);
			
			msg = getMessage__wrappee__Gui(line);
			msg.setReceiver(receiver);
			return msg;
		}
		else if (line.contains("/nick ")) 
		{
			String newNick = line.substring(6);
			if (newNick.equals("")) return null;
			
			String oldNick = user.getUsername();
			String receiver = null;
			if (!checkIfUsernameExist(newNick))
			{
				user.setUsername(newNick);
				Users.updateUserMap(oldNick, newNick);
				line = oldNick + " changed nick to " + newNick;
			}
			else
			{
				line = "nick '"+ newNick +"' already exists";
				receiver = oldNick;
			}
			return new TextMessage(Server.NAME, line, receiver);
		}
		
		return getMessage__wrappee__Gui(line);
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
	private ArrayList<ChatLineListener> listeners = new ArrayList<ChatLineListener>();

	

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
	 * fire Listener method for the observer pattern
	 */
	public void fireAddLine(String line) {
		for (Iterator<ChatLineListener> iterator = listeners.iterator(); iterator.hasNext();) {
			ChatLineListener listener = iterator.next();
			listener.newChatLine(line);
		}
	}

	

	 private void  stop__wrappee__ROT13  () {
		thread = null;
	}

	

	public void stop() {
		stop__wrappee__ROT13();
		
		try {
			outputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	private User getRandomUser()
	{
		String username = "user" + String.valueOf((new Random()).nextInt(4) +1);
		return Users.getUser(username); 
	}

	
	
	private BufferedWriter outputFile;

	
	
	private void openFile() throws IOException
	{
		outputFile = new BufferedWriter(new FileWriter(user.getUsername() + ".txt",true));
	}

	
	
	private User getUser(String sender)
	{
		return Users.getUser(sender);
	}

	
	
	private String getUserColor(String sender)
	{
		return getUser(sender).getColor();
	}

	
	
	private boolean checkIfUsernameExist(String username)
	{
		return Users.existUsername(username);
	}

	

	private final List<String> restrictedList = createRestrictedList();

	

    private List<String> createRestrictedList()
    {
    	List<String> result = new ArrayList<String>();
        result.add("spam");
        return result;
    }

	
	
	private boolean isSpam(String msg) 
	{
		String content = msg.toLowerCase();
		
		for (String restrictedWord : restrictedList) 
		{
			if (content.contains(restrictedWord.toLowerCase())) 
			{
				return true;
			}
		}
		return false;
	}


}
