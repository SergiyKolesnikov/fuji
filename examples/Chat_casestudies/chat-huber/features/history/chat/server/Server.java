package chat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import chat.IClient;
import chat.IMessageVisitor;
import chat.Logger;
import chat.client.ClientUnicastVisitor;
import chat.messages.AMessage;
import chat.server.visitors.AuthenticationVisitor;
import chat.server.visitors.BroadcastVisitor;
import chat.server.visitors.ServerUnicastVisitor;
import chat.server.visitors.SpamFilterVisitor;

public class Server implements IServer {

	private Logger logger = new Logger(null, "serverlog.txt");

	private void receive(AMessage message, IClient client) {
		original(message, client);

		message.accept(logger);
	}
}
