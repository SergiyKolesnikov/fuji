import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; public   class  ObjectStreamSocketHandler  extends SocketHandler {
	

	private ObjectInputStream in;

	
	private ObjectOutputStream out;

	

	public ObjectStreamSocketHandler(Socket socket, ChatServer server) {
		super(socket, server);
		try {
			this.in = new ObjectInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	@Override
	public void run() {
		try {
			Object o = null;
			while ((o = in.readObject()) != null) {

				if (o instanceof Message)
					server.processMessage((Message) o, this);
			}
		} catch (IOException e) {
			// e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			server.removeHandle(this);
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	

	 private void  send__wrappee__Username(Message msg) {
			try {
				synchronized (out) {
					out.writeObject(msg);
				}

				out.flush();
			} catch (IOException e) {
				// e.printStackTrace();
			}
	}

	

	@Override
	public void send(Message msg) {

		if (authenticated) {
			send__wrappee__Username(msg);
		}
	}


}
