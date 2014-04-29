package gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import gui.Gui;
import gui.util.GuiTLH;
import gui.util.ImagePanel;

public class GlobePanel extends AbstractTabPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6529096996155297547L;

	private JButton btCreateClient;

	public GlobePanel(Gui g) {
		super(new BorderLayout(), g);
		init();
	}

	private void init() {
		setName("Globe");
		setToolTipText("Start Server or Clients");
		setIcon(GuiTLH.loadImageIcon("Globe.png"));
		initView();
	}

	private void initView() {
		// Splash Preview
		JPanel northPanel = new JPanel();
		ImagePanel imgView = new ImagePanel(550, 300);
		imgView.setImage(GuiTLH.loadImage("splashscreen.png"));
		northPanel.add(imgView);

		// Buttons
		JPanel panelBTContainer = new JPanel();
		panelBTContainer.setLayout(new BoxLayout(panelBTContainer,
				BoxLayout.LINE_AXIS));
		// StartServer
		JButton btStartServer = new JButton("Server erstellen");
		btStartServer.setIcon(GuiTLH.loadImageIcon("Server.png"));
		btStartServer.addActionListener(this);
		btStartServer.setActionCommand("btStartServer");
		panelBTContainer.add(btStartServer);
		// RigidArea
		panelBTContainer.add(Box.createRigidArea(new Dimension(100, 0)));
		// CreateClient
		btCreateClient = new JButton("Client erstellen");
		btCreateClient.setIcon(GuiTLH.loadImageIcon("Client.png"));
		btCreateClient.addActionListener(this);
		btCreateClient.setActionCommand("btCreateClient");
		panelBTContainer.add(btCreateClient);

		JPanel centerPanel = new JPanel();
		centerPanel.add(panelBTContainer);


		// Finalize
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEntering() {
		if (getGui().tabsCount() < 8) {
			btCreateClient.setEnabled(true);
		}
	}

	@Override
	public void onLeaving() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String acom = e.getActionCommand();
		if (acom.equals("btStartServer")) {
			JButton btStartServer = (JButton) e.getSource();
			btStartServer.setEnabled(false);
			getGui().insertTabPanel(new ServerPanel(getGui()), 1);
		} else if (acom.equals("btCreateClient")) {
			getGui().addTabPanel(new ClientPanel(getGui()));
			if (getGui().tabsCount() > 7) {
				JButton btCreateClient = (JButton) e.getSource();
				btCreateClient.setEnabled(false);
			}
		}

	}

}
