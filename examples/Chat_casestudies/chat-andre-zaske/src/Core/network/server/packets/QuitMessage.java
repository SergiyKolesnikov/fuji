package network.server.packets; 

import crypto.AbstractCiphering; 


public  class  QuitMessage  extends BroadcastPacket {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 9093790253403538529L;

	
	
	private String name;

	
	
	public QuitMessage(String name){
		this.name = name;
	}

	
	
	public String getName(){
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
