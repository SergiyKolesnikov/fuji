package chat.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

import chat.TextMessage;

import chat.client.Client;
import chat.client.IChatLineListener;

public class GUI extends JFrame implements IChatLineListener
{
	private static final long serialVersionUID = 1L;

	protected JScrollPane		mOutputTextboxScroller;	
	protected JTextPane 		mOutputTextbox;
	protected StyledDocument 	mStyledDoc;
	protected Style 			mStyle;
	protected JTextField 		mInputField;

	private Client 				mChatClient;

	public GUI(Client client)
	{
		this.mChatClient = client;
		
		System.out.println("starting gui...");
		
		this.mOutputTextbox = new JTextPane();
		this.mOutputTextbox.setEditable(false);
		
	    this.mStyledDoc = (StyledDocument)this.mOutputTextbox.getDocument();
	    this.mStyle = this.mStyledDoc.addStyle("ChatStyle", null);
	
		this.mOutputTextboxScroller = new JScrollPane(this.mOutputTextbox);		
	    
		this.mInputField = new JTextField();
		this.mInputField.addActionListener(this.getInput());
		
	    GroupLayout layout = new GroupLayout(this.getContentPane());
	    this.setLayout(layout);
	    layout.setHorizontalGroup(
	        layout.createParallelGroup(Alignment.LEADING)
	        .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
	            .addContainerGap()
	            .addGroup(layout.createParallelGroup(Alignment.TRAILING)
	                .addComponent(this.mOutputTextboxScroller, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
	                .addComponent(this.mInputField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
	            .addContainerGap())
	    );
	    layout.setVerticalGroup(
	        layout.createParallelGroup(Alignment.LEADING)
	        .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
	            .addContainerGap()
	            .addComponent(this.mOutputTextboxScroller, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
	            .addPreferredGap(ComponentPlacement.RELATED)
	            .addComponent(this.mInputField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	            .addContainerGap())
	    );
		
		// register listener so that we are informed whenever a new chat message
		// is received (observer pattern)
		this.mChatClient.addLineListener(this);
	
		/**
		 * Layout stuff.
		 */
		this.setTitle("Little Chat");
		this.setSize(400, 200);
		this.setLocation(200, 200);
		this.setMinimumSize(new Dimension(400, 200));
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try
		{
			/**
			 * Prints some usage information to the GUI.
			 */
			this.mStyledDoc.insertString(
					this.mStyledDoc.getLength(), 
					"Willkommen zum Little Chat!\n" +
					"Befehle:\n" +
					"[i]<text>[/i] - Kursiv\n" +
					"[b]<text>[/b] - Fett\n" +
					"[red]|[green]|[blue]<text> - Farbe\n" +
					"[msg] <username> <message> - Private Nachricht\n" +
					"[exit] - Programm beenden\n\n", 
					this.mStyle);
		}
		catch (BadLocationException e) 
		{
			System.err.println("The information text hasn't been added to the GUI: " + e.getMessage());
			e.printStackTrace();
		}
		
		/*
		 * Set keyboard focus to the input field. 
		 */
		this.mInputField.requestFocus();
	}
	
	/**
	 * Handles the "Return"-Key input of the InputField.
	 * 
	 * @return
	 */
	private ActionListener getInput() 
	{
		return new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				/**
				 * Checks for empty string.
				 */
				if(GUI.this.mInputField.getText().equals(""))
				{
					GUI.this.addNewChatLine("[red][b]System: you can not send an empty string.[/b]");
				}
				else
				{
					/**
					 * Checks for exit string and closes the program.
					 */
					if(GUI.this.mInputField.getText().equals("[exit]"))
					{
						System.out.println("Closing program...");
						System.exit(0);
					}
					else
					{
						/**
						 * Sends the entered string to the server.
						 */
						GUI.this.mChatClient.send( new TextMessage(
								GUI.this.mChatClient.getUserName(),
								GUI.this.mInputField.getText()));
						
						/**
						 * Deletes the entered text. 
						 */
						GUI.this.mInputField.setText("");
					}
				}
			}
		};
	}
	
	/**
	 * This method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void addNewChatLine(String line) 
	{		
		try
		{
			/*
			 * Adds the current letter/char to the end of the current line
			 * in the JTextPane.
			 */
			this.mStyledDoc.insertString(this.mStyledDoc.getLength(), line, this.mStyle);
		}
		catch (BadLocationException e) 
		{
			System.err.println("The text hasn't been added to the GUI: " + e.getMessage());
			e.printStackTrace();
		}				

		/**
		 * Keep the scroll bar always at the bottom.
		 */
		this.mOutputTextboxScroller.getVerticalScrollBar().setValue(
				this.mOutputTextboxScroller.getVerticalScrollBar().getMaximum());
	}
}
