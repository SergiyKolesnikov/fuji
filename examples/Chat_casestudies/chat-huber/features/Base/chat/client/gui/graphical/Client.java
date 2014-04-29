package chat.client.gui.graphical;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import chat.IMessageVisitor;
import chat.Logger;
import chat.client.ClientModel;
import chat.client.ClientUnicastVisitor;
import chat.messages.AMessage;
import chat.messages.ColoredTextMessage;
import chat.messages.DefaultTextMessage;
import chat.messages.LoginMessage;
import chat.messages.StatusMessage;

public class Client extends JFrame {

	private JTextField input = new JTextField();
	private JTextArea area = new JTextArea();
	private ClientModel model;
	private IMessageVisitor visitor;

	public Client(List<IMessageVisitor> inPlugins,
			List<IMessageVisitor> outPlugins) {
		super("Client");
		setLayout(new BorderLayout());

		add(new JScrollPane(area), BorderLayout.CENTER);
		add(input, BorderLayout.SOUTH);
		area.setEditable(false);

		input.addKeyListener(new InpListener());

		try {
			model = new ClientModel("localhost", 2000, inPlugins, outPlugins);
			visitor = new MessageVisitor(model);
			help();
		} catch (UnknownHostException e) {
			append("Could not connect to server...");
			input.setEnabled(false);
		} catch (IOException e) {
			append("Could not connect to server...");
			input.setEnabled(false);
		}
		
		init();

		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void init() {
		
	}

	private void prepare_send(String text) {
		if (text.trim().startsWith("#")) {
			String[] parts = text.replaceFirst("#", "").split(" ");
			cmd(parts[0], parts);
		} else if (model != null) {
			model.send(new DefaultTextMessage(text));
		}
	}

	private void cmd(String command, String... params) {
		if (command.equals("login") || command.equals("l")) {
			if (params.length > 1 && model != null)
				model
						.send(new LoginMessage(params[2].trim(), params[1]
								.trim()));
		}

		if (command.equals("help") || command.equals("h")) {
			help();
		}

		if (command.equals("exit") || command.equals("quit")) {
			System.exit(0);
		}
	}

	private void append(String text) {
		area.append(text);
		area.setCaretPosition(area.getDocument().getLength());
	}

	private class InpListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				prepare_send(input.getText());
				input.setText("");
			}
		}
	}

	private class MessageVisitor implements IMessageVisitor, Observer {
		public MessageVisitor(Observable obs) {
			if (obs != null) {
				obs.addObserver(this);
			}
		}

		@Override
		public void visit(DefaultTextMessage message) {
			append(message.getOrigin() + " :" + message.getContent() + "\n");
		}

		@Override
		public void visit(LoginMessage message) {

		}

		@Override
		public void visit(StatusMessage message) {
			append("> STATUS :" + message.getContent() + "\n");
		}

		@Override
		public void update(Observable o, Object arg) {
			if (arg instanceof AMessage)
				((AMessage) arg).accept(this);
		}

		public void setNextVisitor(IMessageVisitor next) {
		}
	}

	private static String join(String[] params, int startindex, String glue) {
		StringBuilder builder = new StringBuilder();
		for (int i = startindex; i < params.length; i++) {
			builder.append(params[i]);
			if (i != params.length - 1) {
				builder.append(glue);
			}
		}
		return builder.toString();
	}

	private void help() {
		append("-------------\n#login username password (Passwort ist hardcodiert 'password')\n#"
				+ "color colorname text (Colormessage)\n#message usernametarget text\n#username mynewusername\n#help this message\n#exit\n-------------\n");
	}

	public static void main(String[] args) {
		new Client(Arrays.asList(new IMessageVisitor[] {}),
				Arrays.asList(new IMessageVisitor[] { new ClientUnicastVisitor(
						null) }));
	}
}
