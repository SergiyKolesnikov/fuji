class Application {
	
	//@ invariant account != null;
	Account account = new Account();

	void /*@ helper @*/ nextDay$$BankAccount() {
	}

	void nextYear() {
	}
	
	// SOURCE CODE FROM FEATURE DailyLimit
	
	/*@
	 @ ensures FeatureModel.dailyLimit ==> account.withdraw == 0;
	 @*/
	void nextDay() {
		if (!FeatureModel.dailyLimit) {
			nextDay$$BankAccount();
			return;
		}
		nextDay$$BankAccount();
		account.withdraw = 0;
	}

}