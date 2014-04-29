package client;

import javax.swing.JComboBox;

import common.*;

public class Gui {

	private JComboBox _colorList = null;
	private String[] _colors = {"default color", "red", "green", "blue", "yellow", "orange" };
	
	
	private ITextMessage feat_Color_getColoredMessage(ITextMessage msg) {
		
		String color = (String)feat_Color_getColorChoiseComboBox().getSelectedItem();
		// insert color tags if a color is chosen (per decorator)
		if (color != "default color") 
			return new ColorTextMessage(msg, color);
		
		return msg;
	}
	
	private JComboBox feat_Color_getColorChoiseComboBox() {
		if (_colorList == null) {
			_colorList = new JComboBox();
			for(String color : _colors) {
				_colorList.addItem(color);
			}
		}
		return _colorList;
	}
}
