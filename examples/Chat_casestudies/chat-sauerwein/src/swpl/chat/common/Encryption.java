package swpl.chat.common;


public interface Encryption {
	public abstract String encode(String msg);
	public abstract String decode(String msg);
}