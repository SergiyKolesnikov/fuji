import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;

/**
 * simple AWT gui for the chat client
 */
public class Gui extends Frame implements ChatLineListener {

	private static final long serialVersionUID = 1L;

	protected TextArea outputTextbox;
	protected TextField inputField;

	private ClientConnection clientConnection;

	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param clientConnection
	 *            chatClient that is used for sending and receiving messages
	 */
	public Gui(String title, ClientConnection clientConnection) {
		super(title);
		
		System.out.println("starting gui...");
		setLayout(new BorderLayout());
		
		createContents();
		
		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		clientConnection.addLineListener(this);

		pack();
		setVisible(true);
		inputField.requestFocus();

		this.clientConnection = clientConnection;
	}
	
	private void createContents() {
		outputTextbox = new TextArea();
		add("Center", outputTextbox);
		outputTextbox.setEditable(false);

		inputField = new TextField();
		add("South", inputField);
	}

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void newChatLine(TextMessage line) {
		outputTextbox.append(line.getContent() + "\n");
	}

	/**
	 * handles AWT events (enter in textfield and closing window)
	 */
	public boolean handleEvent(Event e) {
		if (e.target == inputField && e.id == Event.ACTION_EVENT) {
			clientConnection.send(MessageFactory.newTextMessage((String) e.arg));
			inputField.setText("");
			return true;
		}
		else if ((e.target == this) && (e.id == Event.WINDOW_DESTROY)) {
			if (clientConnection != null)
				clientConnection.close();
			setVisible(false);
			System.exit(0);
			return true;
		}
		
		return super.handleEvent(e);
	}
}
