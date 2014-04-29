package interfaces;

public interface IClientProxy {
	
	public void send(String line);
	
	public boolean addClient(IClient client);
	
	public boolean removeClient(IClient client);
}
