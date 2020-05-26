import java.util.ArrayList;
public class GroceryStore_DL{
	
	ScannerReadFile scanner = new ScannerReadFile();
	ArrayList<String> data = scanner.returnData();
	StoreItem_DL[] items = new StoreItem_DL[data.size() / 5];
	private int currentDay = 0;
	public GroceryStore_DL(){
		init();
	}
	
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

	public final void init(){
		System.out.println(data);
		this.populateItems();
		System.out.printf("%-15s","Item Name");
		System.out.printf("%-15s","Item Price");
		System.out.printf("%-15s","Item Quantity");
		System.out.printf("%-15s","Bulk Quantity");
		System.out.printf("%-15s","Bulk Price");
		System.out.printf("%-15s","Popularity Index");
		System.out.println();
		
		for(int q = 0; q < items.length; q++){
			System.out.println(items[q].toString());
		}

	}

}

	
