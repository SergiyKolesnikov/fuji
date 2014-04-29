package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.GroupLayout.Group;

public class GUI extends JFrame implements ChatListener {	
	protected JComboBox colorComboBox;
	
	private void addComponents(Group h, Group v) {
		original(h,v);
		colorComboBox = new JComboBox(new String[] { "black", "red", "green", "blue" });
		colorComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chatClient.setColor(colorComboBox.getSelectedItem().toString());
			}
		});
		h.addComponent(colorComboBox);
		v.addComponent(colorComboBox);
	}
	
	public void handleColoredMessage(String from, String text, String color) {
		output("[" + color + "] " + from + ": " + text + "\n");
	}
}