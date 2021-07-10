package com.example.vidhyalay;

public class Notes {
    String title,url;

    public Notes(String title, String url) {
        this.title = title;
        this.url = url;
    }
    public Notes() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
