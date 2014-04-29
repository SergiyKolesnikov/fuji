package presenter;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import model.Application;

import view.PreferencesView;

public class PreferencesPresenter extends Observable implements ActionListener,
		Observer {
	private PreferencesView view;
	private Application app;

	public PreferencesPresenter(PreferencesView preferencesView, Application app) {
		this.view = preferencesView;
		this.app = app;
		app.setPrefPres(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(view.getCancelButton())) {
			view.getFrame().setVisible(false);
		}
		if (e.getSource().equals(view.getOkButton())) {
				init();
				view.getFrame().setVisible(false);
		}
	}
	
	private void init() {
		String ipString = view.getIpField().getText();
		int port = Integer.parseInt(view.getPortField().getText());
		try {
			InetAddress ip = InetAddress.getByName(ipString);
			final Socket skt = new Socket(ip, port);
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						buildGUI(skt);
						buildConsole(skt);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (UnknownHostException convertExcept) {
			JOptionPane
					.showMessageDialog(view.getFrame(),
							"Error converting IP.\n"
									+ "System will shut down.",
							"I/O error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (IOException ioExcept) {
			JOptionPane
					.showMessageDialog(view.getFrame(),
							"Error creating socket."
									+ "System will shut down.",
							"I/O error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	public void update(Observable o, Object arg) {
		if (o instanceof ViewPresenter) {
			view.getFrame().setVisible(true);
		}
	}
	
	private void buildGUI(Socket skt) {
	}
	
	private void buildConsole(Socket skt) {
	}
}
