# Breaking News

No matter where you are, with this app you can access the world's news whenever you want.

## Description

This app shows some news about all over the world (up to 20 news)

### Installation

#### Prerequisites
Before setting up the project, ensure you have the following installed:

- [Java Development Kit (JDK)](https://adoptopenjdk.net/) (JDK 8 or later)
- [Gradle](https://gradle.org/install/)
- [Git](https://git-scm.com/) (optional, for cloning the repository)

## Cloning the Repository
If the project is hosted on GitHub or another version control platform, clone it using:

```sh
git clone https://github.com/aeen-bolandian/Second-Assignment-BreakingNews.git
cd Second-Assignment-BreakingNews
```

## Setting Up Dependencies

This project uses [`org.json`](https://github.com/stleary/JSON-java) for JSON parsing. Gradle will automatically handle its installation.

Ensure the following dependency is present in your `build.gradle` file:

```gradle
dependencies {
    implementation 'org.json:json:20210307'
}
```
Then, refresh Gradle dependencies using the following command:

```sh
gradle build
```
Running the Project
After setting up dependencies, you can run the project using:

```sh
gradle run
```
or if using the Java application plugin:

```sh
gradle run --args="your_arguments"
```
Verifying Installation
To check if the dependencies are installed correctly, run:

```sh
gradle dependencies
```
## Usage
after you run the program you will see at least 20 news from all over the world and type number of the news which you want to see it's full information
then you can press enter to see news list.

## Resources
this project uses news api https://newsapi.org/ 