class Application {
	
	//@ invariant account != null;
	Account account = new Account();

	void /*@ helper @*/ nextDay$$BankAccount() {
	}

	void /*@ helper @*/ nextYear$$BankAccount() {
	}
	
	// SOURCE CODE FROM FEATURE DailyLimit
	
	/*@
	 @ requires account != null;
	 @ ensures account != null;
	 @
	 @ ensures FeatureModel.dailyLimit ==> account.withdraw == 0;
	 @*/
	void /*@ helper @*/ nextDay$$DailyLimit() {
		if (!FeatureModel.dailyLimit) {
			nextDay$$BankAccount();
			return;
		}
		nextDay$$BankAccount();
		account.withdraw = 0;
	}

	// SOURCE CODE FROM FEATURE Interest
	
	/*@
	 @ ensures FeatureModel.interest ==> (FeatureModel.dailyLimit ==> account.withdraw == 0) 
	 @   && (account.balance >= 0 ==> account.interest >= \old(account.interest)) 
	 @   && (account.balance <= 0 ==> account.interest <= \old(account.interest));
	 @ ensures !FeatureModel.interest ==> (FeatureModel.dailyLimit ==> account.withdraw == 0);
	 @*/
	void nextDay() {
		if (!FeatureModel.interest) {
			nextDay$$DailyLimit();
			return;
		}
		nextDay$$DailyLimit();
		account.interest += account.calculateInterest();
	}

	/*@
	 @ ensures FeatureModel.interest ==> 
	 @   account.balance == \old(account.balance) + \old(account.interest) 
	 @   && account.interest == 0;
	 @*/
	void nextYear() {
		if (!FeatureModel.interest) {
			nextYear$$BankAccount();
			return;
		}
		nextYear$$BankAccount();
		account.balance += account.interest;
		account.interest = 0;
	}

}