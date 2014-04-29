package chat.client; 

import java.io.EOFException; 
import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.util.ArrayList; 
import java.util.Iterator; 

import chat.TextMessage; 

import chat.client.AuthenticationDialog; 
import chat.client.GUI; 
import chat.client.History; 
import chat.Crypto; 

public   class  Client  implements Runnable {
	 
	public static void main(String[] args) 
	{ 
		Client client = null;
		if (args.length != 2)
		{
			client = new Client("localhost", 8080);
		}
		else
		{
			client = new Client(args[0], Integer.parseInt(args[1]));
		}
	}

	 

	protected ObjectInputStream 	mInputStream;

	 //stream to write to
	protected ObjectOutputStream 	mOutputStream;

	 //stream listen to
	protected Thread 				mThread;

	
	private	  String				mUserName = "anonym";

	
	private   int					mCryptoType = 0;

	

	public Client(String host, int port)
	{
		this.init(host, port);
	}

	
	
	public int getCryptoType()
	{
		return this.mCryptoType;
	}

	
	
	public void setCryptoType(int type)
	{
		this.mCryptoType = type;
	}

	
	
	 private void  init__wrappee__Client  (String host, int port)
	{
		try
		{
			System.out.println("Connecting to " + host + " (port " + port + ")...");
			Socket s = new Socket(host, port);
			this.mOutputStream = new ObjectOutputStream((s.getOutputStream()));
			this.mInputStream = new ObjectInputStream((s.getInputStream()));
			this.mThread = new Thread(this);
			this.mThread.start();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
	}

	
	 private void  init__wrappee__GUI  (String host, int port)
	{
		init__wrappee__Client(host, port);
		
		/**
		 * Starts GUI.
		 */
		new GUI(this);
	}

	 //is client authenticated at server?

	public void init(String host, int port)
	{
		init__wrappee__GUI(host, port);
		
		/**
		 * User can enter name and password.
		 */
		AuthenticationDialog theDialog = new AuthenticationDialog(this);
		theDialog.setLocation(200, 200);
		theDialog.setModal(true);
		theDialog.setVisible(true);
	}

	
	
	/**
	 * This thread method listens for incoming messages.
	 */
	public void run() 
	{
		try 
		{
			Thread thisthread = Thread.currentThread();
			
			while (this.mThread == thisthread) 
			{
				try 
				{
					/**
					 * Read on socket blocks.
					 */
					Object msg = this.mInputStream.readObject();
					
					/**
					 * Work with new message.
					 */
					this.handleIncomingMessage(msg);
				} 
				catch (EOFException e) 
				{
					e.printStackTrace();
				} 
				catch (ClassNotFoundException e) 
				{
					e.printStackTrace();
				}
			}
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{
			this.mThread = null;
			try 
			{
				this.mOutputStream.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}

	

	/**
	 * Decides what to do with incoming messages.
	 * 
	 * @param msg - message (Object) received from the sockets
	 */
	 private void  handleIncomingMessage__wrappee__Color(Object msg) 
	{
		if(this.isAuthenticated == true)
		{
			if (msg instanceof TextMessage) 
			{
				System.out.println("Incoming message: " + ((TextMessage) msg).getContent());
	
				this.fireAddLine(
							((TextMessage) msg).getFrom() + 
							": " + 
							((TextMessage) msg).getContent() + 
							"\n");				
			}
		}
		else
		{
			/**
			 * Server messages for authentication.
			 * '@ACK' vs. '@DEN'  
			 */
			
			String temp = ((TextMessage) msg).getContent();
			if(temp.equals("@ACK"))
			{
				System.out.println("Authentication successfull...");
				this.isAuthenticated = true;
			}
			else
			{
				if(temp.equals("@DEN"))
				{
					System.out.println("Authentication NOT successfull...");
					this.fireAddLine("[red][b]FALSCHES PASSWORT! Bitte Programm neustarten![/b]\n");
				}
			}			
		}
	}

	
	 private void  handleIncomingMessage__wrappee__History  (Object msg) 
	{
		if(this.isAuthenticated == true)
		{
			if (msg instanceof TextMessage) 
			{
				/**
				 * Append to the history. 
				 */
				History.append(
						" " + 
						((TextMessage) msg).getFrom() + 
						": " +
						((TextMessage) msg).getContent() );
			}
		}		
		handleIncomingMessage__wrappee__Color(msg);
	}

	
	protected void handleIncomingMessage(Object msg) 
	{
		if(((TextMessage) msg).getCryptoType() == 1)
		{
			((TextMessage) msg).setContent(Crypto.doSwapCrypto(((TextMessage) msg).getContent()));
		}
		
		handleIncomingMessage__wrappee__History(msg);
	}

	

	/**
	 * Creates a new TextMessage with given author, line.
	 * 
	 * @param username
	 * @param line
	 */
	public void send(String name, String line) 
	{
		send(new TextMessage(name, line));
	}

	

	public void sendSystem(String name, String line)
	{
		try 
		{
			TextMessage msg = new TextMessage(name, line);
			msg.setCryptoType(0);
			
			/*
			 * Sending the system message to the server.
			 */
//			System.out.println("Sending to Server: " + msg.getContent());
			this.mOutputStream.writeObject(msg);
			this.mOutputStream.flush();
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
			this.stop();
		}		
	}

	

	/**
	 * Sends a TextMessage to the server.
	 * 
	 * @param msg
	 */
	 private void  send__wrappee__History  (TextMessage msg) 
	{
		try 
		{
			/*
			 * Sending the message to the server.
			 */
			System.out.println("Sending to Server: " + msg.getContent());
			this.mOutputStream.writeObject(msg);
			this.mOutputStream.flush();
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
			this.stop();
		}
	}

	
	
	public void send(TextMessage msg) 
	{
		msg.setCryptoType(1);
		msg.setContent(Crypto.doSwapCrypto(msg.getContent()));
		
		send__wrappee__History(msg);
	}

		
	
	/**
	 * listener-list for the observer pattern
	 */
	private ArrayList listeners = new ArrayList();

	

	/**
	 * addListner method for the observer pattern
	 */
	public void addLineListener(IChatLineListener listener) 
	{
		listeners.add(listener);
	}

	

	/**
	 * removeListner method for the observer pattern
	 */
	public void removeLineListener(IChatLineListener listner) 
	{
		listeners.remove(listner);
	}

	

	/**
	 * fire Listner method for the observer pattern
	 */
	public void fireAddLine(String line) 
	{
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) 
		{
			IChatLineListener listener = (IChatLineListener) iterator.next();
			listener.addNewChatLine(line);
		}
	}

	

	public void stop() 
	{
		this.mThread = null;
	}

	
	
	public void setUserName(String name)
	{
		this.mUserName = name;
	}

	

	public String getUserName()
	{
		return this.mUserName;
	}

	
	private boolean isAuthenticated = false;


}
