package gui.panels;

import java.util.ArrayList;
import java.util.List;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;

import engine.ChatClient;
import gui.panels.ClientPanel;

import network.server.packets.DataPacket;
import network.server.packets.JoinMessage;
import network.server.packets.OnlineStatusMessage;
import network.server.packets.PrivateMessage;
import network.server.packets.QuitMessage;
import network.server.packets.TextMessage;

/**
 * TODO description
 */
public class ClientPanel {

	private DefaultListModel<String> friendsListModel;
	private JList<String> friendsList;
	private ArrayList<String> friendsArray;

	private void initChatView() {
		original();
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BorderLayout());
		friendsArray = new ArrayList<String>();
		friendsListModel = new DefaultListModel<String>();
		friendsList = new JList<String>(friendsListModel);
		friendsList.setVisibleRowCount(10);
		friendsList.setFixedCellWidth(100);
		JPanel wrapperPanel = new JPanel();
		wrapperPanel.add(friendsList);

		westPanel.add(wrapperPanel, BorderLayout.CENTER);
		add(westPanel, BorderLayout.WEST);
	}

	public void handlePacketIn(DataPacket packet) {
		original(packet);
		if (packet instanceof JoinMessage) {
			JoinMessage joinMSG = (JoinMessage) packet;
			if (!chatClient.getName().equals(joinMSG.getName())) {
				addFriend(joinMSG.getName());
			}
		} else if (packet instanceof OnlineStatusMessage) {
			OnlineStatusMessage onlineMSG = (OnlineStatusMessage) packet;
			addFriend(onlineMSG.getName());
		} else if (packet instanceof QuitMessage) {
			QuitMessage quitMSG = (QuitMessage) packet;
			friendsListModel.removeElement(quitMSG.getName());
		} else if (packet instanceof PrivateMessage) {
			Document doc = tpShowText.getDocument();
			Style style = chatStyles.getStyle("private");
			PrivateMessage pm = (PrivateMessage) packet;
			String msg = "(p) " + pm.getName() + ": " + pm.getContent();
			if (!chatClient.getName().equals(pm.getDestination())) {
				msg += " (" + pm.getDestination() + ")";
			}
			msg += "\n";
			try {
				doc.insertString(doc.getLength(), msg, style);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			tpShowText.setCaretPosition(tpShowText.getDocument().getLength());
		}
	}
	
	private void addFriend(String friendsName) {
		if (!friendsArray.contains(friendsName)) {
			friendsListModel.addElement(friendsName);
			friendsArray.add(friendsName);
		}
	}

	public void actionPerformed(ActionEvent e) {
		String acom = e.getActionCommand();
		if (acom.equals("btSendMSG") && friendsList.getSelectedValue() != null) {
			List<String> selected = friendsList.getSelectedValuesList();
			for (String name : selected) {
				PrivateMessage pm = new PrivateMessage(chatClient.getName(),
						"", taSendText.getText());
				pm.setDestination(name);
				chatClient.sendPacket(pm);
			}
			taSendText.setText("");
		} else {
			original(e);
		}
	}
}