package model;

import presenter.PreferencesPresenter;
import view.PreferencesView;

public class Application {
	
	protected static PreferencesView dialoge;
	private PreferencesPresenter prefPres;

	public static void main(String args[]) {
		Application app = new Application();
		dialoge = new PreferencesView(app);
	}

	public PreferencesPresenter getPrefPres() {
		return prefPres;
	}

	public void setPrefPres(PreferencesPresenter prefPres) {
		this.prefPres = prefPres;
	}
}
