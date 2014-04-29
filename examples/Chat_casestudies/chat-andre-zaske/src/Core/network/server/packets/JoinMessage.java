package network.server.packets; 

import crypto.AbstractCiphering; 


public  class  JoinMessage  extends BroadcastPacket {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -355438161936539804L;

	

	private String name;

	

	public JoinMessage(String name) {
		this.name = name;
	}

	

	public String getName() {
		return name;
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
