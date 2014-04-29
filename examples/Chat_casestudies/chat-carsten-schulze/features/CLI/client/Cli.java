package client;

import common.Message;

public class Cli implements ChatPlugin {

	private byte type = 0x10;
	/**
	 * simple cli output of incomming messages
	 * @param client assoziated Client
	 */
	public Cli (Client client){
		client.loadPlugin(this);
		System.out.println("CLI loaded.");
	}
	@Override
	public byte getType() {
		return type;
	}

	@Override
	public Message process(Message msg) {
		System.out.println(msg.getText());
		return msg;
	}

}
