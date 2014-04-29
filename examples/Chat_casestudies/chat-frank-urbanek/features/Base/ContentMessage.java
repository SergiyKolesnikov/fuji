public class ContentMessage extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private ClientInfo clientInfo;
	protected Content payload;
	
	public ContentMessage(MessageContext context, Content payload) {
		super(context);
		this.payload = payload;
	}

	public Content getPayload() {
		return payload;
	}

}
