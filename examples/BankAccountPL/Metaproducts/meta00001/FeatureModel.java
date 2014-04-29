public class FeatureModel {
	
	//@ static invariant fm();
	public final static boolean bankAccount, overdraft, interest, interestEstimation, creditWorthiness, dailyLimit;
	
	static {
		bankAccount = random();
		overdraft = random();
		interest = random();
		interestEstimation = random();
		creditWorthiness = random();
		dailyLimit = random();
		if (!fm())
			throw new Error();
	}
	
	/*@pure@*/ static boolean fm() {
		return bankAccount && (!overdraft || bankAccount) && (!interest || bankAccount) && (!creditWorthiness || bankAccount) && (!dailyLimit || bankAccount) && (!interestEstimation || interest);
	}

	private static boolean random() {
		return Math.random() < 0.5;
	}

}
