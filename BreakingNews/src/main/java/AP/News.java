package AP;

public class News {
    private String title;
    private String description;
    private String author;
    private String sourceName;
    private String url;
    private String publishedAt;

    public News(String title, String description, String author, String sourceName, String url, String publishedAt) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.sourceName = sourceName;
        this.url = url;
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void displayNews(){
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Author: " + author);
        System.out.println("Source Name: " + sourceName);
        System.out.println("URL: " + url);
        System.out.println("Published At: " + publishedAt);
    }
}