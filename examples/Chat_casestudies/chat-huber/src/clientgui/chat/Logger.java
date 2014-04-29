package chat; 

import java.io.BufferedWriter; 
import java.io.File; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.util.Observable; 
import java.util.Observer; 

import chat.messages.AMessage; 

import chat.messages.ColoredTextMessage; 
import chat.messages.DefaultTextMessage; 
import chat.messages.LoginMessage; 
import chat.messages.StatusMessage; 
import chat.messages.PrivateMessage; 

public   class  Logger  extends AMessageVisitor  implements Observer {
	
	private BufferedWriter writer;

	

	public Logger(IMessageVisitor next, String logfilename) {
		super(next);
		if (logfilename == null) {
			logfilename = "log_" + System.currentTimeMillis() + ".txt";
		}

		try {
			writer = new BufferedWriter(new FileWriter(new File(logfilename)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	@Override
	public void visit(DefaultTextMessage message) {
		log("> " + message.getOrigin() + ": " + message.getContent() + "\n");
		forward(message);
	}

	

	@Override
	public void visit(LoginMessage message) {
		log(message.getOrigin() + " logs in\n");
		forward(message);
	}

	

	@Override
	public void visit(StatusMessage message) {
		log(message.getStatus() + "\n");
		forward(message);
	}

	

	public void log(String msg) {
		if (writer != null) {
			try {
				writer.write(msg);
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof AMessage) {
			((AMessage) arg1).accept(this);
		}
	}

	
	@Override
	public void visit(ColoredTextMessage message) {
		log("> " + message.getOrigin() + " ( " + message.getColor() + " ): "
				+ message.getContent() + "\n");
		forward(message);
	}

	

	@Override
	public void visit(PrivateMessage message) {
		log("> " + message.getOrigin() + " -> " + message.getUsername() + ": " + message.getContent() + "\n");
		forward(message);
	}


}
