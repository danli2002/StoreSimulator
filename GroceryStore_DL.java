/*
    Class that handles the entirety of the grocery store. I wanted a single, easy to use class that could pull methods and data from other classes to work together.
    This class is one big effort to keep my main() method uncluttered.
    The constructor of this class includes the core functionality of the store, which are defined by several key functions below.
    Like the other classes, I will need to eliminate the spaghetti code later.

    by Daniel Li
    5/27/20
*/

import java.util.*;
public class GroceryStore_DL{
    
    ScannerReadFile scanner = new ScannerReadFile();
    ArrayList<String> data = scanner.returnData();
    StoreItem_DL[] items = new StoreItem_DL[data.size() / 5];
    Finances_DL finances = new Finances_DL();
    InventoryManagement InventoryManager = new InventoryManagement();
    EventHandler events = new EventHandler();
    private boolean running = true;
    private int currentDay = 0;
    private int utilityBillCounter = 0;
    private String dailyHeadlines;
    public GroceryStore_DL(){
        init();
        runStore();
        //dailySale();
    }
    
    // Adds items from the StoreData.txt data file
    public void populateItems(){
        for(int i = 0; i < data.size() / 5; i++){
            StoreItem_DL item = new StoreItem_DL(
                data.get(i * 5),
                Double.parseDouble(data.get(i * 5 + 1)),
                Integer.parseInt(data.get(i * 5 + 2)),
                Double.parseDouble(data.get(i* 5 + 3)),
                Double.parseDouble(data.get(i* 5 + 4))
                );
            items[i] = item;
        }
    }

    public void init(){
        populateItems();
        // Welcome messages
        System.out.println("\n\nWelcome to Store Simulator. Your goal is to successfully manage a small store, which tasks you with buying and selling goods.");
        System.out.println("Circumstances change, so you will have to change prices and adjust some factors to keep your store profitable.");
        System.out.println("To start, take out a simple loan from the bank. You will have to pay it back. Good luck!\n");
        dailyHeadlines = generateHeadlines();
    }

    public void showItemData(){
        // Prints out headers, this is for debug purposes for now
        System.out.printf("%-15s","Item ID");
        System.out.printf("%-15s","Item Name");
        System.out.printf("%-15s","Item Price");
        System.out.printf("%-15s","Item Quantity");
        System.out.printf("%-15s","Bulk Quantity");
        System.out.printf("%-15s","Bulk Price");
        System.out.printf("%-15s","Popularity Index");
        System.out.println();

        // Prints out items and their details in a nice format
        for(int q = 0; q < items.length; q++){
            String formatted = items[q].toString();
            System.out.println(String.format("%-15d %s",q+1,formatted));
        }
        System.out.println();
    }
    
    // Daily business at the store
    public void dailySale(){
        System.out.println();
        for(int i = 0; i < items.length; i++){
            StoreItem_DL item = items[i];
            // Prints out the drop off in popularity statistics and the random seed of the daily sales (i.e. sales are determined randomly within a range, depending on it's pop. index)
            // System.out.println("\nDebug stats for item: " + item.n);
            double saleRate;
            
            // A poorly thought out way of figuring out how much would sell on a given day, need to clean this up later
            double dropOff = (Math.pow(0.0489769,item.getPIndex()) * 100);
            System.out.println(String.format("Dropoff: %.2f",dropOff));
            double seed = (Math.random() * dropOff * 1.2)/100;
            
            // avoid selling "negative" amounts of goods
            System.out.println(String.format("seed: %.2f",seed));
            if(item.getPIndex() * 100 - dropOff >= 0){
                saleRate = ((item.getPIndex() - dropOff/100) + seed);
                System.out.println(saleRate);
            }
            else{
                saleRate = 0;
            }

            // Differentiated messages based on the performance of some goods on the shelves
            int unitSold = (int)(saleRate * item.getQuantity());
            if(unitSold > item.getQuantity()){
                finances.addBalance(item.getQuantity() * item.getRetailPrice());
                item.setQuantity(0);
                System.out.println(String.format("%s completely sold out at a popularity index of %.2f",item.getName(),item.getPIndex()));
            }
            else if (unitSold < item.getQuantity() && unitSold != 0) {
                finances.addBalance(unitSold * item.getRetailPrice());
                item.incrementQuantity(-1 * unitSold);
                System.out.println(String.format("%s sold %d units at a popularity index of %.2f",item.getName(),unitSold,item.getPIndex()));
            }
            else {
                System.out.println(String.format("%s sold nothing at a popularity index of %.2f",item.getName(),item.getPIndex()));
            }

        }
        System.out.println("\nYour balance is now at $" + String.format("%.2f",finances.getBalance()));
    }

