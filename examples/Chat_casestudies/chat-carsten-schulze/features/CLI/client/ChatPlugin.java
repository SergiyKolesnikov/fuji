package client;

import common.Message;
/**
 * Listener that gets informed every time when the chat client receives a new
 * message
 */
public interface ChatPlugin {
	/**
	 * Get Type of Plugin bitcoded:
	 * 	  Bit - 
	 * 		0 - 0x01 Dummy Plugin 
	 * 		1 - 0x02 Authentication
	 * 		2 - 0x04 Encryption
	 * 		3 - 0x08 Filter
	 * 		4 - 0x10 UserInterface
	 * 		5 - 0x20
	 * 		6 - 0x40
	 * 		7 - 0x80 Error 
	 *  
	 * @return Byte containing the typeflags
	 */
	public byte getType();
	
	/**
	 * The given message is processed and than returned by plugin
	 * 
	 * @param msg Message for the Plugin
	 * @return Processed Message form Plugin
	 */
	public Message process(Message msg);
}
