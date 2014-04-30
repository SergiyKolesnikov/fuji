
package NewEquation1;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileWriter;



abstract class Client$$Basic implements Runnable{
  	protected ObjectInputStream inputStream;
    protected ObjectOutputStream outputStream;
    protected Thread thread;

	public static void main(String args[]) throws IOException {
   		if (args.length != 2) {
   			throw new RuntimeException("Syntax: ChatClient <host> <port>");
    	}
		Client client = new Client(args[0], Integer.parseInt(args[1]));        
 		new UserInterface(client);
	}
	
	public Client$$Basic(String host, int port) {
    	try {
        	System.out.println("Connecting to " + host + " (port " + port + ")...");
            Socket s = new Socket(host, port);
            this.outputStream = new ObjectOutputStream((s.getOutputStream()));
            this.inputStream = new ObjectInputStream((s.getInputStream()));
            thread = new Thread(this);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
      }
    /**
     * main method. waits for incoming messages.
     */
    public void run() {
        init();
        try {	
        	while (true) {
                try {
                    Object msg = inputStream.readObject();
                    //Ueberpruefung ob eine TextMessage empfangen wurde
                    if(msg instanceof TextMessage)
                    	handleIncomingMessage((TextMessage) msg);
                } catch (ClassNotFoundException e) {
            		e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        } finally {
            thread = null;
            try {
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
// Inhalt der Nachricht wird an die Listener weiter geleitet
 	public void handleIncomingMessage(TextMessage msg) {      
	 String line = ((TextMessage) msg).getContent();
     fireAddLine(line + "\n");	 	
    
    }	
    
    // Neue Textnachricht wird erzeugt
    public void send(String line) {
    	send(new TextMessage(line));
    }
    
    // Nachricht wird versendet
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
    public void init(){
    }
    
}



abstract class Client$$Crypt extends  Client$$Basic  {
	
	//Eingehende Nachrichten werden entschlüsselt
	public void handleIncomingMessage(TextMessage msg){
		msg.setContent( fliptext( msg.getContent()) );
		msg.setContent( doConvert( msg.getContent()) );
		super.handleIncomingMessage(msg);
			
	}
	
	//Ausgehende Nachrichten werden verschlüsselt
	public void send(String line){
		line=fliptext(line);
		line=doConvert(line);
		super.send(line);	
	}
	
	//String wird vertauscht
	private String fliptext (String text){
		text=new StringBuffer(text).reverse().toString();
		return text;
	
	}
	//ROT 13 
	//Quelle: http://en.literateprograms.org/Rot13_(Java)

    public String doConvert(String in) {
		String encodedMessage = "";
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			if      (c >= 'a' && c <= 'm') c += 13;
			else if (c >= 'n' && c <= 'z') c -= 13;
			else if (c >= 'A' && c <= 'M') c += 13;
			else if (c >= 'N' && c <= 'Z') c -= 13;
			encodedMessage += c;
		}
		return encodedMessage;
       
    
	}
      // inherited constructors


	
	public Client$$Crypt ( String host, int port ) { super(host, port); }
	
	
	
}



abstract class Client$$Color extends  Client$$Crypt  {

	 public void handleIncomingMessage(TextMessage msg){
	 	 
	 	 if(msg instanceof ColorMessage){
	 	 	 	 fireAddLine("["+((ColorMessage) msg).getColor()+"] " + msg.getContent() + "\n");
	 	 	 	 
	 	 	 	 }
	 	 else{
	 			 fireAddLine(msg.getContent() + "\n");	 
	 			 
	 			  }	 	 	 
	 }
      // inherited constructors


	
	public Client$$Color ( String host, int port ) { super(host, port); }

}





abstract class Client$$History extends  Client$$Color  {
	private FileWriter stream;
	private String newline;
	
	public void init(){
		newline=System.getProperty("line.separator");
		try {
            stream = new FileWriter(new File("ClientHistory.txt"), true);
        } catch (Exception e) {
            System.out.println("Die Logdatei konnte die erstellt werden");
        }
        
	}
	
	 public void handleIncomingMessage(TextMessage msg){
	 	super.handleIncomingMessage(msg);
	 	try {
	    	stream.write(((TextMessage) msg).getContent());
	        stream.append(newline);
	        stream.flush();
	        } 
	        catch (Exception e) {
	            System.out.println("Logdatei konnte nicht beschrieben werden");
	        }
	 	
	 }
      // inherited constructors


	
	public Client$$History ( String host, int port ) { super(host, port); }
	

}



public class Client extends  Client$$History  {
	private String passwort="EPMD4EVER";
	
	public void init(){
		super.init();
		TextMessage auth=new AuthMessage(passwort);
		send (auth);
	}
      // inherited constructors


	
	public Client ( String host, int port ) { super(host, port); }
	
}