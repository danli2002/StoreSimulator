public class Finances_DL{

    public double balance;
    public double debt;
    public double creditScore; // default credit score
    public double interestRate;

    public Finances_DL(){
         balance = 100;
         debt = 0;
         creditScore = 30;
         interestRate = 0;
    }

    public double getBalance(){return balance;}

    public void addBalance(double amt){balance += amt;}

    public double getDebt(){return debt;}

    public double getCredScore(){return creditScore;}

    public double getInterestRate(){return interestRate;}

    public double getLoanRange(){
        return ((creditScore / 800) * 100000); // calculates the max amount of money someone can loan out with a given credit score
    }

    public void printLR(){
        System.out.println(getLoanRange());
    }

    // mathematical model to calculate the probability of securing a successful loan based on credit score
    public double loanApprovalRate(){
        double e = Math.exp(1); // initializing this variable as the constant 'e'
        double growthRate = 0.0085017428; // calculated accoring to the logistic growth model: high dropoff in loan approval rate at a certain point
        double numerator = 999 * Math.pow(e,growthRate * creditScore); // I want to avoid messy equations lol
        double denominator = 99.9 + 10 * Math.pow(e,growthRate * creditScore) - 10;
        double rate = numerator/denominator;
        return rate;
    }

    public void applyForLoan(double amt){
        double seed = (Math.random() * 100);
        if(amt > 0 && debt == 0 && seed < loanApprovalRate() && amt <= getLoanRange()){
            balance += amt;
            debt += amt;
            interestRate = -0.0069087 * creditScore + 10.1124; // sets interest rate based on a linear model relating rate vs cred. score
            System.out.println("You had a " + String.format("%.2f", loanApprovalRate()) + "% chance to get a loan!");
            System.out.println("You have just taken out a loan for $" + String.format("%.2f",amt) + " at an interest rate of " + String.format("%.2f",getInterestRate()) + 
                " percent. Your balance is now at $" + String.format("%.2f",getBalance()) + " and you owe $" + String.format("%.2f",getDebt()));
        }

        /*
            error messages due to missed criteria
        */

        else if (debt > 0) {
            System.out.println("You must repay all of your existing debts before applying for another loan.");
        }
        else if (amt > getLoanRange()) {
            System.out.println("Due to your credit score, you are not allowed to loan out this amount of money. You are allowed to loan out up to $" + String.format("%.2f",getLoanRange()));
        }
        else if (amt <= 0) {
            System.out.println("You can't loan out zero or negative amounts.");
        }
        else{
            System.out.println("We're sorry, due to your credit score, your loan was not approved. You may apply for another loan in 2 days.");
        }

    }

    // How to pay back debt! 
    public void payDebt(double amt){
        if (amt <= 0){
            System.out.println("Invalid amount");
        }
        else if (amt > debt) {
            debt = 0;
            balance -= debt;
        }
        else{
            debt -= amt;
            balance -= amt;
        }
    }

}