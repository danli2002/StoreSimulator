import java.util.ArrayList;
public class GroceryStore_DL{
	
	ScannerReadFile scanner = new ScannerReadFile();
	ArrayList<String> data = scanner.returnData();
	StoreItem_DL[] items = new StoreItem_DL[data.size() / 4];
	public GroceryStore_DL(){
		init();
	}
	
	public void populateItems(){
		for(int i = 0; i < data.size() / 4; i++){
			StoreItem_DL item = new StoreItem_DL(
				data.get(i * 4),
				Double.parseDouble(data.get(i * 4 + 1)),
				Integer.parseInt(data.get(i * 4 + 2)),
				Double.parseDouble(data.get(i* 4 + 3))
				);
			items[i] = item;
		}
	}

	public final void init(){
		this.populateItems();
		System.out.printf("%-15s","Item Name");
		System.out.printf("%-15s","Item Price");
		System.out.printf("%-15s","Bulk Price");
		System.out.printf("%-15s","Bulk Size");
		System.out.println();
		
		for(int q = 0; q < items.length; q++){
			System.out.println(items[q].toString());
		}

	}

}

	
