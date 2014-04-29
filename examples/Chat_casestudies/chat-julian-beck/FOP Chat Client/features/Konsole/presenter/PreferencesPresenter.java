package presenter;

import view.Console;

public class PreferencesPresenter extends Observable implements ActionListener,
		Observer {

	private void buildConsole(Socket skt) {
		new Console(skt, app).start();
	}
}
