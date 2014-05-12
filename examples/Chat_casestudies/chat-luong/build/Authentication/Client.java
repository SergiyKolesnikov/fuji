package Authentication;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.text.*;
import java.util.*;





/**
 * simple chat client
 */
abstract class Client$$client implements Runnable {
	String servAddr;
	int servPort;
	Socket s;
	
	public static void main(String args[]) throws IOException {
		Client client = new Client();
		
	}

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;
	public Client$$client(boolean validate){}
	public Client$$client() {
		getConfiguration();
		connectToServer();
		initUI();
	}
	synchronized boolean connectToServer(){
		 
		
		try
		{
			System.out.println("Connecting to " + servAddr + " (port " + servPort
					+ ")...");
			InetAddress addr = InetAddress.getByName(servAddr); 
			s = new Socket(addr, servPort);
		}
		catch( UnknownHostException e )
		{
			JOptionPane.showMessageDialog( null,"Host Not Found, Reconfigure...","Host Lookup Error",JOptionPane.ERROR_MESSAGE );
			return false;
		}
		catch( IOException e )
		{
			JOptionPane.showMessageDialog( null,"Server Not Found, Check If Server Exists...","Socket Error",JOptionPane.ERROR_MESSAGE );
			System.exit(0);
			return false;
		}

		try
		{
			((Client) this).outputStream = new ObjectOutputStream((s.getOutputStream()));
			((Client) this).inputStream = new ObjectInputStream((s.getInputStream()));
		}
		catch( IOException e )
		{
			JOptionPane.showMessageDialog( null,"Cannot Create Data Stream, Closing Client...","Data Stream Error",JOptionPane.ERROR_MESSAGE );
			try
			{
				s.close();
			}
			catch( IOException io_e )
			{}
			
			return false;
		}
		thread = new Thread(((Client) this));
		thread.start();
				
		return true;
		
	}

	/**
	 * main method. waits for incoming messages.
	 */
	public void run() {
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
	protected synchronized void getConfiguration() {
		try {
			char[] buffer = new char[255];

			FileReader configFile = new FileReader("clientConfig.cfg");

			configFile.read(buffer);
			servAddr = String.copyValueOf(buffer);
			String[] temp = servAddr.split(";");

			servAddr = temp[0];
			servPort = Integer.parseInt(temp[1]);

		} catch (FileNotFoundException fnf_e) {
			JOptionPane.showMessageDialog(null,
					"Configuration File Not Found, Using Defaults",
					"Configuration File Missing", JOptionPane.ERROR_MESSAGE);

			servPort = 1665;
			servAddr = "localhost";
		} catch (IOException io_e) {
			JOptionPane.showMessageDialog(null,
					"Error Reading Configuration File, Using Defaults",
					"Configuration Error", JOptionPane.ERROR_MESSAGE);

			servPort = 1665;
			servAddr = "localhost";
		}
	}

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			fireAddLine((TextMessage)msg);
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
	protected ArrayList listeners = new ArrayList();

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
	public void fireAddLine(TextMessage msg) {
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
			ChatLineListener listener = (ChatLineListener) iterator.next();
			listener.newChatLine(msg);
		}
	}

	public void stop() {
		thread.stop();
	}
	public void initUI(){
	}

}



abstract class Client$$Authentication extends  Client$$client  {
	LoginInterface login;
	
	public static void main(String[] args){
		boolean authentication = true;
		Client client = new Client(authentication);
		
	}
	public Client$$Authentication(boolean validate) { super(validate); 

		getConfiguration();
		login = new LoginInterface(((Client) this)); }

