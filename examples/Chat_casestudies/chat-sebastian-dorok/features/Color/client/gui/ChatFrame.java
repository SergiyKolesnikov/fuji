package client.gui;

import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import common.message.TextColor;

public class ChatFrame extends JFrame {

	private JComboBox textColor;

	private LayoutManager createLayout() {
		GroupLayout layout = new GroupLayout(this.getContentPane());

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		int rowstextarea = 20;
		int colstextarea = 60;

		outputTextbox = new JTextArea(rowstextarea, colstextarea);
		outputTextbox.setEditable(false);
		inputField = new JTextField(colstextarea - 10);
		textColor = new JComboBox(TextColor.values());

		Group horizontalGroup = layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				outputTextbox);

		Group verticalGroup = layout.createSequentialGroup().addComponent(
				outputTextbox);

		horizontalGroup.addGroup(layout.createSequentialGroup()
				.addComponent(inputField).addComponent(textColor));
		verticalGroup.addGroup(layout.createParallelGroup()
				.addComponent(inputField).addComponent(textColor));

		layout.setHorizontalGroup(horizontalGroup);
		layout.setVerticalGroup(verticalGroup);

		return layout;
	}

	public TextColor getTextColor() {
		return (TextColor) this.textColor.getSelectedItem();
	}

	public void appendTextLine(String from, String msg, TextColor textColor) {
		StringBuilder builder = new StringBuilder();
		if (from != null) {
			builder.append(from);
			builder.append(": ");
		}
		switch (textColor) {
		case BLUE:
			builder.append("<blue>");
			builder.append(msg);
			builder.append("</blue>");
			break;
		case GREEN:
			builder.append("<green>");
			builder.append(msg);
			builder.append("</green>");
			break;
		case YELLOW:
			builder.append("<yellow>");
			builder.append(msg);
			builder.append("</yellow>");
			break;
		case RED:
			builder.append("<red>");
			builder.append(msg);
			builder.append("</red>");
			break;
		default:
			builder.append(msg);
			break;
		}
		builder.append("\n");
		outputTextbox.append(builder.toString());
	}

}
