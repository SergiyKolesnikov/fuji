package engine; 

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.EventListener; 

public  class  InputThread  implements Runnable {
	

	private InputThreadListener Listener = null;

	

	public void setInputThreadListener(InputThreadListener inputThreadListener) {
		Listener = inputThreadListener;
	}

	

	@Override
	public void run() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		while (true) {
			do {
				try {
					// wait until we have data to complete a readLine()
					while (!br.ready()) {
						Thread.sleep(200);
					}
					input = br.readLine();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while ("".equals(input));
			// fire Status
			if (Listener != null) {
				if (input.equals("Q")) {
					Listener.shuttingDown();
					break;
				} else {
					Listener.newInput(input);
				}
			}
		}
	}

	

	public  interface  InputThreadListener  extends EventListener {
		
		public void newInput(String msg);

		
		public void shuttingDown();


	}


}
