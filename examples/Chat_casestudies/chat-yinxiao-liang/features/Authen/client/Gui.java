package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import client.Client;

/**
 * TODO description
 */
public class Gui {
	public JComboBox userComboBox;
	public String touser = "all";
	
	public Gui(String title, Client chatClient){
	userComboBox = new JComboBox(chatClient.userlist);
	userComboBox.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JComboBox source = (JComboBox) e.getSource();
			touser = (String) source.getSelectedItem();
		}
	});
	pnlChat.add(userComboBox);
	}
}