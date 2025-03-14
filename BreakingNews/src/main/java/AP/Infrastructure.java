package AP;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Infrastructure {

    private final String URL;
    private final String APIKEY;
    private String jsonResult;
    private final String CSV_PATH;
    private ArrayList<News> newsList;


    public Infrastructure(String APIKEY, String CSV_PATH) {
        this.APIKEY = APIKEY;
        this.CSV_PATH = CSV_PATH;
        this.URL = "https://newsapi.org/v2/everything?q=tesla&from=" + LocalDate.now().minusDays(1) + "&sortBy=publishedAt&apiKey=";
        this.jsonResult = getInformation();
    }

    public ArrayList<News> getNewsList() {
        if (newsList == null){
            parseInformation();
        }

        return newsList;
    }
    
    public ArrayList<News> getAndRefreshNewsList() {
        this.jsonResult = getInformation();
        parseInformation();
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
        if (jsonResult == null) {
            System.err.println("Couldn't get information from the API");
            throw new RuntimeException("Couldn't get information from the API");
        }

        newsList = new ArrayList<>(); // Refresh news list

        JSONObject object = new JSONObject(jsonResult);
        JSONArray jsonArray = object.getJSONArray("articles");

        for (int i = 0; i < Math.min(jsonArray.length(), 20); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String title = jsonObject.getString("title");
            String description = jsonObject.getString("description");
            JSONObject sourceName = jsonObject.getJSONObject("source");
            String author = jsonObject.optString("author");
            String url = jsonObject.getString("url");
            ZonedDateTime publishedAt = ZonedDateTime.parse(jsonObject.getString("publishedAt"));
            News news = new News(title, description, sourceName, author, url, publishedAt);
            newsList.add(news);
        }
    }

    public void displayNewsList() {
        if (newsList == null){
            parseInformation();
        }

        for (News news : newsList) {
            System.out.println((newsList.indexOf(news)+ 1) + "." +  news.title());
        }
    }

    public void displayNews(String title) {
        if (newsList == null){
            parseInformation();
        }

        List<News> jsonList = newsList.stream().filter(news -> news.title().equals(title)).toList();

        if (jsonList.size() == 1) {
            jsonList.getFirst().displayNews();
        }
    }

    public void displayNews(Integer number) {
        try {
            newsList.get(number -1).displayNews();
        }catch (IndexOutOfBoundsException e) {
            System.err.println("Could not get news with number " + number);
        }

    }

    public void writeToCSV(List<Integer> numbersToSave) {
        String[] header = {"Title", "Description", "Source Name", "Author", "URL", "Published At"};
        List<News> list = new ArrayList<>();

        numbersToSave.forEach(integer -> {
            try {
                list.add(newsList.get(integer - 1));
            }catch (IndexOutOfBoundsException e) {
                System.err.println("Could not get news with number " + integer);
            }
        });

        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND)){
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header));

            for (News news : list) {
                csvPrinter.printRecord(
                        news.title(),
                        news.description(),
                        news.sourceName() != null ? news.sourceName().optString("name") : "N/A",
                        news.author(),
                        news.url(),
                        news.publishedAt().toLocalDate()
                );
            }

            csvPrinter.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Successfully written to CSV file in " + CSV_PATH);
    }

}
