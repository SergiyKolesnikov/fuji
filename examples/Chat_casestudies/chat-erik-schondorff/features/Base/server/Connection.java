package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import common.TextMessage;

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
		String clientName = socket.getInetAddress().toString();
		try
		{
			server.broadcast(clientName + " try joining.");

			Object msg = null;
			while ((msg = inputStream.readObject()) != null)
			{
				handleIncomingMessage(clientName, msg);
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
			server.broadcast(clientName + " has left.");
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

	protected TextMessage incMsg = null;

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void handleIncomingMessage(String name, Object msg)
	{
		if (!(msg instanceof TextMessage))
			return;
		else
			this.incMsg = (TextMessage)msg;
		
		this.prehandleIncomingMessage(name);
		this.mainhandleIncomingMessage(name);
//		if (msg instanceof TextMessage)
//		{
//			this.incMsg = (TextMessage) msg;
//			modifyIncomingMessage(this.incMsg);
//			server.broadcast(name + " - " + this.incMsg.getContent());
//		}
		
		
	}

	private void prehandleIncomingMessage (String name)
	{
		
	}
	
	private void mainhandleIncomingMessage (String name)
	{
		server.broadcast(name + " - " + this.incMsg.getContent());
	}

	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	public void send(String line)
	{
		TextMessage msg = new TextMessage(line);

		send(msg);
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
