package AP;

public record News(String title, String description, org.json.JSONObject sourceName, String author,
                   String url, java.time.ZonedDateTime publishedAt) {


    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", publishedAt=" + publishedAt +
                '}';
    }

    public void displayNews(){
        System.out.println(this);
    }

}
