package CafeSystem;

import java.util.Scanner;
	
		public class Main {
			public static void main(String[] args) {
				Scanner sc = new Scanner(System.in);
				Menu menu = new Menu();
				Order order = new Order();
				UI ui = new UI();
				Receipt receipt = new Receipt();
				
				ui.showMainMenu();
				
				int boothAnswer = InputHandler.getInt(sc, "Choose your booth: ", 1, 3);

				
				String username = InputHandler.getNonEmptyString(sc, "Enter Username: ");
				
						
				User user = new User(boothAnswer, username);
				
				System.out.println();
				
				while(true) {
				
					ui.showServices();
				
					System.out.println();
					int serviceAnswer = InputHandler.getInt(sc, "Select your service: ", 1 , 4);
					
					System.out.println();
					
					switch(serviceAnswer) {
						case 1:
							System.out.println("Selected Booth: " + user.getBoothType() + "\n");
							

							int hours = InputHandler.getPositiveInt(sc, "Enter amount of hours: ");
							
							user.addSession(hours);
							
							int cost = user.calculateCost();
							
							System.out.println("Cost: " + cost + "\n");
							
							user.addCost(cost);
							
							receipt.addLine(hours + " Hour(s) " + user.getBoothType() + " - ₱" + cost);
						
							break;
						case 2:
							 while(true) {

							        menu.showMenu();

							        System.out.println((menu.getItems().size() + 1) + ". Cancel Order");
							        System.out.println("0. Finish");

							  
							        int foodAnswer = InputHandler.getInt(sc, "Select Option: ", 0 , menu.getItems().size() + 1);

							        System.out.println();

							  
							        if(foodAnswer == 0) {
							            break;
							        }

							      
							        if(foodAnswer == menu.getItems().size() + 1) {

							            if(order.getOrderSize() == 0) {

							                System.out.println("No orders to cancel.\n");
							                continue;
							            }

							            order.showOrders();

	
								            int removeChoice = InputHandler.getInt(sc, "Select order number to remove: ", 1, order.getOrderSize());
	
								            order.removeItem(removeChoice - 1);
	
								            System.out.println("Order removed.\n");
	
								            continue;
								        }

							        int quantity = InputHandler.getPositiveInt(sc, "Enter Quantity: ");
							      

							        System.out.println();

							        Item chosenItem = menu.getItems().get(foodAnswer - 1);

							        order.addItem(chosenItem, quantity);

							        System.out.println("Added: " + chosenItem.getName() + " x" + quantity + "\n");
							    }

							    order.showOrders();

							    user.addCost(order.calculateTotal());

							    order.clearOrders();

							    break;
					
						case 3:
							
							int printCost = ui.photocopyMenu();

							user.addCost(printCost);
							
							receipt.addLine("Photocopy Service - ₱" + printCost);
							
							break;
						case 4:
							receipt.showReceipt(user);
							
							while(true) {
								System.out.print("Check out? (Y/N): ");
								
								String checkOut = sc.nextLine();
								System.out.println();
								
								if(checkOut.equalsIgnoreCase("YES") || checkOut.equalsIgnoreCase("Y")) {
									System.out.println("Thank you! Exiting...");
									return;
									
								}else if(checkOut.equalsIgnoreCase("NO") || checkOut.equalsIgnoreCase("N")) {
									break;
									
								}else {
									System.out.println("(Invalid answer. (Y/N)");
								}	
							}
							break;
					}
				}
			}
		}