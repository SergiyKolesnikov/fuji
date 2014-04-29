package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import server.Connection;

import common.Message;

public class Server {
	private List connections;
	private ServerSocket socket;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Server().run();
	}
	
	public void run() {
		try {
			socket = new ServerSocket(8017);
		} catch (IOException e) {
			e.printStackTrace();
		}
		connections = new ArrayList();
		while(!Thread.interrupted()) {
			try {
				connections.add(new Connection(socket.accept(), this));
				System.out.println("connected");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void broadcast(Message m) {
		for (int i = 0; i < connections.size(); i++) {
			((Connection)connections.get(i)).send(m);
		}
	}
	
	public void sendTo(String to, Message m) {
		for (Iterator i = connections.iterator(); i.hasNext();) {
			Connection c = (Connection) i.next();
			if(c.getUsername().equals(to)) {
				c.send(m);
			}
		}
	}

	public void disconnect(Connection c) {
		connections.remove(c);
		System.out.println("disconnected");
	}

}
