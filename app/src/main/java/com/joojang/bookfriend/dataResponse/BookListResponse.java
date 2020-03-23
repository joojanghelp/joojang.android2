package com.joojang.bookfriend.dataResponse;


import com.joojang.bookfriend.model.Book;

import java.util.ArrayList;

public class BookListResponse extends DefaultResponse {


    public ArrayList<Book> items;

    public int last_page;

    public int getLast_page() {
        return last_page;
    }

    public ArrayList<Book> getItems() {
        return items;
    }

}
