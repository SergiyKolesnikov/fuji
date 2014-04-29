package chat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import chat.messages.ColoredTextMessage;
import chat.messages.DefaultTextMessage;
import chat.messages.LoginMessage;
import chat.messages.StatusMessage;

public class Logger extends AMessageVisitor {
	@Override
	public void visit(ColoredTextMessage message) {
		log("> " + message.getOrigin() + " ( " + message.getColor() + " ): "
				+ message.getContent() + "\n");
		forward(message);
	}
}
