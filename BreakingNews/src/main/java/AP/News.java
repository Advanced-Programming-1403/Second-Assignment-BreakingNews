public class News {
    private String title;
    private String description;
    private String sourceName;
    private String author;
    private String url;
    private String publishedAt;

    public News(String title, String description, String sourceName, String author, String url, String publishedAt) {
        this.title = title;
        this.description = description;
        this.sourceName = sourceName;
        this.author = author;
        this.url = url;
        this.publishedAt = publishedAt;
    }


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

    public String getPublishedAt() {
        return publishedAt;
    }

    public void displayNews() {
        System.out.println("\n📰 NEWS ARTICLE");
        System.out.println("📌 Title: " + title);
        System.out.println("📝 Description: " + (description != null && !description.isEmpty() ? description : "No Description Available"));
        System.out.println("🏛️ Source: " + sourceName);
        System.out.println("✍️ Author: " + (author != null && !author.isEmpty() ? author : "Unknown"));
        System.out.println("📅 Published At: " + publishedAt);
        System.out.println("🔗 Read More: " + url);
        System.out.println("-----------------------------------------");
    }
}
