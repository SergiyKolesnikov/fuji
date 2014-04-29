import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.JTextPane;
import javax.swing.GroupLayout.Alignment.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * simple swing gui for the chat client
 */
public class Gui extends JFrame implements ChatLineListener {

	private static final long serialVersionUID = 1L;

	protected JTextComponent outputTextbox;
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

		///// additional Code //////
		//#if FeatureHandler
		//featureHandler = new FeatureHandler(features);
		//#endif
		//// given Code //////

		/* Anpassung auf JTextPane */
		outputTextbox = new JTextPane();
		outputTextbox.setPreferredSize(new Dimension(Gui.colstextarea*10, Gui.rowstextarea*10));
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
		chatClient.addLineListener(this);

		setTitle(title);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.chatClient = chatClient;
		
		//endif
	}
	
	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String line = (String) inputField.getText();
				
				line = formatOutput(line);
				chatClient.send(line);
				inputField.setText("");
			}
		};
	}
	
	
	public String formatOutput(String line){
		return line;
	}
	
	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void newChatLine(String line) {
		
		try{
			SimpleAttributeSet attributes = new SimpleAttributeSet();
			ShowStyledChatline(line, attributes);
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public void ShowStyledChatline(String line, SimpleAttributeSet attributes){
		
		Document doc = outputTextbox.getDocument();
		if (doc != null) {
            try {
				doc.insertString(doc.getLength(), line, attributes);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}

}
