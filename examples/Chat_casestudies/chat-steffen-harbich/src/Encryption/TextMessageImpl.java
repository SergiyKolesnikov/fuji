
public class TextMessageImpl {

	private static EncryptionAlgorithm encryptionAlgorithm;

	public String getContent() {
		return encryptionAlgorithm.decrypt(original());
	}
	
	public void setContent(String content) {
		original(encryptionAlgorithm.encrypt(content));
	}
	
}
