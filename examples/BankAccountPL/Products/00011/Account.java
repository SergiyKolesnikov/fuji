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
	 @
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
	
	//@ invariant withdraw >= DAILY_LIMIT;
	int withdraw = 0;

	/*@
	 @ ensures (!\result ==> withdraw == \old(withdraw)) 
	 @   && (\result ==> withdraw <= \old(withdraw)) 
	 @   && ( !\result ==> balance == \old(balance)) 
	 @   && (\result ==> balance == \old(balance) + x);
	 @*/
	boolean update(int x) {
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
	 @ requires amount >= 0;
	 @ ensures balance >= amount <==> \result;
	 @*/
	boolean credit(int amount) {
		return balance >= amount;
	}

}