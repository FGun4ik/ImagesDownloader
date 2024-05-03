package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLConnection;
import java.util.HashSet;
import java.io.*;
import java.net.URL;


            //Евстифеев Егор
public class Main {
    public static void main(String[] args) {
        try {
            String url = "https://sirius-db-dev.github.io/db-docs/";
            Document doc = Jsoup.connect(url).get();
            Elements images = doc.select("img");
            HashSet<String> links = new HashSet<>();

            for (Element image : images) {
                links.add(image.attr("abs:src"));
            }

            int number = 1;
            for (String link : links) {
                String extension = link.replaceAll("^.+\\.", "").replace("?.+$", "");
                String filePath = "data/" + number++ + "." + extension;
                try {
                    download(link, filePath);
                } catch (IOException e) {
                    System.out.println("Error downloading image from: " + link);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void download(String link, String filePath) throws IOException {
        URLConnection connection = new URL(link).openConnection();
        try (InputStream inStream = connection.getInputStream();
             OutputStream outStream = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            System.out.println("Downloaded: " + filePath);
        }
    }
}