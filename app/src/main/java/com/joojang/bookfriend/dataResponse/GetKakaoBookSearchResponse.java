package com.joojang.bookfriend.dataResponse;


import com.joojang.bookfriend.model.KakaoBookDocument;

import java.util.ArrayList;

public class GetKakaoBookSearchResponse extends DefaultResponse {

    public ArrayList<KakaoBookDocument> documents;

    public ArrayList<KakaoBookDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<KakaoBookDocument> documents) {
        this.documents = documents;
    }
}
