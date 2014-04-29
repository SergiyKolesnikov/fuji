package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import client.Client;

/**
 * TODO description
 */
public class Gui {
	public String whisper = "public";
	private JComboBox privateComboBox;
	public Gui(String title, Client chatClient) {
		privateComboBox = new JComboBox();
		privateComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox source = (JComboBox) e.getSource();
				whisper = (String) source.getSelectedItem();
			}

		});
		privateComboBox.setModel(new DefaultComboBoxModel(new String[] { "Public",
				"private" }));
		pnlChat.add(privateComboBox);
	}
}