package client; 

import java.awt.Dimension; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import javax.swing.JEditorPane; 
import javax.swing.JFrame; 
import javax.swing.JScrollPane; 
import javax.swing.JTextField; 
//import javax.swing.JTextArea;
import javax.swing.GroupLayout; 

import common.Message; 
//import javax.swing.GroupLayout.Alignment.*;



/**
 * simple swing gui for the chat client
 */
public   class  Gui  extends JFrame  implements ChatPlugin {
	

	private static final long serialVersionUID = 1L;

	
	private static final byte type = 0x10;

	

	protected JEditorPane outputTextbox;

	
	protected JTextField inputField;

	
	protected JScrollPane scrollPane;

	

	private Client chatClient;

	

	public Gui  (String title, Client chatClient) {
		System.out.println("starting gui...");
		
		outputTextbox = new JEditorPane("text/plain", "Welcome to EPMD_Chat!");

		outputTextbox.setEditable(false);
		
		scrollPane = new JScrollPane(outputTextbox,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setEnabled(true);
        scrollPane.createVerticalScrollBar();
        scrollPane.setMinimumSize(new Dimension(400, 100));
        
		inputField = new JTextField();
		inputField.addActionListener(getInput());
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                	.addComponent(scrollPane)
                    .addComponent(inputField))
        );

        // layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

        layout.setVerticalGroup(layout.createSequentialGroup()
        			.addComponent(scrollPane)
                    .addComponent(inputField));
		
    	// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.loadPlugin(this);

		setTitle(title);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.chatClient = chatClient;
		System.out.println("GUI loaded.");
	


		outputTextbox = new JEditorPane ("text/html","<html><body>Welcome to EPMD_Chat!<br>\n</body></html>");

	}

	
	public byte getType(){
		return type;
	}

	
	
	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = inputField.getText();

				chatClient.send( new Message( str,(byte) 0 ));

				inputField.setText("");
			}
		};
	}

	

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	@Override
	public Message process(Message msg) {
		if (msg.getStatus()==0||msg.getStatus()==3){ // only react on typematching Messages
			// new line is appended to JEditorPane, in HTML
			outputTextbox.setText(outputTextbox.getText().replaceAll("</body>\\s</html>", msg.getText() + "<br>\n</body></html>"));
			try { // Scroll to the last entered line
				outputTextbox.setCaretPosition(outputTextbox.getDocument().getLength());
			}
			catch(IllegalArgumentException ex){	
			}
		}
		return msg;
	}


}
