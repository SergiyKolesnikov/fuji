package client.gui;

import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatFrame extends JFrame {

	private static final long serialVersionUID = 4391101892507219227L;

	private JTextArea outputTextbox;
	private JTextField inputField;

	public ChatFrame(ActionListener listener) {
		getContentPane().setLayout(createLayout());

		inputField.addActionListener(listener);
		inputField.setActionCommand("CHAT");

		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private LayoutManager createLayout() {
		GroupLayout layout = new GroupLayout(this.getContentPane());

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		int rowstextarea = 20;
		int colstextarea = 60;

		outputTextbox = new JTextArea(rowstextarea, colstextarea);
		outputTextbox.setEditable(false);
		inputField = new JTextField(colstextarea - 10);

		Group horizontalGroup = layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				outputTextbox);

		Group verticalGroup = layout.createSequentialGroup().addComponent(
				outputTextbox);

		horizontalGroup.addGroup(layout.createSequentialGroup().addComponent(
				inputField));
		verticalGroup.addGroup(layout.createParallelGroup().addComponent(
				inputField));

		layout.setHorizontalGroup(horizontalGroup);
		layout.setVerticalGroup(verticalGroup);

		return layout;
	}

	public String getMessageToSend() {
		return this.inputField.getText();
	}

	public void resetMessageToSend() {
		this.inputField.setText("");
	}

	public void appendTextLine(String from, String msg) {
		StringBuilder builder = new StringBuilder();
		if (from != null) {
			builder.append(from);
			builder.append(": ");
		}
		builder.append(msg);
		builder.append("\n");
		outputTextbox.append(builder.toString());
	}

}
