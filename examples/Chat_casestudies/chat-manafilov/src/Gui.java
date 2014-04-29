
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.Toolkit;



/**
 * simple AWT gui for the chat client
 */
abstract class Gui$$Base extends Frame implements ChatLineListener, ActionListener {

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
	public Gui$$Base(String title, Client chatClient) {
		super(title);
		this.chatClient = chatClient;
		this.startGui();
		
	}
	
	public void startGui(){
		
		System.out.println("starting gui...");
		setLayout(new BorderLayout());
		outputTextbox = new TextArea();
		add(outputTextbox, BorderLayout.PAGE_START);
		outputTextbox.setEditable(false);
		inputField = new TextField();
		add(inputField, BorderLayout.PAGE_END);

		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(((Gui) this));

		pack();
		setVisible(true);
		inputField.requestFocus();
		
	
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
	
	public void actionPerformed(ActionEvent e) {
	}
}




abstract class Gui$$Colors extends  Gui$$Base  {
	
	protected Button b1, b2, b3, b4;
	
	public void startGui(){
	
		System.out.println("starting gui...");
		setLayout(new BorderLayout());
		outputTextbox = new TextArea();
		add(outputTextbox, BorderLayout.PAGE_START);
		outputTextbox.setEditable(false);

        final Panel buttons = new Panel();
        buttons.setLayout(new FlowLayout());
        add(buttons, BorderLayout.CENTER);
        b1 = new Button("Blue");
        buttons.add(b1);
		b1.addActionListener(((Gui) this));
		b2 = new Button("Red");
		buttons.add(b2);
		b2.addActionListener(((Gui) this));;
		b3 = new Button("Green");
		buttons.add(b3);
		b3.addActionListener(((Gui) this));
		b4 = new Button("Farbe aus");
		buttons.add(b4);
		b4.addActionListener(((Gui) this));

		inputField = new TextField();
		add(inputField, BorderLayout.PAGE_END);
		
		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(((Gui) this));

		pack();
		setVisible(true);
		inputField.requestFocus();
	}
	
	public void actionPerformed(ActionEvent e) {
        // Get label of the button clicked in event passed in
        String arg = e.getActionCommand();    
        
        if (arg.equals("Blue")){
        	chatClient.color = "Blue";
        }else{
        	if(arg.equals("Red")){
        		chatClient.color = "Red";
        	}else{
        	if(arg.equals("Green")){
        			chatClient.color = "Green";
        		}else{
        			chatClient.color = "";
        		}
        	}
        }		
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
	public Gui$$Colors ( String title, Client chatClient ) { super(title, chatClient); }	

}



public class Gui extends  Gui$$Colors  {
	
	public void newChatLine(String line) {
		outputTextbox.append(line);
		//Toolkit.getDefaultToolkit().beep(); // Deaktiviert,da auf ein PC
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