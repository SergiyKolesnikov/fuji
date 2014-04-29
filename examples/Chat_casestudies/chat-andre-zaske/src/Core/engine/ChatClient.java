package engine; 

import java.util.ArrayList; 
import java.util.EventListener; 
import java.util.EventObject; 

import network.client.ClientSocketListener; 
import network.client.ClientSocketStatusEvent; 
import network.client.InPacketEvent; 
import network.client.ClientSocketStatusEvent.ClientSocketStatus; 
import network.server.AuthentifikationService; 
import network.server.Connection; 
import network.server.Connection.ConnectionStatus; 
import network.server.packets.AuthentifikationPacket; 
import network.server.packets.DataPacket; 
import network.server.packets.JoinMessage; 
import network.server.packets.TextMessage; 
import network.server.packets.AuthentifikationPacket.AuthentifikationFlag; 

public  class  ChatClient  implements ClientSocketListener {
	

	public enum  ChatClientStatus {
		CONNECTIONESTABLISHED ,  CONNECTIONLOST ,  LOGINSUCESSFUL ,  CONNECTIONNOTPOSSIBLE}

	

	private Connection client;

	
	private AuthentifikationPacket authPacket;

	
	private AuthentifikationService authService;

	
	private ArrayList<ChatClientListener> chatClientListeners = null;

	

	public ChatClient(String address, int port, String nickName) {
		// ConnectionSocket
		client = new Connection(address, port);
		client.addClientSocketListener(this);
		client.setName(nickName);
		client.start();
		// AuthentificationSevice
		authService = new AuthentifikationService();
		// ListenerList
		chatClientListeners = new ArrayList<ChatClientListener>();
	}

	

	public String getName() {
		return client.getName();
	}

	

	public void stop() {
		client.stop();
	}

	

	public void sendTextMessage(String msg) {
		sendPacket(new TextMessage(client.getName(), "", msg));
	}

	

	public void sendPacket(DataPacket packet) {
		client.send(packet);
	}

	

	public void login(String passwort) {
		if (authPacket != null) {
			authService.sendAuthPW(client, authPacket, passwort);
		}
	}

	

	public void addChatClientListener(ChatClientListener chatListener) {
		chatClientListeners.add(chatListener);
	}

	

	public void removeChatClientListener(ChatClientListener chatListener) {
		chatClientListeners.remove(chatListener);
	}

	

	private void fireClientStatusChanged(ChatClientStatus cstatus) {
		ChatClientEvent csEvent = new ChatClientEvent(this, cstatus);
		for (ChatClientListener ipListener : chatClientListeners) {
			ipListener.handleClientChatStatusChanged(csEvent);
		}
	}

	

	private void firePacketIn(DataPacket packet) {
		for (ChatClientListener ipListener : chatClientListeners) {
			ipListener.handlePacketIn(packet);
		}
	}

	

	@Override
	public void handlePacketIn(InPacketEvent inEvent) {
		DataPacket packet = inEvent.getDataPacket();
		if (client.getConnnectStatus() == ConnectionStatus.AUTHREQUEST) {
			if (packet instanceof AuthentifikationPacket) {
				AuthentifikationPacket aPacket = (AuthentifikationPacket) packet;
				if (aPacket.getAuthentifikationState() == AuthentifikationFlag.AUTHREQUESTACK) {
					authPacket = aPacket;
					fireClientStatusChanged(ChatClientStatus.CONNECTIONESTABLISHED);
				} else if (aPacket.getAuthentifikationState() == AuthentifikationFlag.NOAUTH) {
					authService.sendNoAuthAck(client, aPacket);
				} else if (aPacket.getAuthentifikationState() == AuthentifikationFlag.AUTHRUNACK) {
					client.setConnnectStatus(ConnectionStatus.AUTHENTICATED);
					fireClientStatusChanged(ChatClientStatus.LOGINSUCESSFUL);
					client.send(new JoinMessage(client.getName()));
				}
			}
		} else if (client.getConnnectStatus() == ConnectionStatus.AUTHENTICATED) {
			firePacketIn(packet);
		}
	}

	

	@Override
	public void handleClientSocketStatusChanged(ClientSocketStatusEvent e) {
		if (e.getStatus() == ClientSocketStatus.CONNECTIONESTABLISHED) {
			authService.sendAuthRequest(client);
		} else if (e.getStatus() == ClientSocketStatus.CONNECTIONLOST
				|| e.getStatus() == ClientSocketStatus.SOCKETCLOSED) {
			fireClientStatusChanged(ChatClientStatus.CONNECTIONLOST);
		} else if (e.getStatus() == ClientSocketStatus.CONNECTIONREFUSED) {
			fireClientStatusChanged(ChatClientStatus.CONNECTIONNOTPOSSIBLE);
		}

	}

	

	public  interface  ChatClientListener  extends EventListener {
		
		public void handleClientChatStatusChanged(ChatClientEvent clientEvent);

		

		public void handlePacketIn(DataPacket packet);


	}

	

	public  class  ChatClientEvent  extends EventObject {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 3250604555085810805L;

		

		private ChatClientStatus chatClientStatus;

		

		public ChatClientEvent(Object source, ChatClientStatus status) {
			super(source);
			chatClientStatus = status;
		}

		

		public ChatClientStatus getChatClientStatus() {
			return chatClientStatus;
		}


	}


}
