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
	private Logger logger = new Logger(null, "clientlog.txt");

	private void init() {
		if (model != null) {
			model.addObserver(logger);
		}

		original();
	}
}
