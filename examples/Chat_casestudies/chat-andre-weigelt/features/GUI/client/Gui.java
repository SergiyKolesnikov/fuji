package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.util.List;
import java.util.ArrayList;

import client.ChatLineListener;
import client.Client;

import common.TextMessage;

/**
 * simple swing gui for the chat client
 */
public class Gui extends JFrame implements ChatLineListener{

	private static final long serialVersionUID = 1L;

	protected JTextArea outputTextbox;
	protected JTextField inputField;
	private List<JComboBox> additinalBoxes = new ArrayList<JComboBox>();
	
	private static int rowstextarea = 20;
	private static int colstextarea = 60;

	private Client chatClient;

	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param chatClient
	 *            chatClient that is used for sending and receiving messages
	 */
	public Gui(Client chatclient) {
		System.out.println("starting gui...");
		this.chatClient = chatclient;
		outputTextbox = new JTextArea(Gui.rowstextarea, Gui.colstextarea);
		outputTextbox.setEditable(false);
		inputField = new JTextField();
		inputField.addActionListener(getInput());

		init();
	}

	public void init() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		ParallelGroup grp = layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		SequentialGroup grp2 = layout.createSequentialGroup();
		grp.addComponent(outputTextbox);
		grp2.addComponent(outputTextbox);


		for (JComboBox<String> box : additinalBoxes) {
			grp.addComponent(box);
			grp2.addComponent(box);
		}
		
		grp.addComponent(inputField);
		grp2.addComponent(inputField);

		layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(grp));
		layout.setVerticalGroup(grp2);

		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)

		chatClient.addLineListener(this);

		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newChatLine("Welcome to chat!\n");
	}

	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TextMessage msg = new TextMessage(inputField.getText());
				chatClient.send(msg);
				inputField.setText("");
			}
		};
	}

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void newChatLine(String line) {
		outputTextbox.append(line);
	}


}
