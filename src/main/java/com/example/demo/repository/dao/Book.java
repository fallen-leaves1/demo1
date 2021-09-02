package com.example.demo.repository.dao;

import lombok.Data;
import org.springframework.lang.Nullable;

public class Book {

    private int id;
    private String bookname;
    private String author;
    private int num;

    public Book() {
    }

    public Book(int id, String bookname, String author, int num) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.num=num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getBookname() {
        return bookname;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookname='" + bookname + '\'' +
                ", author='" + author + '\'' +
                ", num=" + num +
                '}';
    }
}
