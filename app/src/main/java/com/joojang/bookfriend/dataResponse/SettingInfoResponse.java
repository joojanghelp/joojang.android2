package com.joojang.bookfriend.dataResponse;

public class SettingInfoResponse extends DefaultResponse {

    public String name;
    public String activity_state;
    public String activity_count;
    public String read_book_count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity_state() {
        return activity_state;
    }

    public void setActivity_state(String activity_state) {
        this.activity_state = activity_state;
    }

    public String getActivity_count() {
        return activity_count;
    }

    public void setActivity_count(String activity_count) {
        this.activity_count = activity_count;
    }

    public String getRead_book_count() {
        return read_book_count;
    }

    public void setRead_book_count(String read_book_count) {
        this.read_book_count = read_book_count;
    }
}
