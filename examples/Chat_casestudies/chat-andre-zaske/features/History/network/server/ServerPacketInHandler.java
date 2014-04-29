package network.server;

import java.util.ArrayList;

import network.client.InPacketEvent;
import network.server.Connection;
import network.server.Connection.ConnectionStatus;
import network.server.packets.DataPacket;
import network.server.packets.JoinMessage;


public class ServerPacketInHandler {

	ArrayList<DataPacket> historyList;
	
	public ServerPacketInHandler() {
		historyList = new ArrayList<DataPacket>();
	}
	

	
	public void handlePacketIn(InPacketEvent e) {
		Connection c = (Connection) e.getSource();
		DataPacket packet = e.getDataPacket();
		ConnectionStatus status = c.getConnnectStatus();
		if (status == ConnectionStatus.AUTHENTICATED) {
			if (packet instanceof JoinMessage) {

				for (DataPacket histPacket : historyList) {
					c.send(histPacket);
				}
			}
		}
		original(e);
	}

	private void broadcast(DataPacket packet) {
		historyList.add(packet);
		original(packet);
	}
}