package features;

import client.Gui;

public class AuthentificationPlugin implements interfaces.AuthentificationInterface{

	private Gui gui = null;
	
	public AuthentificationPlugin(Gui gui, String password) {
		this.gui = gui;
		
		if(!checkPassword(password)) {
			this.gui.wrongPassword();
			
		}
	}

		public boolean checkPassword(String password) {
			if (password.equalsIgnoreCase("epmd")) {
				return true;
			} else {
				return false;
			}

		}

}
