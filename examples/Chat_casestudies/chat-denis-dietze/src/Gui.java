
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import javax.swing.JComboBox;



/**
 * simple AWT gui for the chat client
 */
abstract class Gui$$Base extends Frame implements ChatLineListener {

	private static final long serialVersionUID = 1L;

	protected TextArea outputTextbox;

	protected TextField inputField;
	
	protected String[] strColor = {"schwarz", "rot", "blau", "gruen", "gelb"};
	
	protected JComboBox comboColor;

	protected Client chatClient;

	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param chatClient
	 *            chatClient that is used for sending and receiving messages
	 */
	public Gui$$Base(String title, Client chatClient) {
		super(title);
		System.out.println("starting gui...");
		setLayout(new BorderLayout());
		outputTextbox = new TextArea();
		add("Center", outputTextbox);
		outputTextbox.setEditable(false);
		inputField = new TextField();
		add("South", inputField);
		
		
		addColor();

		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(this);

		pack();
		setVisible(true);
		inputField.requestFocus();

		this.chatClient = chatClient;
		authOut();
	}
	public void authOut() {}

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
	 public String getMsg(String txt) {
	 	return txt;
	 }
	public boolean handleEvent(Event e) {
		if ((e.target == inputField) && (e.id == Event.ACTION_EVENT)) {
			chatClient.send(getMsg((String) e.arg));
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
	
	public void addColor() {}
}

abstract class Gui$$Authorization extends  Gui$$Base  {
	public void authOut() {
		super.chatClient.send("passwort junge");
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
	public Gui$$Authorization ( String title, Client chatClient ) { super(title, chatClient); }
}

abstract class Gui$$Color extends  Gui$$Authorization  {
	public void addColor() {
		super.addColor();
		comboColor = new JComboBox(strColor);
		comboColor.setSelectedIndex(0);
		add("East",comboColor);
		pack();
		setVisible(true);
		inputField.requestFocus();
	}
	
	public String getMsg(String txt) {
		txt = "(" + super.comboColor.getSelectedItem() + ")" + txt;
		return txt;
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

public class Gui extends  Gui$$Color  {
	public void newChatLine(String line) {
		System.out.print(line);
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