package network.server;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import network.server.Connection.ConnectionStatus;
import network.server.packets.AuthentifikationPacket;
import network.server.packets.AuthentifikationPacket.AuthentifikationFlag;
import crypto.AbstractCiphering;
import crypto.BlankEncoder;

public class AuthentifikationService {

	private AbstractCiphering activeCoder;

	public AuthentifikationService() {
		activeCoder = new BlankEncoder();
	}

	public AbstractCiphering getActivCiphering() {
		return activeCoder;
	}

	public String getActivCipheringName() {
		return activeCoder.getClass().getName();
	}

	public void setActivCiphering(String name) {
		activeCoder = newCipheringPlugin(name);
	}

	// Client Auth
	public void sendAuthRequest(Connection c) {
		// Create AuthentifikationPacket
		AuthentifikationPacket aPacket = new AuthentifikationPacket(c.getName());
		c.send(aPacket);
		// Set ClientStatus
		c.setConnnectStatus(ConnectionStatus.AUTHREQUEST);
	}

	public void sendAuthPW(Connection c, AuthentifikationPacket packet,
			String pw) {
		// Configurate AuthService
		setActivCiphering(packet.getChipheringCoder());
		// Configurate Client
		c.setAuthKey(packet.getAccountPasswort());
		c.setCiphCoder(getActivCiphering());
		// Update Packet
		packet.setAuthentifikationState(AuthentifikationFlag.AUTHRUN);
		packet.setAccountPasswort(c.getLogin(pw));
		// Sending Packet
		c.send(packet);
	}

	public void sendNoAuthAck(Connection c, AuthentifikationPacket packet) {
		// Configurate AuthService
		setActivCiphering(packet.getChipheringCoder());
		// Configurate Client
		c.setAuthKey(packet.getAccountPasswort());
		c.setCiphCoder(getActivCiphering());
		// Update Packet
		packet.setAuthentifikationState(AuthentifikationFlag.NOAUTH);
		packet.setAccountPasswort(new byte[] { 0000 });
		// Sending Packet
		c.send(packet);
	}

	// Server Auth
	public void sendAuthKey(Connection c, AuthentifikationPacket packet) {
		// Configurate Client
		c.setName(packet.getAccountName());
		c.setCiphCoder(getActivCiphering());
		// Update Packet
		packet.setChipheringCoder(getActivCipheringName());
		// Generate Key and prepare Auth Packet
		byte[] authKey = generateKey();
		// Configurate Client
		c.setAuthKey(authKey);
		// Update Packet
		packet.setAuthentifikationState(AuthentifikationFlag.AUTHREQUESTACK);
		packet.setAccountPasswort(authKey);
		// Set ClientStatus
		c.setConnnectStatus(ConnectionStatus.AUTHREQUEST);
		// Send Auth Packet
		c.send(packet);
	}

	public void sendAuthSuccess(Connection c, AuthentifikationPacket packet) {
		// Update Packet
		packet.setAuthentifikationState(AuthentifikationFlag.AUTHRUNACK);
		packet.setAccountPasswort(new byte[] { 0000 });
		// Send Auth Packet
		c.send(packet);
		// Set ClientStatus
		c.setConnnectStatus(ConnectionStatus.AUTHENTICATED);
	}

	private byte[] generateKey() {
		byte[] b = new byte[20];
		new Random().nextBytes(b);
		return b;
	}

	private AbstractCiphering newCipheringPlugin(String name) {
		try {
			Class<? extends AbstractCiphering> curClass = (Class<? extends AbstractCiphering>) Class
					.forName(name);

			Constructor<? extends AbstractCiphering> constructor = curClass
					.getConstructor();
			return (AbstractCiphering) constructor.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}