	synchronized boolean connectToServer(){
		 
		
		try
		{
			System.out.println("Connecting to " + servAddr + " (port " + servPort
					+ ")...");
			InetAddress addr = InetAddress.getByName(servAddr); 
			s = new Socket(addr, servPort);
		}
		catch( UnknownHostException e )
		{
			JOptionPane.showMessageDialog( login,"Host Not Found, Reconfigure...","Host Lookup Error",JOptionPane.ERROR_MESSAGE );
			return false;
		}
		catch( IOException e )
		{
			JOptionPane.showMessageDialog( login,"Server Not Found, Check If Server Exists...","Socket Error",JOptionPane.ERROR_MESSAGE );
			return false;
		}

		try
		{
			((Client) this).outputStream = new ObjectOutputStream((s.getOutputStream()));
			((Client) this).inputStream = new ObjectInputStream((s.getInputStream()));
		}
		catch( IOException e )
		{
			JOptionPane.showMessageDialog( login,"Cannot Create Data Stream, Closing Client...","Data Stream Error",JOptionPane.ERROR_MESSAGE );
			try
			{
				s.close();
			}
			catch( IOException io_e )
			{}
			
			return false;
		}
		if (!verify()) return false;
			
		login.setVisible(false);
		//window = new Gui("EPMD Chat", this);
		initUI();
		thread = new Thread(((Client) this));
		thread.start();
				
		return true;
		
	}
	private boolean verify(){			
		LoginRequest req = new LoginRequest();
		
		req.Usrname = login.txtUserName.getText();
		req.Pwd = new String(login.txtPwd.getPassword());
		
		try{
			outputStream.writeObject(req);
			LoginReply reply = (LoginReply)inputStream.readObject();
			if( reply.status == false ){
				JOptionPane.showMessageDialog( login,"Username or password is wrong","Login failed",JOptionPane.ERROR_MESSAGE );
				return false;
			}
			
			return true;
		} catch( Exception e ){}
		
		return false;
	}
      // inherited constructors


	public Client$$Authentication (  ) { super(); } 
}



abstract class Client$$Color extends  Client$$Authentication  {
	protected Color textColor = Color.BLACK;
	public void send(String line){
		TextMessage msg = new TextMessage(line);
		msg.setColor(textColor);
		send(msg);
	}
      // inherited constructors


	public Client$$Color ( boolean validate ) { super(validate); }
	public Client$$Color (  ) { super(); }
	
}



abstract class Client$$GUI extends  Client$$Color  {
	Gui window;
	public void initUI(){
		window = new Gui("EPMD Chat",((Client) this));
	}
      // inherited constructors


	public Client$$GUI ( boolean validate ) { super(validate); }
	public Client$$GUI (  ) { super(); }
}



abstract class Client$$Encryption extends  Client$$GUI  {
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			String content = ((TextMessage)msg).getContent();
			int contentIndex = content.indexOf("-") + 1;
			String prefix = content.substring(0,contentIndex);
			content = content.substring(contentIndex + 1,content.length());
			String decodedContent = Encryption.encrptMethod2(Encryption.encrptMethod1(content));
			((TextMessage)msg).setContent(prefix + " " + decodedContent);
			fireAddLine((TextMessage)msg);
		}
	}
      // inherited constructors


	public Client$$Encryption ( boolean validate ) { super(validate); }
	public Client$$Encryption (  ) { super(); }
}



public class Client extends  Client$$Encryption  {
	protected void handleIncomingMessage(Object msg) {
		super.handleIncomingMessage(msg);
		if (msg instanceof TextMessage) {
			archive((TextMessage)msg);
		}
	}
	public synchronized void archive(TextMessage usrMessage){
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter("clientLog.log",true));
			TimeZone tz = TimeZone.getTimeZone("EST"); 
         	Date now = new Date();
         	DateFormat df = new SimpleDateFormat ("yyyy.mm.dd hh:mm:ss ");
         	df.setTimeZone(tz);
         	String currentTime = df.format(now);

			bw.write(currentTime + " " + usrMessage.getContent() + "\n");
			bw.newLine();
			bw.flush();
			bw.close();
		} catch(Exception e){}
	}
      // inherited constructors


	public Client ( boolean validate ) { super(validate); }
	public Client (  ) { super(); }
}