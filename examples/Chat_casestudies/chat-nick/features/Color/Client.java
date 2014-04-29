public class Client {
	private void printTextMessage(TextMessage msg) {
		fireAddLine(msg.getContentWithColor() + "\n");
	}
}