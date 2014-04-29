package client.gui;

import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1461082124778416953L;

	private JTextField serverInput;
	private JTextField portInput;
	private JTextField userInput;
	private JPasswordField passwordInput;
	JButton connectButton;

	public LoginFrame(ActionListener listener, String host, int port) {
		getContentPane().setLayout(createLayout());
		serverInput.setText(host);
		portInput.setText(String.valueOf(port));

		connectButton.addActionListener(listener);
		connectButton.setActionCommand("CONNECT");

		setTitle("Login");
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private LayoutManager createLayout() {
		GroupLayout layout = new GroupLayout(this.getContentPane());

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		JLabel serverLabel = new JLabel("server:");
		serverInput = new JTextField(10);

		JLabel portLabel = new JLabel("port:");
		portInput = new JTextField(10);

		JLabel userLabel = new JLabel("user:");
		userInput = new JTextField(10);

		JLabel passwordLabel = new JLabel("password:");
		passwordInput = new JPasswordField(10);

		connectButton = new JButton("connect");

		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(Alignment.LEADING)
								.addComponent(serverLabel)
								.addComponent(userLabel))
				.addGroup(
						layout.createParallelGroup(Alignment.LEADING)
								.addComponent(serverInput)
								.addComponent(userInput))
				.addGroup(
						layout.createParallelGroup(Alignment.LEADING)
								.addComponent(portLabel)
								.addComponent(passwordLabel))
				.addGroup(
						layout.createParallelGroup(Alignment.LEADING)
								.addComponent(portInput)
								.addComponent(passwordInput)
								.addComponent(connectButton)));

		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup().addComponent(serverLabel)
								.addComponent(serverInput)
								.addComponent(portLabel)
								.addComponent(portInput))
				.addGroup(
						layout.createParallelGroup().addComponent(userLabel)
								.addComponent(userInput)
								.addComponent(passwordLabel)
								.addComponent(passwordInput))
				.addComponent(connectButton));

		return layout;
	}

	public String getServer() {
		return this.serverInput.getText();
	}

	public String getPort() {
		return this.portInput.getText();
	}

	public String getUserName() {
		return this.userInput.getText();
	}

	public String getPassword() {
		return String.valueOf(this.passwordInput.getPassword());
	}
}
