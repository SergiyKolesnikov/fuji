package client;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.ChatLineListener;
import client.Client;

/**
 * simple swing gui for the chat client
 */
public class Gui extends JFrame implements ChatLineListener{
	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param chatClient
	 *            chatClient that is used for sending and receiving messages
	 */

	private Client chatClient;
	public Gui(Client chatclient) {
		this.chatClient = chatclient;

	}
	
	public void init() {
		additinalBoxes.add(chatClient.getEncrypt().getComboBox());
		
		original();
	
	}

}