    public void showNavMenu(){
        // Displays the basic navigation menu
        System.out.println("\nType in the number of the option you want to navigate to:\n");
        String header = String.format("%-10s %-10s","[ID]","[Option]");
        String[] options = {"Buy Stock","Check Headlines","Bank","Open Store","Quit"};
        // Prints out the cool formatted header
        System.out.println(header);
        for(int i =0; i < options.length;i++){
            String row = String.format("%-10d %-10s",i + 1,options[i]);
            System.out.println(row);
        }
        System.out.println();
    }

    public void printSeparator(){
        System.out.println();
        for(int i = 0; i < 30; i++){
            System.out.print("--");
        }
        System.out.println();
    }

    public String generateHeadlines(){
        String indEventsFormat = "\n[Today's News:]\n\n";
        double coinflip = Math.random();
        if(coinflip < 0.9){
            for(int i = 0; i < items.length; i++){
                indEventsFormat += events.indShock(items[i]) + "\n";
            }
            return indEventsFormat;
        }
        else{
            return("\nToday's News:\n\n" + events.marketShock(items));
        }
    }

    public void runStore(){
        // Check if the store is not technically bankrupt and is still running, according to the user conditions
        while (finances.isBankrupt() == false && running == true){
            // Displays day number and stats at the top of the screen, might have to tweak in the future
            printSeparator();
            String dayDisplay = ("[Day " + currentDay + "]");
            String heading = String.format("\n%-16s [Balance]: $%-16.2f [Debt]: $%-16.2f\n",dayDisplay,finances.getBalance(),finances.getDebt());
            System.out.println(heading);
            //System.out.println("\n[Day " + currentDay + "]\n");
            showNavMenu();
            try{
                Scanner inputScan = new Scanner(System.in);
                int choice = inputScan.nextInt();
                switch(choice){
                case 1:
                    System.out.println("\nPlease select an item from the list to buy a case\n");
                    showItemData();
                    int itemChoice = inputScan.nextInt();
                    try{
                        System.out.println(String.format("\nHow many bulk-units of %s would you like to buy?\n",items[itemChoice - 1].getName()));
                        int itemQuant = inputScan.nextInt();
                        InventoryManager.buyProduct(items[itemChoice - 1], finances, itemQuant);
                        break;
                    }
                    catch (Exception e){
                        System.out.println("\nInvalid input, try again please!");
                        break;
                    }
                case 2:
                    System.out.println(dailyHeadlines);
                    //System.out.println("\nSales records feature coming soon");
                    break;
                case 3:
                    finances.setMenuStatus(true);
                    while(finances.getMenuStatus() == true){
                        System.out.println("\nType in the number of the option you want to navigate to:\n");
                        finances.showBankMenu();
                        finances.runMenu();
                    }
                    break;
                case 4:
                    dailySale();
                    dailyHeadlines = generateHeadlines();
                    currentDay += 1;
                    finances.incrementLoanDays(1);
                    utilityBillCounter += 1;
                    if(utilityBillCounter == 7){
                        finances.payUtilities();
                        System.out.println("You just paid your weekly $" + finances.getUtilCost() + " utility costs.\n");
                        utilityBillCounter = 0;
                    }
                    break;
                case 5:
                    System.out.println("\nGoodbye!\n");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid input. Please try again");

            }
            }
            catch(Exception e){
                System.out.println("\nInvalid input, please try again.");
            }
            // Switch cases that handle different inputs from users
        }
        // Prints a message if you go bankrupt :(
        if(finances.isBankrupt() == true){System.out.println("You have gone broke after " + currentDay + " days.");}
    }

}

    
