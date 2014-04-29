package network.server;

import java.util.HashMap;

import network.client.ClientSocketStatusEvent;
import network.client.InPacketEvent;
import network.client.ClientSocketStatusEvent.ClientSocketStatus;
import network.server.AuthentifikationService;
import network.server.Connection;
import network.server.Connection.ConnectionStatus;
import network.server.packets.AuthentifikationPacket;
import network.server.packets.DataPacket;
import network.server.packets.AuthentifikationPacket.AuthentifikationFlag;

public class ServerPacketInHandler {

	private AuthentifikationService authService;

	public ServerPacketInHandler(){
		authService = new AuthentifikationService();
	}

	public void handlePacketIn(InPacketEvent e) {
		Connection c = (Connection) e.getSource();
		DataPacket packet = e.getDataPacket();
		ConnectionStatus status = c.getConnnectStatus();
		// Authentification Packets
		if (packet instanceof AuthentifikationPacket) {
			AuthentifikationPacket aPacket = (AuthentifikationPacket) packet;
			if (status == ConnectionStatus.UNKOWN
					&& aPacket.getAuthentifikationState() == AuthentifikationFlag.AUTHREQUEST) {
				// Add to Known Connections
				addConnection(c);
				authService.sendAuthKey(c, aPacket);
			} else if (status == ConnectionStatus.AUTHREQUEST) {
				if (aPacket.getAuthentifikationState() == AuthentifikationFlag.AUTHRUN) {
					if (checkLogin(c, aPacket)) {
						// Send Success
						authService.sendAuthSuccess(c, aPacket);
					} else {
						removeConnection(c);
						c.stop();
					}
					// No Auth Option
				} else if (aPacket.getAuthentifikationState() == AuthentifikationFlag.NOAUTH) {
					authService.sendAuthSuccess(c, aPacket);
				}
			}
		}else{
			original(e);
		}
	}

	private boolean checkLogin(Connection c, AuthentifikationPacket packet) {
		return c.checkLogin(packet.getAccountPasswort(), c.getName());
	}

	public void handleClientSocketStatusChanged(ClientSocketStatusEvent e) {
		if (e.getStatus() == ClientSocketStatus.CLIENTSTOPPED
				|| e.getStatus() == ClientSocketStatus.SOCKETCLOSED) {
			removeConnection((Connection) e.getSource());
		}
		original(e);
	}
}