package presenter;

import view.GUI;

public class PreferencesPresenter extends Observable implements ActionListener,
		Observer {
	
	private void buildGUI(Socket skt) {
		GUI window = new GUI(skt, app);
		window.getFrame().setVisible(true);
	}
}
