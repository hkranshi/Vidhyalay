package com.example.vidhyalay;

public class Member {
    String title,uname,vedioUrl,search;

    public Member(String title, String uname, String vedioUrl,String search) {
      this.title = title;
       this.uname = uname;
        this.vedioUrl = vedioUrl;
        this.search=search;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
       this.title = title;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getVedioUrl() {
        return vedioUrl;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl;
    }

    public Member() {

    }

}
