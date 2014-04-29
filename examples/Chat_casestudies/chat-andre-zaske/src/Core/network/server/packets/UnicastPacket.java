package network.server.packets; 

import crypto.AbstractCiphering; 

public  class  UnicastPacket  extends DataPacket {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2221689275201333608L;

	

	private String uDestination;

	
	
	public void setDestination(String destination) {
		uDestination = destination;
	}

	
	
	public String getDestination() {
		return uDestination;
	}

	
	
	@Override
	public void actionBeforeSend(AbstractCiphering encoder) {
		// TODO Auto-generated method stub

	}

	

	@Override
	public void actionAfterReceive(AbstractCiphering decoder) {
		// TODO Auto-generated method stub

	}


}
