




import java.io.Serializable;

import java.awt.Color;



/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
abstract class Message$$Root implements Serializable{
	protected String content = "";
	protected String userName = "";
	
	/**
	 * Create a new message 
	 * 
	 * @param userName who sending the message 
	 * @param content of the message
	 */
	public Message$$Root(String userName, String content){
		setUserName(userName);
		setContent(content);
	}
	
	/**
	 * Create a new message 
	 *  
	 * @param content of the message
	 */
	public Message$$Root(String content){
		setContent(content);
	}
	
	/**
	 * set the content of the message 
	 *  
	 * @param content of the message
	 */
	public void setContent(String content){
		((Message) this).content = content;	
	}
	
	
	/**
	 * Set the user who sending the message 
	 *  
	 * @param userName who sending the message
	 */
	protected void setUserName(String userName){
		((Message) this).userName = userName;
	}
	
	/**
	 * Get the user who sending the message  
	 *  
	 * @param content of the message
	 */
	public String getUserName(){
		return userName;
	}
	
	/**
	 * Get the output line:
	 * user: message  
	 *  
	 * @return user: message 
	 */ 
	public String getUserName_And_Content(){
		if(userName.compareTo("") == 0){
			return getContent();
		}else{
			return userName + ": " + getContent();
		}
	}
	
	/**
	 * return the content of message
	 * 
	 * @return content of message
	 */
	public String getContent(){
		return content; 
	}
}



abstract class Message$$Security extends  Message$$Root  implements Serializable{
	static Cryptography crypto;
	
	public void setContent(String content){
		
		if(crypto == null){
			crypto = Cryptography.create();
		}	
		if(crypto != null){
			content = crypto.encrypt(content);
		}
	
		super.setContent(content);
	}
	
	
	public String getContent(){
		String content = super.getContent();
		
		if(crypto == null){
			crypto = Cryptography.create();
		}	
		if(crypto != null){
			content = crypto.decrypt(content);
		}
		
		return content;
	}
      // inherited constructors


	
	/**
	 * Create a new message 
	 *  
	 * @param content of the message
	 */
	public Message$$Security ( String content ) { super(content); }
	
	/**
	 * Create a new message 
	 * 
	 * @param userName who sending the message 
	 * @param content of the message
	 */
	public Message$$Security ( String userName, String content ) { super(userName, content); }
	
	
}




public class Message extends  Message$$Security  {
	Color textColor = Color.BLACK;
	
	public Color getColor(){
		return textColor;	
	}
	
	public void setColor(Color color){
		textColor = color;	
	}
      // inherited constructors


	
	/**
	 * Create a new message 
	 *  
	 * @param content of the message
	 */
	public Message ( String content ) { super(content); }
	
	/**
	 * Create a new message 
	 * 
	 * @param userName who sending the message 
	 * @param content of the message
	 */
	public Message ( String userName, String content ) { super(userName, content); }
}