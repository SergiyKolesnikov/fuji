package server; 

import common.TextMessage;


/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public  class  Connection  extends Thread {
	
	private final String SETUSERNAME = "/username ";
	private final String PM = "/msg ";
	
	private boolean setClientName(TextMessage msg) {
		 String content = msg.getContent();
		 if(content.startsWith(SETUSERNAME)) {
			 String newUserName = content.substring(SETUSERNAME.length());
			 boolean allowed = server.updateClientName(this, newUserName);
			 
			 if(allowed) {
				 String oldName = this.name;
				 this.name = newUserName;
				 server.broadcast(new TextMessage(oldName + " changed her/his name to: " + newUserName, ""));
			 } else {
				 send(new TextMessage("the username " + newUserName + " is already taken", ""));
			 }
			 return true;
		 }
		 
		 return false;
	}
	
	private TextMessage getPrivateMessage(Object msg) {
		if (msg instanceof TextMessage) {
			TextMessage tm = (TextMessage) msg;
			tm.setSource(getClientName());
			String content = tm.getContent();
			if(content.startsWith(PM)) {
				return new TextMessage(content.substring(PM.length()), tm.getSource());
			}
		}
		
		return null;
	}
		
	private boolean sendPrivateMessage(TextMessage msg) {
		String username = msg.getContent().substring(0,msg.getContent().indexOf(" "));
		msg.setContent(msg.getContent().substring(msg.getContent().indexOf(" ")));
		
		return server.sendPrivateMessage(username, msg);
	}
	
	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void handleIncomingMessage(Object msg) {
		TextMessage privateMessage = getPrivateMessage(msg);
		
		if(privateMessage == null)
			original(msg);
		else		
			sendPrivateMessage(privateMessage);
	}
	
}
