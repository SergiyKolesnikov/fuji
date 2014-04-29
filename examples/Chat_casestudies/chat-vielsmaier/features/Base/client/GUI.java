package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;

import common.JoinMessage;
import common.TextMessage;

/**
 * simple swing gui for the chat client
 */
public class GUI extends JFrame implements ChatListener {

	private static final long serialVersionUID = 1L;

	protected JTextArea outputTextbox;
	protected JTextField inputField;

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
	public GUI(String title, Client chatClient) {
		System.out.println("starting gui...");

		outputTextbox = new JTextArea(GUI.rowstextarea, GUI.colstextarea);
		outputTextbox.setEditable(false);
		inputField = new JTextField();
		inputField.addActionListener(getInput());
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		Group horizontalGroup = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		Group verticalGroup = layout.createSequentialGroup();
		
        
        horizontalGroup.addComponent(outputTextbox)
        				.addComponent(inputField);

        verticalGroup.addComponent(outputTextbox)
                     	.addComponent(inputField);
                     	
        addComponents(horizontalGroup, verticalGroup);
		
        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(horizontalGroup));
		layout.setVerticalGroup(verticalGroup);
		pack();

		chatClient.addChatListener(this);

		setTitle(title);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.chatClient = chatClient;
		new ServerDialog(chatClient);
	}	
	
	private void addComponents(Group h, Group v) {
	}

	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(inputField.getText().startsWith("/")) {
					handleCommand(inputField.getText());
				} else {
					chatClient.send((String) inputField.getText());
				}
				inputField.setText("");
			}
		};
	}
	
	private void output(String s) {
		outputTextbox.append(s);
	}

	public void handleLine(String from, String text) {
		output(from + ": " + text + "\n");
	}

	public void handleJoin(String username) {
		output(username + " joined\n");
	}
	
	private void handleCommand(String command) {
	}
}