package client; 

import common.JoinMessage; 
import common.TextMessage; 

public   interface  ChatListener {
	
	public void handleLine(String from, String text);

	
	public void handleJoin(String username);

	
	public void handleColoredMessage(String from, String text, String color);

	
	public void handlePrivateMessage(String from, String text);

	
	public void handleUsernameChange(String oldName, String newName);


}
