import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ChatServer implements Runnable {
	public static final int PORT = 5555;
	protected ServerSocket listen;
	protected Vector<Connection> connections;
	protected boolean offline = true;
	Thread connection;
	protected JFrame frame;
	private JButton btn;

	public ChatServer() {
		try {
			listen = new ServerSocket(PORT);
		} catch (IOException ioExcept) {
			JOptionPane.showMessageDialog(frame,
					"Error occured creating socket.\n"
							+ "System will shut down.", "I/O error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		connections = new Vector<Connection>();
		connection = new Thread(this);
		initGui();
	}

	private void initGui() {
		frame = new JFrame("Chat-Server");
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		btn = new JButton("Start server");
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (offline) {
					offline = !offline;
					btn.setText("Stop server");
					connection.start();
				} else {
					System.exit(1);
				}
			}
		};
		btn.addActionListener(al);
		frame.add(btn);
		frame.setSize(250, 80);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String args[]) {
		new ChatServer();
	}

	public void run() {
		try {
			while (true) {
				Socket client = listen.accept();
				Connection thisConnection = new Connection(this, client);
				connections.addElement(thisConnection);
			}
		} catch (IOException ioExcept) {
			JOptionPane.showMessageDialog(frame,
					"Error occured waiting for connection.\n"
							+ "System will shut down.", "I/O error",
					JOptionPane.ERROR_MESSAGE);
			System.err.println("Fehler beim Warten auf Verbindungen: "
					+ ioExcept);
			System.exit(1);
		}
	}

	public void sendMsgTo(Connection reciever, String dispatcher, String message) {
		reciever.getOut().append(
				dispatcher + ": " + message + "\n");
		reciever.getOut().flush();
	}
	
	public void broadcast(String message) {
		Connection currentConnection;
		for (int i = 0; i < connections.size(); i++) {
			currentConnection = connections.elementAt(i);
			String dispatcher = currentConnection.getClientName();
			sendMsgTo(currentConnection, dispatcher, message);
		}
	}

	public JFrame getFrame() {
		return frame;
	}
}