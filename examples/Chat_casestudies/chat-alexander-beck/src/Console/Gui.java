
import java.lang.*;
import java.io.*;
/**
 * simple AWT gui for the chat client
 */
public class Gui extends Thread implements ChatLineListener {

	private static final long serialVersionUID = 1L;

	protected TextArea outputTextbox;

	protected TextField inputField;

	private Client chatClient;

	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param chatClient
	 *            chatClient that is used for sending and receiving messages
	 */
	public Gui(Client chatClient) {
		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(this);
		System.out.println("Geben Sie was ein");
		
		
		this.chatClient = chatClient;
	}
	public void run() {
		while(true){
		try{
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String strInput = input.readLine();
			chatClient.send(strInput);
		} catch (Exception ef) {}
		}
	}
	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void newChatLine(String line) {
		
		System.out.println(line);
	}

	
}
