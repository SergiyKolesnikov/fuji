public  class  StdOutDebug  implements Debug {
	

	@Override
	public void append(String msg) {
		System.out.println("Debug: " + msg);
	}

	

	@Override
	public void appendError(String msg) {
		System.err.println("Error: " + msg);
	}


}
