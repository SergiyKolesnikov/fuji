
package ColorLog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;



abstract class ClientGUI$$Chat extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2565422293410796014L;

	private JTextArea messages;
	private JTextField sendMessage;
	private JButton sendButton;
	
	protected Client client;
	
	protected final String clientName = "b" + Math.random();
	
	public ClientGUI$$Chat() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		messages = new JTextArea();
		sendMessage = new JTextField();
		sendButton = new JButton("send");
		
		client = new Client();
		
		setLayout(new GridLayout(6,1));
		
		messages.setEditable(false);
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = sendMessage.getText();
				msg = preMessageSent(msg);
				
				synchronized(client) {
					try {
						client.sendMessage(msg);
						sendMessage.setText("");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		add(messages);
		add(sendMessage);
		add(sendButton);
		
		initGUI();	
		
		
		pack();
		setVisible(true);
		setSize(300, 700);
		
		
		new Thread() {
			public void run() {
				while (true) {
					synchronized(client) {
						try {
							String newMessage = client.getMessage();
							if (newMessage == null) 
								newMessage = "";
							
							newMessage = postMessageReceived(newMessage);
							messages.setText(newMessage + messages.getText());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
				}
			}
		}.start();
	}
	
	public String preMessageSent(String msg) {
		return msg;
	}
	
	public String postMessageReceived(String newMessage) {
		return newMessage;
	}
	
	public void initGUI() throws Exception {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new ClientGUI();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}



abstract class ClientGUI$$Color extends  ClientGUI$$Chat  {
	private JCheckBox colorBox;
	private boolean color = false;
	
	private final static String COLOR_PREFIX = "{color}";
	private final static String NOCOLOR_PREFIX = "{nocolor}";
	
	public void initGUI() throws Exception {
		super.initGUI();
		colorBox = new JCheckBox("Color");
		colorBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				color = colorBox.isSelected();
			}
		});
		
		add(colorBox);
	}
	
	public String postMessageReceived(String newMessage) {
		String msg = super.postMessageReceived(newMessage);
		
		if (msg.contains(COLOR_PREFIX))
			msg = "(colored) " + msg.replace(COLOR_PREFIX, "");
		else if (msg.contains(NOCOLOR_PREFIX))
			msg = msg.replace(NOCOLOR_PREFIX, "");
		
		return msg;
	}
	
	public String preMessageSent(String msg) {
		msg = super.preMessageSent(msg);
		if (color)
			msg = COLOR_PREFIX + msg;
		else
			msg = NOCOLOR_PREFIX + msg;
		return msg;
	}
      // inherited constructors


	
	public ClientGUI$$Color (  )  throws Exception{ super(); }
}



public class ClientGUI extends  ClientGUI$$Color  {
	private Logger log;
	
	public void initGUI() throws Exception {
		log = new Logger("client-" + clientName);
		super.initGUI();
	}
	
	public String postMessageReceived(String newMessage) {
		if (newMessage.length() > 0)
			try {
				log.log(newMessage);
			} catch(IOException e) {
				e.printStackTrace();
			}
		return super.postMessageReceived(newMessage);
	}
      // inherited constructors


	
	public ClientGUI (  )  throws Exception{ super(); }
}