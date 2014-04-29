/**
 * TODO description
 */
public class PrivateMessage extends ContentMessage{
	private String to;
	
	public PrivateMessage(MessageContext context, Content payload, String to) {
		super(context, payload);
		this.to = to;
	}
	
	public String GetDestination(){
		return to;
	}
}