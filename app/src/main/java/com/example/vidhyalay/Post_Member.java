package com.example.vidhyalay;

public class Post_Member {
    private String imageUri;
    private String title;
    private String name;
    public Post_Member(){}

    public Post_Member(String imageUri, String title,String name) {
        this.imageUri = imageUri;
        this.title = title;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
