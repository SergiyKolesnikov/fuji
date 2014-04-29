public class AnnounceMessage extends Message {
	
	String name;

	public AnnounceMessage(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
