
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;



abstract class Gui$$Gui extends JFrame implements ActionListener, ChatLineListener {
	
	/** Scoll-Pane */
	protected JScrollPane scrollPane;
	
	/** Text-Pane */
	protected JTextPane outputPane;
	
	/** Eingabe-Feld */
	protected JTextField inputField;
	
	/** Panel */
	protected JPanel panel;
	
	/** Der Chat-Client */
	protected Client chatClient;
	
	/**
	 * Erzeugt das Layout.
	 * @param title der Fenster-Titel
	 * @param chatClient der Chat-Client zum Senden und Empfangen von Nachrichten.
	 */
	public Gui$$Gui(String title, final Client chatClient) {
		super(title);
		this.chatClient = chatClient;
		setLayout(new BorderLayout());
		initComponents();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (chatClient != null)
					chatClient.stop();
				setVisible(false);
				System.exit(0);
			}
		});
		this.chatClient.addLineListener(this);
		setSize(400, 300);
		setVisible(true);
	}
	
	protected void initComponents() {
		outputPane = new JTextPane();
		outputPane.setEditable(false);
		
		scrollPane = new JScrollPane(outputPane);
		add(BorderLayout.CENTER, scrollPane);
		
		panel = new JPanel(new BorderLayout());
		inputField = new JTextField();
		inputField.addActionListener(((Gui) this));
		init();
		panel.add(BorderLayout.CENTER, inputField);
		add(BorderLayout.SOUTH, panel);
	}	
	
	protected void init() {}	

	/**
	 * Observer-Pattern: Methode wird jedesmal aufgerufen, falls eine neue
	 * Nachricht empfangen wird und die Nachricht wird auf dem Text-Pane
	 * dargestellt.
	 */
	public void newChatLine(TextMessage msg) {
		append(msg.toString() + '\n');
	}

	/**
	 * Fuegt dem Text-Pane eine neue Zeile an.
	 * @param eine beliebige Zeichenkette
	 */
	private void append(String line) {
		Document doc = outputPane.getDocument();
		int length = doc.getLength();
		try {
			doc.insertString(length, line, null);
		} catch(BadLocationException e) {
			System.err.println(e.getMessage());
		}		
	}
	
	protected void process(TextMessage msg) {}	
	
	public void actionPerformed(ActionEvent e) {
		String content = inputField.getText();
		TextMessage msg = new TextMessage(content, "localhost");	  
		chatClient.send(msg);
		inputField.setText("");
	}	
}



public class Gui extends  Gui$$Gui  {
	
	/** Ein einfacher Color-Chooser **/
	protected JComboBox colorChooser;

	protected void init() {
	    String[] items = { "RED", "GREEN", "BLUE", "YELLOW" };
		colorChooser = new JComboBox(items);
		panel.add(BorderLayout.WEST, colorChooser);
		super.init();
	}	
	
	
	protected void process(TextMessage msg) {
		Color[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW };
		int i = colorChooser.getSelectedIndex();
		msg.setColor(colors[i]);
		super.process(msg);
	}
	
	public void newChatLine(TextMessage msg) {	
		SimpleAttributeSet attributes = new SimpleAttributeSet();
		StyleConstants.setForeground(attributes, Color.RED);
		Document doc = outputPane.getDocument();
		int length = doc.getLength();
		try {
			doc.insertString(length, msg.toString() + '\n', attributes);
		} catch(BadLocationException e) {
			System.err.println(e.getMessage());
		}		
	}
      // inherited constructors


	
	/**
	 * Erzeugt das Layout.
	 * @param title der Fenster-Titel
	 * @param chatClient der Chat-Client zum Senden und Empfangen von Nachrichten.
	 */
	public Gui ( String title, final Client chatClient ) { super(title, chatClient); }
}