package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import gui.panels.AbstractTabPanel;
import gui.panels.GlobePanel;
import gui.util.GuiTLH;
import gui.util.ToolButton;

public class Gui extends JFrame implements ChangeListener, ActionListener,
		WindowListener {
	/**
	 * 
	 */
	// AppVars
	private static final long serialVersionUID = 4965873780237833495L;
	private static final Dimension appSize = new Dimension(1024, 768);
	private static final int appPosX = (GuiTLH.getscreenWidth() / 2)
			- (appSize.width / 2);
	private static final int appPosY = (GuiTLH.getscreenHeight() / 2)
			- (appSize.height / 2);
	private static Rectangle appBounds = new Rectangle(appPosX, appPosY,
			appSize.width, appSize.height);
	private static final String appTitle = "EPMD - Programmieraufgaben";
	// BasicPanel
	private JPanel contentPanel = null;
	// ToolBar
	private JToolBar toolbar = null;
	// SplitPane
	private JSplitPane verSplitPane = null;
	// Tabbing
	private JTabbedPane mainTabbedPane = null;
	private ArrayList<AbstractTabPanel> mainTabbedPanels = null;
	private int activPanelID = 0;

	// Debug JTextPane
	private JTextPane debugTextPane = null;

	public Gui() {
		super(appTitle);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			String msg = e.getMessage();
			if (msg == null)
				msg = "Undefinierter Fehler.";
			JOptionPane.showMessageDialog(null, msg, "Kritischer Fehler",
					JOptionPane.ERROR_MESSAGE);
		}
		init();
	}

	private void init() {
		contentPanel = new JPanel(new BorderLayout());
		mainTabbedPanels = new ArrayList<AbstractTabPanel>();
		setContentPane(contentPanel);
		// initToolbar();
		initView();
		initListener();
	}

	private void initView() {
		// Tabbs
		mainTabbedPane = new JTabbedPane(JTabbedPane.TOP,
				JTabbedPane.SCROLL_TAB_LAYOUT);
		mainTabbedPane.setBorder(null);
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
		mainTabbedPane.setFont(font);
		mainTabbedPane.addChangeListener(this);
		// Globe-Tab
		addTabPanel(new GlobePanel(this));
		// Debug TextArea
		debugTextPane = new JTextPane();
		debugTextPane.setEditable(false);
		// Makes text red
		Style style = debugTextPane.addStyle("Red", null);
		StyleConstants.setForeground(style, Color.red);

		// SplitPane
		verSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
				mainTabbedPane, new JScrollPane(debugTextPane));
		verSplitPane.setOneTouchExpandable(true);
		verSplitPane.setDividerSize(10);
		verSplitPane.setDividerLocation(1.0d);
		// Finalize
		contentPanel.add(verSplitPane, BorderLayout.CENTER);
	}

	@SuppressWarnings("unused")
	private void initToolbar() {
		// Init Toolbar
		toolbar = new JToolBar("Configuration");
		toolbar.setFloatable(false);
		toolbar.setMargin(new Insets(2, 0, 2, 0));
		// Load
		ToolButton loadButton = new ToolButton(
				GuiTLH.loadImageIcon("OpenConfig.png"), "Open");
		loadButton.setToolTipText("Load old saved state.");
		loadButton.setActionCommand("btload");
		loadButton.addActionListener(this);
		toolbar.add(loadButton);
		toolbar.addSeparator();
		// Save
		ToolButton saveButton = new ToolButton(
				GuiTLH.loadImageIcon("SaveConfig.png"), "Save");
		saveButton.setToolTipText("Save current state.");
		saveButton.setActionCommand("btsave");
		saveButton.addActionListener(this);
		toolbar.add(saveButton);
		toolbar.addSeparator();
		// Show Config
		ToolButton configButton = new ToolButton(
				GuiTLH.loadImageIcon("ShowConfig.png"), "Show");
		configButton.setToolTipText("Shows current Configuration.");
		configButton.setActionCommand("btshow");
		configButton.addActionListener(this);
		toolbar.add(configButton);

		// Finalize
		contentPanel.add(toolbar, BorderLayout.NORTH);
	}

	private void initListener() {
		this.addWindowListener(this);
	}

	public void addTabPanel(AbstractTabPanel tpanel) {
		tpanel.addTabToPanel(mainTabbedPane);
		mainTabbedPanels.add(tpanel);
	}

	public void insertTabPanel(AbstractTabPanel tpanel, int pos) {
		pos = Math.max(pos, 1);
		mainTabbedPane.insertTab(tpanel.getName(), tpanel.getIcon(), tpanel,
				tpanel.getToolTipText(), pos);
		mainTabbedPanels.add(tpanel);
	}

	public void removeTabPanel(AbstractTabPanel tpanel) {
		int pos = mainTabbedPane.indexOfComponent(tpanel);
		tpanel.onClose();
		mainTabbedPane.removeTabAt(pos);
		mainTabbedPanels.remove(tpanel);
	}

	public int tabsCount() {
		return mainTabbedPane.getTabCount();
	}

	public void createAndShow() {
		setIconImage(GuiTLH.loadImageIcon("logo.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(appBounds);
		setMinimumSize(appSize);
		setVisible(true);
		verSplitPane.setDividerLocation(1.0d);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	// ChangeListener für das TabbedPanel
	public void stateChanged(ChangeEvent evt) {
		JTabbedPane pane = (JTabbedPane) evt.getSource();
		// Get current tab
		int newPanelID = pane.getSelectedIndex();
		if (activPanelID != newPanelID) {
			mainTabbedPanels.get(activPanelID).onLeaving();
			mainTabbedPanels.get(newPanelID).onEntering();
			validate();
			repaint();
			activPanelID = newPanelID;
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		for (AbstractTabPanel atp : mainTabbedPanels) {
			atp.onClose();
		}

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
