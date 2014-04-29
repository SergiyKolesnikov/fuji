package network.client; 

import java.io.EOFException; 
import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.ConnectException; 
import java.net.Socket; 
import java.net.SocketException; 
import java.util.ArrayList; 

import network.client.ClientSocketStatusEvent.ClientSocketStatus; 
import network.server.packets.DataPacket; 
import crypto.AbstractCiphering; 
import crypto.BlankEncoder; 

public  class  ClientSocket  implements Runnable {
	

	private Socket clientSocket = null;

	
	private boolean clientSocketOpened = false;

	
	private boolean isStopped = false;

	
	private String hostAddress =  null;

	
	private int port;

	
	private AbstractCiphering ciphCoder;

	

	private ObjectInputStream inputStream = null;

	
	private ObjectOutputStream outputStream = null;

	
	private ArrayList<ClientSocketListener> inPacketListeners = null;

	

	public ClientSocket(String host, int port) {
		hostAddress = host;
		this.port = port;
		inPacketListeners = new ArrayList<ClientSocketListener>();
		ciphCoder = new BlankEncoder();
	}

	

	public ClientSocket(Socket cSocket) {
		clientSocket = cSocket;
		clientSocketOpened = true;
		inPacketListeners = new ArrayList<ClientSocketListener>();
		ciphCoder = new BlankEncoder();
	}

	

	public AbstractCiphering getCiphCoder() {
		return ciphCoder;
	}

	

	public void setCiphCoder(AbstractCiphering ciphCoder) {
		this.ciphCoder = ciphCoder;
	}

	

	private synchronized boolean isStopped() {
		return isStopped;
	}

	

	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}

	

	public synchronized void stop() {
		this.isStopped = true;
		if (clientSocket != null) {
			try {
				if(inputStream != null){
					outputStream.close();
					inputStream.close();
				}
				if(!clientSocket.isClosed()){
					clientSocket.close();
				}
			}catch(SocketException e){

			} catch (IOException e) {
				throw new RuntimeException("Error closing ClientSocket", e);
			}finally{
				fireClientStatusChanged(ClientSocketStatus.CLIENTSTOPPED);
			}
		}
	}

	

	public void run() {
		openClientSocket();
		Object packet = null;
		try {
			while (!isStopped()) {
				packet = inputStream.readObject();
				if (packet instanceof DataPacket) {
					handleIncomingPacket((DataPacket) packet);
				}
			}
		} catch (EOFException e) {
			fireClientStatusChanged(ClientSocketStatus.CONNECTIONLOST);
		}catch(SocketException e){
			fireClientStatusChanged(ClientSocketStatus.SOCKETCLOSED);
		} catch (IOException e) {
			throw new RuntimeException(
					"Error handling incomming Object in Client", e);
		}catch(ClassNotFoundException e){
			throw new RuntimeException(
					"Error handling incomming Object in Client", e);
		} finally {
			stop();
		}
	}

	

	public void send(DataPacket packet) {
		packet.actionBeforeSend(ciphCoder);
		try {
			outputStream.writeObject(packet);
			outputStream.flush();
		}catch (SocketException e){
			fireClientStatusChanged(ClientSocketStatus.SOCKETCLOSED);
			this.stop();
		} catch (IOException e) {
			this.stop();
			throw new RuntimeException("Error sending DataPacket in Client", e);
		}
	}

	

	public void addClientSocketListener(ClientSocketListener ipListener) {
		inPacketListeners.add(ipListener);
	}

	

	public void removeClientSocketListener(ClientSocketListener ipListener) {
		inPacketListeners.remove(ipListener);
	}

	

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	private void handleIncomingPacket(DataPacket packet) {
		packet.actionAfterReceive(ciphCoder);
		InPacketEvent impEvent = new InPacketEvent(this, packet);
		for (ClientSocketListener ipListener : inPacketListeners) {
			ipListener.handlePacketIn(impEvent);
		}
	}

	

	private void fireClientStatusChanged(ClientSocketStatus cstatus) {
		ClientSocketStatusEvent csEvent = new ClientSocketStatusEvent(this, cstatus);
		for (ClientSocketListener ipListener : inPacketListeners) {
			ipListener.handleClientSocketStatusChanged(csEvent);
		}
	}

	

	private void openClientSocket() {
		if (!clientSocketOpened) {
			try {
				clientSocket = new Socket(hostAddress, port);
				clientSocketOpened = true;
			} catch(ConnectException e){
				fireClientStatusChanged(ClientSocketStatus.CONNECTIONREFUSED);
				this.stop();
				return;
			} catch (IOException e) {
				throw new RuntimeException("Cannot open Client Server: "
						+ hostAddress + " Port:" + port, e);
			}
		}
		try {
			outputStream = new ObjectOutputStream(
					(clientSocket.getOutputStream()));
			inputStream = new ObjectInputStream((clientSocket.getInputStream()));
		} catch (IOException e) {
			throw new RuntimeException(
					"Extracting In-/OutputStream form ClientSocket", e);
		}
		fireClientStatusChanged(ClientSocketStatus.CONNECTIONESTABLISHED);
	}


}
