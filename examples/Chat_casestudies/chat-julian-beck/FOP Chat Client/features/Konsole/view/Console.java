package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Observable;
import presenter.ViewPresenter;
import model.Application;
import model.Client;


public class Console extends Observable implements Runnable {
	
	Thread thread;
	private BufferedReader inStream;
	private Client client;

	public Console(Socket skt, Application app) {
		ViewPresenter presenter = new ViewPresenter(this, skt, app);
		client = new Client(skt);
		
		client.addObserver(presenter);
		this.addObserver(presenter);
	}

	public void printMessage(String message) {
		setChanged();
		notifyObservers(message);
	}
	
	public void start () {
		inStream = new BufferedReader(new InputStreamReader(System.in));
		if (thread == null) {	
			thread = new Thread(this);
			thread.setPriority(Thread.MIN_PRIORITY);
			thread.start();
		}
	}

	public void run() {
		while (true) {
			try {
				String text = inStream.readLine();
				printMessage(text);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	public Client getClient() {
		return client;
	}

}
