package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.GroupLayout.SequentialGroup;
//#if Color
//@import javax.swing.ButtonGroup;
//@import javax.swing.JRadioButton;
//#endif
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;

/**
 * simple swing gui for the chat client
 */
public class Gui extends JFrame implements ChatLineListener
{

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
	public Gui(String title, Client chatClient)
	{
		System.out.println("starting gui...");

		outputTextbox = new JTextArea(Gui.rowstextarea, Gui.colstextarea);
		outputTextbox.setEditable(false);

		inputField = new JTextField();
		inputField.addActionListener(getInput());

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		Component[] newComponents = this.addChatComponents();

		if (newComponents == null)
		{
			layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(
					layout.createParallelGroup(
							javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(outputTextbox).addComponent(inputField)));
		}
		else
		{
			ParallelGroup pg = layout.createParallelGroup();
			for (Component comp : newComponents)
			{
				pg.addComponent(comp);
			}
			layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(
					layout.createParallelGroup(
							javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(outputTextbox).addComponent(inputField).addGroup(pg)));
			
			SequentialGroup sg = layout.createSequentialGroup();
			for (Component comp : newComponents)
			{
				sg.addComponent(comp);
			}
			layout.setVerticalGroup(layout.createSequentialGroup()
					.addComponent(outputTextbox).addComponent(inputField).addGroup(sg));
		}

		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		chatClient.addLineListener(this);

		setTitle(title);
		pack();
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.chatClient = chatClient;
	}

	protected Component[] addChatComponents()
	{
		return null;
	}

	private ActionListener getInput()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String inputText = inputField.getText();

				chatClient.send(inputText);
				inputField.setText("");
			}
		};
	}

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void newChatLine(String line)
	{
		outputTextbox.append(line + "\n");
	}

}
