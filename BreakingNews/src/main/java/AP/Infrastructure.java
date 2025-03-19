package AP;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class Infrastructure {

    private final String URL;
    private final String APIKEY;
    private final String JSONRESULT;
    private ArrayList<News> newsList;

    public Infrastructure(String APIKEY) {
        this.APIKEY = APIKEY;
        this.URL = "https://newsapi.org/v2/everything?q=tesla&from=" 
                   + LocalDate.now().minusDays(1) + "&sortBy=publishedAt&apiKey=";
        this.JSONRESULT = getInformation();
        parseInformation(); // Ensure newsList is populated
    }

    public ArrayList<News> getNewsList() {
        return newsList;
    }

    private String getInformation() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + APIKEY))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new IOException("HTTP error code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return null;
    }

    private void parseInformation() {
        if (JSONRESULT == null || JSONRESULT.isEmpty()) {
            System.out.println("No data received from API.");
            return;
        }

        newsList = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(JSONRESULT);
            JSONArray articles = jsonResponse.getJSONArray("articles");

            for (int i = 0; i < Math.min(20, articles.length()); i++) {
                JSONObject article = articles.getJSONObject(i);

                String title = article.optString("title", "No Title");
                String description = article.optString("description", "No Description");
                String sourceName = article.has("source") ? article.getJSONObject("source").optString("name", "Unknown Source") : "Unknown Source";
                String author = article.optString("author", "Unknown");
                String url = article.optString("url", "No URL");
                String publishedAt = article.optString("publishedAt", "Unknown Date");

                newsList.add(new News(title, description, sourceName, author, url, publishedAt));
            }
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
    }

    public void displayNewsList() {
        if (newsList == null || newsList.isEmpty()) {
            System.out.println("No news available.");
            return;
        }

        for (int i = 0; i < newsList.size(); i++) {
            System.out.println((i + 1) + ". " + newsList.get(i).getTitle());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter the number of the article you want to read, or 0 to exit:");

        int choice = scanner.nextInt();

        if (choice > 0 && choice <= newsList.size()) {
            newsList.get(choice - 1).displayNews();
        } else if (choice == 0) {
            System.out.println("Exiting...");
        } else {
            System.out.println("Invalid choice. Please enter a valid number.");
        }
        scanner.close();
    }
}
