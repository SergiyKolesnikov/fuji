package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import common.*;

public class Console implements Runnable, IUserInterface {
	
	private Client _client;
	private Thread _thread;
	
	public Console(Client client) {
		_client = client;
		_client.addLineListener(this);
		_thread = new Thread(this);
		_thread.start();
		
	}
	
	@Override
	public void sendMessage(ITextMessage message) {
		_client.send(message);
	}

	@Override
	public void messageReceived(String message) {
		System.out.println(message);
	}

	@Override
	public void run() {
		while (true) {
			try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String line = reader.readLine();
			
			// Feature: Color
			if (feat_Color_Color(_client, line)) continue;			
			// Feature: Encryption			
			if (feat_Encryption_Encryption(_client, line)) continue;
			// Feature: UserName			
			if (feat_UserName_UserName(_client, line)) continue;
			
			ITextMessage msg = new TextMessage(line, _client.getName());
			
			// Feature: Color
			msg = feat_Color_getColoredMessage(msg);
			// Feature: Encryption
			msg = feat_Encryption_getEncryptedMessage(msg);
			
			sendMessage(msg);
			
			} catch (Exception ex){
			}
		}
	}
	
	private boolean feat_Encryption_Encryption(Client client, String line) { return false;	}	
	private boolean feat_Color_Color(Client client, String line) { return false; }
	private boolean feat_UserName_UserName(Client client, String line) { return false; }
	private ITextMessage feat_Encryption_getEncryptedMessage(ITextMessage msg)	{ return msg; }
	private ITextMessage feat_Color_getColoredMessage(ITextMessage msg)	{ return msg; }
}
