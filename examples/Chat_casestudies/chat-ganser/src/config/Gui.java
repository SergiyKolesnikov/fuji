
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import javax.swing.JFrame; 
import javax.swing.JTextField; 
import javax.swing.JTextArea; 
import javax.swing.GroupLayout; 
import javax.swing.GroupLayout.Alignment.*; import java.awt.Container; 

import javax.swing.BoxLayout; 
import javax.swing.JComboBox; 
import javax.swing.JPanel; 
import javax.swing.JComboBox; 

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
	this.setup(title, chatClient);
    }

	

     private void  setup__wrappee__Base  (String title, Client chatClient) {
	System.out.println("starting gui...");

	outputTextbox = new JTextArea(Gui.rowstextarea, Gui.colstextarea);
	outputTextbox.setEditable(false);
	inputField = new JTextField();
	inputField.addActionListener(getInput());

	GroupLayout layout = new GroupLayout(getContentPane());
	getContentPane().setLayout(layout);
	layout.setAutoCreateGaps(true);
	layout.setAutoCreateContainerGaps(true);

	layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(
		layout.createParallelGroup(
			javax.swing.GroupLayout.Alignment.LEADING)
			.addComponent(outputTextbox).addComponent(inputField)));

	// layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

	layout.setVerticalGroup(layout.createSequentialGroup()
		.addComponent(outputTextbox).addComponent(inputField));

	// register listener so that we are informed whenever a new chat message
	// is received (observer pattern)
	chatClient.addLineListener(this);

	setTitle(title);
	pack();
	setVisible(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.chatClient = chatClient;
    }

	
    
    private void setup(String title, Client chatClient) {
	setup__wrappee__Base(title, chatClient);
	Container oldContentPane = this.getContentPane();
	JPanel newContentPane = new JPanel();
	newContentPane.setLayout(new BoxLayout(newContentPane, BoxLayout.Y_AXIS));
	newContentPane.add(oldContentPane);
	this.chooser = new JComboBox(new String[] { BLACK, BLUE, BROWN, GREEN,
		ORANGE, PINK, PURPLE, RED, YELLOW, WHITE });
	newContentPane.add(chooser);
	this.setContentPane(newContentPane);
	this.pack();
    }

	
    
    private ActionListener getInput  () {
	return new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		String color = (String)Gui.this.chooser.getSelectedItem();
		chatClient.send((String) inputField.getText(), color);
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

	
    
    private static final String WHITE = "white";

	
    private static final String BLACK = "black";

	
    private static final String BLUE = "blue";

	
    private static final String GREEN = "green";

	
    private static final String YELLOW = "yellow";

	
    private static final String ORANGE = "orange";

	
    private static final String RED = "red";

	
    private static final String PINK = "pink";

	
    private static final String PURPLE = "purple";

	
    private static final String BROWN = "brown";

	
    
    private JComboBox chooser;


}
