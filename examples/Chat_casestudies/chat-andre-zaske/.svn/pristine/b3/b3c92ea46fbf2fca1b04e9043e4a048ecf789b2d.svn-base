package kernel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;

import network.server.Server;
import network.server.ServerPacketInHandler;

/**
 * TODO description
 */
public class Main {
	private BufferedReader consoleInput;

	private void startup() {
		// Welcome Message
		System.out.println("<a> Starting EPMD Server for Console ...");
	}

	private void init() {
		consoleInput = new BufferedReader(new InputStreamReader(System.in));
	}

	private void finish() {
		startCServer();
	}

	private void startCServer() {
		// Asking for Server port
		String sPort = getInputChecked("Server-Port",
				"Use S for Standardport 8080.", "[\\dS]+");
		int serverPort = 8080;
		if (!sPort.equals("S")) {
			serverPort = Integer.valueOf(sPort);
		}

		// Init and Start Server
		Server server = new Server(serverPort, new ServerPacketInHandler());
		try {
			server.start();
		} catch (BindException e1) {
			System.out.println("<e> Serverport kann nicht geöffnet werden, "
					+ "da die Addresse bereits verwendet wird.");
			System.out.println("<a> Shuting down...");
			System.exit(0);
		}
		System.out.println("<i> - Server is online ...");
		getInputChecked("Server-ActionCodes", "Use Q for Exit.", "[Q]");
		System.out.println("<a> Shuting down...");
		server.stop();
		System.exit(0);
	}

	private String getInput(String getWhat, String hint) {
		System.out.println("<?> Please enter " + getWhat + "!");
		// Showing Hint ?
		if (hint.length() > 1) {
			System.out.println("<h> " + hint);
		}
		System.out.print("=>");
		String strOut = "";
		try {
			strOut = consoleInput.readLine();
		} catch (IOException e) {
			System.out.println("<e> Error reading " + getWhat
					+ "\n <a> Shuting down...");
			System.exit(0);
		}
		System.out.println("");
		return strOut;
	}

	private String getInputChecked(String getWhat, String hint, String regex) {
		String strOut = getInput(getWhat, hint);
		while (!strOut.matches(regex)) {
			System.out.println("<w> Input is incorrect please try again.");
			strOut = getInput(getWhat, "");
		}
		return strOut;
	}
}