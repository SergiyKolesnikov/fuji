public class BackendStub implements Backend {

	private final String passwd = "foobar";

	@Override
	public boolean authenticate(String user, String passwd) {
		return this.passwd.equals(passwd);
	}

}
