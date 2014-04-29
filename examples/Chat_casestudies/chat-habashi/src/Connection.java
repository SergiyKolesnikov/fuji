
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;




abstract class Connection$$Base extends Thread{

	private Socket socket;
	protected Server server;
	
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private boolean connectionOpen = true;
	
	public Connection$$Base(Socket socket, Server server){
		this.socket = socket;
		try{
			this.inputStream = new ObjectInputStream(socket.getInputStream()); 
			this.outputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch(IOException e){
			e.printStackTrace();
		}
		this.server = server;
	}
	
	public void run(){
		String clientName = socket.getInetAddress().toString();
		try{
			server.broadcast(clientName + " has joined!");
			while(connectionOpen){
				try{
					Object msg = inputStream.readObject();
					handleIncomingMessage(clientName, msg);
				} catch(ClassNotFoundException e){
					e.printStackTrace();
				}
			}
		} catch(IOException e){
			e.printStackTrace();
		} finally{
			server.removeConnection(((Connection) this));
			server.broadcast(clientName + " has left");
			try{
				socket.close();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	protected void handleIncomingMessage(String clientName, Object msg) {
		System.out.println("du");
		if(msg instanceof TextMessage){
			TextMessage temptextmessage = (TextMessage) msg;
			IncomingTextMessage textmessage = new IncomingTextMessage(temptextmessage.getText(), temptextmessage.getColor());
			//textmessage.setText(textmessage.decodeText());
			server.broadcast(clientName + " - " + "<" + textmessage.textColor() + "> : " + textmessage.getText());
		}
	}

	public void send(String text) {
		OutgoingTextMessage temp = new OutgoingTextMessage(text);
		
		send(temp);
	}
	
	public void send(TextMessage textMessage){
		try{
			synchronized (outputStream) {
				outputStream.writeObject(textMessage);
			}
			outputStream.flush();
		} catch(IOException e){
			connectionOpen = false;
		}
	}
	
}

public class Connection extends  Connection$$Base  {

	protected void handleIncomingMessage(String clientName, Object msg) {
		System.out.println("ich");
		if(msg instanceof TextMessage){
			TextMessage temptextmessage = (TextMessage) msg;
			IncomingTextMessage textmessage = new IncomingTextMessage(temptextmessage.getText(), temptextmessage.getColor());
			textmessage.setText(textmessage.decodeText());
			server.broadcast(clientName + " - " + "<" + textmessage.textColor() + "> : " + textmessage.getText());
		}
	}
      // inherited constructors


	
	public Connection ( Socket socket, Server server ) { super(socket, server); }

}