package com.joojang.bookfriend.model;


public class Book {


    public int list_id;
    public int book_id;
    public String uuid;

    public String title;
    public String authors;
    public String contents;
    public String isbn;
    public String publisher;
    public String thumbnail;

    public String gubun;
    public String gubun_name;
    public boolean read_check;

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getGubun() {
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }

    public String getGubun_name() {
        return gubun_name;
    }

    public void setGubun_name(String gubun_name) {
        this.gubun_name = gubun_name;
    }

    public boolean isRead_check() {
        return read_check;
    }

    public void setRead_check(boolean read_check) {
        this.read_check = read_check;
    }
}
