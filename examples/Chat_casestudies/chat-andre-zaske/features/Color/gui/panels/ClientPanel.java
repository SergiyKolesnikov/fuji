package gui.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;

import network.server.packets.DataPacket;
import network.server.packets.QuitMessage;
import network.server.packets.TextMessage;

/**
 * TODO description
 */
public class ClientPanel {

	private JComboBox<String> cbColorStyle;

	private void modifyExtensionPanel(JPanel panel) {
		// Color Chooser
		JLabel colorLabel = new JLabel("Farbe:");
		colorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		cbColorStyle = new JComboBox<String>(chatStyles.getStyleList());
		cbColorStyle.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
		colorPanel.add(colorLabel);
		colorPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		colorPanel.add(cbColorStyle);
		panel.add(colorPanel);
		original(panel);
	}

	public void actionPerformed(ActionEvent e) {
		String acom = e.getActionCommand();
		if (acom.equals("btSendMSG")) {
			String stylename = (String) cbColorStyle.getSelectedItem();
			TextMessage txm = new TextMessage(chatClient.getName(), stylename,
					taSendText.getText());
			chatClient.sendPacket(txm);
			taSendText.setText("");
		} else {
			original(e);
		}
	}

	public void handlePacketIn(DataPacket packet) {
		Document doc = tpShowText.getDocument();
		Style style = chatStyles.getDefaultStyle();
		String msg = "";
		boolean handlePacket = true;
		if (packet instanceof TextMessage) {
			TextMessage txtMsg = (TextMessage) packet;
			style = chatStyles.getStyle(txtMsg.getStyleName());
			msg = txtMsg.getName() + ": " + txtMsg.getContent();
		} else if (packet instanceof JoinMessage) {
			JoinMessage joinMSG = (JoinMessage) packet;
			style = chatStyles.getStyle("blue");
			msg = "-> " + joinMSG.getName() + " joins the Chat.";
		} else if (packet instanceof QuitMessage) {
			QuitMessage quitMSG = (QuitMessage) packet;
			style = chatStyles.getStyle("red");
			msg = "-> " + quitMSG.getName() + " leaves the Chat.";
		} else {
			handlePacket = false;
		}
		msg += "\n";
		if (handlePacket) {
			try {
				doc.insertString(doc.getLength(), msg, style);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			tpShowText.setCaretPosition(tpShowText.getDocument().getLength());
		} else {
			original(packet);
		}
	}
}