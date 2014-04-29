import java.io.Serializable; 

public  class  LoginMessage  implements Serializable {
	

    private static final long serialVersionUID = 4047863306110543911L;

	

    private String password;

	
    
    public LoginMessage(String password) {
	this.password = password;
    }

	
    
    public String getPassword() {
	return this.password;
    }


}
