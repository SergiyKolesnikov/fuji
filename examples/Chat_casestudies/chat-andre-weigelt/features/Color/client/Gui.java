package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.ChatLineListener;
import client.Client;

import common.TextMessage;

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
		additinalBoxes.add(chatClient.getColorPlugin().getComboBox());
		
		original();
	
	}

}
