/**
 * TODO description
 */
public class Connection {
	
	private void sendMessage(String name, TextMessage msg){	
		msg.sender = name;
			
		if (msg.isPrivate){
			server.privateMessage(msg);
		}else{
			original(name,msg);
		}
	}
	
	private boolean checkServerCommand  (TextMessage msg){
		
		boolean retval = false;
			if (msg.getContent().startsWith("/usr")){
				String line = msg.getContent();
				int indexFromName = 5;
				server.addPrivateConnection(line.substring(indexFromName), this);
				retval = true;
				server.broadcast("private connection hinzugefuegt für " + line.substring(indexFromName));
			}
			
			if (msg.getContent().startsWith("/msg")){
				String line = msg.getContent();
				int indexFromName = 5;
				line = line.trim();
				if (line.indexOf(' ') != -1){
					line = line.substring(indexFromName, line.length());
					line = line.substring(0, line.indexOf(' '));
					msg.receiverName = line;
					msg.isPrivate = true;
				}

			}
			
			return retval;
			
		}
}