package gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.BindException;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import gui.Gui;
import gui.util.GuiTLH;
import gui.util.ImagePanel;
import network.server.Server;
import network.server.ServerPacketInHandler;

public class ServerPanel extends AbstractTabPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7288908967029691847L;

	private Server server = null;
	private JSpinner spinnerPort;
	private JComboBox<String> cbCipherMethod;
	private TreeMap<String,String> knowCiphers;

	public ServerPanel(Gui g) {
		super(new BorderLayout(), g);
		init();
		initView();	
	}

	private void init() {
		setName("Server");
		setToolTipText("Global Serverpanel");
		setIcon(GuiTLH.loadImageIcon("Server.png"));
		knowCiphers = new TreeMap<String,String>();
	}

	private void initView() {
		// Splash Preview
		JPanel rahmen = new JPanel();
		ImagePanel imgView = new ImagePanel(550, 300);
		imgView.setImage(GuiTLH.loadImage("epmd_server.png"));
		rahmen.add(imgView);

		// Layout
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

		// Port-Field
		spinnerPort = new JSpinner(new SpinnerNumberModel(8080, 1024, 49151, 1));
		panel.add(spinnerPort);

		// RigidArea
		panel.add(Box.createRigidArea(new Dimension(20, 0)));

		// Ciphering
		// Handle Plugins
		String arrayString = "Keine Verschlüsselung";
		for (String chipher : knowCiphers.keySet()) {
			arrayString += "#0#" + chipher;
		}
		cbCipherMethod = new JComboBox<String>(arrayString.split("#0#"));
		panel.add(cbCipherMethod);

		// RigidArea
		panel.add(Box.createRigidArea(new Dimension(20, 0)));
		
		// Button
		JButton btStartServer = new JButton("Server starten");
		btStartServer.addActionListener(this);
		panel.add(btStartServer);

		// Border 2
		JPanel rahmen2 = new JPanel();
		rahmen2.add(panel);
		// Finalize
		add(rahmen, BorderLayout.NORTH);
		add(rahmen2, BorderLayout.CENTER);
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEntering() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLeaving() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClose() {
		if (server != null) {
			server.stop();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btStartServer = (JButton) e.getSource();
		int port = ((Number) spinnerPort.getValue()).intValue();
		String chipherName = (String)cbCipherMethod.getSelectedItem();
		String cipherClassName = knowCiphers.get(chipherName);
		ServerPacketInHandler serverPacketInHandler = new ServerPacketInHandler();
		if(cipherClassName != null){
			serverPacketInHandler.setCipherName(cipherClassName);
		}
		
		server = new Server(port, serverPacketInHandler);
		try {
			server.start();
			btStartServer.setEnabled(false);
			spinnerPort.setEnabled(false);
			cbCipherMethod.setEnabled(false);
			
		} catch (BindException e1) {
			JOptionPane.showMessageDialog(null,
					"Serverport kann nicht geöffnet werden, "
							+ "da die Addresse bereits verwendet wird.",
					"Serverport bereits besetzt", JOptionPane.ERROR_MESSAGE);
		}
	}
}
