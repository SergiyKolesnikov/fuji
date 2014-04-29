package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import client.Client;
import common.*;

public class Console{
	
	private String _color = null;
	private String[] _colors = {"default color", "red", "green", "blue", "yellow", "orange" };
	private String _colorParam = "-setcolor";
	
	private boolean feat_Color_Color(Client client, String line) {
		
		if (line.toLowerCase().startsWith(_colorParam))
		{
			setColor(client, line.substring(_colorParam.length() + 1));
			return true;
		}
		return false;
	}

	private void setColor(Client client, String newColor){
		for(String color : _colors){
			if (newColor.toLowerCase().equals(color)) {
				_color = color;
				client.fireAddLine("Color set to " + color);
				return;
			}
		}
		_color = null;
		client.fireAddLine("Color set to default color");
	}
	
	private ITextMessage feat_Color_getColoredMessage(ITextMessage msg) {
		if (_color != null)
			return new ColorTextMessage(msg, _color);
		return msg;
	}
}
