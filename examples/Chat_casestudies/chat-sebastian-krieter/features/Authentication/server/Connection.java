package server;

public class Connection {

	private boolean waitForLogin() {
		boolean access = false;
		try {
			if (Commands.COMMAND_LOGIN == inputStream.readInt()) {
				if (Server.PASSWORD.equals(inputStream.readObject().toString())) {
					outputStream.writeInt(Commands.RESPONSE_OK);
					outputStream.writeObject(username);
					access = true;
				} else {
					outputStream.writeInt(Commands.RESPONSE_DENIED);
				}
				outputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return access;
	}
}
