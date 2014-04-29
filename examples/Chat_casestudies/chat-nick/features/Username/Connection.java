public class Connection {
	private String username;
	
	public String getUsername() {
		if (username == null)
			return "";
		return username;
	}
	
	private void handleTextMessage(String host, TextMessage msg) {
		
		if (msg.getContent().startsWith("\\username")) {
			String[] artefacts = msg.getContent().split(" ");
			String username = artefacts[1];
			this.username = username;
			send("Username " + username + " registered!");
			return;
		}

		if (msg.getContent().startsWith("\\msg")) {
			String[] artefacts = msg.getContent().split(" ");
			String recipient = artefacts[1];
			StringBuffer textToSend = new StringBuffer("");
			for (int i = 2; i < artefacts.length; i++) {
				textToSend.append(artefacts[i] + " ");
			}
			msg.setContent(textToSend.toString());
			server.sendPrivateMessage(this, recipient, msg);
			
			return;
		}
		
		original(host, msg);
	}	
}