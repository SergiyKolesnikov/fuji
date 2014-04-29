
import java.awt.Event;



public class Console implements ChatLineListener {

	
		private static final long serialVersionUID = 1L;
		private Client chatClient;
		String color = "Black";
		
		public Console(String title, Client chatClient) {
			System.out.println("starting console...");

			// register listener so that we are informed whenever a new chat message
			// is received (observer pattern)
			chatClient.addLineListener(this);

			this.chatClient = chatClient;			
		}

		/**
		 * this method gets called every time a new message is received (observer
		 * pattern)
		 */
		public void newChatLine(String line) {
			System.out.println(line);
		}
		
		public boolean handleEvent() {
			
			chatClient.send("halloWelt" + " (" + color + ")");
			return true;
		}
}