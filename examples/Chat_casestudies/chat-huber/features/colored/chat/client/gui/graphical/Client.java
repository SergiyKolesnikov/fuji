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

	private ClientModel model;

	private void cmd(String command, String... params) {
		if (command.equals("color") || command.equals("c")) {
			if (params.length > 1 && model != null)
				model.send(new ColoredTextMessage(params[2].trim(), params[1]
						.trim()));
		}
		
		original(command, params);
	}

	private class MessageVisitor implements IMessageVisitor, Observer {
		
		@Override
		public void visit(ColoredTextMessage message) {
			append(message.getOrigin() + " (" + message.getColor() + ") :"
					+ message.getContent() + "\n");
		}
		
	}
}
