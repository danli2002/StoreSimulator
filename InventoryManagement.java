/*
    I created this class because I wanted a single method to handle buying stock and whatnot. It's kind of scuffed.
    Feel free to send me some suggestions on what to do next.

    by Daniel Li
    5/27/2020
*/

import java.util.*;
public class InventoryManagement{
    Scanner scan = new Scanner(System.in);

    public InventoryManagement(){}

    public void buyProduct(StoreItem_DL item, Finances_DL account, int amt){
        try{
            if(account.getBalance() >= amt*(item.getBulkPrice())){
                // Confirmation, avoid accidentally buying stuff
                String warning = String.format("\nYou are about to buy %d cases of %s for the total price of %.2f. Are you sure? Y/N:\n",amt,item.getName(),amt*(item.getBulkPrice()));
                System.out.println(warning);
                String choice = scan.next();
                if(choice.equals("y") || choice.equals("Y")){
                    item.incrementQuantity(amt * item.getBulkQuant());
                    account.addBalance(-1 * amt * item.getBulkPrice());
                    String confirmation = String.format("\nYou just bought %d cases of %s for the total price of %.2f.",amt,item.getName(),amt*(item.getBulkPrice()));
                    System.out.println(confirmation);
                }
                else if(choice.equals("n") || choice.equals("N")) {
                    System.out.println("\nYou have cancelled the transaction. You have not been charged.");
                }
                else{
                    System.out.println("\nYou have entered an invalid input, please try the transaction again.\n");
                }
            }
            // Handling the scenario if you don't have enough money to buy stuff
            else{
                System.out.println("\nYou have insufficient funds!");
            }
        }
        catch (Exception e){
            System.out.println("Invalid input, please try again.");
        }

    }

    public void adjustPrice(StoreItem_DL item, double price){
        item.priceShock(item.getRetailPrice(),price);
        item.setPrice(price);
    }
}