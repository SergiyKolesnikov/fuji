package network.server.packets; 

import java.io.Serializable; 

import crypto.AbstractCiphering; 

public abstract  class  DataPacket  implements Serializable , Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3392198171973436874L;

	
	public abstract void actionBeforeSend(AbstractCiphering encoder);

	
	public abstract void actionAfterReceive(AbstractCiphering decoder);

	
	
	@Override
	public DataPacket clone() {
		try {
			return (DataPacket)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}


}
