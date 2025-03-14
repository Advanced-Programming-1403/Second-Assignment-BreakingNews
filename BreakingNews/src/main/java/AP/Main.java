package AP;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String apiKey = "c3456d6c9ce64e889f1001baca14059a"; // Use any available API keys.
        Infrastructure infrastructure = new Infrastructure(apiKey);

        while (true) {
            System.out.println("\nNews Menu:");
            System.out.println("1. View News List");
            System.out.println("2. View Favorite Articles");
            System.out.println("3. Launch GUI");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    infrastructure.displayNewsList();
                    break;
                case 2:
                    infrastructure.displayFavoriteArticles();
                    break;
                case 3:
                    infrastructure.launchGUI();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
