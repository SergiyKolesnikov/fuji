package server;

import java.util.ArrayList;

public class Server_PluginManager {

	private ArrayList<INew_Client_Connection> onConnect = new ArrayList<INew_Client_Connection>();

	public Server_PluginManager() {

	}

	public void onClientConnect(Connection c) {
		for (INew_Client_Connection ncc : onConnect) {
			ncc.on_connect(c);
		}
	}
}
