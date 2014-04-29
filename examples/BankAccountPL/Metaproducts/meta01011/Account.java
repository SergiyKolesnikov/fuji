class Account {

	final static int OVERDRAFT_LIMIT = 0;

	//@ invariant balance >= OVERDRAFT_LIMIT;
	int balance = 0;

	/*@
	 @ ensures balance == 0;
	 @*/
	Account() {
	}
	
	/*@
	 @ requires balance >= OVERDRAFT_LIMIT;
	 @ ensures balance >= OVERDRAFT_LIMIT;
	 @ ensures (!\result ==> balance == \old(balance)) && (\result ==> balance == \old(balance) + x);
	 @*/
	boolean /*@ helper @*/ update$$BankAccount(int x) {
		int newBalance = balance + x;
		if (newBalance < OVERDRAFT_LIMIT)
			return false;
		balance = newBalance;
		return true;
	}

	// SOURCE CODE FROM FEATURE DailyLimit
	
	final static int DAILY_LIMIT = -1000;
	
	//@ invariant FeatureModel.dailyLimit ==> withdraw >= DAILY_LIMIT;
	int withdraw = 0;

	/*@
	 @ ensures FeatureModel.dailyLimit ==> ((!\result ==> balance == \old(balance)) && (\result ==> balance == \old(balance) + x)) && (!\result ==> withdraw == \old(withdraw)) && (\result ==> withdraw <= \old(withdraw));
	 @ ensures !FeatureModel.dailyLimit ==> (!\result ==> balance == \old(balance)) && (\result ==> balance == \old(balance) + x);
	 @*/
	boolean update(int x) {
		if (!FeatureModel.dailyLimit)
			return update$$BankAccount(x);
		int newWithdraw = withdraw;
		if (x < 0)  {
			newWithdraw += x;
			if (newWithdraw < DAILY_LIMIT) 
				return false;
		}
		if (!update$$BankAccount(x))
			return false;
		withdraw = newWithdraw;
		return true;
	}
	
	// SOURCE CODE FROM FEATURE CreditWorthiness
	
	/*@
	 @ requires FeatureModel.creditWorthiness ==> (amount >= 0);
	 @ ensures FeatureModel.creditWorthiness ==> (balance >= amount <==> \result);
	 @*/
	boolean credit(int amount) {
		return balance >= amount;
	}

	// SOURCE CODE FROM FEATURE Interest
	
	final static int INTEREST_RATE = 2;

	int interest = 0;

	/*@
	 @ ensures FeatureModel.interest ==> (balance >= 0 ==> \result >= 0) && (balance <= 0 ==> \result <= 0);
	 @*/
	int /*@ pure @*/ calculateInterest() {
		return balance * INTEREST_RATE / 36500;
	}
	
}