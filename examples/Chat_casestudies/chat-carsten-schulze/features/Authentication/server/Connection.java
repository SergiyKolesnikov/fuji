package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;


import common.Message;

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {
	
	private static final String password = "123456"; // password for authentication

	public Connection(Socket s, Server server) {
		this.socket = s;
		
		try {
			inputStream = new ObjectInputStream((s.getInputStream()));
			outputStream = new ObjectOutputStream((s.getOutputStream()));
			//#if Authentication	
			Object msg = null;
			while ((msg = inputStream.readObject())!= null){
				if ((msg instanceof Message)&&((((Message) msg).getStatus()&0x02) !=0)){
					if (((Message)msg).getText().equals(password)){
						outputStream.writeObject(new Message("Authentication succesful.", (byte) 0x02));
						System.out.println(socket.getInetAddress().toString()+" authenticated.");
						break;
					}else{
						outputStream.writeObject(new Message("Authentication denied.", (byte) 0x02));
						//server.removeConnection(this);
						try {
							socket.close();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			
			}
			//#endif
		} catch (IOException e) {
			e.printStackTrace();
			//#if Authentication
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			//#endif
		}

			
			
		this.server = server;
	}
}
