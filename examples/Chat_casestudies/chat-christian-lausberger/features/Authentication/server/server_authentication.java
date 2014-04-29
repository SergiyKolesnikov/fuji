package server;

import java.io.IOException;


public class server_authentication implements INew_Client_Connection {

	public void on_connect(Connection c) {
		
		try {
		String buff = c.inputStream.readObject().toString();
		if (!buff.equalsIgnoreCase("1234")) {
			c.outputStream.writeObject("denied");
			return;
		}
			c.outputStream.writeObject("ack");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
