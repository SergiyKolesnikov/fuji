package client; 

import java.io.IOException; 

public  class  client_authentication  implements IOn_Server_Connect {
	

	public void onConnect(Client client) {
		try {

			client.outputStream.writeObject("1234");
			String buf = client.inputStream.readObject().toString();
			if (!buf.equalsIgnoreCase("ack")) {
				client.manager
						.newChatLine("Wrong Pass! Connection Refused....\n");
				client.setAccess(false);
			} else
				client.setAccess(true);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
