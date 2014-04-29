package network.client; 

import java.util.EventObject; 

import network.server.packets.DataPacket; 

public  class  InPacketEvent  extends EventObject {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7844159283614309677L;

	

	private DataPacket dataPacket;

	
	
	public InPacketEvent(Object source, DataPacket packet) {
		super(source);
		dataPacket = packet;
	}

	
	
	public DataPacket getDataPacket(){
		return dataPacket;
	}


}
