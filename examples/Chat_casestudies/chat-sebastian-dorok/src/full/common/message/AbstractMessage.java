package common.message; 

import java.io.Serializable; 

import common.crypto.ICryptoAlgorithm; 

public abstract   class  AbstractMessage  implements Serializable, Cloneable {
	

	private static final long serialVersionUID = 1L;

	
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	

	public abstract void encode(ICryptoAlgorithm cryptoAlgorithm);

	

	public abstract void decode(ICryptoAlgorithm cryptoAlgorithm);


}
