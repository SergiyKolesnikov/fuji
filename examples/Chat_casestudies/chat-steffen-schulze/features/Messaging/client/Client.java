package client;

import java.util.List;

import server.Server;

import common.TextMessage;
import common.Users;

/**
 * simple chat client
 */
public class Client implements Runnable {

	
	protected TextMessage getMessage(String line)
	{
		TextMessage msg;
		if (line.contains("/msg ")) 
		{
			int index = line.indexOf(' ', 5);
			if (index < 0) return original(line);
			
			String receiver = line.substring(5, index);
			line = line.substring(index);
			
			msg = original(line);
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
		
		return original(line);
	}
	
	private boolean checkIfUsernameExist(String username)
	{
		return Users.existUsername(username);
	}
}
