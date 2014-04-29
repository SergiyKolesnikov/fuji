package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import common.TextMessage;
import common.Log;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JProgressBar;
import javax.swing.JToolBar;
import java.awt.Toolkit;




abstract class GUI$$Base$client implements ChatLineListener {

	private static final String NEW_LINE = "\n";
	JFrame frame;
	protected JTextField txtMessage;
	protected DefaultStyledDocument chatDoc;
	protected Style style;
	private JEditorPane dtrpnChat;
	protected Client chatClient;
	public JLabel lblNichtVerbunden;
	public JMenuItem mntmVerbinden;

	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public GUI$$Base$client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setBounds(100, 100, 608, 416);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnChat = new JMenu("Chat");
		menuBar.add(mnChat);
		
		JMenuItem mntmEinstellungen = new JMenuItem("Einstellungen");
		mntmEinstellungen.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				Settings.main(null);
			}
		});
		
		mntmVerbinden = new JMenuItem("Verbinden");
		mntmVerbinden.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (mntmVerbinden.getText().equals("Verbinden")) {
					initClient();
				} else {
					chatClient.stop();
					mntmVerbinden.setText("Verbinden");
					lblNichtVerbunden.setText("Nicht Verbunden");
				}
			}
		});
		mnChat.add(mntmVerbinden);
		mnChat.add(mntmEinstellungen);
		
		JMenuItem mntmSchliessen = new JMenuItem("Schliessen");
		mntmSchliessen.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				System.exit(0);
			}
		});
		mnChat.add(mntmSchliessen);
		frame.getContentPane().setLayout(null);
		
		txtMessage = new JTextField();
		txtMessage.setText("Message");
		txtMessage.setBounds(30, 312, 356, 28);
		frame.getContentPane().add(txtMessage);
		txtMessage.setColumns(10);
		txtMessage.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				int key = arg0.getKeyCode();
		        if (key == KeyEvent.VK_ENTER) {
		           sendMessage();
		        }
			}
		});
		
		JButton btnSenden = new JButton("Senden");
		btnSenden.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				sendMessage();
			}
		});
		btnSenden.setBounds(453, 313, 117, 29);
		frame.getContentPane().add(btnSenden);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(30, 17, 539, 284);
		frame.getContentPane().add(scrollPane);
		
		dtrpnChat = new JTextPane();
		dtrpnChat.setEditable(false);
		dtrpnChat.setBackground(Color.WHITE);

		StyleContext sContext = StyleContext.getDefaultStyleContext();
		Style defaultStyle = sContext.getStyle(StyleContext.DEFAULT_STYLE);
		Style sendStyle = sContext.addStyle("send", defaultStyle);
		Style receiveStyle = sContext.addStyle("receive", defaultStyle);
		Style systemStyle = sContext.addStyle("system", defaultStyle);

		StyleConstants.setFontSize(sendStyle, 12);
		StyleConstants.setForeground(sendStyle, Color.BLACK);
		StyleConstants.setFontSize(receiveStyle, 12);
		StyleConstants.setForeground(receiveStyle, Color.BLACK);
		StyleConstants.setFontSize(systemStyle, 12);
		StyleConstants.setBold(systemStyle, true);
		StyleConstants.setForeground(systemStyle, Color.BLACK);
		chatDoc = new DefaultStyledDocument(sContext);
		dtrpnChat.setDocument(chatDoc);
		
		style = chatDoc.getStyle("send");

		try {
			chatDoc.insertString(chatDoc.getLength(),"Chat started: " +
					Calendar.getInstance().getTime() + NEW_LINE, systemStyle);
			dtrpnChat.setCaretPosition(chatDoc.getLength());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		scrollPane.setViewportView(dtrpnChat);
		
		lblNichtVerbunden = new JLabel("Nicht Verbunden");
		lblNichtVerbunden.setBounds(0, 356, 126, 16);
		frame.getContentPane().add(lblNichtVerbunden);
	}
	
	protected void initClient() {
		Preferences prefs = Preferences.userNodeForPackage(Settings.class);
		chatClient = new Client(prefs.get("host", "127.0.0.1"), Integer.parseInt(prefs.get("port", "8080")));
		
		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(((GUI) this));
		lblNichtVerbunden.setText("Verbunden");
		mntmVerbinden.setText("Verbindung Trennen");
	}
	
	protected void sendMessage() {
		try {
			chatClient.send(Settings.nickname + Settings.trennzeichen + txtMessage.getText());
			dtrpnChat.setCaretPosition(chatDoc.getLength());
			txtMessage.setText("");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void newChatLine(String msg) {
		try {
			chatDoc.insertString(chatDoc.getLength(),msg + NEW_LINE, style);
			dtrpnChat.setCaretPosition(chatDoc.getLength());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}



 abstract class GUI$$COLOR$client extends  GUI$$Base$client  {
	
	public void newChatLine(String msg) {
		Preferences prefs = Preferences.userNodeForPackage(Settings.class);
		StyleConstants.setForeground(chatDoc.getStyle("receive"), Color.BLACK);
		StyleConstants.setForeground(chatDoc.getStyle("send"), Color.YELLOW);
	
		if (msg.startsWith(Settings.nickname)) {
			style = chatDoc.getStyle("send");
		} else {
			style = chatDoc.getStyle("receive");
		}
		super.newChatLine(msg);
	}
      // inherited constructors



	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public GUI$$COLOR$client (  ) { super(); }
}

abstract class GUI$$AUTH$client extends  GUI$$COLOR$client  {
	
	protected void initClient() {
		Preferences prefs = Preferences.userNodeForPackage(Settings.class);
		chatClient = new Client(prefs.get("host", "127.0.0.1"), Integer.parseInt(prefs.get("port", "8080")));

		chatClient.send(new TextMessage("1234567"));
		
		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(((GUI) this));
		lblNichtVerbunden.setText("Verbunden");
		mntmVerbinden.setText("Verbindung Trennen");
	}
      // inherited constructors



	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public GUI$$AUTH$client (  ) { super(); }

}

abstract class GUI$$LOG$client extends  GUI$$AUTH$client  {
	
	public void newChatLine(String msg) {
		super.newChatLine(msg);
		Log.info(msg);
	}
      // inherited constructors



	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public GUI$$LOG$client (  ) { super(); }

}

abstract class GUI$$SPAM$client extends  GUI$$LOG$client  {
	
	protected void sendMessage() {
		if (!txtMessage.getText().contains("scheisse") || !txtMessage.getText().contains("penner")) {
				super.sendMessage();
		}
	}
      // inherited constructors



	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public GUI$$SPAM$client (  ) { super(); }
	
}

public class GUI extends  GUI$$SPAM$client  {

	public void newChatLine(String msg) {
		super.newChatLine(msg);
		Toolkit.getDefaultToolkit().beep();
	}
      // inherited constructors



	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public GUI (  ) { super(); }

}