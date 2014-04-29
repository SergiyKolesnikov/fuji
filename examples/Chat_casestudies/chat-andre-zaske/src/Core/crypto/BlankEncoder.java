package crypto; 

public  class  BlankEncoder  extends AbstractCiphering {
	
	
	public BlankEncoder(){
		
	}

	

	@Override
	public String encode(String phrase){
		return phrase;
	}

	

	@Override
	public String decode(String code){
		return code;
	}

	

	@Override
	public String getCipheringName() {
		return "BlankEncoder";
	}


}
