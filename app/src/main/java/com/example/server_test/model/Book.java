package com.example.server_test.model;

import androidx.annotation.NonNull;

public class Book {

    private Integer id;

    private String name;

    private String author;

    private String pages;

    private String rate;

    private String progress_pages;

    private String review;


    @NonNull
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", pages='" + pages + '\'' +
                ", rate='" + rate + '\'' +
                ", progress_pages='" + progress_pages + '\'' +
                ", review='" + review + '\'' +
                '}';
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPages() {
        return pages;
    }

    public void setProgress_pages(String progress_pages) {
        this.progress_pages = progress_pages;
    }

    public String getProgress_pages() {
        return progress_pages;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReview() {
        return review;
    }

}
