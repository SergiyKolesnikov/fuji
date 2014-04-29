package server; 

import java.util.ArrayList; 

/**
 * TODO description
 */
public   class  Server_PluginManager {
	

	private ArrayList<INew_Client_Connection> onConnect = new ArrayList<INew_Client_Connection>();

	
	public Server_PluginManager  () {

	
		onConnect.add(new server_authentication());
	}

	

	public void onClientConnect(Connection c) {
		for (INew_Client_Connection ncc : onConnect) {
			ncc.on_connect(c);
		}
	}


}
