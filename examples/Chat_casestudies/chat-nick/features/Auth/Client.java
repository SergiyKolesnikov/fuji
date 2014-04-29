import java.io.IOException;

public class Client {
	public void send(TextMessage msg) {

		if (msg.getContent().startsWith("\\auth")) {
			String[] artefacts = msg.getContent().split(" ");
			if (artefacts.length == 3) {
				String user = artefacts[1];
				String pw = artefacts[2];
				send(new AuthMessage(user, pw));
			} else {
				fireAddLine("\\auth command is wrong!\n");
			}
			return;
		}
		
		original(msg);
	}
	
	public void send(AuthMessage msg) {
		try {
			outputStream.writeObject(msg);
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			this.stop();
		}
	}	
}