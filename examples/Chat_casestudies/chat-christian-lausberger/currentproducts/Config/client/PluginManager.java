package client; 

import java.util.ArrayList; 

import common.TextMessage; 
import output.*; 

import messageExtension.*; 

import client.Client; 
import encryption.*; 

public   class  PluginManager {
	

	private Client client;

	
	private ArrayList<IOn_Server_Connect> onConnect = new ArrayList<IOn_Server_Connect>();

	
	private ArrayList<IChatLineListener> chatLineListeners = new ArrayList<IChatLineListener>();

	
	private ArrayList<IMessageExtension> messageExtensionListeners = new ArrayList<IMessageExtension>();

	
	public PluginManager  (Client client) {
		this.client = client;
		addMessageExtension(new SenderExtension());
	
		addLineListener(new ConsoleOutput());
	
		addLineListener(new Gui("Chat " + client.getUser() + ":"
				+ client.getPort(), client));
	
		addMessageExtension(new ColorExtension());
	
		onConnect.add(new client_authentication());
	
		addLineListener(new History(client.getUser()));
	
		addMessageExtension(new Rot13());
	
		addMessageExtension(new SpamFilter());
	}

	

	private void addLineListener(IChatLineListener listener) {
		chatLineListeners.add(listener);
	}

	
	
	private void addMessageExtension(IMessageExtension ext) {
		messageExtensionListeners.add(ext);
	}

	

	public void stop() {
		for (int i = 0; i < chatLineListeners.size(); i++) {
			IChatLineListener listener = chatLineListeners.get(i);
			listener.stop();
		}
		chatLineListeners.clear();
		for (int i = 0; i < messageExtensionListeners.size(); i++) {
			IMessageExtension me = messageExtensionListeners.get(i);
			me.stop();
		}
		messageExtensionListeners.clear();
	}

	
	
	public void onConnect(){
		for (IOn_Server_Connect s : onConnect) {
			s.onConnect(client);
		}
	}

	
	
	public void newChatLine(String line) {
		for (IChatLineListener listener : chatLineListeners) {
			listener.newChatLine(line);
		}
	}

	

	public void incomingMessage(TextMessage msg) {
		//msg with extensions (not from server)
		if ( msg.getSender() != null){
			for (IMessageExtension me : messageExtensionListeners) {
				msg = me.readMessage(msg);
			}
		}
		newChatLine(msg.getContent() + "\n");
	}

	
	
	public TextMessage send(String line){
		TextMessage msg = new TextMessage(line);
		for (IMessageExtension me : messageExtensionListeners) {
			msg = me.extendMessage(msg, client);
		}
		return msg;
	}


}
