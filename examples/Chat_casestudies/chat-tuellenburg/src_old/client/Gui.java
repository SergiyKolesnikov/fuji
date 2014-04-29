package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;



/**
 * simple swing gui for the chat client
 */
public class Gui extends JFrame implements ChatLineListener {

	private static final long serialVersionUID = 1L;

	protected JTextArea outputTextbox;
	protected JTextField inputField;
	
	//added 13.05
	protected JButton historyButton;
	protected JComboBox colorComboBox;
	
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
		//added 13.05
		historyButton = new JButton("History");
		historyButton.addActionListener(buttonPressed());
		colorComboBox = new JComboBox(new String[]{"black", "blue", "red"});
		colorComboBox.addActionListener(colorChosen());
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outputTextbox)
                    .addComponent(inputField)
                    .addComponent(historyButton)
                    .addComponent(colorComboBox))
        );

        // layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

        layout.setVerticalGroup(layout.createSequentialGroup()
                    .addComponent(outputTextbox)
                    .addComponent(inputField)
                    .addComponent(historyButton)
                    .addComponent(colorComboBox));
		
    	// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(this);

		setTitle(title);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.chatClient = chatClient;
		chatClient.login("geheim");
	}
	
	private ActionListener getInput() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = (String) inputField.getText();
				String line = "<" + chatClient.getCurrentColor() + ">" + text;
				chatClient.send(line);
				addToHistory(line);
				inputField.setText("");
			}
		};
	}
	//added 13.05
	private ActionListener buttonPressed() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent c){
				showHistory();
			}
		};
	}
	//added 13.05
	private ActionListener colorChosen() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent c) {
				chatClient.setCurrentColor((String)colorComboBox.getSelectedItem());
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

	/**
	 * addToHistory: adds a new Line to the Historyfile on Client-side
	 * @param line
	 */
	public void addToHistory(String line) {
		try {
			chatClient.getHistoryWriter().write(line + "\n");
			chatClient.getHistoryWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * showHistory shows a new Window with the most recent history File
	 * of this session
	 */
	public void showHistory(){
		JFrame historyFrame = new JFrame("History");
		JTextArea historyTextBox = new JTextArea();
		historyTextBox.setPreferredSize(new Dimension(600,800));
		historyTextBox.setEditable(false);
		
		try {
			BufferedReader historyReader = new BufferedReader(
							new FileReader(chatClient.getHistoryFile()));
			String line;
			while ((line = historyReader.readLine()) != null) {
				historyTextBox.append(line + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Container pane = historyFrame.getContentPane();
		pane.add(historyTextBox,BorderLayout.CENTER);
		
		historyFrame.pack();
		historyFrame.setVisible(true);
	}

}
