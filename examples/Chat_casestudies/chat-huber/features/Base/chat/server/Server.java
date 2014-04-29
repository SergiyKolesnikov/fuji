package chat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import chat.IClient;
import chat.IMessageVisitor;
import chat.Logger;
import chat.client.ClientUnicastVisitor;
import chat.messages.AMessage;
import chat.server.visitors.AuthenticationVisitor;
import chat.server.visitors.BroadcastVisitor;
import chat.server.visitors.ServerUnicastVisitor;
import chat.server.visitors.SpamFilterVisitor;

public class Server implements IServer {
	private int port;

	private List clients;
	private ServerFilter filter;

	public Server(int port) {
		this.port = port;
		this.clients = new LinkedList();
	}

	public void setPlugins(List<IMessageVisitor> inPlugins,
			List<IMessageVisitor> outPlugins) {
		filter = new ServerFilter(inPlugins, outPlugins);
	}

	public void start() {
		listen();
	}

	private void listen() {
		ServerSocket server;
		try {
			server = new ServerSocket(port);

			while (true) {
				clients.add(new ClientSocket(server.accept()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class ClientSocket extends Thread implements IClient {
		private boolean authenticated = true;
		private ObjectInputStream in;
		private ObjectOutputStream out;
		private String string;

		public ClientSocket(Socket socket) {
			string = socket.getRemoteSocketAddress().toString();
			System.out.println("New Client: " + string);

			try {
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			start();
		}

		public void run() {
			Object obj = null;
			try {
				while ((obj = in.readObject()) != null) {
					receive((AMessage) obj, this);
				}
			} catch (IOException e) {
			} catch (ClassNotFoundException e) {
			} finally {
				clients.remove(this);
			}
		}

		public void unicast(AMessage message) {
			try {
				out.writeObject(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public boolean getAuthenticated() {
			return authenticated;
		}

		@Override
		public void setAuthenticated(boolean authenticated) {
			this.authenticated = authenticated;
		}

		public String toString() {
			return string;
		}
	}

	public void broadcast(AMessage message) {
		for (Object client : clients) {
			ClientSocket socket = (ClientSocket) client;

			if (socket.getAuthenticated()) {
				socket.unicast(message);
			}
		}
	}

	private void receive(AMessage message, IClient client) {
		message.setClient(client);
		message.setOrigin(client.toString());

		filter.filter(message);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server(2000);
		server.setPlugins(Arrays
				.asList(new IMessageVisitor[] {
						new SpamFilterVisitor(null)}), Arrays
				.asList(new IMessageVisitor[] { new ServerUnicastVisitor(null),
						new BroadcastVisitor(null, server) }));
		server.start();
	}
}
