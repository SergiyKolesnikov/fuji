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

import java.awt.Color; 
import javax.swing.text.StyleConstants; 

public   class  GUI  extends JFrame  implements IChatLineListener {
	
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

	
	public void addNewChatLine  (String line) 
	{
		/**
		 * Handle style information and add the line to the GUI.
		 */
		this.handleStyle(line);
					
		/**
		* Reset the text style for the new line.
		*/
		this.resetStyle();
		
		/**
		 * Keep the scroll bar always at the bottom.
		 */
		this.mOutputTextboxScroller.getVerticalScrollBar().setValue(
				this.mOutputTextboxScroller.getVerticalScrollBar().getMaximum());		
	}

	

	/**
	 * Parses and sets the style information in the given string.
	 * 
	 * @param line - String to show in GUI.
	 */
	private void handleStyle(String line)
	{
		String workString = "";
		
		int length = line.length();
		
		for( int i = 0; i < length; i++)
		{
			String letter = String.valueOf( line.charAt(i) );
			
			/**
			 * Style start marks.
			 */
			if( String.valueOf(line.charAt(i)).equals("[") )
			{
				try
				{
					/**
					 * Italic start mark.
					 */
					workString = line.substring(i, i + 3);
					if( workString.equals("[i]") )
					{
						StyleConstants.setItalic(this.mStyle, true);						
					}

					/**
					 * Bold start mark.
					 */
//					workString = line.substring(i, i + 3);
					if( workString.equals("[b]") )
					{
						StyleConstants.setBold(this.mStyle, true);						
					}
					
					/**
					 * Blue color start mark.
					 */
					workString = line.substring(i, i + 6);
					if( workString.equals("[blue]") )
					{
						StyleConstants.setForeground(this.mStyle, Color.BLUE);						
					}

					/**
					 * Red color start mark.
					 */
					workString = line.substring(i, i + 5);
					if( workString.equals("[red]") )
					{
						StyleConstants.setForeground(this.mStyle, Color.RED);						
					}

					/**
					 * Green color start mark.
					 */
					workString = line.substring(i, i + 7);
					if( workString.equals("[green]") )
					{
						StyleConstants.setForeground(this.mStyle, Color.GREEN);						
					}
					
					/**
					 * Black color start mark.
					 */
					workString = line.substring(i, i + 7);
					if( workString.equals("[black]") )
					{
						StyleConstants.setForeground(this.mStyle, Color.BLACK);						
					}
				}
				catch(Exception e)
				{
					/**
					 * Don't care about this.
					 * It's easier than 100 if-conditions. ;)
					 */
				}
			}
			
			try
			{
				/**
				 * Adds the current letter/char to the end of the current line
				 * in the JTextPane.
				 */
				this.mStyledDoc.insertString(this.mStyledDoc.getLength(), letter, this.mStyle);
			}
			catch (BadLocationException e) 
			{
				System.err.println("The text hasn't been added to the GUI: " + e.getMessage());
				e.printStackTrace();
			}

			/**
			 * Style end marks.
			 */
			if( String.valueOf(line.charAt(i)).equals("]") )
			{
				/**
				 * Italic end mark.
				 */
				workString = line.substring(i - 3, i + 1);
				if( workString.equals("[/i]") )
				{
					StyleConstants.setItalic(this.mStyle, false);						
				}

				/**
				 * Bold end mark.
				 */
				workString = line.substring(i - 3, i + 1);
				if( workString.equals("[/b]") )
				{
					StyleConstants.setBold(this.mStyle, false);						
				}
				
				/**
				 * Not needed.
				 * Keep color until another color is set.
				 */
//				//blue color end
//				workString = line.substring(i - 6, i + 1);
//				if( workString.equals("[/blue]") )
//				{
//					StyleConstants.setForeground(this.mStyle, Color.BLACK);						
//				}
//
//				//red color end
//				workString = line.substring(i - 5, i + 1);
//				if( workString.equals("[/red]") )
//				{
//					StyleConstants.setForeground(this.mStyle, Color.BLACK);						
//				}
//
//				//green color end
//				workString = line.substring(i - 5, i + 1);
//				if( workString.equals("[/green]") )
//				{
//					StyleConstants.setForeground(this.mStyle, Color.BLACK);						
//				}
			}
		}
	}

	
	
	/**
	 * Resets the text style (color, italic, bold) of the JTextPane.
	 */
	private void resetStyle()
	{
		StyleConstants.setItalic(this.mStyle, false);
		StyleConstants.setBold(this.mStyle, false);
		StyleConstants.setForeground(this.mStyle, Color.BLACK);	
	}


}
