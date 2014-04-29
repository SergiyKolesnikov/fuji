package common.message;

import java.io.Serializable;

import common.crypto.ICryptoAlgorithm;

public abstract class AbstractMessage implements Serializable, Cloneable {

	public abstract void encode(ICryptoAlgorithm cryptoAlgorithm);

	public abstract void decode(ICryptoAlgorithm cryptoAlgorithm);
}
