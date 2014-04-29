/**
 * TODO description
 */
public class Connection {
	
	/*
	 * 	/*	public void ShowStyledChatline(String line, SimpleAttributeSet attributes){
		EncryptionPlugin encryption = new EncryptionPlugin(EncryptionPlugin.Encoding.Rot13);
		line = encryption.handleInputMessage(line);
		original(line, attributes);
	}
	
	public String formatOutput(String line){
		EncryptionPlugin encryption = new EncryptionPlugin(EncryptionPlugin.Encoding.Rot13);
		line = encryption.handleOutputMessage(line);
		return line;
	}*/
	 
	private void sendMessage(String name, TextMessage msg){
			EncryptionPlugin encryption = new EncryptionPlugin(EncryptionPlugin.Encoding.Rot13);
			msg = new TextMessage(encryption.handleOutputMessage(msg.getContent()));
			
			if (((TextMessage) msg).getContent().charAt(0) != '/'){
				msg = new TextMessage(encryption.handleInputMessage(msg.getContent()));
				server.broadcast(name + " - " + ((TextMessage) msg).getContent());
			}else{
				checkServerCommand(msg);
			}
	}

}