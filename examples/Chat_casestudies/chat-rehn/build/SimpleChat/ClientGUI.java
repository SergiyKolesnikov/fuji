
package SimpleChat;

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



public class ClientGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2565422293410796014L;

	private JTextArea messages;
	private JTextField sendMessage;
	private JButton sendButton;
	
	private Client client;
	private boolean color;
	
	private final static String COLOR_PREFIX = "{color}";
	private final static String NOCOLOR_PREFIX = "{nocolor}";
	
	public ClientGUI() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		messages = new JTextArea();
		sendMessage = new JTextField();
		sendButton = new JButton("send");
		color = false;
		
		setLayout(new GridLayout(6,1));
		
		messages.setEditable(false);
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = sendMessage.getText();
				if (color)
					msg = COLOR_PREFIX + msg;
				else
					msg = NOCOLOR_PREFIX + msg;
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
		
		String clientName = "b" + Math.random();
		//log = new Logger("client-" + clientName);
		client = new Client();
		client.authenticate(clientName, ServerAuthentication.REQ_PASSWORD);
		
		new Thread() {
			public void run() {
				while (true) {
					synchronized(client) {
						try {
							String newMessage = client.getMessage();
							if (newMessage == null) 
								newMessage = "";
							
							if (newMessage.contains(COLOR_PREFIX))
								newMessage = "(colored) " + newMessage.replace(COLOR_PREFIX, "");
							else if (newMessage.contains(NOCOLOR_PREFIX))
								newMessage = newMessage.replace(NOCOLOR_PREFIX, "");
							
							//if (newMessage.length() > 0)
							//	log.log(newMessage);
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
	
	public void initGUI() {
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