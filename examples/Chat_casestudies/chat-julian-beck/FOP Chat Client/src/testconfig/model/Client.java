package model; 

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.io.PrintStream; 
import java.net.Socket; 
import java.util.Observable; 
import javax.swing.JOptionPane; 

public  class  Client  extends Observable  implements Runnable {
	
	public static final int PORT = 5555;

	
	private Socket socket;

	
	BufferedReader in;

	
	PrintStream out;

	
	Thread thread;

	

	public Client(Socket skt) {
		this.socket = skt;
	}

	

	public void start() {
		try {
			in = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			out = new PrintStream(socket.getOutputStream());
		} catch (IOException ioExcept) {
			JOptionPane.showMessageDialog(null, "Error while starting client.",
					"Socket error.", JOptionPane.ERROR_MESSAGE);
		}
		if (thread == null) {
			thread = new Thread(this);
			thread.setPriority(Thread.MIN_PRIORITY);
			thread.start();
		}
	}

	

	@SuppressWarnings("deprecation")
	public void stop() {
		try {
			socket.close();
		} catch (IOException ioExcept) {
			JOptionPane.showMessageDialog(null, "Error while stoping client.",
					"Socket error.", JOptionPane.ERROR_MESSAGE);
		}
		if (thread != null && thread.isAlive()) {
			thread.stop();
			thread = null;
		}
	}

	

	@SuppressWarnings("deprecation")
	public void run() {
		String line;
		while (true) {
			try {
				line = in.readLine();
				if (line != null) {
					newMessage(line);
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Lost Connection to Server.", "Connecting failed.",
						JOptionPane.ERROR_MESSAGE);
				thread.stop();
			}
		}
	}

	

	public void newMessage(String msg) {
		this.setChanged();
		notifyObservers(msg);
	}

	

	public PrintStream getOut() {
		return out;
	}

	

	public void setOut(PrintStream out) {
		this.out = out;
	}


}
