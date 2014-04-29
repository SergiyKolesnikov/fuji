package chat.client; 

import java.io.IOException; 
import java.net.UnknownHostException; 
import java.util.List; 
import java.util.Observable; 
import java.util.Observer; 

import chat.IMessageVisitor; 
import chat.messages.AMessage; 

public  class  ClientModel  extends Observable  implements Observer {
	
	private ClientNetwork network;

	
	private ClientFilter filter;

	

	public ClientModel(String host, int port, List<IMessageVisitor> inPlugins,
			List<IMessageVisitor> outPlugins) throws UnknownHostException,
			IOException {
		network = new ClientNetwork(host, port);
		network.addObserver(this);

		filter = new ClientFilter(inPlugins, outPlugins);
	}

	

	public void send(AMessage message) {
		/*
		 * Set the client of the message, so that filters can send messages if
		 * necessary
		 */
		message.setClient(network);
		filter.outgoing(message);
	}

	

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof AMessage) {
			filter.incoming((AMessage) arg1);

			/*
			 * Update the others for that message
			 */
			setChanged();
			notifyObservers(arg1);
		}
	}


}
