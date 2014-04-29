class Account {

	//@ invariant balance >= OVERDRAFT_LIMIT;
	int balance = 0;
	
	/*@
	 @ ensures balance == 0;
	 @*/
	Account() {
	}
	
	/*@
	 @ ensures (!\result ==> balance == \old(balance)) && (\result ==> balance == \old(balance) + x); 
	 @*/
	boolean update(int x) {
		int newBalance = balance + x;
		if (newBalance < OVERDRAFT_LIMIT)
			return false;
		balance = newBalance;
		return true;
	}

	// SOURCE CODE FROM FEATURE Overdraft

	final static int OVERDRAFT_LIMIT = -5000;

	// SOURCE CODE FROM FEATURE CreditWorthiness
	
	/*@
	 @ requires amount >= 0;
	 @ ensures balance >= amount <==> \result;
	 @*/
	boolean credit(int amount) {
		return balance >= amount;
	}

	// SOURCE CODE FROM FEATURE Interest
	
	final static int INTEREST_RATE = 2;

	int interest = 0;

	/*@
	  @ ensures (balance >= 0 ==> \result >= 0) && (balance <= 0 ==> \result <= 0);
	  @*/
	int /*@ pure @*/ calculateInterest() {
		return balance * INTEREST_RATE / 36500;
	}
	
	// SOURCE CODE FROM FEATURE InterestEstimation
	
	/*@
	 @ requires daysLeft >= 0;
	 @ ensures calculateInterest() >= 0 ==> \result >= interest;
	 @*/
	int /*@ pure @*/ estimatedInterest(int daysLeft) {
		return interest + daysLeft * calculateInterest();
	}
	
}