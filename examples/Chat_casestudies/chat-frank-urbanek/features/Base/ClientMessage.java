
public class ClientMessage extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private ClientInfo clientInfo;
	private ContentMessage payload;
	

	
	public ClientMessage(MessageContext context, ContentMessage payload) {
		super(context);
		this.payload = payload;
		//this.context = ;
	}

	
	
	public ContentMessage getPayload() {
		return payload;
	}

}
