package client; 

import common.ITextMessage; 

public  interface  IUserInterface {
	
	void sendMessage(ITextMessage message);

	
	void messageReceived(String message);


}
