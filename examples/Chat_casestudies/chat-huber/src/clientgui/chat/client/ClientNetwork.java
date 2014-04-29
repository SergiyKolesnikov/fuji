package chat.client; 

import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.net.UnknownHostException; 
import java.util.Observable; 

import chat.IClient; 
import chat.messages.AMessage; 

public  class  ClientNetwork  extends Observable  implements IClient {
	

	private Socket socket;

	
	private ObjectOutputStream out;

	
	private ObjectInputStream in;

	

	public ClientNetwork(String host, int port) throws UnknownHostException,
			IOException {
		connect(host, port);
		Thread t = new Thread(new Reader());
		t.start();
	}

	

	private void connect(String host, int port) throws UnknownHostException,
			IOException {
		this.socket = new Socket(host, port);
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
	}

	

	public void unicast(AMessage message) {
		try {
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	@Override
	public boolean getAuthenticated() {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public void setAuthenticated(boolean authenticated) {
		// TODO Auto-generated method stub

	}

	

	private  class  Reader  implements Runnable {
		
		public void run() {
			try {
				Object obj = null;
				try {
					while ((obj = in.readObject()) != null) {
						if (obj instanceof AMessage) {
							((AMessage) obj).setClient(ClientNetwork.this);
							setChanged();
							notifyObservers(obj);
						}
					}
				} catch (ClassNotFoundException e) {
					notifyObservers(new Exception("Input Stream got closed..."));
				}
			} catch (IOException e) {
			}
		}


	}


}
