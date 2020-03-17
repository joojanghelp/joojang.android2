package com.joojang.bookfriend.data;


import com.joojang.bookfriend.model.Book;

import java.util.ArrayList;

public class UserBookListResponse extends DefaultResponse {


    public ArrayList<Book> items;

    public ArrayList<Book> getItems() {
        return items;
    }

    public void setItems(ArrayList<Book> items) {
        this.items = items;
    }
}
