package com.joojang.bookfriend.dataResponse;


import com.joojang.bookfriend.model.Book;
import com.joojang.bookfriend.model.RecommGubun;

import java.util.ArrayList;

public class RecommGubunResponse extends DefaultResponse {


    public ArrayList<RecommGubun> items;

    public ArrayList<RecommGubun> getItems() {
        return items;
    }
}
