package chat.server;

import java.util.Iterator;

import chat.TextMessage;
import chat.server.Connection;

public class Server 
{
	/**
	 * send a message to all known connections
	 * 
	 * @param text
	 *            content of the message
	 */
	public void broadcast(TextMessage msg) 
	{
		//decrypt the message text to filter it
		switch(msg.getCryptoType())
		{
			case 1:
			{
				msg.setContent(this.doSwapCrypto(msg.getContent()));
				break;
			}
			case 2:
			{
				msg.setContent(this.doROT13Crypto(msg.getContent()));
				break;
			}
		}

		String workString = "";
		
		if(msg.getContent().startsWith("[msg]"))
		{
			System.out.println("Private message from: " + msg.getFrom());
			workString = msg.getContent().substring(6);
			System.out.println("Private message to: " + workString + "!");
			workString = workString.substring(0, workString.indexOf(" "));
			System.out.println("Sending message to: " + workString + "!");

			//encrypt the message text
			switch(msg.getCryptoType())
			{
				case 1:
				{
					msg.setContent(this.doSwapCrypto(msg.getContent()));
					break;
				}
				case 2:
				{
					msg.setContent(this.doROT13Crypto(msg.getContent()));
					break;
				}
			}

			//empfänger
			Connection con = this.getConnectionByName(workString);
			Connection con2 = this.getConnectionByName(msg.getFrom());
			if(con != null)
			{
				con.send(msg);
				if(con2 != null)
				{
					con2.send(msg);
				}
			}
			else
			{
				if(con2 != null)
				{
					msg.setContent("[red]No user with this name found.");
					//encrypt the message text
					switch(msg.getCryptoType())
					{
						case 1:
						{
							msg.setContent(this.doSwapCrypto(msg.getContent()));
							break;
						}
						case 2:
						{
							msg.setContent(this.doROT13Crypto(msg.getContent()));
							break;
						}
					}						
					con2.send(msg);
				}
			}
		}
		else
		{
			//encrypt the message text
			switch(msg.getCryptoType())
			{
				case 1:
				{
					msg.setContent(this.doSwapCrypto(msg.getContent()));
					break;
				}
				case 2:
				{
					msg.setContent(this.doROT13Crypto(msg.getContent()));
					break;
				}
			}
				
			original(msg);
		}
	}
	
	private Connection getConnectionByName( String Name )
	{
		Connection con = null;
		
		synchronized (connections) 
		{
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) 
			{
				Connection connection = (Connection) iterator.next();
				if(connection.name.equals(Name))
				{
					con = connection;
					break;
				}
			}
		}
		
		return con;
	}
}