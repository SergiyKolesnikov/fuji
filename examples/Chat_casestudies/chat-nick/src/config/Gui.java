
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import javax.swing.JComboBox; 
import javax.swing.JFrame; 
import javax.swing.JTextField; 
import javax.swing.JTextArea; 

import javax.swing.GroupLayout; 
import javax.swing.GroupLayout.Alignment.*; import java.util.LinkedList; 
import java.util.List; 

public   class  Gui  extends JFrame  implements ChatLineListener {
	

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
		inputField.addActionListener(getInput());
		
		initLayout();
		
		
    	// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(this);

		setTitle(title);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.chatClient = chatClient;
	}

		
	
	private void initLayout  () {

		colorPicker = new JComboBox();
		availableColors = new LinkedList<Color>();
				
		availableColors.add(new Color("Schwarz"));
		availableColors.add(new Color("Gr�n"));			
		availableColors.add(new Color("Gelb"));
		availableColors.add(new Color("Rot"));
		
		for (Color c: availableColors) {
			colorPicker.addItem(c);
		}
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outputTextbox)
                    .addComponent(inputField))
                .addGroup(layout.createSequentialGroup()
                	.addComponent(colorPicker))
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
                    .addComponent(outputTextbox)
              .addGroup(layout.createParallelGroup()
                    .addComponent(inputField)
                    .addComponent(colorPicker))
        );				
	}

	
	
	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TextMessage msg = new TextMessage((String) inputField.getText());
				sendNewTextMessage(msg);
				inputField.setText("");
			}
		};
	}

	
	
	 private void  sendNewTextMessage__wrappee__Gui  (TextMessage msg) {
		chatClient.send(msg);		
	}

		
	
	private void sendNewTextMessage(TextMessage msg) {
		msg.setColor((Color) colorPicker.getSelectedItem());
		sendNewTextMessage__wrappee__Gui(msg);
	}

	

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void newChatLine(String line) {
		outputTextbox.append(line);
	}

	
	protected JComboBox colorPicker;

	
	protected List<Color> availableColors;


}
