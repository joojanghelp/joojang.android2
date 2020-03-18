package com.joojang.bookfriend.dataResponse;


import com.joojang.bookfriend.model.Book;

import java.util.ArrayList;

public class BookListResponse extends DefaultResponse {


    public ArrayList<Book> items;

    public ArrayList<Book> getItems() {
        return items;
    }

}
