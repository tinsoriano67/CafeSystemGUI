package CafeSystem;

import java.util.ArrayList;

public class Menu {
	private ArrayList<Item> items = new ArrayList<>();
	
	Menu(){
		items.add(new Item("Pancit Canton", 25));
		items.add(new Item("Hotdog", 15));
		items.add(new Item("Coke", 20));
		items.add(new Item("Mountain Dew", 20));
		items.add(new Item("Water", 15));
	}
	
	public void showMenu() {
		System.out.println("Welcome to our food Menu! \n");
		
		for(int i = 0; i < items.size(); i++) {
			System.out.println((i + 1) + ". " + items.get(i).getName() + " - ₱" + items.get(i).getPrice());
		}
	}

	
	public ArrayList<Item> getItems() {
		return items;
	}
}