public abstract class View implements Observer {

	private void init() {
		original();
		client.send(new AuthMessage(username, "foobar"));
	}
}
