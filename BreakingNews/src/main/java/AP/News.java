package AP;

import java.text.SimpleDateFormat;
import java.util.Date;

public class News {
    private String title;
    private String description;
    private String sourceName;
    private String author;
    private String url;
    private Date publishedAt;

    public News(String title, String description, String sourceName, String author, String url, Date publishedAt) {
        this.title = title;
        this.description = description;
        this.sourceName = sourceName;
        this.author = author;
        this.url = url;
        this.publishedAt = publishedAt;
    }


     // Displays all the details of this news article.
    public void displayNews() {
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Source: " + sourceName);
        System.out.println("Author: " + author);
        System.out.println("URL: " + url);
        System.out.println("Published At: " + publishedAt);
    }

    /**
     * Returns the publish date formatted with the given pattern.
     *
     * @param format e.g., "yyyy-MM-dd"
     * @return formatted date string
     */
    public String getFormattedPublishedAt(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(publishedAt);
    }

    // Getters for use in the GUI and menus
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }
}
