class Application {
	
	//@ invariant account != null;
	Account account = new Account();

	void /*@ helper @*/ nextDay$$BankAccount() {
	}

	void /*@ helper @*/ nextYear$$BankAccount() {
	}
	
	// SOURCE CODE FROM FEATURE DailyLimit
	
	/*@
	 @ ensures account.withdraw == 0;
	 @*/
	void nextDay() {
		nextDay$$BankAccount();
		account.withdraw = 0;
	}

}