import java.util.Iterator;

public class Server {

    public void unicast(String text, String userName) {

	synchronized (connections) {

	    for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
		Connection connection = (Connection) iterator.next();

		if ((connection.getUserName() != null)
			&& (connection.getUserName().equals(userName))) {
		    connection.send(text);
		}
	    }
	}
    }

    public void broadcast(String text) {
	String msgCmd = "/msg";

	if (text.matches(".*" + msgCmd + " [^\\s]+ .+")) {
	    String usrNameExt = text.substring(text.indexOf(msgCmd)
		    + msgCmd.length() + 1);
	    String userName = usrNameExt.substring(0, usrNameExt.indexOf(' '));
	    String message = text.replaceFirst(msgCmd + " [^\\s]+", "");
	    this.unicast(message, userName);
	} else {
	    original(text);
	}
    }
}
