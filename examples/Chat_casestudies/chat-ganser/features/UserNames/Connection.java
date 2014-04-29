public class Connection {

    private String userName;

    public String getUserName() {
	return this.userName;
    }

    private void handleIncomingMessage(String name, Object msg) {

	if (msg instanceof TextMessage) {
	    String text = ((TextMessage) msg).getContent().trim();
	    String setUserCmd = "/username";

	    if (text.matches(".*" + setUserCmd + " .+")) {
		text = text.substring(text.indexOf(setUserCmd)
			+ setUserCmd.length() + 1);
		int spaceIndex = text.indexOf(' ');
		
		if (spaceIndex > 0) {
		    this.userName = text.substring(0, spaceIndex);
		} else {
		    this.userName = text;
		}
		this.send("OK. User name is " + this.userName);
		return;
	    }
	}
	original(name, msg);
    }
}
