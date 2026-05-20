package CafeSystem;


import java.util.Scanner;



public class UI {
	Scanner sc = new Scanner(System.in);
	static void showMainMenu() {
		System.out.println("Welcome to our Cafe! ");
		System.out.println("1. VIP ");
		System.out.println("2. High-End Gaming PC ");
		System.out.println("3. Standard Browsing \n");
	}
	
	static void showServices() {
		System.out.println("Available services: ");
		System.out.println("1. Time/Package");
		System.out.println("2. Food & Drinks");
		System.out.println("3. Print/Photocopy ");
		System.out.println("4. Balance Tracker");
	}
	
	public int photocopyMenu() {
		
	    System.out.println("Select paper size:");
	    System.out.println("1. A4");
	    System.out.println("2. Short");
	    System.out.println("3. Long");
	    
	    int paperChoice = InputHandler.getInt(sc, "Choice: ", 1, 3);

	    int pageNum = InputHandler.getPositiveInt(sc, "Enter number of pages: ");

	    System.out.println("\nSelect print type:");
	    System.out.println("1. Colored");
	    System.out.println("2. Black & White");

	    int printChoice = InputHandler.getInt(sc, "Choice: ", 1, 2);
	    
	    int numCopies = InputHandler.getPositiveInt(sc, "Enter number of copies: ");

	    System.out.println("\nPrinting Style:");
	    System.out.println("1. Single-sided");
	    System.out.println("2. Back-to-back");

	    int styleChoice = InputHandler.getInt(sc, "Choice: ", 1, 2);



	    String paperSize = switch(paperChoice) {
	        case 1 -> "A4";
	        case 2 -> "Short";
	        case 3 -> "Long";
	        default -> "Unknown";
	    };

	    String printType = switch(printChoice) {
	        case 1 -> "Colored";
	        case 2 -> "Black & White";
	        default -> "Unknown";
	    };

	    String printingStyle = switch(styleChoice) {
	        case 1 -> "Single-sided";
	        case 2 -> "Back-to-back";
	        default -> "Unknown";
	    };



	    int pricePerPage;

	    if(printChoice == 1) {
	        pricePerPage = 10;
	    } else {
	        pricePerPage = 3;
	    }

	    int total = pageNum * numCopies * pricePerPage;


	    System.out.println("\n===== PRINT SUMMARY =====");

	    System.out.println("Paper Size: " + paperSize);
	    System.out.println("Pages: " + pageNum);
	    System.out.println("Print Type: " + printType);
	    System.out.println("Copies: " + numCopies);
	    System.out.println("Style: " + printingStyle);

	    System.out.println("Total Cost: ₱" + total + "\n");

	    return total;
		
		
	}
}