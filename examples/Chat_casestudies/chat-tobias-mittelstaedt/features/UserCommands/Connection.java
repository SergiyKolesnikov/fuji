package server;

import common.TextMessage;

public class Connection {

	private String username = "";
	
	public void setUsername(String name){
		this.username = name + ": ";
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public void send(String line) {
		if (line != null) {
			send(new TextMessage(this.username + line));
		} else {
			return;
		}
	}
	
	private void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof TextMessage) {
			String text = ((TextMessage) msg).getContent();
			
			if(text.startsWith("\setname")) {
				String[] temp = text.split(" ");
				this.username = temp[1];
				return;
			}
			
			if(this.username == ""){		
			server.broadcast(name + " - " + ((TextMessage) msg).getContent());
			} else {
				server.broadcast(username + ((TextMessage) msg).getContent());
			}
		}
	
}