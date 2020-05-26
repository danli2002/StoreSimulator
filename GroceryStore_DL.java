import java.util.ArrayList;
public class GroceryStore_DL{
	
	ScannerReadFile scanner = new ScannerReadFile();
	ArrayList<String> data = scanner.returnData();
	StoreItem_DL[] items = new StoreItem_DL[data.size() / 5];
	Finances_DL finances = new Finances_DL();
	private int currentDay = 0;
	public GroceryStore_DL(){
		init();
		dailySale();
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
		// Prints out headers, this is for debug purposes for now
		System.out.printf("%-15s","Item Name");
		System.out.printf("%-15s","Item Price");
		System.out.printf("%-15s","Item Quantity");
		System.out.printf("%-15s","Bulk Quantity");
		System.out.printf("%-15s","Bulk Price");
		System.out.printf("%-15s","Popularity Index");
		System.out.println();

		// Prints out items and their details in a nice format
		for(int q = 0; q < items.length; q++){
			System.out.println(items[q].toString());
		}

	}
	// Daily business at the store
	public void dailySale(){
		for(int i = 0; i < items.length; i++){
			StoreItem_DL item = items[i];
			// Prints out the drop off in popularity statistics and the random seed of the daily sales (i.e. sales are determined randomly within a range, depending on it's pop. index)
			System.out.println("\nDebug stats for item: " + item.n);
			double saleRate;
			
			// A poorly thought out way of figuring out how much would sell on a given day, need to clean this up later
			double dropOff = (Math.pow(0.0489769,item.getPIndex()) * 100);
			System.out.println(String.format("Dropoff: %.2f",dropOff));
			double seed = (item.getPIndex() * 100 - dropOff) + Math.random() * dropOff;
			
			// avoid selling "negative" amounts of goods
			System.out.println(String.format("seed: %.2f",seed));
			if(item.getPIndex() * 100 - dropOff >= 0){
				saleRate = ((item.getPIndex() * 100 - dropOff) + seed)/100;
			}
			else{
				saleRate = 0;
			}

			// Differentiated messages based on the performance of some goods on the shelves
			int unitSold = (int)(saleRate * item.quantity);
			if(unitSold > item.quantity){
				finances.balance += item.quantity * item.rPrice;
				item.quantity = 0;
				System.out.println(String.format("%s completely sold out at a popularity index of %.2f",item.n,item.pIndex));
			}
			else if (unitSold < item.quantity && unitSold != 0) {
				finances.balance += unitSold * item.rPrice;
				item.quantity -= unitSold;
				System.out.println(String.format("%s sold %d units at a popularity index of %.2f",item.n,unitSold,item.pIndex));
			}
			else {
				System.out.println(String.format("%s sold nothing at a popularity index of %.2f",item.n,item.pIndex));
			}

		}
		System.out.println("\nYour balance is now at $" + String.format("%.2f",finances.balance));
	}

}

	
