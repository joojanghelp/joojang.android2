package com.joojang.bookfriend.dataResponse;


import com.joojang.bookfriend.model.Book;

import java.util.ArrayList;

public class BookDetailResponse extends DefaultResponse {


    public String uuid;
    public String user_id;
    public String user_name;
    public String title;
    public String authors;
    public String contents;
    public String isbn;
    public String publisher;
    public String thumbnail;

    public String getUuid() {
        return uuid;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getContents() {
        return contents;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
