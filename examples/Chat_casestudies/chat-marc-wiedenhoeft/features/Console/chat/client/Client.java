package chat.client;

import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * TODO description
 */
public class Client {
	
	/**
	 * ConsoleIO is the default input/output class.
	 * If no GUI plugin will be found, the input and output
	 * of the chat program will be handled by the console.
	 * 
	 * @author Marc Wiedenhoeft
	 * @version 1.0
	 *
	 */
	class ConsoleIO implements IChatLineListener 
	{
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        ConsoleIO()
		{
			Client.this.addLineListener(this);
			
			System.out.println(
			"Willkommen zum Little Chat!\n" +
			"Befehle:\n" +
			"[i]<text>[/i] - Kursiv\n" +
			"[b]<text>[/b] - Fett\n" +
			"[red]|[green]|[blue]<text> - Farbe\n" +
			"[msg] <username> <message> - Private Nachricht\n" +
			"[exit] - Programm beenden\n\n");		
		}
		
		public void typeMessage()
		{
	        try 
	        {
	        	/*
	        	 * Read in a new line from the console.
	        	 * Waits until 'return' is pressed.
	        	 */
	            System.out.println("Enter a message: ");
	            String s = br.readLine();
	            
	            /*
	             * Exits program.
	             */
	            if(s.equals("[exit]"))
	            {
	            	System.exit(0);
	            }
	            
	            /*
	             * Sends message to server.
	             */
	            Client.this.send(Client.this.mUserName, s);
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
	        
	        /*
	         * Restart console input.
	         */
	        this.typeMessage();
		}
		
		@Override
		public void addNewChatLine(String line) 
		{
			/*
			 * Prints out new messages from the server.
			 */
			System.out.println("New message: ");
			System.out.println(line);
		}
	}
}