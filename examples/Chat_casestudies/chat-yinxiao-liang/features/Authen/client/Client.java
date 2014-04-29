package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.JOptionPane;

import common.ConnectionMessage;
import common.LoginReply;
import common.UserMessage;

import client.Gui;
import client.Login;

/**
 * TODO description
 */
public class Client {
	public Gui gui;
	public Vector userlist;
	public Login login;
		public Client(boolean LoginFlag){
			 login = new Login("chat", this);
			
		}
		synchronized boolean connectToServer(boolean LoginFlag)
		{
			boolean Flag = false;
			original(Flag);
			return true;
		}
		
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
			original(msg);
		}
}