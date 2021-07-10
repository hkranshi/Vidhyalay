package com.example.vidhyalay;

public class Paper {
    String text,url;

    public Paper(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public Paper() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
