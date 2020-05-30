/*
    A class that handles individual items inside of the grocery store.

    by Daniel Li
    5/27/20
*/

import java.util.ArrayList;

public class StoreItem_DL{
    
    private String n; // name of the item
    private double rPrice; // retail price of the item
    private int bulkQuantity; // number of units in a bulk quantity
    private double bPrice; // price of a bulk purchase of the specific item
    private int quantity; // quantity of specific item in stock
    private double pIndex; // a number that basically tells how popular an item is, determines sales
    
    public StoreItem_DL(String name, double retailPrice, int bulkQuant, double bulkPrice, double popIndex){
        n = name;
        rPrice = retailPrice;
        quantity = 0; // quantity of items are always going to start at this(DEBUG PURPOSES)
        bulkQuantity = bulkQuant;
        bPrice = bulkPrice;
        pIndex = popIndex;
    }

    public String getName(){
        return n;
    }

    public double getRetailPrice(){
        return rPrice;
    }

    public int getBulkQuant(){
        return bulkQuantity;
    }

    public double getBulkPrice(){
        return bPrice;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int amt){
        quantity = 0;
    }

    public void incrementQuantity(int amt){
        quantity += amt;
    }

    public double getPIndex(){
        return pIndex;
    }

    public void incrementPIndex(double amt){
        pIndex *= amt;
    }

    public void setPIndex(double index){
        pIndex = index;
    }

    public void setPrice(double price){
        rPrice = price;
    }

    public String toString(){
        String itemString = String.format("%-18s %-18.2f %-18d %-18d %-18.2f",
        n,rPrice,quantity,bulkQuantity,bPrice); // returns a formatted string so it can be easily printed out
        return itemString;
    }

    public void priceShock(double oldPrice, double newPrice){
        double multiplier = oldPrice / newPrice;
        pIndex *= multiplier;
    }

}






