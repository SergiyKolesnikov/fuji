package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import common.*;

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {

	protected boolean isAuthValid(String username) throws ClassNotFoundException, IOException
	{
		String password = readString();
		if (!server.checkLogin(username, password)) 
		{
			send(Server.NAME, "Login failed");
			outputStream.writeObject(Constants.LOGIN_FAILED);
			return false;
		}
		return true;
	}
	
}
