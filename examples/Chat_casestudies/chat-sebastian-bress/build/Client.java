


import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


//import java.util.


//import common.TextMessage;

/**
 * simple chat client
 */
abstract class Client$$Base implements Runnable {
	
	static Client client;
	
	public static void main(String args[]) throws IOException {
		if (args.length != 2) throw new RuntimeException("Syntax: ChatClient <host> <port>");

		client = new Client(args[0], Integer.parseInt(args[1]));
		//new Gui("Chat " + args[0] + ":" + args[1], client);
	}

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;
	
	//my Code:
	
	protected String textmessagecolor;
	
	protected String password="3Qdy%(nO";
	
	//needed for writing the log file
	protected File file;
    protected BufferedWriter bw;
    
    //you may want to change the location of the log file
    private String logfile_path = "./chat_client_messages.log";//"/home/sebastian/Programme/chat_client_messages.log";
	//my Code end

	public Client$$Base(String host, int port) {
		try {
			System.out.println("Connecting to " + host + " (port " + port
					+ ")...");
			Socket s = new Socket(host, port);
			this.outputStream = new ObjectOutputStream((s.getOutputStream()));
			this.inputStream = new ObjectInputStream((s.getInputStream()));
			
			//my Code
			 this.file = new File(logfile_path);
		     this.bw = new BufferedWriter(new FileWriter(file));
            //my Code end
			
			thread = new Thread(this);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * main method. first authenticates by the server, if that was successful, it waits for incoming messages.
	 */
	public void run() {
		
		//authenticate by the server 
		if(initialize_Connection()){
		//if(true){
		try {

			while (true) {
				try {
					Object msg = inputStream.readObject();
					handleIncomingMessage(msg);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			thread = null;
			try {
				outputStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		
		}
		
	}

	
	/**
	 * the Client has to authenticate himself by the Server. First the Client sends a request 
	 * with the password to the Server. Then he waits for a reply. If the client is successfully 
	 * authenticated, then he gets the status ok, otherwise a 'failed' is returned.   
	 */
	
	protected boolean initialize_Connection() {
	  return true;
	}
	/*
	private boolean initialize_Connection() {
		
		send(new TextMessage("authenticate","","",password,""));
		
		Object msg= new Object(); //to avoid the "not initialized error"
		
		try {
			msg = inputStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (msg instanceof TextMessage) {
			TextMessage tms = (TextMessage) msg;
			
			if(tms.getMessagetyp().equals("authenticated")){
			
				if(tms.getReply_status().equals("ok")){
					
					System.err.println("Successful authenticated!");
					return true;
					
				}else if(tms.getReply_status().equals("failed")){
					
					System.err.println("authentication failed! Maybe wrong Password?");
					
				}else{
					
					System.err.println("Protokollfehler: invalid reply_status_value " + tms.getReply_status());
				}
				
			}
			
		}
		
		return false; //something went wrong
			
	}

	private void log(String text){
		
       try {
		bw.write(text);
		bw.newLine();
		bw.flush();
	  } catch (IOException e) {
		System.err.println("Fehler beim schreiben der Log Datei! Path: "+logfile_path);
		e.printStackTrace();
	  }
       
    
     		
	}
	*/
	
	
	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			
			//write the Message to the log
			
			//this.log(((TextMessage) msg).getContent());
			
			
			fireAddLine(((TextMessage) msg).getContent() + "\n");
		}
	}

	public void send(String line) {
		send(new TextMessage(line));
	}

	public void send(TextMessage msg) {
		try {
			outputStream.writeObject(msg);
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			thread.stop();
		}
	}

	/**
	 * listener-list for the observer pattern
	 */
	private ArrayList listeners = new ArrayList();

	/**
	 * addListner method for the observer pattern
	 */
	public void addLineListener(ChatLineListener listener) {
		listeners.add(listener);
	}

	/**
	 * removeListner method for the observer pattern
	 */
	public void removeLineListener(ChatLineListener listner) {
		listeners.remove(listner);
	}

	/**
	 * fire Listner method for the observer pattern
	 */
	public void fireAddLine(String line) {
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
			ChatLineListener listener = (ChatLineListener) iterator.next();
			listener.newChatLine(line);
		}
	}

	public void stop() {
		thread.stop();
	}

}



abstract class Client$$Authentification extends  Client$$Base  {


/**
	 * the Client has to authenticate himself by the Server. First the Client sends a request 
	 * with the password to the Server. Then he waits for a reply. If the client is successfully 
	 * authenticated, then he gets the status ok, otherwise a 'failed' is returned.   
	 */
	
	protected boolean initialize_Connection() {
		
		send(new TextMessage("authenticate","","",password,""));
		
		Object msg= new Object(); //to avoid the "not initialized error"
		
		try {
			msg = inputStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (msg instanceof TextMessage) {
			TextMessage tms = (TextMessage) msg;
			
			if(tms.getMessagetyp().equals("authenticated")){
			
				if(tms.getReply_status().equals("ok")){
					
					System.err.println("Successful authenticated!");
					return true;
					
				}else if(tms.getReply_status().equals("failed")){
					
					System.err.println("authentication failed! Maybe wrong Password?");
					
				}else{
					
					System.err.println("Protokollfehler: invalid reply_status_value " + tms.getReply_status());
				}
				
			}
			
		}
		
		return false; //something went wrong
			
	}
      // inherited constructors

//"/home/sebastian/Programme/chat_client_messages.log";
	//my Code end

	public Client$$Authentification ( String host, int port ) { super(host, port); }




}



abstract class Client$$Logging extends  Client$$Authentification  {

	private void log(String text){
		
		
	       try {
			bw.write(text);
			bw.newLine();
			bw.flush();
			System.out.println("LOGGING: writing message '"+text+"' to logfile...");
		  } catch (IOException e) {
			//System.err.println("Fehler beim schreiben der Log Datei! Path: "+logfile_path);
			e.printStackTrace();
		  }
	       
	   	     		
   }
   
   protected void handleIncomingMessage(Object msg) {
		
			
			//write the Message to the log
			
			log(((TextMessage) msg).getContent());
            super.handleIncomingMessage(msg);
	
	}
      // inherited constructors

//"/home/sebastian/Programme/chat_client_messages.log";
	//my Code end

	public Client$$Logging ( String host, int port ) { super(host, port); }


}




public class Client extends  Client$$Logging  {

  public static void main(String args[]) throws IOException {
  
  Client$$Logging.main(args);
  
  /* 
   try{
    Super().main(args);
   
   }catch(IOException e){
   	
   }
   */
   
   new Gui("Chat " + args[0] + ":" + args[1], client);

 }
      // inherited constructors

//"/home/sebastian/Programme/chat_client_messages.log";
	//my Code end

	public Client ( String host, int port ) { super(host, port); }



}