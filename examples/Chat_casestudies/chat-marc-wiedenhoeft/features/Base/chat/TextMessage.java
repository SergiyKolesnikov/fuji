package chat;

import java.io.Serializable;

/**
 * This class provides a serializable message object that can be send over a
 * socket to share messages between server and clients.
 * 
 * @author Marc Wiedenhöft
 * @version 1.0
 */
public class TextMessage implements Serializable 
{
	private static final long serialVersionUID = -9161595018411902069L;
	
	private String 	mContent; //Message text
	private String  mFrom = "anonym";

	public TextMessage(String from, String content) 
	{
		super();
		
		this.mFrom 			= from;
		this.mContent 		= content;
	}

	/**
	 * @return - Message text 
	 */
	public String getContent() 
	{
		return this.mContent;
	}
	
	/**
	 * @param content - Message text
	 */
	public void setContent( String content )
	{
		this.mContent = content;
	}

	/**
	 * @param from - Author name
	 */
	public void setFrom( String from )
	{
		this.mFrom = from;
	}
	
	/**
	 * @return - Author name
	 */
	public String getFrom()
	{
		return this.mFrom;
	}

	private int 	mCryptoType = 0; //Encryption type
	
	/** 
	 * @return
	 */
	public int getCryptoType()
	{
		return this.mCryptoType;
	}
	
	/**
	 * Sets the encryption type:<br>
	 * - none<br>
	 * - swap first 2 letters<br>
	 * - ROT13<br>
	 * <br>
	 * Look at the Crypto class.<br>
	 * 
	 * @param crypto - Encryption type
	 */
	public void setCryptoType( int crypto )
	{
		this.mCryptoType = crypto;
	}	
}