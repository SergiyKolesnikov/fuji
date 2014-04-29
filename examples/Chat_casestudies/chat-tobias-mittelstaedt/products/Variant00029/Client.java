package client; 

import client.Gui; 

import common.TextMessage; 

public  class  TextColorPlugin  implements interfaces.MessageInterface {
	

	public Client(String[] args){
		original();
		gui.enableElement("lblNewLabel");
		gui.enableElement("rdbtnNewRadioButton");
		gui.enableElement("rdbtnNewRadioButton_1");
		gui.enableElement("rdbtnNewRadioButton_2");
		gui.enableElement("rdbtnNewRadioButton_3");
	}

	

	public void send(String line) {
		String color = Gui.getSelection("color").getText();
		line = "<" + color + ">" + line + "</" + color + ">";
		original(line);
		}


}
