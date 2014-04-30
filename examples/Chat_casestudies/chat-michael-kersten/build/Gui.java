

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;

import java.awt.*;



/**
 * simple AWT gui for the chat client
 */
abstract class Gui$$Console extends Frame implements ChatLineListener {

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
	public Gui$$Console(String title, Client chatClient) {
		System.out.println("Console loading ...");
		chatClient.addLineListener(this);

		pack();
		setVisible(false);
		
		this.chatClient = chatClient;
		
	}

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void newChatLine(String line) {
		
		
		System.out.print(line);
	}

	/**
	 * handles AWT events (enter in textfield and closing window)
	 */
	
}



public class Gui extends  Gui$$Console {
	
	protected Panel panColor;
	
	public CheckboxGroup cbgColor;

	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param chatClient
	 *            chatClient that is used for sending and receiving messages
	 */
	public Gui(String title, Client chatClient) { super(title, chatClient); 

		
		panColor=new Panel();
		panColor.setLayout(new GridLayout(3,1));
		
		add("East", panColor);
		
		cbgColor = new CheckboxGroup();
		
		
		panColor.add( new Checkbox("rot",cbgColor,true)); 
		panColor.add( new Checkbox("blau",cbgColor,false) );
		panColor.add( new Checkbox("schwarz",cbgColor,false) );
		pack(); }

	
	public boolean handleEvent(Event e) {
		if ((e.target == inputField) && (e.id == Event.ACTION_EVENT)) {
			
			TextMessage msg=new TextMessage((String) e.arg);
			msg.setColor("<"+cbgColor.getCurrent().getLabel().toString()+"> ");
			chatClient.send(msg);
			
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


	
	
}