package network.server.packets; 

import crypto.AbstractCiphering; 

public  class  AuthentifikationPacket  extends DataPacket {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6049423253248027680L;

	

	public enum  AuthentifikationFlag {
		AUTHREQUEST ,  AUTHREQUESTACK ,  AUTHRUN ,  AUTHRUNACK ,  NOAUTH}

	

	private String accountName = "";

	
	private byte[] accountPasswort = new byte[] { 0 };

	
	private AuthentifikationFlag authentifikationState = AuthentifikationFlag.AUTHREQUEST;

	
	private String chipheringCoder = "";

	

	public AuthentifikationPacket(String name) {
		accountName = name;
	}

	

	public String getAccountName() {
		return accountName;
	}

	

	public byte[] getAccountPasswort() {
		return accountPasswort;
	}

	

	public void setAccountPasswort(byte[] accountPasswort) {
		this.accountPasswort = accountPasswort;
	}

	

	public AuthentifikationFlag getAuthentifikationState() {
		return authentifikationState;
	}

	

	public void setAuthentifikationState(
			AuthentifikationFlag authentifikationState) {
		this.authentifikationState = authentifikationState;
	}

	

	public String getChipheringCoder() {
		return chipheringCoder;
	}

	

	public void setChipheringCoder(String chipheringCoder) {
		this.chipheringCoder = chipheringCoder;
	}

	

	@Override
	public void actionBeforeSend(AbstractCiphering encoder) {
		// TODO Auto-generated method stub

	}

	

	@Override
	public void actionAfterReceive(AbstractCiphering decoder) {
		// TODO Auto-generated method stub

	}

	

	public String toString() {
		String sOut = "[ AccountName: " + accountName;
		sOut += ", accountPasswort: " + accountPasswort;
		sOut += ", AuthentifikationFlag: " + authentifikationState;
		sOut += ", ChipheringCoderName: " + chipheringCoder;
		sOut += " ]";
		return sOut;
	}


}
