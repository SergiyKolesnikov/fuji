public class GraphicalUI {

	private void processCommand(String[] tokens) {
		if (tokens[0].equals("\\username") && tokens.length == 2) {
			setUsername(tokens[1]);
		} else {
			original(tokens);
		}
	}

}