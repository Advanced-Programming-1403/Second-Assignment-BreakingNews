# Project Title

This report outlines the tasks completed in implementing a Java-based News Aggregator using Gradle as a package manager. It also details the challenges encountered during development and their solutions.


## Description

Our project is a Java-based News Aggregator that fetches current news articles from NewsAPI.org and displays them using both a console menu and a Swing-based GUI. It uses Gradle for dependency management, with the org.json library to parse the API's JSON responses. The application is organized into key classes: News encapsulates article details, Infrastructure handles API communication and data parsing, and Main provides a user-friendly interface for browsing, selecting, and saving articles as favorites.
## Getting Started

### Dependencies

* JDK (version 11 or later).
* Gradle package (Gradle Wrapper is recommmended.)
* org.json Library
* Java Swing
* An internet connection for fetching news articles. 

### Installing

* This project is available at https://github.com/Advanced-Programming-1403/Second-Assignment-BreakingNews
* Gradle Wrapper:
    If you do not have the wrapper files (gradlew/gradlew.bat), run gradle wrapper in the project root to generate them. This ensures consistent build behavior across different environments.

### Executing program

You can easily run the project using your IDE, or using these commands:
1. Navigate to your project's root directory
2. Run "./gradlew build" for MacOS/Linux, or "gradlew.bat build" for Windows.
3. Run "./gradlew run" for MacOS/Linux, or "gradlew.bat run" for Windows.


## Help

Make sure you've installed Gradle Wrapper (gradlew) and ensure JSON Library is in build.gradle.
Check you internet connection or try to change your ip using any DNS or VPN.

## Authors

Amirmohammad Gholami [@The_amirrzh](https://t.me/the_amirrzh)

## Version History

* 0.1
    * Initial Release
