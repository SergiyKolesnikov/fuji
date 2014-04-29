import gov.nasa.jpf.util.test.TestJPF; import org.junit.Test; public class TransactionTest extends TestJPF {

	@Test
	public void testTransactionJPF() {
		if (verifyNoPropertyViolation()) {
			FeatureModel.transaction_ = true;
			testTransaction();
		}
	}


	public void testTransaction() {
		Account[] accounts = new Account[]{new Account(),new Account(),new Account()};
		for (Account a : accounts) {
			a.balance = 1000;
		}
		
		
		new Transaction().transfer(accounts[0], accounts[1], 100);
		new Transaction().transfer(accounts[1], accounts[2], 150);
		new Transaction().transfer(accounts[2], accounts[1], 250);
		
		











		
		int i = 0;
		int counter = 0;
		for (Account a : accounts) {
			System.out.println("A" + counter++ + " : " + a.balance);
			i+=a.balance;
		}
		assertEquals(i, 1000 * accounts.length);
	}


}
