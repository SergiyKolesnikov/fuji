public class ServerMessage extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextMessage payload;
	
	public ServerMessage(MessageContext context, TextMessage payload) {
		super(context);
		this.payload = payload;
	}

	public TextMessage getPayload() {
		return payload;
	}
	


}
