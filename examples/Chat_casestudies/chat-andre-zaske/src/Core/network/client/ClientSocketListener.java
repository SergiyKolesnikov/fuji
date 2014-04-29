package network.client; 

import java.util.EventListener; 

public  interface  ClientSocketListener  extends EventListener {
	
	public void handlePacketIn(InPacketEvent e);

	
	public void handleClientSocketStatusChanged(ClientSocketStatusEvent e);


}
