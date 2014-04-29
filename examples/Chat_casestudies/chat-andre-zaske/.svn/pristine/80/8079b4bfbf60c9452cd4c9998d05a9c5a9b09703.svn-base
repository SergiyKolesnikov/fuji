package network.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

	private ServerSocket serverSocket;
	private ServerPacketInHandler packetInHandler;
	private ExecutorService threadPool;

	private int serverPort;
	private boolean isStopped = false;

	public Server(int port, ServerPacketInHandler packetInHandler) {
		serverPort = port;
		threadPool = Executors.newCachedThreadPool();
		this.packetInHandler = packetInHandler;
	}

	private synchronized boolean isStopped() {
		return isStopped;
	}

	public void start() throws BindException{
		Thread thread = new Thread(this);
		try {
			openServerSocket();
			thread.start();
		}catch(BindException e){
			throw e;
		} catch (IOException e) {
			throw new RuntimeException("Error open serversocket.",
					e);
		}
		
	}

	public synchronized void stop() {
		this.isStopped = true;
		if (serverSocket != null) {
			try {
				this.serverSocket.close();
			} catch (IOException e) {
				throw new RuntimeException("Error closing server", e);
			}
		}
	}

	@Override
	public void run() {
		while (!isStopped()) {
			Socket clientSocket = null;
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				if (isStopped()) {
					return;
				}
				throw new RuntimeException("Error accepting client connection",
						e);
			}
			if (clientSocket != null) {
				Connection c = new Connection(clientSocket);
				c.addClientSocketListener(packetInHandler);
				threadPool.execute(c);
			}
		}
		threadPool.shutdown();
	}

	private void openServerSocket() throws IOException {
		this.serverSocket = new ServerSocket(serverPort);
	}

}
