/*
    This is a pretty robust class that handles the financial aspects of the grocery store, including bank accounts, loans, debt, credit score, etc.
    It also handles the bank functionality of the game, including the menu, actions, etc.
    A lot of this code is honestly spaghetti, which I have to remove eventually.

    by Daniel Li
    5/27/20
*/

import java.util.*;

public class Finances_DL{

    private double balance;
    private double debt;
    private double creditScore; // default credit score
    private double interestRate;
    private static final int utilityCost = 200; // weekly costs of utilities
    private int daysSinceLastLoan = 2; // avoid loan spamming
    private boolean menuRunning = true; // controls the 'active' state of the menu
    Scanner input = new Scanner(System.in);

    public Finances_DL(){
         balance = 100;
         debt = 0;
         creditScore = 500;
         interestRate = 0;
    }

    // These getter/setter methods don't really serve a purpose now since I converted all of my financial variables to public...
    
    public double getBalance(){return balance;}

    public void addBalance(double amt){balance += amt;}

    public double getDebt(){return debt;}

    public double getCredScore(){return creditScore;}

    public double getInterestRate(){return interestRate;}

    public void setMenuStatus(boolean status){menuRunning = status;}

    public boolean getMenuStatus(){return menuRunning;}

    public int getUtilCost(){return utilityCost;}

    public double getLoanRange(){
        return ((creditScore / 800) * 100000); // calculates the max amount of money someone can loan out with a given credit score
    }

    public void incrementLoanDays(int amt){
        daysSinceLastLoan += amt;
    }

    // Prints the max amount of money you can loan out (pretty useless, need to delete later)
    public void printLR(){
        System.out.println(getLoanRange());
    }

    public void payUtilities(){
        debt += utilityCost;
    }

    // this function handles the 'Bank' menu that you access through the main menu. I didn't want to spaghetti up my main code so I offloaded it here. (nested 'if' statements are bad)
    public void runMenu(){
        try{
            int choice = input.nextInt();
            switch(choice){
            // Prints out account information
            case 1:
                String details = String.format("\n[Account Details]\nBalance: $%.2f\nCredit Score: %.0f\nOutstanding Debt: $%.2f ",balance,creditScore,debt);
                System.out.println(details);
                break;
            // Applying for a loan; knows when you have taken a loan out so you can't spam it :)
            case 2:
                if(daysSinceLastLoan >= 2){
                    String loanDisclaimer = String.format("\nYou have requested for a loan from Stez Bank. For reference, your credit score is %.0f, meaning you can\nloan out at max $%.2f and a %.2f %s chance of a successful loan.",creditScore,getLoanRange(),loanApprovalRate(),"%");
                    System.out.println(loanDisclaimer);
                    System.out.println("\nHow much do you want to loan out?\n");
                    double loanAmt = input.nextDouble();
                    applyForLoan(loanAmt);
                }
                else{
                    System.out.println("\nOur records show that you have requested to take out a loan within the last 2 days. Please try again later.");
                }
                break;
            // Confirmation of paying off debt
            case 3:        
                System.out.println("\nYou are about to pay off some of your debt. Do you want to continue? Y/N:\n");
                String confirm = input.next();
                if(confirm.equals("y") || confirm.equals("Y")){
                    System.out.println("\nHow much do you want to pay right now?:\n");
                    double debtPayment = input.nextDouble();
                    payDebt(debtPayment);
                }
                else if(confirm.equals("y") || confirm.equals("Y")){
                    System.out.println("\nYou have backed out of debt payment.\n");
                }
                else{System.out.println("Not a valid input. Try again.");}
                break;
            // Handles the menu state, I made this menuRunning variable so users wouldn't have to manually navigate back to the bank menu if they had backed out previously
            case 4:
                menuRunning = false;
                break;
            default: 
                System.out.println("Please enter a valid input");
            }
        }
        catch (Exception e){
            System.out.println("Invalid input, try again.");
        }
        
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
            interestRate = -0.0069087 * creditScore + 10.1124; // sets interest rate based on a linear model relating rate vs cred. score
            balance += amt;
            debt += amt * (1 + (interestRate/100));
            //System.out.println("You had a " + String.format("%.2f", loanApprovalRate()) + "% chance to get a loan!");
            System.out.println("\nYou have just taken out a loan for $" + String.format("%.2f",amt) + " at an interest rate of " + String.format("%.2f",getInterestRate()) + 
                " percent. Your balance is now at $" + String.format("%.2f",getBalance()) + " and you owe $" + String.format("%.2f",getDebt()));
            daysSinceLastLoan = 0;
        }

        /*
            error messages due to missed criteria
        */

        else if (debt > 0) {
            System.out.println("\nYou must repay all of your existing debts before applying for another loan.");
        }
        else if (amt > getLoanRange()) {
            System.out.println("\nDue to your credit score, you are not allowed to loan out this amount of money. You are allowed to loan out up to $" + String.format("%.2f",getLoanRange()));
        }
        else if (amt <= 0) {
            System.out.println("\nYou can't loan out zero or negative amounts.");
        }
        else{
            System.out.println("\nWe're sorry, due to your credit score, your loan was not approved. You may apply for another loan in 2 days.");
            daysSinceLastLoan = 0;
        }

    }

    // How to pay back debt! 
    public void payDebt(double amt){
        if (amt <= 0){
            System.out.println("\nInvalid amount\n");
        }
        else if (amt >= debt) {
            debt = 0;
            balance -= debt;
            if(creditScore + 25 <= 800){
                creditScore += 25;
            }
            else{creditScore = 800;}
            System.out.println("\nCongratulations! You have fully paid off your debt. Enjoy the +25 credit score!"); // Need to change this, can be exploited :D
        }
        else{
            debt -= amt;
            balance -= amt;
            System.out.println(String.format("You have just paid $%.2f towards your debt. You now owe $%.2f",amt,debt));
        }
    }

    public void showBankMenu(){
        // shows a navigation menu of the options you have in the bank
        String header = String.format("%-10s %-10s","[ID]","[Option]");
        String[] options = {"View Account Details","Apply For Loan","Pay Debts","Exit to Main Menu"};
        System.out.println(header);
        for(int i =0; i < options.length;i++){
            String row = String.format("%-10d %-10s",i + 1,options[i]);
            System.out.println(row);
        }
        System.out.println();
    }

    // checks if your store is bankrupt
    public boolean isBankrupt(){
        if(creditScore <= 0 && debt >= 150000){
            return true;
        }
        else{return false;}
    }

}