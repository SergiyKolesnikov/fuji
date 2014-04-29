
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;



/**
 * simple AWT gui for the chat client
 */
abstract class Gui$$Base extends Frame implements ChatLineListener {

	private static final long serialVersionUID = 1L;

	protected TextArea outputTextbox;

	protected TextField inputField;
	
	private Client chatClient;

	String color = "Black";
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
        
		
		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(this);
		addColor();

		pack();
		setVisible(true);
		inputField.requestFocus();

		this.chatClient = chatClient;
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
			//System.out.println("This ist gui"+(String) e.arg + " (" + color + ")");
			chatClient.send((String) e.arg + " ( " + color + " )");
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
	
	//=====================================================================
	public void addColor(){}
	
}



public class Gui extends  Gui$$Base    {
 	
 	protected JComboBox colorBox;
 	
 	
	public void addColor(){
		
		colorBox = new JComboBox();
        colorBox.addItem("black");
        colorBox.addItem("red");
        colorBox.addItem("yellow");
        colorBox.addItem("blue");
		add("North", colorBox);	
		
		
		colorBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

               color = colorBox.getSelectedItem().toString();
            }
        });
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