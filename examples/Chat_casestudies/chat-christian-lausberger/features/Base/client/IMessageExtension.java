package client;

import common.TextMessage;


public interface IMessageExtension {
	
	//send message
	public TextMessage extendMessage(TextMessage msg, Client client);
	//get info of recieved message
	public TextMessage readMessage(TextMessage msg);
	
	public void stop();
}
