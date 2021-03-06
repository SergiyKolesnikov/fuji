
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;



abstract class ChatFrame$$gui extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1928852249923310791L;
	private JPanel contentPane;
	private ChatClient chatClient;
	private String recipient;
	protected Color selectedColor;
	
	private JTextPane textPaneConversation;
	protected JTextArea textAreaUser;
	
	protected JMenu mnText;
	protected JMenuBar menuBar;

	/**
	 * Create the frame.
	 */
	public ChatFrame$$gui(String recipient, ChatClient chatClient) {
		this.recipient	= recipient;
		this.chatClient= chatClient;
		this.selectedColor= Color.BLACK;
		setTitle(recipient+" - joChat");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 550, 350);
		
		init();
	}
	
	protected void init() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnText = new JMenu("Text");
		menuBar.add(mnText);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane);
		
		JPanel UserPanel = new JPanel();
		splitPane.setRightComponent(UserPanel);
		UserPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlUserAvatar = new JPanel();
		UserPanel.add(pnlUserAvatar, BorderLayout.WEST);
		pnlUserAvatar.setPreferredSize(new Dimension(100, 75));
		pnlUserAvatar.setLayout(new BorderLayout(0, 0));
		
		JLabel lblUsername = new JLabel("");
		pnlUserAvatar.add(lblUsername, BorderLayout.NORTH);
		
		textAreaUser = new JTextArea();
		UserPanel.add(textAreaUser, BorderLayout.CENTER);
		textAreaUser.setLineWrap(true);
		textAreaUser.addKeyListener(((ChatFrame) this));
		
		JPanel ConversationPanel = new JPanel();
		splitPane.setLeftComponent(ConversationPanel);
		ConversationPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlFriendAvatar = new JPanel();
		ConversationPanel.add(pnlFriendAvatar, BorderLayout.WEST);
		pnlFriendAvatar.setPreferredSize(new Dimension(100, 175));
		pnlFriendAvatar.setLayout(new BorderLayout(0, 0));
		
		JLabel lblFriendname = new JLabel(recipient);
		pnlFriendAvatar.add(lblFriendname, BorderLayout.NORTH);
		
		textPaneConversation = new JTextPane();
		ConversationPanel.add(textPaneConversation, BorderLayout.CENTER);
		textPaneConversation.setAutoscrolls(true);
		textPaneConversation.setEditable(false);
	}

	public void appendConversationWithColor(String s, Color c) {
		if (c==null) {
			c= Color.BLACK;
		}
		AbstractDocument doc; 
		SimpleAttributeSet attr = new SimpleAttributeSet();
		StyleConstants.setForeground(attr, c);
		try {
			doc = (AbstractDocument) textPaneConversation.getStyledDocument();
			doc.insertString(doc.getLength(), "\n"+s, attr);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public String getRecipient() {
		return recipient;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==10) {
			String userText= textAreaUser.getText();
			textAreaUser.setText("");
			Date currentDate= new Date(System.currentTimeMillis());
			String timestamp= currentDate.toString().substring(11, 19);
			appendConversationWithColor(chatClient.getUsername()+"["+timestamp+"]: "+userText, selectedColor);
			chatClient.sendMessage(new MessageObject(MessageType.TEXT, null, new String[]{recipient} , selectedColor, userText));
			e.consume();
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}





public class ChatFrame extends  ChatFrame$$gui  {
	
	protected void init() {
		super.init();
		JMenuItem mntmSetColor = new JMenuItem("set color");
		mntmSetColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color returnedColor= JColorChooser.showDialog(null, "choose text color", selectedColor);
				if (returnedColor!=null) {
					selectedColor = returnedColor;
					textAreaUser.setForeground(selectedColor);
				}
			}
		});
		mnText.add(mntmSetColor);
	}
      // inherited constructors



	/**
	 * Create the frame.
	 */
	public ChatFrame ( String recipient, ChatClient chatClient ) { super(recipient, chatClient); }
	
	
}