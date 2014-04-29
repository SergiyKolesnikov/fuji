package client; 

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import javax.swing.JCheckBox; 
import javax.swing.JComboBox; 
import javax.swing.JFrame; 
import javax.swing.JTextField; 
import javax.swing.JTextArea; 
import javax.swing.GroupLayout; 
import javax.swing.GroupLayout.Group; 

import common.JoinMessage; 
import common.TextMessage; 

import common.RotationCryptoProvider; 
import common.XORCryptoProvider; 

public   class  GUI  extends JFrame  implements ChatListener {
	

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
	public GUI(String title, Client chatClient) {
		System.out.println("starting gui...");

		outputTextbox = new JTextArea(GUI.rowstextarea, GUI.colstextarea);
		outputTextbox.setEditable(false);
		inputField = new JTextField();
		inputField.addActionListener(getInput());
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		Group horizontalGroup = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		Group verticalGroup = layout.createSequentialGroup();
		
        
        horizontalGroup.addComponent(outputTextbox)
        				.addComponent(inputField);

        verticalGroup.addComponent(outputTextbox)
                     	.addComponent(inputField);
                     	
        addComponents(horizontalGroup, verticalGroup);
		
        layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(horizontalGroup));
		layout.setVerticalGroup(verticalGroup);
		pack();

		chatClient.addChatListener(this);

		setTitle(title);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.chatClient = chatClient;
		new ServerDialog(chatClient);
	}

		
	
	 private void  addComponents__wrappee__Base  (Group h, Group v) {
	}

	
	
	 private void  addComponents__wrappee__History  (Group h, Group v) {
		addComponents__wrappee__Base(h,v);
		colorComboBox = new JComboBox(new String[] { "black", "red", "green", "blue" });
		colorComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chatClient.setColor(colorComboBox.getSelectedItem().toString());
			}
		});
		h.addComponent(colorComboBox);
		v.addComponent(colorComboBox);
	}

	
	
	private void addComponents(Group h, Group v) {
		addComponents__wrappee__History(h,v);
		encryptCheckBox = new JCheckBox("Encrypt");
		encryptionComboBox = new JComboBox(new String[] { "XOR", "ROT13" });
		encryptionComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(encryptionComboBox.getSelectedItem().toString().equals("XOR"))
					chatClient.setCryptoProvider(new XORCryptoProvider("lala"));
				else
					chatClient.setCryptoProvider(new RotationCryptoProvider(13));
			}
		});
		h.addComponent(encryptCheckBox);
		v.addComponent(encryptCheckBox);
		h.addComponent(encryptionComboBox);
		v.addComponent(encryptionComboBox);
	}

	
	
	private ActionListener getInput  () {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(inputField.getText().startsWith("/")) {
					handleCommand(inputField.getText());
				} else {
					if(encryptCheckBox.isSelected())
						chatClient.sendEncrypted((String) inputField.getText());
					else
						chatClient.send((String) inputField.getText());
				}
				inputField.setText("");
			}
		};
	}

	
	
	private void output(String s) {
		outputTextbox.append(s);
	}

	

	public void handleLine(String from, String text) {
		output(from + ": " + text + "\n");
	}

	

	public void handleJoin(String username) {
		output(username + " joined\n");
	}

	
	
	 private void  handleCommand__wrappee__Encryption  (String command) {
	}

		
	 private void  handleCommand__wrappee__PrivateMessage  (String command) {
		handleCommand__wrappee__Encryption(command);
		if(command.toLowerCase().matches("/msg .+ .+")) {
			String[] a = command.split(" ", 3);
			chatClient.sendPrivate(a[1], a[2]);
		}
	}

		
	private void handleCommand(String command) {
		handleCommand__wrappee__PrivateMessage(command);
		if(command.toLowerCase().matches("/setusername .+")) {
			String[] a = command.split(" ", 2);
			chatClient.setUsername(a[1].replace(" ", ""));
		}
	}

		
	protected JComboBox colorComboBox;

	
	
	public void handleColoredMessage(String from, String text, String color) {
		output("[" + color + "] " + from + ": " + text + "\n");
	}

		
	protected JCheckBox encryptCheckBox;

	
	protected JComboBox encryptionComboBox;

	
	
	public void handlePrivateMessage(String from, String text) {
		output("Private message from " + from + ": " + text + "\n");
	}

	
	
	public void handleUsernameChange(String oldName, String newName) {
		output(oldName + " changed his name to " + newName + "\n");
	}


}
