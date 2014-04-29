package common; 

import java.io.Serializable; 
/**
 * 	@author Carsten Schulze
 *	
 *	Serializable Message Object for chatappicaliptions
 */
public  class  Message  implements Serializable {
	
	
	private static final long serialVersionUID = -9161595018411902079L;

	
	protected String text;

	
	protected byte status;

	
	protected String dest,src;

	
	
//	public Message(String text) {
//		super();
//		this.text	= text;
//		this.status	= 0;
//		this.src	= null;
//		this.dest	= null;
//	}
	public Message(String text,byte status) {
		super();
		this.text	= text;
		this.status	= status;
		this.src	= null;
		this.dest	= null;
	}

	
	public Message(String text,byte status, String src, String dest) {
		super();
		this.text	= text;
		this.status	= status;
		this.src	= src;
		this.dest	= dest;
	}

	
	/**
	 * Get the payload text of the message.
	 * @return String content.
	 */
	public String getText() {
		return text;
	}

	
	/**
	 * Get Status of the Message.
	 * 		
	 * 		0 - plain text
	 * 		1 - authenticate
	 * 		2 - encrypted
	 * 		3 - private message
	 * 		...
	 * 		7 - error message 0b10000000
	 * @return Byte containing the inner State of the message.
	 */
	public byte getStatus(){
		return status;
	}

	
	/**
	 * Set the Statusbyte. 
	 * @param Byte status
	 */
	public void setStatus(byte status){
		this.status =status;
	}

	
	public String getDestination(){
		return dest;
	}


}
