package server; 

import java.io.EOFException; 

import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.net.SocketException; 

import common.TextMessage; import java.io.FileInputStream; 
import java.util.Vector; 

import server.Server; 

import common.ConnectionMessage; 
import common.LoginReply; 
import common.UserMessage; 

import server.WordFilter; 

/**
 * TODO description
 */
public   class  Connection  extends Thread {
	
	protected Socket socket;

	
	protected ObjectInputStream inputStream;

	
	protected ObjectOutputStream outputStream;

	
	private Server server;

	

	public Connection(Socket s, Server server) {
		this.socket = s;
		try {
			inputStream = new ObjectInputStream((s.getInputStream()));
			outputStream = new ObjectOutputStream((s.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.server = server;
	}

	

	/**
	 * waits for incoming messages from the socket
	 */
	public void run() {
		String clientName = socket.getInetAddress().toString();
		try {
//			server.broadcast(clientName + " has joined.");
			Object msg = null;
			while ((msg = inputStream.readObject()) != null) {
				handleIncomingMessage(clientName, msg);
			}
		} catch (SocketException e) {
		} catch (EOFException e) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			server.removeConnection(this);
			server.broadcast(clientName + " has left.");
			try {
				socket.close();
			} catch (IOException ex) {
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
	 private void  handleIncomingMessage__wrappee__Log  (String name, Object msg) {
		if (msg instanceof TextMessage)
			server.broadcast(name + " - " + ((TextMessage) msg).getContent());
	
	}

	
	 private void  handleIncomingMessage__wrappee__Authen  (String name, Object msg)
	{
		if (msg instanceof ConnectionMessage)
		{
			LoginReply reply = new LoginReply();
			username = ((ConnectionMessage)msg).Username;
				if (Server.password.equals(((ConnectionMessage)msg).Pwd))
				{
					try{
						int login_flag=0;
		      	        for(int a=0;a<Server.userOnline.size();a++)
		      	        {
		      	            if(	username.equals(Server.userOnline.elementAt(a)))
		      	            {
		      	            	login_flag=1;
		      	            	break;
		      	            }
		      	        }
		      	        
		      	        if (login_flag==0)
		      	        {
		      	            reply.status = true;
		      	            reply.reason = "sucess";
		      	            reply.Userlist = Server.userOnline;	
		      	          outputStream.writeObject( reply );
		      	          Server.userOnline.addElement(username);
		       	        }
		       	        else
		       	        {
		       	            reply.status = false;
		       	            reply.reason = "already";
		       	            outputStream.writeObject( reply );
		       	        }
						
						server.broadcast(username + " has joined.");
						UserMessage um = new UserMessage();
						um.add = true;
						um.user= username;
						server.broadcast( um );
					} catch( IOException w ){w.toString();}
				}else{
					try{
						
						reply.status = false;
						reply.reason = "wrong";
						outputStream.writeObject( reply );
					} catch( IOException w ){w.toString();}
				}	
			
		}
		handleIncomingMessage__wrappee__Log(username, msg);
	}

	
	private void handleIncomingMessage(String name, Object msg)
	{
		if (msg instanceof ConnectionMessage){
			LoginReply reply = new LoginReply();
			username = ((ConnectionMessage)msg).Username;
				if (Server.password.equals(((ConnectionMessage)msg).Pwd))
				{
					try{
						int login_flag=0;
		      	        for(int a=0;a<Server.userOnline.size();a++)
		      	        {
		      	            if(	username.equals(Server.userOnline.elementAt(a)))
		      	            {
		      	            	login_flag=1;
		      	            	break;
		      	            }
		      	        }
		      	        
		      	        if (login_flag==0)
		      	        {
		      	            reply.status = true;
		      	            reply.reason = "sucess";
		      	            reply.Userlist = Server.userOnline;	
		      	          outputStream.writeObject( reply );
		      	          Server.userOnline.addElement(username);
		       	        }
		       	        else
		       	        {
		       	            reply.status = false;
		       	            reply.reason = "already";
		       	            outputStream.writeObject( reply );
		       	        }
						
					
						server.broadcast(username + " has joined.");
						UserMessage um = new UserMessage();
						um.add = true;
						um.user= username;
						server.broadcast( um );
					} catch( IOException w ){w.toString();}
				}else{
					try{
						
						reply.status = false;
						reply.reason = "wrong";
						outputStream.writeObject( reply );
					} catch( IOException w ){w.toString();}
				}	
			
		}
		if(msg instanceof TextMessage)
		{
			if(!((TextMessage) msg).whisper&&((TextMessage) msg).chatToUser.equals("all"))
					{
				handleIncomingMessage__wrappee__Authen(username, msg);
					}
			if(!((TextMessage) msg).whisper&& !((TextMessage) msg).chatToUser.equals("all"))
					server.broadcast(username + " to " + ((TextMessage) msg).chatToUser +" - " + ((TextMessage) msg).getContent() );
			if(((TextMessage) msg).whisper)
				server.privatemessage(((TextMessage) msg).getContent(),((TextMessage) msg).chatToUser,((TextMessage) msg).chatUser);
		}
	}

	
	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	 private void  send__wrappee__PrivateTalk  (String line) {
			send(new TextMessage(line));
	}

	
	public void send(String line)
	{
		String tmp = WordFilter.filter(line);
		send__wrappee__PrivateTalk(tmp);
	}

	

	public void send(TextMessage msg) {
		try {
			synchronized (outputStream) {
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} catch (IOException ex) {
		}
	}

	
	public String username;

	
	
	public void send(UserMessage msg) {
		try {
			synchronized (outputStream) {
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} catch (IOException ex) {
		}
	}


}
