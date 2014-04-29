package network.client; 

import java.util.EventObject; 

public  class  ClientSocketStatusEvent  extends EventObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4694171067599957770L;

	
	
	public enum  ClientSocketStatus {
		CONNECTIONREFUSED ,  CONNECTIONESTABLISHED ,  CONNECTIONLOST ,  SOCKETCLOSED ,  CLIENTSTOPPED}

	

	private ClientSocketStatus chatStatus;

	
	
	public ClientSocketStatusEvent(Object source, ClientSocketStatus cstatus) {
		super(source);
		chatStatus = cstatus;
	}

	
	
	public ClientSocketStatus getStatus(){
		return chatStatus;
	}


}
