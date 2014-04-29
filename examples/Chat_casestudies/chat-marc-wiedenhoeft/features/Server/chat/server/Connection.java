package chat.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import chat.TextMessage;

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread 
{	
	protected Socket socket;
	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;
	private Server server;
	private boolean isAuthenticated = false;
	public String name = "anonym";

	public Connection(Socket s, Server server) 
	{
		this.socket = s;
		try 
		{
			inputStream = new ObjectInputStream((s.getInputStream()));
			outputStream = new ObjectOutputStream((s.getOutputStream()));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		this.server = server;
	}

	/**
	 * waits for incoming messages from the socket
	 */
	public void run() 
	{
		try 
		{
			Object msg = null;
			while ((msg = inputStream.readObject()) != null) 
			{
				handleIncomingMessage(msg);
			}
		} 
		catch (SocketException e) 
		{
		} 
		catch (EOFException e) 
		{
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			server.removeConnection(this);
			server.broadcast( new TextMessage("Server", this.name + " @ " + socket.getInetAddress() + " has left.") );
			
			try 
			{
				socket.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void handleIncomingMessage(Object msg) 
	{
		if (msg instanceof TextMessage)
		{
			/**
			 * Send messages, if authentication passed.
			 */
			if( this.isAuthenticated == true)
			{
				/**
				 * Send message to all clients.
				 */
				server.broadcast(((TextMessage) msg));
			}
			else
			{
				String temp = ((TextMessage) msg).getContent();

				/**
				 * Authentication messages.
				 */
				if( temp.startsWith("@") )
				{
					if( temp.substring(1, temp.indexOf("|")).equals("password") )
					{
						this.isAuthenticated = true;
						this.send("", "@ACK");
						
						this.name = temp.substring(temp.indexOf("|") + 1);
						server.broadcast( new TextMessage("Server", this.name + " has joined.") );						
					}
					else
					{
						this.isAuthenticated = false;
						this.send("", "@DEN");
					}
				}
			}
		}
	}

	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	public void send(String name, String line) 
	{
		send(new TextMessage(name, line));
	}

	public void send(TextMessage msg) 
	{
		try 
		{
			synchronized (outputStream) 
			{
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} 
		catch (IOException ex) 
		{
		}
	}
}
