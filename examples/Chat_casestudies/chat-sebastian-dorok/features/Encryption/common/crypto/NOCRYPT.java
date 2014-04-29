package common.crypto;

public class NOCRYPT implements ICryptoAlgorithm {

	@Override
	public Object encode(Object o) {
		return o;
	}

	@Override
	public Object decode(Object o) {
		return o;
	}

}
