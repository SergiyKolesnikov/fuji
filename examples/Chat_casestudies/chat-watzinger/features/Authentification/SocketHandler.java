import java.net.Socket;

public abstract class SocketHandler implements Runnable {
	protected boolean authenticated;
	
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	
	public boolean isAuthenticated() {
		return authenticated;
	}

}
