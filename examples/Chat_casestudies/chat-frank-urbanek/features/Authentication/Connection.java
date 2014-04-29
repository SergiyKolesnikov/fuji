/**
 * TODO description
 */
public class Connection {
	
	private void handleMessage(Message msg){
		if (!msg.getProperty("password").equals(Authentication.PASSWORD)){
			 handleAuthenticationFailed(msg);
		}else{
			original(msg);
		}
	}
	
/*
	private void handleAuthenticationSuccessfull(String name, Message msg) {
		// forward Message
		broadCast(msg);
	}
*/
	
	private void handleAuthenticationFailed(Message msg) {
		this.send(server.createServerMessage("authentication failed"));
	}

}