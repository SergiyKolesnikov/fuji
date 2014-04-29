package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import common.*;

/**
 * simple swing gui for the chat client
 */
public class Gui extends JFrame implements IUserInterface {

	private static final long serialVersionUID = 1L;

	protected JTextArea outputTextbox;
	protected JTextField inputField;

	private static int rowstextarea = 20;
	private static int colstextarea = 60;
	
	private Client chatClient;

	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param chatClient
	 *            chatClient that is used for sending and receiving messages
	 */
	public Gui(String title, Client chatClient) {
		System.out.println("starting gui...");

		outputTextbox = new JTextArea(Gui.rowstextarea, Gui.colstextarea);
		outputTextbox.setEditable(false);
		inputField = new JTextField();
		if(!chatClient.isConnected()){
			inputField.setEnabled(false);
		}
		inputField.addActionListener(getInput());
				
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outputTextbox)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    		// Feature: Color
                    		.addComponent(feat_Color_getColorChoiseComboBox())
                    		.addComponent(inputField)
                    		// Feature: Encryption
                    		.addComponent(feat_Encryption_getEncryptionChoiseComboBox())
                    		))
        );

        // layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

        layout.setVerticalGroup(layout.createSequentialGroup()
        			// Feature: Color
        			.addComponent(feat_Color_getColorChoiseComboBox())
        			// Feature: Encryption
        			.addComponent(feat_Encryption_getEncryptionChoiseComboBox())
        			.addGroup(layout.createSequentialGroup()
        					.addComponent(outputTextbox)
        					.addComponent(inputField))
        );
		
    	// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(this);

		setTitle(title);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.chatClient = chatClient;
	}
	
	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String text = (String) inputField.getText();
				//Feature: Username
				if (feat_UserName_UserName(chatClient, text)) {
					inputField.setText("");
					return;
				}
				
				
				ITextMessage msg = new TextMessage((String) inputField.getText(), chatClient.getName());
				
				// Feature: Color
				msg = feat_Color_getColoredMessage(msg);
				// Feature: Encryption
				msg = feat_Encryption_getEncryptedMessage(msg);
				
				sendMessage(msg);
				inputField.setText("");
			}
		};
	}



	@Override
	public void sendMessage(ITextMessage message) {
		chatClient.send(message);
	}

	@Override
	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void messageReceived(String message) {
		outputTextbox.append(message);
		if (!message.endsWith("\n"));
			outputTextbox.append("\n");
	}
	
	private boolean feat_UserName_UserName(Client client, String text) { return false; }
	
	private JComboBox feat_Color_getColorChoiseComboBox() { return null; }
	private JComboBox feat_Encryption_getEncryptionChoiseComboBox() { return null; }
	
	private ITextMessage feat_Color_getColoredMessage(ITextMessage msg) { return msg; }
	private ITextMessage feat_Encryption_getEncryptedMessage(ITextMessage msg) { return msg; }
}
