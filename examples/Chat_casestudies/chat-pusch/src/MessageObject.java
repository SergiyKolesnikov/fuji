
import java.awt.Color;
import java.io.Serializable;



/**
 * message class for joChat.
 * MessageObjects can be send over the socket
 * and contain the message and information about it.
 * @author Jonas Pusch
 *
 */
public class MessageObject implements Serializable {

	private static final long serialVersionUID = 6830091317306744805L;
	private static final String WS= " ";
	private String messageType;
	private String sender;
	private String[] recipients;
	private Color color;
	private String message;
	// TODO: maybe store timestamp here?
	
	public MessageObject(String messageType, String sender, String[] recipients, Color color, String message) {
		this.messageType = messageType;
		this.sender=sender;
		this.recipients = recipients;
		this.color = color;
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		((MessageObject) this).message = message;
	}
	
	public Color getColor() {
		return color;
	}
	
	public String[] getRecipients() {
		return recipients;
	}
	
	public void setSender(String sender) {
		((MessageObject) this).sender=sender;
	}
	
	public String getSender() {
		return sender;
	}

	public String toString() {
		StringBuilder sb= new StringBuilder();
		sb.append("typ:");
		sb.append(messageType+WS);
		sb.append("sdr:");
		sb.append(sender+WS);
		sb.append("rec:");
		sb.append(java.util.Arrays.toString(recipients)+WS);
		sb.append("col:");
		if (color==null)
			sb.append("null"+WS);
		else
			sb.append(color.getRed()+"/"+color.getGreen()+"/"+color.getBlue()+WS);
		sb.append("msg:");
		if (message==null)
			sb.append("null");
		else
			sb.append("\""+message+"\"");
		return sb.toString();
	}
}