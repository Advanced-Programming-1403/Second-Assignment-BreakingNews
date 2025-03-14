package AP;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.time.LocalDate;

public class Infrastructure {

    private final String URL;
    private final String APIKEY;
    private final String JSONRESULT;
    private ArrayList<News> newsList;

    public class News {
        private String title;
        private String description;
        private String url;

        public News(String title, String description, String url) {
            this.title = title;
            this.description = description;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return "Title: " + title + "\nDescription: " + description + "\nURL: " + url + "\n";
        }
    }

    public Infrastructure(String APIKEY) {
        this.APIKEY = APIKEY;
        this.URL = "https://newsapi.org/v2/everything?q=tesla&from=" + LocalDate.now().minusDays(1) + "&sortBy=publishedAt&apiKey=";
        this.newsList = new ArrayList<>();
        this.JSONRESULT = getInformation();
        parseInformation(); // باید بعد از دریافت JSON اجرا شود
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
            System.out.println("!!Exception : " + e.getMessage());
        }
        return null;
    }

    private void parseInformation() {
        if (JSONRESULT == null || !JSONRESULT.contains("\"articles\"")) {
            System.out.println("No valid JSON data available.");
            return;
        }

        newsList.clear();

        // پیدا کردن بخش مقالات
        String[] jsonParts = JSONRESULT.split("\"articles\":\\[");
        if (jsonParts.length < 2) {
            System.out.println("Error: No articles found in JSON.");
            return;
        }

        String articlesSection = jsonParts[1].split("]")[0];
        String[] articles = articlesSection.split("\\},\\{");

        for (int i = 0; i < Math.min(20, articles.length); i++) {
            String article = articles[i].trim();
            if (article == null || article.isEmpty()) continue;

            // استخراج اطلاعات با استفاده از substring و indexOf
            String title = extractValue(article, "\"title\":\"", "\"");
            String description = extractValue(article, "\"description\":\"", "\"");
            String url = extractValue(article, "\"url\":\"", "\"");

            if (title != null && url != null) {
                newsList.add(new News(title, description != null ? description : "No description available", url));
            }
        }
    }

    private String extractValue(String text, String startDelimiter, String endDelimiter) {
        int startIndex = text.indexOf(startDelimiter);
        if (startIndex == -1) return null;
        startIndex += startDelimiter.length();
        int endIndex = text.indexOf(endDelimiter, startIndex);
        if (endIndex == -1) return null;
        return text.substring(startIndex, endIndex).replace("\\\"", "\"");
    }

    public void displayNewsList() {
        if (newsList.isEmpty()) {
            System.out.println("No news available.");
            return;
        }
        System.out.println("News List:");
        for (int i = 0; i < newsList.size(); i++) {
            System.out.println((i + 1) + ". " + newsList.get(i).getTitle());
        }
    }
}