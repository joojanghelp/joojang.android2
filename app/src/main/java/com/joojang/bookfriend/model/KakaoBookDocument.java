package com.joojang.bookfriend.model;

import java.util.ArrayList;

public class KakaoBookDocument {

    public ArrayList<String> authors;
    public String contents;
    public String publisher;
    public String thumbnail;
    public String title;


    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAllAuthors() {
        String allAuthors="";

        for(int i=0 ; i<authors.size(); i++){
            allAuthors += authors.get(i);
            if ( i != (authors.size()-1 ) ){
                allAuthors+=",";
            }
        }

        return allAuthors;
    }

}
