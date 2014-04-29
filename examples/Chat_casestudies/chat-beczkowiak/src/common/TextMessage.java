package common;

import java.io.Serializable;

import crypt.Crypt;



/**
 * Serializable message that can be send over the sockets between client and
 * server.
 */
abstract class TextMessage$$Base$common implements Serializable, IMessage {

    protected static final long serialVersionUID = -9161595018411902079L;
    protected String content;

    public TextMessage$$Base$common(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}



public class TextMessage extends  TextMessage$$Base$common  {
	
	protected byte[] contentCrypted;
	
	public void encode(Crypt crypt) {
        byte[] array = crypt.encode(((TextMessage) this).content);
        if (array != null) {
            ((TextMessage) this).content = "";
            ((TextMessage) this).contentCrypted = array;
        }
    }

    public void decode(Crypt crypt) {
        Object obj = crypt.decode(((TextMessage) this).contentCrypted);
        if (obj != null) {
            if (obj instanceof String) {
                ((TextMessage) this).content = (String) obj;
            } else {
                throw new RuntimeException("Failed to decode a textmessage.");
            }
        }
    }
      // inherited constructors



    public TextMessage ( String content ) { super(content); }
}