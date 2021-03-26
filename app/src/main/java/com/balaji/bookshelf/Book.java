package com.balaji.bookshelf;

public class Book {
    private String imageUrl;
    private String title;
    private String authors;
    private String date;
    private String pgCount;
    public Book(){}
    public Book(String title, String imageUrl, String authors, String date, String pgCount){
        this.imageUrl = imageUrl;
        this.title = title;
        this.authors = authors;
        this.date = date;
        this.pgCount = pgCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPgCount() {
        return pgCount;
    }

    public void setPgCount(String pgCount) {
        this.pgCount = pgCount;
    }
}
