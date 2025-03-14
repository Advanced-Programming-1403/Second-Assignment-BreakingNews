package AP;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your API Key: ");
        String apiKey = scanner.nextLine();

        Infrastructure infrastructure = new Infrastructure(apiKey);
        infrastructure.displayNewsList();

        System.out.print("Enter the number of the news you want to see details (or 0 to exit): ");
        int choice = scanner.nextInt();

        while (choice != 0) {
            if (choice > 0 && choice <= infrastructure.getNewsList().size()) {
                System.out.println(infrastructure.getNewsList().get(choice - 1));
            } else {
                System.out.println("Invalid choice. Try again.");
            }

            System.out.print("Enter another number (or 0 to exit): ");
            choice = scanner.nextInt();
        }

        System.out.println("Exiting program.");
        scanner.close();

    }
}