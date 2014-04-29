package gui.panels;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JTabbedPane;

import gui.Gui;
import gui.util.ButtonTabComponent;

public abstract class AbstractCloseTabPanel extends AbstractTabPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6323577295178413649L;

	public AbstractCloseTabPanel(LayoutManager layout, Gui g) {
		super(layout, g);
	}
	
	@Override
	public void addTabToPanel(JTabbedPane jtpane) {
		int pos = jtpane.getTabCount();
		jtpane.addTab(this.getName() + "    ", this.getIcon(), this,
				this.getToolTipText());
		ButtonTabComponent btc = new ButtonTabComponent(this.getName() + "    ",this.getIcon());
		btc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onTabButtonClose();	
			}
		});
		jtpane.setTabComponentAt(pos, btc);
	}
	
	public abstract void onTabButtonClose();

}
