package client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import server.Server;

import common.*;

/**
 * simple chat client
 */
public class Client implements Runnable {
	
	protected void authenticate() throws IOException
	{
		original();
		this.outputStream.writeObject(user.getPassword());
		this.outputStream.flush();
	}
	

}
