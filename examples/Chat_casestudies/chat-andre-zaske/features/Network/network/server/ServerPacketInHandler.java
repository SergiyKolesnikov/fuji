package network.server;

import java.util.ConcurrentModificationException;
import java.util.HashSet;

import crypto.BlankEncoder;

import network.client.ClientSocketListener;
import network.client.ClientSocketStatusEvent;
import network.client.InPacketEvent;
import network.client.ClientSocketStatusEvent.ClientSocketStatus;
import network.server.Connection;
import network.server.Connection.ConnectionStatus;
import network.server.packets.AuthentifikationPacket;
import network.server.packets.BroadcastPacket;
import network.server.packets.DataPacket;
import network.server.packets.QuitMessage;
import network.server.packets.AuthentifikationPacket.AuthentifikationFlag;

public class ServerPacketInHandler implements ClientSocketListener {

	private HashSet<Connection> connections;
	private String cipherName = BlankEncoder.class.getName();

	public ServerPacketInHandler() {
		connections = new HashSet<Connection>();
	}

	public void setCipherName(String name) {
		cipherName = name;
	}

	public String getCipherName() {
		return cipherName;
	}

	private synchronized void addConnection(Connection connection) {
		connections.add(connection);
	}

	private synchronized void removeConnection(Connection connection) {
		if (connections.remove(connection)) {
			broadcast(new QuitMessage(connection.getName()));
		}
	}

	private void broadcast(DataPacket packet) {
		try {
			synchronized (connections) {
				DataPacket localCopy = null;
				for (Connection c : connections) {
					localCopy = packet.clone();
					c.send(localCopy);
				}
			}
		} catch (ConcurrentModificationException cme) {
			;
		}
	}

	public void handlePacketIn(InPacketEvent e) {
		// Get needed Data
		Connection c = (Connection) e.getSource();
		DataPacket packet = e.getDataPacket();
		ConnectionStatus status = c.getConnnectStatus();
		if (status == ConnectionStatus.UNKOWN) {
			// Add to Known Connections
			addConnection(c);
		}

		if (packet instanceof AuthentifikationPacket) {
			AuthentifikationPacket aPacket = (AuthentifikationPacket) packet;
			if (status == ConnectionStatus.UNKOWN) {
				// Authentification Packets
				c.setName(aPacket.getAccountName());
				// Configurate Client
				c.setAuthKey(new byte[] { 0000 });
				// Update Packet
				aPacket.setChipheringCoder(cipherName);
				aPacket.setAuthentifikationState(AuthentifikationFlag.NOAUTH);
				// Set ClientStatus
				c.setConnnectStatus(ConnectionStatus.AUTHREQUEST);
				// Send Auth Packet
				c.send(aPacket);
			} else if (status == ConnectionStatus.AUTHREQUEST) {
				// Update Packet
				aPacket.setAuthentifikationState(AuthentifikationFlag.AUTHRUNACK);
				aPacket.setAccountPasswort(new byte[] { 0000 });
				// Send Auth Packet
				c.send(aPacket);
				// Set ClientStatus
				c.setConnnectStatus(ConnectionStatus.AUTHENTICATED);
			}
		}
		if (status == ConnectionStatus.AUTHENTICATED) {
			if (packet instanceof BroadcastPacket) {
				broadcast(packet);
			}
		}
	}

	public void handleClientSocketStatusChanged(ClientSocketStatusEvent e) {
		if (e.getStatus() == ClientSocketStatus.CLIENTSTOPPED
				|| e.getStatus() == ClientSocketStatus.SOCKETCLOSED) {
			removeConnection((Connection) e.getSource());
		}
	}

}
