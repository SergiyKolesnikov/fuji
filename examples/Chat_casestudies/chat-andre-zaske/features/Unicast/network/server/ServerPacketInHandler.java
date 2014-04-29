package network.server;

import java.util.HashMap;

import network.client.ClientSocketStatusEvent;
import network.client.InPacketEvent;
import network.client.ClientSocketStatusEvent.ClientSocketStatus;
import network.server.Connection;
import network.server.Connection.ConnectionStatus;
import network.server.packets.BroadcastPacket;
import network.server.packets.DataPacket;
import network.server.packets.JoinMessage;
import network.server.packets.OnlineStatusMessage;
import network.server.packets.UnicastPacket;

/**
 * TODO description
 */
public class ServerPacketInHandler {
	private HashMap<String, Connection> authConnections;
	
	public ServerPacketInHandler(){
		authConnections = new HashMap<String, Connection>();
	}

	private synchronized void addAuthConnection(Connection connection) {
		authConnections.put(connection.getName(), connection);
	}

	private synchronized void removeAuthConnection(Connection connection) {
		authConnections.remove(connection.getName());
	}
	
	public void handlePacketIn(InPacketEvent e) {
		Connection c = (Connection) e.getSource();
		DataPacket packet = e.getDataPacket();
		ConnectionStatus status = c.getConnnectStatus();
		if (status == ConnectionStatus.AUTHENTICATED) {
			if (packet instanceof UnicastPacket) {
				UnicastPacket up = (UnicastPacket) packet;
				Connection destC = authConnections.get(up.getDestination());
				if(destC != null){
					destC.send(packet.clone());
					c.send(packet);
				}
			}else if (packet instanceof JoinMessage) {
				for(String  knownConnections : authConnections.keySet()){
					c.send(new OnlineStatusMessage(knownConnections));
				}
				addAuthConnection(c);
			}
		}
		original(e);
	}
	public void handleClientSocketStatusChanged(ClientSocketStatusEvent e) {
		original(e);
		if (e.getStatus() == ClientSocketStatus.CLIENTSTOPPED
				|| e.getStatus() == ClientSocketStatus.SOCKETCLOSED) {
			removeAuthConnection((Connection) e.getSource());
		}
	}
}