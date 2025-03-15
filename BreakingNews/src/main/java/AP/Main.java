package AP;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);

        // گرفتن API Key از کاربر
//        System.out.print("Please enter your API Key: ");
        String apiKey =  "0c2d4b40c8604ffe82df18376edcfcc8";

        // ساخت نمونه‌ای از کلاس Infrastructure
        Infrastructure infrastructure = new Infrastructure(apiKey);

        // نمایش لیست اخبار
        System.out.println("Fetching news...");
        infrastructure.displayNewsList();
    }
}