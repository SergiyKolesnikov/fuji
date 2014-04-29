import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Connection extends Thread {
	protected String clientName;
	protected Socket client;
	protected BufferedReader in;
	protected PrintStream out;
	protected ChatServer server;

	public Connection(ChatServer server, Socket client) {
		this.server = server;
		this.client = client;
		this.clientName = this.client.getInetAddress().toString();
		try {
			in = new BufferedReader(new InputStreamReader(client
					.getInputStream()));
			out = new PrintStream(client.getOutputStream());
		} catch (IOException ioExcept) {
			try {
				client.close();
			} catch (IOException ioExcept2) {			
			JOptionPane.showMessageDialog(this.server.getFrame(),
					"Error occured establishing the connection", "I/O error",
					JOptionPane.ERROR_MESSAGE);
			}
			return;
		}
		this.start();
	}

	public void run() {
		String line;
		try {
			while (true) {
				line = in.readLine();
				if (line != null) {
					sendMsg(line);
				}
			}
		} catch (IOException ioExcept) {
			JOptionPane.showMessageDialog(server.getFrame(),
					"Lost connection to\n" + client.getInetAddress(),
					"I/O error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void sendMsg(String line) {
		broadcast(line);
	}
	
	private void broadcast(String line) {
		server.broadcast(line);
	}
	
	public Socket getClient() {
		return client;
	}

	public PrintStream getOut() {
		return out;
	}
	
	public String getClientName() {
		return clientName;
	}
}