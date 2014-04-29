package swpl.chat.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

import swpl.chat.client.ChatLineListener;
import swpl.chat.client.Client;
import swpl.chat.common.TextMessage;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.text.StyleConstants;



abstract class ClientWindow$$Gui$swpl$chat$client implements ChatLineListener {

	private Client chatClient;

	protected JFrame frame;
	private JTextPane tPLog;
	private JTextField tFMessage;

	/**
	 * Create the application.
	 */
	public ClientWindow$$Gui$swpl$chat$client(Client chatClient) {
		initialize();

		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(this);
		this.chatClient = chatClient;
	}

	public void show() {
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (chatClient != null) {
					chatClient.stop();
				}
			}
		});
		frame.setBounds(100, 100, 450, 254);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), }, new RowSpec[] {
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		tPLog = new JTextPane();
		tPLog.setEditable(false);
		frame.getContentPane().add(tPLog, "1, 1, 3, 1, fill, fill");
		
		tFMessage = new JTextField();
		tFMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TextMessage textMsg = getTextMessage(tFMessage.getText()); 
				chatClient.send(textMsg);
				tFMessage.setText("");
			}
		});
		frame.getContentPane().add(tFMessage, "1, 5, 3, 1, fill, default");
		tFMessage.setColumns(10);
	}

	public void newChatLine(TextMessage textMsg) {
		try {
			showMessage(tPLog.getDocument(), textMsg);
		} catch (BadLocationException e) {
		}
	}
	
	public TextMessage getTextMessage(String text) {
		return new TextMessage(text);
	}

	public void showMessage(Document textBox, TextMessage textMsg) throws BadLocationException {
		textBox.insertString(textBox.getLength(), textMsg.getContent() + "\n", 
					new SimpleAttributeSet());
	}
	
}



public class ClientWindow extends  ClientWindow$$Gui$swpl$chat$client  {

	private JComboBox cBColors;

	protected void initialize() {
		super.initialize();

		JLabel lblColor = new JLabel("Color");
		frame.getContentPane().add(lblColor, "1, 3, right, default");

		cBColors = new JComboBox();
		frame.getContentPane().add(cBColors, "3, 3, left, default");

		// fill colors combobox
		ColorComboBoxRenderer renderer = new ColorComboBoxRenderer();
		renderer.setPreferredSize(new Dimension(20, 20));
		cBColors.setRenderer(renderer);

		cBColors.addItem(Color.BLACK);
		cBColors.addItem(Color.BLUE);
		cBColors.addItem(Color.RED);
		cBColors.addItem(Color.YELLOW);
		cBColors.setSelectedIndex(0);
	}

	public TextMessage getTextMessage(String text) {
		return new ColorTextMessage(text, (Color) cBColors.getSelectedItem());
	}

	public void showMessage(Document textBox, TextMessage textMsg) throws BadLocationException {
		SimpleAttributeSet attributes = new SimpleAttributeSet();
		
		if (textMsg instanceof ColorTextMessage) {
			ColorTextMessage colorMsg = (ColorTextMessage) textMsg;
		    attributes.addAttribute(StyleConstants.CharacterConstants.Foreground, colorMsg.getColor());
		}
		
	    textBox.insertString(textBox.getLength(), textMsg.getContent() + "\n", attributes);
	}
      // inherited constructors



	/**
	 * Create the application.
	 */
	public ClientWindow ( Client chatClient ) { super(chatClient); }
}