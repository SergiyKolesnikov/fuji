
import java.io.IOException;

import javax.swing.SwingUtilities;



public class Program {
	public static void main(String args[]) throws IOException {
		if (args.length < 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");
		ConfigUtil.setConfig(args);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Gui inst = new Gui();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
}
