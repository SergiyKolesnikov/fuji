package FullGUI;

import java.io.Serializable;



/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
abstract class TextMessage$$Chat implements Serializable {

	private static final long serialVersionUID = -9161595018411902079L;
	
	protected String content;

	public TextMessage$$Chat(String content) {
		setContent(content);
	}
	
	public void setContent(String content) {
		((TextMessage) this).content = content;
	}

	public String getContent() {
		return content;
	}

}



/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
abstract class TextMessage$$BackwardsEncryption extends  TextMessage$$Chat  {

	public void setContent(String content) {
		super.setContent(backwards(content));
	}

	public String getContent() {
		return backwards(super.getContent());
	}

	private String backwards(String text) {
		char[] chars = new char[text.length()];
		for (int i = 0; i < text.length(); i++)
			chars[i] = text.charAt(text.length() - i - 1);
		return new String(chars);
	}
      // inherited constructors



	public TextMessage$$BackwardsEncryption ( String content ) { super(content); }

}



/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
public class TextMessage extends  TextMessage$$BackwardsEncryption  {

	private static final int KEY = 13;
	
	public void setContent(String content) {
		super.setContent(rotate(content, KEY));
	}

	public String getContent() {
		return rotate(super.getContent(), -KEY);
	}

	private String rotate(String text, int key) {
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++)
			chars[i] = (char) (chars[i] + key);
		return new String(chars);
	}
      // inherited constructors



	public TextMessage ( String content ) { super(content); }

}