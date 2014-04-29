import java.io.Serializable; 

public  class  LoginReplyMessage  implements Serializable {
	

    private static final long serialVersionUID = -2608688470747244895L;

	

    private boolean loginSuccessful;

	
    
    public LoginReplyMessage(boolean loginSuccessful) {
	this.loginSuccessful = loginSuccessful;
    }

	
    
    public boolean wasLoginSuccessful() {
	return this.loginSuccessful;
    }


}
