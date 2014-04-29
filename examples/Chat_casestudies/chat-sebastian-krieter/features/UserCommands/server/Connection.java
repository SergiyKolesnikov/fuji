package server;

public class Connection {

	private void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			server.broadcast((TextMessage) msg);
		} else if (msg instanceof Integer) {
			int cmnd = (Integer) msg;
			if (cmnd == Commands.COMMAND_USERNAME) {
				String newName = null;
				try {
					newName = inputStream.readObject().toString();
				
					if (newName != null && UsernameManager.allocate(newName)) {
						UsernameManager.remove(username);
						server.changeUsername(username, newName);
						username = newName;
						outputStream.writeObject(Commands.RESPONSE_OK);
					} else {
						outputStream.writeObject(Commands.RESPONSE_DENIED);
					}
					outputStream.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
