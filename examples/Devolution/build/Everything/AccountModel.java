package Everything;import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Properties;



public class AccountModel extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -9045245386649872871L;
	private final String name;
	private Properties props;
	private boolean isNewAccount;


	public AccountModel ( final String name, boolean isNewAccount) {
		super();
		this.name = name;
		this.isNewAccount = isNewAccount;
	}

	public void addAlias ( final AccountModel aliass ) {
		if ( isAccount() ) {
			add( aliass );
			String as  = ((AccountModel) this).props.getProperty("aliases");
			if ( as != null ){
				as = as + aliass.toString() + ";";
			}
			else {
				as = aliass.toString() + ";";
			}
			((AccountModel) this).props.put("aliases",as);
		}
	}

	public void removeAlias(){
		String as =  ((AccountModel) this).props.getProperty("aliases");
		if ( as != null ){
			as = as.replaceAll(((AccountModel) this).name + ";", "");
		}
		((AccountModel) this).props.put("aliases",as);
	}

	public void deleteAccount(){
		Account.deleteAccount(name);
		try{
			((AccountModel) this).finalize();
		} catch (Throwable e){}
	}

	public boolean isAlias () {
		return !isRoot();
	}
	
	public boolean isAccount () {
		return isNewAccount;
	}

	public String toString () {
		return ((AccountModel) this).name;
	}

	public void setProperties(Properties props){
		((AccountModel) this).props = props;
	}

	public Properties getProperties(){
		return ((AccountModel) this).props;
	}

	public AccountModel getAccountNode(){
		if (((AccountModel) this).isAccount()) return ((AccountModel) this);
		else return ((AccountModel) ((AccountModel) this).getParent()).getAccountNode();
	}
}