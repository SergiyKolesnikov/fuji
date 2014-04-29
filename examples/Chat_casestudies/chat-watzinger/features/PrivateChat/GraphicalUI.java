public class GraphicalUI {

	private void processCommand(String[] tokens) {
		if (tokens[0].equals("\\msg") && tokens.length > 2) {
			StringBuilder m = new StringBuilder();

			for (int i = 2; i < tokens.length; i++)
				m.append(tokens[i] + " ");

			client.send(new PrivateMessage(getUsername(), tokens[1], m
					.toString()));
		} else {
			original(tokens);
		}
	}

}