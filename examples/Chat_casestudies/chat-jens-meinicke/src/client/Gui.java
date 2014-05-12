package client;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Choice;



/**
 * simple AWT gui for the chat client
 */
abstract class Gui$$GUI$client extends Frame implements ChatLineListener {

	private static final long serialVersionUID = 1L;

	protected TextArea outputTextbox;

	protected TextField inputField;

	protected Client chatClient;

	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param chatClient
	 *            chatClient that is used for sending and receiving messages
	 */
	public Gui$$GUI$client(String title, Client chatClient) {
		super(title);
		init(chatClient);
	}
	
	protected void init(Client chatClient) {
		System.out.println("starting gui...");
		setLayout(new BorderLayout());
		outputTextbox = new TextArea();
		add("Center", outputTextbox);
		outputTextbox.setEditable(false);
		inputField = new TextField();
		add("South", inputField);

		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(((Gui) this));

		pack();
		setVisible(true);
		inputField.requestFocus();

		((Gui) this).chatClient = chatClient;
	}

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void newChatLine(String line) {
		outputTextbox.append(line);
	}

	/**
	 * handles AWT events (enter in textfield and closing window)
	 */
	public boolean handleEvent(Event e) {
		if ((e.target == inputField) && (e.id == Event.ACTION_EVENT)) {
			chatClient.send((String) e.arg);
			inputField.setText("");
			return true;
		} else if ((e.target == ((Gui) this)) && (e.id == Event.WINDOW_DESTROY)) {
			if (chatClient != null)
				chatClient.stop();
			setVisible(false);
			System.exit(0);
			return true;
		}
		return super.handleEvent(e);
	}
}



public class Gui extends  Gui$$GUI$client  {
	private static final long serialVersionUID = 1L;
	
	public  Choice choice;
	
	protected void init(Client chatClient) {
		super.init(chatClient);
		choice = new Choice();
		choice.add("Black");
		choice.add("Gray");
		choice.add("Red");
		choice.add("Blue");
		choice.add("Green");
		choice.add("Yellow");
		add("East",choice);
	}
	
	public boolean handleEvent(Event e) {
		e.arg = "[" + choice.getSelectedItem() + "] " + (String)e.arg;
		return super.handleEvent(e);	
	}
      // inherited constructors



	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param chatClient
	 *            chatClient that is used for sending and receiving messages
	 */
	public Gui ( String title, Client chatClient ) { super(title, chatClient); }
	
}