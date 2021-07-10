package com.example.vidhyalay;

public class Book {
    String bookurl,name;

    public Book(String bookurl, String name) {
        this.bookurl = bookurl;
        this.name = name;
    }

    public String getBookurl() {
        return bookurl;
    }

    public void setBookurl(String bookurl) {
        this.bookurl = bookurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Book() {
    }
}
