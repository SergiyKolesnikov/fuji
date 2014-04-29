public class ChatClient extends Observable implements Runnable {
	private History history;

	private void initHistory() {
		try {
			this.history = new History(new File(System.currentTimeMillis()+"_client.log"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processMessage(Object o) {
		if (o instanceof Message) {
			history.append((Message)o);
		}

		original(o);
	}

}
