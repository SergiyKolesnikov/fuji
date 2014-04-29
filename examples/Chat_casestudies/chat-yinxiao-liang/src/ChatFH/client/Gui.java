package client; 

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.io.FileOutputStream; 

import javax.swing.DefaultComboBoxModel; 

import javax.swing.JButton; 
import javax.swing.JComboBox; 
import javax.swing.JFrame; 
import javax.swing.JOptionPane; 
import javax.swing.JPanel; 
import javax.swing.JTextField; 
import javax.swing.JTextArea; 
import javax.swing.GroupLayout; 

import client.Client; 
import common.TextMessage; 

/**
 * TODO description
 */
public   class  Gui  extends JFrame  implements ChatLineListener {
	

	private static final long serialVersionUID = 1L;

	

	protected JTextArea outputTextbox;

	
	protected JTextField inputField;

	

	private static int rowstextarea = 20;

	
	private static int colstextarea = 60;

	

	public Client chatClient;

	
	
	

	JPanel pnlChat;

	
	public Gui  (String title, Client chatClient) {
		System.out.println("starting gui...");

		outputTextbox = new JTextArea(Gui.rowstextarea, Gui.colstextarea);
		outputTextbox.setEditable(false);
		inputField = new JTextField();
		inputField.addActionListener(getInput());
		
		pnlChat = new JPanel();

		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outputTextbox)
                    .addComponent(pnlChat)
                    .addComponent(inputField))
        );

        // layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

        layout.setVerticalGroup(layout.createSequentialGroup()
        		  .addComponent(outputTextbox)
                  .addComponent(pnlChat)
                  .addComponent(inputField));
		
    	// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(this);

		setTitle(title);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.chatClient = chatClient;
	
		colorComboBox = new JComboBox();
		colorComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox source = (JComboBox) e.getSource();
				color = (String) source.getSelectedItem();
			}

		});
		colorComboBox.setModel(new DefaultComboBoxModel(new String[] { "Blau",
				"Schwarz" }));
		pnlChat.add(colorComboBox);

	
		JButton btnsave = new JButton("Save");
	    pnlChat.add(btnsave);
	    
	    btnsave.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	          System.out.println(e.getSource());
	          saveMessage();
	        }
	      });
	    
	
	userComboBox = new JComboBox(chatClient.userlist);
	userComboBox.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JComboBox source = (JComboBox) e.getSource();
			touser = (String) source.getSelectedItem();
		}
	});
	pnlChat.add(userComboBox);
	
		privateComboBox = new JComboBox();
		privateComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox source = (JComboBox) e.getSource();
				whisper = (String) source.getSelectedItem();
			}

		});
		privateComboBox.setModel(new DefaultComboBoxModel(new String[] { "Public",
				"private" }));
		pnlChat.add(privateComboBox);
	}

	
	private ActionListener getInput  () {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Send = (String) inputField.getText();
				String tmp = null;
				if(color.equals("Blau"))
					  
				{
					tmp="<".concat("Blau").concat(">").concat(" ").concat(Send).concat(" ").concat("</").concat("Blau").concat(">");
					Send = tmp;
					
				}
				else
				{
					tmp="<".concat("Schwarz").concat(">").concat(" ").concat(Send).concat(" ").concat("</").concat("Schwarz").concat(">");
					Send = tmp;
					
				}
				chatClient.send(Send);
				inputField.setText("");
				
			}
		};
			}

	
	
	

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void newChatLine(String line) {
		outputTextbox.append(line);
	}

	

	private JComboBox colorComboBox;

	
	public String color = "Blau";

	
	JButton btnSave;

	
	
protected void saveMessage() {
		
		try {
			FileOutputStream fileoutput = new FileOutputStream(
					"message.txt", true);
			String temp = outputTextbox.getText();
			
			fileoutput.write(temp.getBytes());
			fileoutput.close();
			JOptionPane.showMessageDialog(null, "The Chat wird gespreichert in" + "message.txt");
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

	
	public JComboBox userComboBox;

	
	public String touser = "all";

	
	public String whisper = "public";

	
	private JComboBox privateComboBox;


}
