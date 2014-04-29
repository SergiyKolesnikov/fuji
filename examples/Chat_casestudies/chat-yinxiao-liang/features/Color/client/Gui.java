package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import client.Client;
import common.TextMessage;

/**
 * TODO description
 */
public class Gui {

	private JComboBox colorComboBox;
	public String color = "Blau";

	public Gui(String title, Client chatClient) {
		colorComboBox = new JComboBox();
		colorComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox source = (JComboBox) e.getSource();
				color = (String) source.getSelectedItem();
			}

		});
		colorComboBox.setModel(new DefaultComboBoxModel(new String[] { "Blau",
				"Schwarz" }));
		pnlChat.add(colorComboBox);

	}
	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Send = (String) inputField.getText();
				String tmp = null;
				if(color.equals("Blau"))
					  
				{
					tmp="<".concat("Blau").concat(">").concat(" ").concat(Send).concat(" ").concat("</").concat("Blau").concat(">");
					Send = tmp;
					
				}
				else
				{
					tmp="<".concat("Schwarz").concat(">").concat(" ").concat(Send).concat(" ").concat("</").concat("Schwarz").concat(">");
					Send = tmp;
					
				}
				chatClient.send(Send);
				inputField.setText("");
				
			}
		};
			}
}