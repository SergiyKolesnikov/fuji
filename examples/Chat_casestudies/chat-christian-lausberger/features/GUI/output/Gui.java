package output;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;

import client.Client;
import client.IChatLineListener;




/**
 * simple swing gui for the chat client
 */
public class Gui extends JFrame implements IChatLineListener {

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
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outputTextbox)
                    .addComponent(inputField))
        );

        // layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

        layout.setVerticalGroup(layout.createSequentialGroup()
                    .addComponent(outputTextbox)
                    .addComponent(inputField));
		
    	// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		//chatClient.addLineListener(this);

		setTitle(title);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.chatClient = chatClient;
		
		addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	Gui.this.chatClient.stop();
		        System.exit(0);
		      }
		    });
	}
	
	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chatClient.hasAccess()){
					chatClient.send(inputField.getText());
				}else{
					chatClient.send("Wrong Pass!\n");
				}
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

	public void stop() {
	}

}
