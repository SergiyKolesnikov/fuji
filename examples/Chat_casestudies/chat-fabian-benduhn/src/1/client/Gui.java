package client; 

import java.awt.Color; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.util.Arrays; 

import javax.swing.GroupLayout.ParallelGroup; 
import javax.swing.JButton; 
import javax.swing.JColorChooser; 
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JOptionPane; 
import javax.swing.JPasswordField; 
import javax.swing.JScrollPane; 
import javax.swing.JTextField; 
import javax.swing.GroupLayout; 


/**
 * simple swing gui for the chat client
 */
public  class  Gui  extends JFrame  implements ChatLineListener {
	

	private static final long serialVersionUID = 1L;

	

	protected ColorPane outputTextbox;

	
	protected JTextField inputField;

	
	JScrollPane scrollpane;

	
	private Client chatClient;

	
	
	private JButton colorButton;

	
	JColorChooser colorChooser;

	
	
	private static char[] password = {'e','p','m','d'};

	

	private void GuiConstructor(String title, Client chatClient) {
		

		outputTextbox = new ColorPane();
		outputTextbox.setEditable(false);
		
		colorButton = new JButton("Change Color");
	colorChooser = new JColorChooser();
	colorChooser.setColor(Color.black);
	ShowColorChooserAction colorAction = new ShowColorChooserAction(this,
			colorChooser);
		colorButton.setAction(colorAction);
	
		inputField = new JTextField();
		inputField.addActionListener(getInput());
		scrollpane = new JScrollPane(outputTextbox);
				GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		 ParallelGroup group =layout.createParallelGroup(
					javax.swing.GroupLayout.Alignment.LEADING)
					
					.addComponent(scrollpane)
					
					.addComponent(inputField)
				
				.addComponent(colorButton)
				
					;
		layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(group));

		// layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(scrollpane).addComponent(inputField)
				
			.addComponent(colorButton)
			
				);

		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.chatClient = chatClient;


		setTitle(title);
		setVisible(true);
		pack();
		colorButton.setVisible(false);
	}

	
	/**
	 * creates layout
	 * 
	 * @param title
	 *            title of the window
	 * @param chatClient
	 *            chatClient that is used for sending and receiving messages
	 */
	public Gui(String title, Client chatClient) {
		GuiConstructor(title,chatClient);
	}

	

	private ActionListener getInput() {
		return new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				chatClient.send("#" +
		
			colorChooser.getColor().getRGB() + 
		"#"	+
		
			 (String) inputField.getText());
				inputField.setText("");
			}
		};
	}

	

	/* (non-Javadoc)
	 * @see client.Igui#newChatLine(java.lang.String)
	 */
	@Override
	public void newChatLine(String line) {
		String[] split = line.split("#");
		Color color = new Color(Integer.parseInt(split[1]));
		outputTextbox.append(color, split[0] + split[2]+"\n");

	}

	

	private boolean showPasswordDialog(String text) {
		 JLabel passwordLabel = new JLabel(text);
		JPasswordField jpf = new JPasswordField();
		
		int option = JOptionPane
				.showConfirmDialog(null, new Object[] { passwordLabel, jpf },
						"Password:", JOptionPane.OK_CANCEL_OPTION);
		
		if (option == JOptionPane.OK_OPTION) {
			if (Arrays.equals(jpf.getPassword(),password)){
		
				return true;
			}
			else {
			return showPasswordDialog("Password or Username wrong! Try again!");
			}
		
		}
		return false;

	}


}
