public class AuthenticationResultMessage implements Message {

	private static final long serialVersionUID = 8802396596233368448L;
	private boolean successful;
	
	public AuthenticationResultMessage(boolean successful) {
		this.successful = successful;
	}
	
	public boolean isSuccessful() {
		return successful;
	}
	
}
