package client;
import common.ServerCommandMessage;

/**
 * TODO Catch here all Messages and test on ServerCommand beeing.
 */
public class Client {
	public void send(String line) {
		ServerCommandMessage commandMsg = ServerCommandMessage.tryInterpretAsServerCommand(line);
		if(commandMsg != null){
			send(commandMsg);
		}else{
			original(line);
		}
	}
}