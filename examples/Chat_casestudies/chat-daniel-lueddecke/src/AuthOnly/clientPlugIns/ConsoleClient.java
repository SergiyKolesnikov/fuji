package clientPlugIns; 

import interfaces.IClient; 
import interfaces.IClientProxy; 

public  class  ConsoleClient  implements IClient {
	
	
	IClientProxy myProxy;

	

	@Override
	public void newChatLine(String line) {
		System.out.println(line);
	}

	

	@Override
	public void start() {}

	

	@Override
	public void stop() {}

	

	@Override
	public void setClientProxy(IClientProxy proxy) {
		//proxy will be used for sending as messages
		myProxy = proxy;
	}


}
