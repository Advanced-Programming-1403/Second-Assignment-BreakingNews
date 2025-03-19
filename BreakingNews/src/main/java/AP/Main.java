import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String apiKey = "dc75747245e245b591be58873847bd6e";

        Infrastructure infrastructure = new Infrastructure(apiKey);

        infrastructure.parseInformation();

        displayMenu(infrastructure);
    }

    private static void displayMenu(Infrastructure infrastructure) {
        if (infrastructure.getNewsList() == null || infrastructure.getNewsList().isEmpty()) {
            System.out.println("No news available. Please check if the API key is correct or if there was an issue with the API.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- News Menu ---");
            for (int i = 0; i < infrastructure.getNewsList().size(); i++) {
                News news = infrastructure.getNewsList().get(i);
                System.out.println((i + 1) + ". " + news.getTitle());
            }

            System.out.println("\nEnter the number of the article you want to read, or 0 to exit:");

            try {
                int choice = scanner.nextInt();

                if (choice == 0) {
                    System.out.println("Exiting...");
                    break;
                }

                if (choice > 0 && choice <= infrastructure.getNewsList().size()) {
                    News selectedNews = infrastructure.getNewsList().get(choice - 1);
                    selectedNews.displayNews();
                } else {
                    System.out.println("Invalid choice. Please enter a valid number.");
                }

            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }

        scanner.close();
    }
}
