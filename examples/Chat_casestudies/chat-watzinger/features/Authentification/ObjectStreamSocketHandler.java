public class ObjectStreamSocketHandler extends SocketHandler {

	@Override
	public void send(Message msg) {

		if (authenticated) {
			original(msg);
		}
	}

}
