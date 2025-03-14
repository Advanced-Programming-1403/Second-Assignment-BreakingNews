package AP;

import java.time.LocalDate;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Infrastructure {

    private final String URL;
    private final String APIKEY;
    private final String JSONRESULT;
    private ArrayList<News> newsList;
    private ArrayList<News> favoriteArticles = new ArrayList<>();

    public Infrastructure(String APIKEY) {
        this.APIKEY = APIKEY;
        // Build the URL with yesterday's date for filtering articles
        this.URL = "https://newsapi.org/v2/everything?q=tesla&from=" + LocalDate.now().minusDays(1) + "&sortBy=publishedAt&apiKey=";
        this.JSONRESULT = getInformation();
        if (JSONRESULT != null) {
            parseInformation();
        } else {
            newsList = new ArrayList<>();
        }
    }

    public ArrayList<News> getNewsList() {
        return newsList;
    }

    /**
     * Retrieves the JSON result from the API.
     */
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

    /**
     * Parses the JSON result to extract the first 20 articles and map them to News objects.
     */
    private void parseInformation() {
        newsList = new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(JSONRESULT);
            JSONArray articles = jsonObj.getJSONArray("articles");
            int count = Math.min(articles.length(), 20);
            for (int i = 0; i < count; i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.optString("title");
                String description = article.optString("description");
                String sourceName = "";
                if(article.has("source")) {
                    JSONObject source = article.getJSONObject("source");
                    sourceName = source.optString("name");
                }
                String author = article.optString("author");
                String url = article.optString("url");
                String publishedAtStr = article.optString("publishedAt");
                Date publishedAt;
                try {
                    publishedAt = Date.from(java.time.Instant.parse(publishedAtStr));
                } catch(Exception e) {
                    publishedAt = new Date();
                }
                News news = new News(title, description, sourceName, author, url, publishedAt);
                newsList.add(news);
            }
        } catch(Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
    }

    /**
     * Displays the list of news titles as a menu so that the user can choose one to view details.
     * Additional options include saving favorite articles and changing the publish date format.
     */
    public void displayNewsList() {
        if (newsList == null || newsList.isEmpty()) {
            System.out.println("No news available.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("\n=== News List ===");
            for (int i = 0; i < newsList.size(); i++) {
                System.out.println((i + 1) + ". " + newsList.get(i).getTitle());
            }
            System.out.println("F. View Favorite Articles");
            System.out.println("G. Open GUI");
            System.out.println("E. Exit");
            System.out.print("Select an article number to view details, or choose an option: ");
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("E")) {
                System.out.println("Exiting News List.");
                break;
            } else if(input.equalsIgnoreCase("F")) {
                displayFavoriteArticles();
            } else if(input.equalsIgnoreCase("G")) {
                launchGUI();
            } else {
                try {
                    int index = Integer.parseInt(input) - 1;
                    if (index >= 0 && index < newsList.size()) {
                        News selectedNews = newsList.get(index);
                        selectedNews.displayNews();
                        System.out.println("\nOptions:");
                        System.out.println("1. Save to Favorite Articles");
                        System.out.println("2. Change Publish Date Format");
                        System.out.println("0. Back to News List");
                        System.out.print("Select an option: ");
                        String option = scanner.nextLine();
                        if (option.equals("1")) {
                            saveFavoriteArticle(selectedNews);
                        } else if (option.equals("2")) {
                            System.out.print("Enter new date format (e.g., yyyy-MM-dd): ");
                            String format = scanner.nextLine();
                            System.out.println("Formatted Date: " + selectedNews.getFormattedPublishedAt(format));
                        }
                    } else {
                        System.out.println("Invalid selection.");
                    }
                } catch(NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }
        }
    }

    /**
     * Saves a news article to the favorites list if it is not already saved.
     */
    private void saveFavoriteArticle(News news) {
        if (!favoriteArticles.contains(news)) {
            favoriteArticles.add(news);
            System.out.println("Article saved to favorites.");
        } else {
            System.out.println("Article is already in favorites.");
        }
    }

    /**
     * Displays the list of favorite articles.
     */
    public void displayFavoriteArticles() {
        if (favoriteArticles.isEmpty()) {
            System.out.println("No favorite articles.");
        } else {
            System.out.println("\n=== Favorite Articles ===");
            for (News news : favoriteArticles) {
                System.out.println(news.getTitle());
            }
        }
    }

    /**
     * Launches a simple Swing-based GUI for the news reader.
     */
    public void launchGUI() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Creates and displays the GUI, which shows a list of news titles, details of the selected news,
     * and provides a button to save articles to favorites.
     */
    private void createAndShowGUI() {
        javax.swing.JFrame frame = new javax.swing.JFrame("News Reader");
        frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new java.awt.BorderLayout());

        // List model and JList for displaying news titles
        javax.swing.DefaultListModel<String> listModel = new javax.swing.DefaultListModel<>();
        for (News news : newsList) {
            listModel.addElement(news.getTitle());
        }
        javax.swing.JList<String> newsJList = new javax.swing.JList<>(listModel);
        javax.swing.JScrollPane listScrollPane = new javax.swing.JScrollPane(newsJList);
        listScrollPane.setPreferredSize(new java.awt.Dimension(250, 600));
        frame.add(listScrollPane, java.awt.BorderLayout.WEST);

        // Text area to display selected news details
        javax.swing.JTextArea newsDetails = new javax.swing.JTextArea();
        newsDetails.setEditable(false);
        frame.add(new javax.swing.JScrollPane(newsDetails), java.awt.BorderLayout.CENTER);

        // Button to save the selected article to favorites
        javax.swing.JButton favoriteButton = new javax.swing.JButton("Save to Favorites");
        favoriteButton.addActionListener(e -> {
            int selectedIndex = newsJList.getSelectedIndex();
            if (selectedIndex != -1) {
                News selectedNews = newsList.get(selectedIndex);
                saveFavoriteArticle(selectedNews);
                javax.swing.JOptionPane.showMessageDialog(frame, "Article saved to favorites!");
            } else {
                javax.swing.JOptionPane.showMessageDialog(frame, "Please select an article first.");
            }
        });

        javax.swing.JPanel bottomPanel = new javax.swing.JPanel();
        bottomPanel.add(favoriteButton);
        frame.add(bottomPanel, java.awt.BorderLayout.SOUTH);

        // When an article is selected, display its full details
        newsJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = newsJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    News selectedNews = newsList.get(selectedIndex);
                    StringBuilder details = new StringBuilder();
                    details.append("Title: ").append(selectedNews.getTitle()).append("\n");
                    details.append("Description: ").append(selectedNews.getDescription()).append("\n");
                    details.append("Source: ").append(selectedNews.getSourceName()).append("\n");
                    details.append("Author: ").append(selectedNews.getAuthor()).append("\n");
                    details.append("URL: ").append(selectedNews.getUrl()).append("\n");
                    details.append("Published At: ").append(selectedNews.getFormattedPublishedAt("yyyy-MM-dd HH:mm:ss")).append("\n");
                    newsDetails.setText(details.toString());
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
