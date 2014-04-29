package Everything;
import org.jivesoftware.smack.*;


abstract class IMBuddy$$InstantBase {
	protected String sUID;
	protected String sNickname;
	
	public IMBuddy$$InstantBase(String sUID, String sNickname) {
		this.sUID = sUID;
		if (this.sNickname == null) {
			this.sNickname = sNickname;
		}
	}
	
	public String toString() {
		if (sNickname == null || sNickname.length() == 0) {
			return sUID;
		} else {
			return sNickname;
		}
	}
	
	public String getUID() {
		return ((IMBuddy) this).sUID;
	}
	
	public String getNickname() {
		return ((IMBuddy) this).sNickname;
	}
}



public class IMBuddy extends  IMBuddy$$InstantBase  {
	Chat jabChat;
	
	public void setChat(Chat jabChat) {
		((IMBuddy) this).jabChat = jabChat;
	}
      // inherited constructors


	
	public IMBuddy ( String sUID, String sNickname ) { super(sUID, sNickname); }
}