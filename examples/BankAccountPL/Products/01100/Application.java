class Application {
	
	//@ invariant account != null;
	Account account = new Account();

	void /*@ helper @*/ nextDay$$BankAccount() {
	}

	void /*@ helper @*/ nextYear$$BankAccount() {
	}
	
	// SOURCE CODE FROM FEATURE Interest
	
	/*@
	 @ ensures (account.balance >= 0 ==> account.interest >= \old(account.interest)) 
	 @   && (account.balance <= 0 ==> account.interest <= \old(account.interest));
	 @*/
	void nextDay() {
		nextDay$$BankAccount();
		account.interest += account.calculateInterest();
	}

	/*@
	 @ ensures account.balance == \old(account.balance) + \old(account.interest) 
	 @   && account.interest == 0;
	 @*/
	void nextYear() {
		nextYear$$BankAccount();
		account.balance += account.interest;
		account.interest = 0;
	}

}