

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;

import java.awt.*;



/**
 * simple AWT gui for the chat client
 */
abstract class Gui$$Java_GUI extends Frame implements ChatLineListener {

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
	public Gui$$Java_GUI(String title, Client chatClient) {
		super(title);
		helpme(chatClient);
	}
	
	protected void helpme(Client chatClient) {
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



/**
 * simple AWT gui for the chat client
 */
abstract class Gui$$Color extends  Gui$$Java_GUI  {
	
	protected Choice colorChoice;
	
	public void helpme(Client chatClient) {
		super.helpme(chatClient);
		
		Label colorLabel = new Label("Farbe:");
		Panel eastpane = new Panel();
		GridLayout layout = new GridLayout(1,6);
		eastpane.setLayout(layout);
		
		((Gui) this).colorChoice = new Choice();
		((Gui) this).colorChoice.add("Blau");
		((Gui) this).colorChoice.add("Rot");
		((Gui) this).colorChoice.add("Gruen");
		((Gui) this).colorChoice.add("Fuchsia");
		
		eastpane.add(colorLabel);
		eastpane.add(((Gui) this).colorChoice);
		
		add("East", eastpane);
		
		pack();
		setVisible(true);
	}
		
	public boolean handleEvent(Event e) {
		if ((e.target == inputField) && (e.id == Event.ACTION_EVENT)) {
			String color = ((Gui) this).colorChoice.getSelectedItem();
			color = color.toLowerCase();
			chatClient.send("<" + color + "> " +(String) e.arg);
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
      // inherited constructors



	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param chatClient
	 *            chatClient that is used for sending and receiving messages
	 */
	public Gui$$Color ( String title, Client chatClient ) { super(title, chatClient); }
	
		
}



/**
 * simple AWT gui for the chat client
 */
public class Gui extends  Gui$$Color  {
	
	protected Choice colorChoice;
	
	public void helpme(Client chatClient) {
		super.helpme(chatClient);
		super.newChatLine("Enter 'password:<PASSWORD>' to get into the conversation\n");
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