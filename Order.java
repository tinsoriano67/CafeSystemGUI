package CafeSystem;


import java.util.ArrayList;


public class Order {

	private ArrayList<Item> orders = new ArrayList<>();
    private ArrayList<Integer> quantities = new ArrayList<>();

    public void addItem(Item item, int quantity) {
        orders.add(item);
        quantities.add(quantity);
    }

    public void showOrders() {
    	System.out.println("Customer Orders:");

        int total = 0;

        for(int i = 0; i < orders.size(); i++) {

            Item item = orders.get(i);
            int quantity = quantities.get(i);

            int subtotal = item.getPrice() * quantity;

            System.out.println((i + 1) + ". " + item.getName()
                + " x" + quantity + " - ₱"+ subtotal
            );

            total += subtotal;
        }

        System.out.println("Total: ₱" + total + "\n");
    }

    public int calculateTotal() {

        int total = 0;

        for(int i = 0; i < orders.size(); i++) {

            total += orders.get(i).getPrice() * quantities.get(i);
        }

        return total;
    }

    public void clearOrders() {
        orders.clear();
        quantities.clear();
    }	
    
    public void removeItem(int index) {

        orders.remove(index);
        quantities.remove(index);
    }
    
    public int getOrderSize() {
        return orders.size();
    }
}