
package server;

import java.io.IOException;

import server.Server;

import common.ConnectionMessage;
import common.LoginReply;
import common.TextMessage;
import common.UserMessage;

/**
 * TODO description
 */
public class Connection {
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
				original(username, msg);
					}
			if(!((TextMessage) msg).whisper&& !((TextMessage) msg).chatToUser.equals("all"))
					server.broadcast(username + " to " + ((TextMessage) msg).chatToUser +" - " + ((TextMessage) msg).getContent() );
			if(((TextMessage) msg).whisper)
				server.privatemessage(((TextMessage) msg).getContent(),((TextMessage) msg).chatToUser,((TextMessage) msg).chatUser);
		}
	}

}