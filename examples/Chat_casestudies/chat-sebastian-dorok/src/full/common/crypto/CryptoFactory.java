package common.crypto; 

public  class  CryptoFactory {
	

	public enum  CRYPTO_ALGORITHMS {
		NO_CRYPT ,  ROT13 ,  SWAP}

	

	private static ICryptoAlgorithm instance_NOCRYPT = null;

	
	private static ICryptoAlgorithm instance_ROT13 = null;

	
	private static ICryptoAlgorithm instance_SWAP = null;

	

	public static ICryptoAlgorithm getCryptoModule(CRYPTO_ALGORITHMS algo) {
		switch (algo) {
		case ROT13:
			if (CryptoFactory.instance_ROT13 == null) {
				CryptoFactory.instance_ROT13 = new ROT13();
			}
			return CryptoFactory.instance_ROT13;
		case SWAP:
			if (CryptoFactory.instance_SWAP == null) {
				CryptoFactory.instance_SWAP = new SWAP();
			}
			return CryptoFactory.instance_SWAP;
		case NO_CRYPT:
			if (CryptoFactory.instance_NOCRYPT == null) {
				CryptoFactory.instance_NOCRYPT = new NOCRYPT();
			}
			return CryptoFactory.instance_NOCRYPT;
		default:
			if (CryptoFactory.instance_NOCRYPT == null) {
				CryptoFactory.instance_NOCRYPT = new NOCRYPT();
			}
			return CryptoFactory.instance_NOCRYPT;
		}
	}


}
