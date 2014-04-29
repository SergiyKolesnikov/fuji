package client;

public class Client {
	private final String password = "123456";
	
	private String login() {
		try {
			outputStream.writeInt(Commands.COMMAND_LOGIN);
			outputStream.writeObject(password);
			outputStream.flush();
			
			if (Commands.RESPONSE_OK == inputStream.readInt()) {
				return inputStream.readObject().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
