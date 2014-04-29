package client;

import javax.swing.JComboBox;

import common.TextMessage;

public class Color {
	private static String[] colors = { "black", "red", "yellow", "blue", "green" };
	private JComboBox<String> colorChooser;

	public Color() {
		colorChooser = new JComboBox<String>(colors);
		colorChooser.setSelectedIndex(0);
	}

	public String[] getColors() {
		return colors;
	}

	public JComboBox<String> getComboBox() {
		return colorChooser;
	}

	public void handleIncomingMessage(TextMessage msg) {
		String content = msg.getContent();
		String firstColorTag = "";
		String secondColorTag = "";
		if (msg.getColor() != null && !msg.getColor().equals("black")) {
			firstColorTag = "<" + msg.getColor() + ">";
			secondColorTag = "</" + msg.getColor() + ">";
			msg.setContent(firstColorTag + content + secondColorTag);
		}		
	}

	public void handleOutgoingMessage(TextMessage msg) {
		msg.setColor(colorChooser.getSelectedItem().toString());
	}
}
