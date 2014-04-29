public class Client {

    public void send(String line, String color) {
	TextMessage message = new TextMessage(line);
	message.setColor(color);
	this.send(message);
    }
}
