public class GraphicalUI extends View {

	private JButton colorPicker;
	private Color color = Color.BLACK;

	private void sendMessage(String msg) {
		if (!msg.isEmpty()) {
			ChatMessage m = new ChatMessage(getUsername(), msg);
			m.setColor(color);
			client.send(m);
		}
	}

	private void processMessage(ChatMessage msg) {
		String display = msg.getSource() + "> " + msg.getMsg();
		display += " (" + msg.getColor() + ")";
		display += "\n";
		textArea.append(display);
		textArea.getCaret().setDot(textArea.getText().length());
		scrollPane.scrollRectToVisible(textArea.getVisibleRect());
	}

	private void initializeComponents() {
		original();

		colorPicker = new JButton("Color");
		colorPicker.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color newColor = JColorChooser.showDialog(frame,
						"Choose a text color", Color.BLACK);
				color = newColor != null ? newColor : color;
				// System.out.println("Set new color to" + color);
			}
		});
		sendPanel.add(colorPicker, BorderLayout.WEST);
	}
}
