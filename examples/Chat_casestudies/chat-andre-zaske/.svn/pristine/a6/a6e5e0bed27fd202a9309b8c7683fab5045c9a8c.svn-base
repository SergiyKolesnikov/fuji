package network.server;

import java.net.Socket;
import java.util.Arrays;

import network.client.ClientSocket;

public class Connection extends ClientSocket {

	public enum ConnectionStatus {
		UNKOWN, AUTHREQUEST, AUTHENTICATED
	}

	private ConnectionStatus connnectStatus;
	private byte[] authKey;
	private String name;

	public Connection(Socket cSocket) {
		super(cSocket);
		connnectStatus = ConnectionStatus.UNKOWN;
	}
	
	public Connection(String host, int port) {
		super(host,port);
		connnectStatus = ConnectionStatus.UNKOWN;
	}

	public ConnectionStatus getConnnectStatus() {
		return connnectStatus;
	}

	public void setConnnectStatus(ConnectionStatus connnectStatus) {
		this.connnectStatus = connnectStatus;
	}

	public byte[] getAuthKey() {
		return authKey;
	}

	public void setAuthKey(byte[] authKey) {
		this.authKey = authKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getLogin(String pw) {
		byte[] nameKey = pw.getBytes();
		byte[] destination = new byte[nameKey.length + authKey.length];
		// Copy nameKey
		System.arraycopy(nameKey, 0, destination, 0, nameKey.length);
		// Append authkey
		System.arraycopy(authKey, 0, destination, nameKey.length,
				authKey.length);
		// Create Hash
		return Hash.sha1(destination);
	}

	public boolean checkLogin(byte[] accKey, String pw) {
		return Arrays.equals(getLogin(pw), accKey);
	}

}
