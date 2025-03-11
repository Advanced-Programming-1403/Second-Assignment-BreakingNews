package AP;

import org.json.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.io.IOException;


public class Infrastructure {

    private final String URL;
    private final String APIKEY;
    private final String JSONRESULT;
    private ArrayList<News> newsList; // TODO: Create the News class


    public Infrastructure(String APIKEY) {
        this.APIKEY = APIKEY;
        this.URL = "https://newsapi.org/v2/everything?q=tesla&from=" + LocalDate.now().minusDays(1) + "&sortBy=publishedAt&apiKey=";
        this.JSONRESULT = getInformation();
        this.newsList = new ArrayList<News>();
        parseInformation();
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
        if(JSONRESULT == null) {
            System.out.println("there is no information to show :(");
            return;
        }

        JSONObject obj = new JSONObject(JSONRESULT);
        JSONArray articles = obj.getJSONArray("articles");
        int count = Math.min(articles.length(), 20);
        for (int i = 0; i < count; i++) {

            JSONObject article = articles.getJSONObject(i);

            String title = article.optString("title" , "no title");
            String author = article.optString("author" , "unknown author");
            String description = article.optString("description" , "no description");
            String publishedAt = article.optString("publish Date" , "unknown date");
            String url = article.optString("url" , "no url");
            String sourceName = article.optString("source" , "unknown source");

            newsList.add(new News(title , description , author , sourceName , url , publishedAt ));
        }
    }

    public void displayNewsList() throws IOException {
        if(newsList.isEmpty()) {
            System.out.println("there is no news to show :(");
            return;
        }
        System.out.println("top 20 Available Articles : \n");
        for(int i = 0; i < newsList.size(); i++) {
            System.out.println((i + 1) + ". " + newsList.get(i).getTitle());
        }
        System.out.println("which article do you want to show : ");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        if(input >= 1 && input <= 20) {
            newsList.get(input-1).displayNews();
            System.out.println("press enter to continue");
            char ch = (char) System.in.read();
            if(ch == '\n')
                displayNewsList();
        }
        else
            System.out.println("please enter a valid number");
    }

}
