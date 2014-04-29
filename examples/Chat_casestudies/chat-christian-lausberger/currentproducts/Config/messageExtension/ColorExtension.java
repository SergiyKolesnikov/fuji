package messageExtension; 

import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.FileReader; 
import java.io.FileWriter; 
import java.io.IOException; 

import common.TextMessage; 

import client.Client; 
import client.IMessageExtension; 

public  class  ColorExtension  implements IMessageExtension {
	

	private static String color = null;

	

	public TextMessage extendMessage(TextMessage msg, Client client) {
		if (color == null) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(
						"color.txt"));
				color = reader.readLine();
				String rest = "", line;
				while ((line = reader.readLine()) != null) {
					rest = rest + line + System.getProperty("line.separator");
				}
				reader.close();
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						"color.txt"));
				writer.write(rest);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		msg.setColor(color);
		return msg;
	}

	

	public void stop() {
		if (color != null) {

			try {
				BufferedReader reader = new BufferedReader(new FileReader(
						"color.txt"));
				String rest = "", line;
				while ((line = reader.readLine()) != null) {
					rest = rest + line + System.getProperty("line.separator");
				}
				rest = rest + color;
				reader.close();
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						"color.txt"));
				writer.write(rest);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			color = null;
		}
	}

	

	public TextMessage readMessage(TextMessage msg) {
		String text = "<" + msg.getColor() + ">" + msg.getContent() + "</"
				+ msg.getColor() + ">";
		msg.setContent(text);
		return msg;
	}


}
