package chat.client;

import java.awt.Color;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

public class GUI 
{
	public void addNewChatLine(String line) 
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
