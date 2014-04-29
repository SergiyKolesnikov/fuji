package client; 

import java.io.IOException; 
import java.io.EOFException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.net.UnknownHostException; 
import java.util.ArrayList; 
import java.util.Iterator; 

import javax.swing.JOptionPane; 

import client.Gui; 

import common.TextMessage; 
import java.util.Vector; 

import common.ConnectionMessage; 
import common.LoginReply; 
import common.UserMessage; 
import client.Login; 

/**
 * TODO description
 */
public   class  Client  implements Runnable {
	

	public static void main(String args[])throws IOException{
		
		boolean LoginFlag = false;
		
		Client client = new Client(LoginFlag);

		
	}

	
	
	public Socket s;

	
	public String host= "localhost";

	
	public int port= 8081;

	
	protected ObjectInputStream inputStream;

	

	protected ObjectOutputStream outputStream;

	

	protected Thread thread;

	
		public Client  (boolean LoginFlag) {
		
			connectToServer(!LoginFlag);
	
			 login = new Login("chat", this);
			
		}

	

	/**
	 * main method. waits for incoming messages.
	 */
	public void run() {
		try {
			Thread thisthread = Thread.currentThread();
			while (true) {
				try {
					Object msg = inputStream.readObject();
					handleIncomingMessage(msg);
				} catch (EOFException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
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

	

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	 private void  handleIncomingMessage__wrappee__Log  (Object msg) {
		if (msg instanceof TextMessage) {
			fireAddLine(((TextMessage) msg).getContent() + "\n");
		}
	}

	 

		protected void handleIncomingMessage(Object msg)
		{
			if (msg instanceof UserMessage) {
				
				if(((UserMessage)msg).add){
					gui.userComboBox.addItem(((UserMessage)msg).user);
					}
				if(!((UserMessage)msg).add){
					gui.userComboBox.removeItem(((UserMessage)msg).user);
					}
			}
			handleIncomingMessage__wrappee__Log(msg);
		}

	

	 private void  send__wrappee__Spamfilter  (String line) {
		send(new TextMessage(line));
	}

	
	public void send(String line)
	{
		String Send;
		 StringBuffer tempReturn = new StringBuffer();

		 	int abyte = 0;
		 	boolean flag = true;
	        for (int i=0; i<line.length(); i++) {            
	        	abyte = line.charAt(i);
            if(abyte != '<'&&flag)
            {
            int cap = abyte & 32;
            abyte &= ~cap;
            abyte = ( (abyte >= 'A') && (abyte <= 'Z') ? ((abyte - 'A' + 13) % 26 + 'A') : abyte) | cap;
            }
            else
            	{if(abyte =='>')
            		flag = true;
            	else
            	flag = false;
            	}
            tempReturn.append((char)abyte);
            }
	        Send = tempReturn.toString();
	        send__wrappee__Spamfilter(Send);
}

	

	 private void  send__wrappee__Authen  (TextMessage msg) {
		try {
			outputStream.writeObject(msg);
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			this.stop();
		}
	}

	
	public void send(TextMessage msg) {
		msg.chatUser = login.StringUsername;
		msg.chatToUser = gui.touser;
		if(gui.whisper.equals("public"))
			msg.whisper = false;
		else
			msg.whisper = true;
		send__wrappee__Authen(msg);
	}

	

	/**
	 * listener-list for the observer pattern
	 */
	@SuppressWarnings("rawtypes")
	private ArrayList listeners = new ArrayList();

	

	/**
	 * addListner method for the observer pattern
	 */
	@SuppressWarnings("unchecked")
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
		thread = null;
	}

	
	
 private synchronized boolean  connectToServer__wrappee__Log  (boolean LoginFlag){
	boolean Login = LoginFlag;
	if(Login==true)
	{
	try
	{
		System.out.println("Connecting to " + host + " (port " + port
				+ ")...");
		s = new Socket(host, port);
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
		this.outputStream = new ObjectOutputStream((s.getOutputStream()));
		this.inputStream = new ObjectInputStream((s.getInputStream()));
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
	new Gui("Chat " + "localhost" + ":" + "8081" , this);
	thread = new Thread(this);
	thread.start();
			
	return true;
	}
	else
		return false;
	}

	
		synchronized boolean connectToServer(boolean LoginFlag)
		{
			boolean Flag = false;
			connectToServer__wrappee__Log(Flag);
			return true;
		}

	
	public Gui gui;

	
	public Vector userlist;

	
	public Login login;

	
		
		synchronized boolean connect(){
			 
			
			try
			{
				System.out.println("Connecting to " + host + " (port " + port
						+ ")...");
				s = new Socket(host, port);
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
				this.outputStream = new ObjectOutputStream((s.getOutputStream()));
				this.inputStream = new ObjectInputStream((s.getInputStream()));
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
			else{
			login.setVisible(false);
			gui = new Gui("Chat " + "localhost" + ":" + "8081" , this);
			thread = new Thread(this);
			thread.start();
					
			return true;
			}
		}

	
		private boolean verify(){

			ConnectionMessage cm = new ConnectionMessage();
	   	    cm.Pwd = login.StringPasswords;
	   	    cm.Username = login.StringUsername;
	   	    try{
				outputStream.writeObject(cm);
				LoginReply reply = (LoginReply)inputStream.readObject();
				if( reply.status == false &&reply.reason.equals("wrong")){
					JOptionPane.showMessageDialog( login,"password is wrong","Login failed",JOptionPane.ERROR_MESSAGE );
					return false;
				}
				if( reply.status == false &&reply.reason.equals("already")){
					JOptionPane.showMessageDialog( login,"Username exist already ","Login failed",JOptionPane.ERROR_MESSAGE );
					return false;
				}
				userlist = reply.Userlist;
				userlist.add(0, "all");
				return true;
			} catch( Exception e ){}
			
			return false;
		}


}
