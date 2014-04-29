import java.net.Socket; 

public  interface  SocketHandlerFactory {
	

	SocketHandler newInstance(Socket socket, ChatServer server);


}
