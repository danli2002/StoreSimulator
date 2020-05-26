public class Finances_DL{

	private double balance = 0;
	private double debt = 0;
	private int creditScore = 650; // default credit score
	private double interestRate;

	public Finances_DL(){}

	public double getBalance(){return balance;}

	public double getDebt(){return debt;}

	public int getCredScore(){return creditScore;}

	public double getInterestRate(){return interestRate;}

	public int getLoanRange(){
		return (int)(creditScore / 800 * 100000); // calculates the max amount of money someone can loan out with a given credit score
	}

	public int loanApprovalRate(){
		return (int)(Math.pow(1.0057730630,this.creditScore)); // mathematical model to calculate the probability of securing a successful loan based on credit score
	}

	public void applyForLoan(double amt){
		int seed = (int)(Math.random() * 100);
		if(amt > 0 && debt >= 0 && seed < this.loanApprovalRate() && amt <= this.getLoanRange()){
			balance += amt;
			debt -= amt;
			interestRate = -0.0069087 * creditScore + 10.1124; // sets interest rate based on a linear model relating rate vs cred. score
		}

		/*
			error messages due to missed criteria
		*/
			
		else if (debt < 0) {
			System.out.println("You must repay all of your existing debts before applying for another loan.");
		}
		else if (amt > this.getLoanRange()) {
			System.out.println("Due to your credit score, you are not allowed to loan out this amount of money. You are allowed to loan out up to $" + this.getLoanRange());
		}
		else if (amt <= 0) {
			System.out.println("You can't loan out zero or negative amounts.");
		}
		else{
			System.out.println("We're sorry, due to your credit score, your loan was not approved. You may apply for another loan in 2 days.");
		}

	}

	public void payDebt(double amt){
		debt += amt;
	}

}