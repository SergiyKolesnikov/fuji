package client.gui; 

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 

import client.ClientObserver; 
import client.Client; 
import client.gui.ChatFrame; 

import common.message.StatusMessage; 
import common.message.TextMessage; 
import common.message.TextColor; 

/**
 * simple swing gui for the chat client
 */
public   class  Gui  implements ClientObserver, ActionListener {
	

	final private Client chatClient;

	

	private LoginFrame loginFrame = null;

	
	private ChatFrame chatFrame = null;

	

	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param chatClient
	 *            chatClient that is used for sending and receiving messages
	 */
	public Gui(Client chatClient) {
		System.out.println("starting gui...");
		this.chatClient = chatClient;

		loginFrame = new LoginFrame(this, chatClient.getHost(),
				chatClient.getPort());
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("CONNECT".equals(e.getActionCommand())) {
			onConnect();
		} else if ("CHAT".equals(e.getActionCommand())) {
			onChat();
		}
	}

	

	private void onConnect() {
		loginFrame.setVisible(false);

		chatFrame = new ChatFrame(this);

		this.chatClient.setHost(loginFrame.getServer());
		this.chatClient.setPort(Integer.valueOf(loginFrame.getPort()));
		this.chatClient.connect(loginFrame.getUserName(),
				loginFrame.getPassword());

		String connectionName = chatClient.getUser() + "@"
				+ loginFrame.getServer() + ":" + loginFrame.getPort();

		chatFrame.setTitle(connectionName);
	}

	

	private void onChat  () {
		chatClient.send(new TextMessage(chatFrame
				.getMessageToSend(), chatFrame.getTextColor()));
		chatFrame.resetMessageToSend();
	}

	

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void printNewChatLine  (TextMessage msg) {
		if (chatFrame != null) {
			chatFrame.appendTextLine(msg.getFrom(), msg.getContent(),
					msg.getTextColor());
		}
	}

	

	@Override
	public void printStatusMessage(StatusMessage msg) {
		if (chatFrame != null) {
			switch (msg.getStatus()) {
			case CONNECT_FAIL:
			case SERVER_WARN:
			case AUTH_FAIL:
				chatFrame.appendTextLine(msg.getStatus().toString(),
						msg.getReason(), TextColor.RED);
				break;
			case CONNECT_SUCC:
			case SERVER_INFO:
			case AUTH_SUCC:
				chatFrame.appendTextLine(msg.getStatus().toString(),
						msg.getReason(), TextColor.GREEN);
				break;
			case USER_LEFT:
				chatFrame.appendTextLine(null, msg.getReason(), TextColor.BLUE);
				break;
			case USER_JOINED:
				chatFrame.appendTextLine(null, msg.getReason(), TextColor.BLUE);
				break;
			}
		}
	}


}
