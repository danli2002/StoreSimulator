import java.util.ArrayList;

public class StoreItem_DL{
	private String n;
	private double rPrice;
	private int q;
	private double bPrice;
	public StoreItem_DL(String name, double retailPrice, int bulkQuant, double bulkPrice){
		n = name;
		rPrice = retailPrice;
		q = bulkQuant;
		bPrice = bulkPrice;
	}

	public String toString(){
		String itemString = String.format("%-15s %-15.2f %-15d %-15.2f",n,rPrice,q,bPrice);
		return itemString;
	}
}






