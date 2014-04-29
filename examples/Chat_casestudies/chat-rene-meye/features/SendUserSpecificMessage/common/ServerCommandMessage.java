package common;

/**
 * TODO description
 */
public class ServerCommandMessage {
	private static boolean isKnownCommand(String command, String options) {
		if(command.equalsIgnoreCase("msg")){
			return true;
		}
		return original(command, options);
	}
}