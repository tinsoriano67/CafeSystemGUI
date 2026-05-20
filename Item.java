package CafeSystem;

public class Item {
	String itemName;
	int itemCost;
	
	Item(String itemName, int itemCost){
		this.itemName = itemName;
		this.itemCost = itemCost;
	}
	
	String getName(){
		return itemName;
	}
	
	int getPrice() {
		return itemCost;
	}
}