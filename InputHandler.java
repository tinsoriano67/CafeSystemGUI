package CafeSystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {

	public static int getInt(Scanner sc, String prompt, int min, int max) {
	    int input;

	    while(true) {
	        try {
	            System.out.print(prompt);

	            input = sc.nextInt();
	            sc.nextLine();

	            // invalid range
	            if(input < min || input > max) {

	                if(min == max) {
	                    System.out.println("Only option is: " + min);
	                } else {
	                    System.out.println("Input must be between " + min + " and " + max);
	                }

	                continue;
	            }

	            return input;

	        } catch(InputMismatchException e) {

	            System.out.println("Numbers only.");
	            sc.nextLine();
	        }
	    }
	}

    public static int getPositiveInt(Scanner sc, String prompt) {
        int input;

        while(true) {
            try {

                System.out.print(prompt);

                input = sc.nextInt();
                sc.nextLine();

                if(input <= 0) {
                    System.out.println( "Input must be positive.");

                    continue;
                }

                return input;

            } catch(InputMismatchException e) {

                System.out.println("Numbers only.");

                sc.nextLine();
            }
        }
    }

    public static String getNonEmptyString(Scanner sc, String prompt) {

        String input;

        while(true) {

            System.out.print(prompt);

            input = sc.nextLine().trim();

            if(input.isEmpty()) {

                System.out.println("Input cannot be empty.");

                continue;
            }

            return input;
        }
    }
}