# Second Assignment AP

This code serve to show 20 recent news from newsapi.org and show them to user detailed.

## Description

This code have 2 parts:

1. Infrastructure class to get information of 20 recent news 
2. javafx pkg to view news in GUI

You can use the Infrastructure class to fetch news, save favorite news in .csv format, or simply read them in detail.

The JavaFX GUI allows you to view the news, but currently, it does not support saving them to a CSV file (yet!).

## Getting Started

### Dependencies

* For building purposes you should have gradle, jdk 21+ , and a good internet connection (javafx would be a pain to download :(  )

* To just use java output, you just need jvm 21+ ready! use fat jar to interact with application.

### Installing

* If you want to Build application, install jdk 21+ and try to build fat jar with ./gradlew :shadowJar this would build your application as fat jar (uber jar).
* Then just double-click on jar file, and you are ready to go :)

### Executing program

#### Steps to use Infrastructure class:
* Load class and read news :

```
Infrastructure api = new Infrastructure("09e4ae22c287412c898681c8170a7a68", "news.csv");
api.displayNewsList();
```
* To see specific news in detailed view :

```
api.displayNews(name news or id news in last step);
```
* To save specific news use:

```
api.writeToCSV(list id news to save);
```

#### Steps to build and run application class:
* Build application as fat jar:


```
./gradlew shadowJar // Linux and mac
gradlew.exe shadowJar // windows
```

* Run application

```
javaw BreakingNews/build/libs/BreakingNews-1.0-SNAPSHOT-all.jar
```

## Code Structure
### Infrastructure Class
The Infrastructure class is responsible for fetching news data from the NewsAPI and managing the data. It includes methods to display news, save news to a CSV file, and retrieve detailed information about specific news articles.
```
public class Infrastructure {
private String APIKEY;
private String CSV_PATH;

    public Infrastructure(String APIKEY, String CSV_PATH) {
        this.APIKEY = APIKEY;
        this.CSV_PATH = CSV_PATH;
    }

    public void displayNewsList() {
        // Fetch and display the list of recent news articles
    }

    public void displayNews(String newsTitle || Integer newsId) {
        // Display detailed information about a specific news article
    }

    public void writeToCSV(List<Integer> newsIds) {
        // Save specific news articles to a CSV file
    }
}
```
## Future Improvements
Save to CSV in GUI: Implement the functionality to save news articles to a CSV file directly from the javafx GUI.

## Authors

Hesan

