 class  Application {
	
	
	//@ invariant account != null;
	Account account = new Account();

	

	 private void  nextDay__wrappee__Overdraft  () {
	}

	
	
	/*@
	 @ ensures \original 
	 @   && (account.balance >= 0 ==> account.interest >= \old(account.interest)) 
	 @   && (account.balance <= 0 ==> account.interest <= \old(account.interest));
	 @*/
	 private void  nextDay__wrappee__CreditWorthiness  () {
		nextDay__wrappee__Overdraft();
		account.interest += account.calculateInterest();
	}

	
	
	/*@
	 @ ensures account.withdraw == 0;
	 @*/
	void nextDay() {
		nextDay__wrappee__CreditWorthiness();
		account.withdraw = 0;
	}

	

	 private void  nextYear__wrappee__Overdraft  () {
	}

	

	/*@
	 @ ensures account.balance == \old(account.balance) + \old(account.interest) 
	 @   && account.interest == 0;
	 @*/
	void nextYear() {
		nextYear__wrappee__Overdraft();
		account.balance += account.interest;
		account.interest = 0;
	}


}
