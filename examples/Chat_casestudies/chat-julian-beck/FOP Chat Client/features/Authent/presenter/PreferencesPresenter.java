package presenter;

import javax.swing.JOptionPane;

public class PreferencesPresenter extends Observable implements ActionListener,
		Observer {

	private final String pasword = "pw1337";
	private boolean correctAuthentification;

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		correctAuthentification = false;
		if (e.getSource().equals(view.getCancelButton())) {
			view.getFrame().setVisible(false);
		}
		if (e.getSource().equals(view.getOkButton())) {
			
			if (view.getPwField().getText().equals(pasword)) {
				init();
			} else {
				JOptionPane.showMessageDialog(view.getFrame(),
						"Incorrect password.", "Authentification failed.",
						JOptionPane.ERROR_MESSAGE);
			}
			if (correctAuthentification) {
				view.getFrame().setVisible(false);
			}
		}
	}

	private void init() {
		original();
		correctAuthentification = true;
	}
	
	public void update(Observable o, Object arg) {
		if (o instanceof ViewPresenter) {
			view.getFrame().setVisible(true);
		}
	}
	
}
