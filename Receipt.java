package CafeSystem;


import java.util.ArrayList;

public class Receipt {

    private ArrayList<String> receiptLines = new ArrayList<>();

    public void addLine(String line) {
        receiptLines.add(line);
    }

    public void showReceipt(User user) {

        System.out.println("\n========= RECEIPT =========");

        System.out.println("Customer: " + user.getUser());
        System.out.println("Booth: " + user.getBoothType());

        System.out.println();

        for(String line : receiptLines) {
            System.out.println(line);
        }

        System.out.println("--------------------------");
        System.out.println("TOTAL: ₱" + user.getTotalCost());


    }
    public ArrayList<String> getReceiptLines() {
    return this.receiptLines;
    }
}