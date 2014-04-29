/**
 * TODO description
 */
public class Client {

	public void send(Message msg) {
		msg.addProperty("password", Authentication.PASSWORD);
		original(msg);
	}
	
	

}