import org.junit.Test; import gov.nasa.jpf.util.test.TestJPF; import gov.nasa.jpf.jvm.Verify; public  class AccountTest extends TestJPF {

	@Test
	public void accountTest() {
		if (verifyNoPropertyViolation()) {
			assertEquals(0, (new Account()).balance);
		}
	}


	@Test
	public void updateTest() {
		if (verifyNoPropertyViolation()) {
			Account a = new Account();
			a.update(100);
			assertEquals(100, a.balance);
		}
	}


	@Test
	public void undoUpdateTest() {
		if (verifyNoPropertyViolation()) {
			Account a = new Account();
			a.update(100);
			a.undoUpdate(100);
			assertEquals(0, a.balance);
		}
	}


	public void update() {
		FeatureModel.overdraft_ = true;
		Account a = new Account();
		a.balance = -4900;
		assertTrue(a.update(-40));
		assertFalse(a.update(-100));
	}


	@Test
	public void calculateInterestTest() {
		if (verifyNoPropertyViolation()) {
			if (!FeatureModel.interest()) return;
			Account a = new Account();
			a.balance = Verify.getInt(a.OVERDRAFT_LIMIT,100);
			if (a.balance >= 0) {
				assertTrue(a.calculateInterest() >= 0);
			} else {
				assertTrue(a.calculateInterest() <= 0);
			}
		}
	}


	@Test
	public void estimatedInterestTest() {
		if (verifyNoPropertyViolation()) {
			FeatureModel.interestestimation_ = true;
			Account a = new Account();
			a.interest = 100;
			assertTrue(a.estimatedInterest(Verify.getInt(0, 100)) >= 0);
		}
	}


	@Test
	public void creditTest() {
		if (verifyNoPropertyViolation()) {
			FeatureModel.creditworthiness_ = true;
			Account a = new Account();
			a.update(100);
			assertTrue(a.credit(100));
			assertFalse(a.credit(101));
		}
	}


	@Test
	public void updateTest_2() {
		if (verifyNoPropertyViolation()) {
			FeatureModel.dailylimit_ = true;
			Account a = new Account();
			a.update(10000);
			assertTrue(a.update(-900));
			assertFalse(a.update(-200));
		}
	}


}
