import java.net.Socket;

public class ClientConnection {

	private AuthenticationMessage message = new AuthenticationMessage(Application.args[3], Application.args[4]);
	
	protected void runCommunicationLoop() {
		connectionEstablished();
		original();
		connectionClosed();
	}
	
	private void connectionEstablished() {
		send(message);
		
		Message msg = receive();
		
		if (msg instanceof AuthenticationResultMessage) {
			if (((AuthenticationResultMessage) msg).isSuccessful() == false) {
				System.out.println("WARNING: authentication failed!");
				close();
			}
			else {
				setUserName(message.getUserName());
			}	
		}
		else if(msg != null) {
			System.out.println("WARNING: authentication protocol neglected!");
		}
	}
	
	private void connectionClosed() {
	}

}
